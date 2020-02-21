package org.springframework.samples.outreach.websockets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.samples.outreach.company.Company;
import org.springframework.samples.outreach.company.CompanyRepository;
import org.springframework.samples.outreach.events.Event;
import org.springframework.samples.outreach.events.EventRepository;
import org.springframework.samples.outreach.subscription.Subscription;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * chat websocket server
 * 
 * @author creimers
 * @author kschrock
 */
@ServerEndpoint("/notify/{username}")
@Service
@Configurable
public class HelloWorldSocket {

	@Autowired
	static CompanyRepository companyRepository;

	@Autowired
	static EventRepository eventRepository;

	@Autowired
	public void setCompanyRepository(CompanyRepository companyRepository) {
		HelloWorldSocket.companyRepository = companyRepository;
	}

	@Autowired
	public void setEventRepository(EventRepository eventRepository) {
		HelloWorldSocket.eventRepository = eventRepository;
	}

	// Store all socket session and their corresponding username.
	private static Map<Session, String> sessionUsernameMap = new HashMap<>();
	private static Map<String, Session> usernameSessionMap = new HashMap<>();

	private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) throws IOException {
		logger.info("Entered into Open");
		sessionUsernameMap.put(session, username);
		usernameSessionMap.put(username, session);

		String message = "User:" + username + " has Joined the Chat";
		broadcast(message);

	}

	@OnMessage
	public void onMessage(Session session, String eventInfo) throws IOException, JSONException {
		logger.info("enter onmessage");
		ObjectMapper mapper = new ObjectMapper();
		try {
			JSONObject jsonObject = new JSONObject(eventInfo);
			String username = jsonObject.getString("username");

			Event event = new Event();
			event.setEventname(jsonObject.getString("eventName"));
			event.setDate(jsonObject.getString("date"));
			event.setLocation(jsonObject.getString("location"));
			event.setTime(jsonObject.getString("time"));
			event.setCompanyname(jsonObject.getString("companyName"));
			event.setUsername(jsonObject.getString("username"));

			Company company = companyRepository.findCompanyByUsername(username);
			logger.info("company name is" + company.getCompanyName());
			company.getEvents().add(event);
			company = companyRepository.save(company);
			companyRepository.flush();
			eventRepository.flush();
			broadcastEvent(company.getSubscriptions(), event); // change to getSubscribers() later
			logger.info("Entered into Message: Got Message:" + eventInfo);
		}

		catch (Exception e) {
			logger.info("didn't work");
			e.printStackTrace();
		}
	}

	@OnClose
	public void onClose(Session session) throws IOException {
		logger.info("Entered into Close");

		String username = sessionUsernameMap.get(session);
		sessionUsernameMap.remove(session);
		usernameSessionMap.remove(username);

		String message = username + " disconnected";
		broadcast(message);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
		logger.info("Entered into Error");
		throwable.printStackTrace();
	}

	private void broadcastEvent(Set<Subscription> set, Event event) {

		ObjectMapper mapper = new ObjectMapper();
		for (Subscription owner : set) {
			Session session = usernameSessionMap.get(owner.getOwner().getUsername());
			//
			if (session != null) {
				try {
					session.getBasicRemote().sendText(mapper.writeValueAsString(event));
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void sendMessageToPArticularUser(String username, String message) {
		try {
			usernameSessionMap.get(username).getBasicRemote().sendText(message);
		} catch (IOException e) {
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		}
	}

	private static void broadcast(String message) throws IOException {
		sessionUsernameMap.forEach((session, username) -> {
			synchronized (session) {
				try {
					session.getBasicRemote().sendText(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
