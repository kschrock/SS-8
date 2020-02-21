package org.springframework.samples.outreach.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.company.Company;
import org.springframework.samples.outreach.company.CompanyRepository;
import org.springframework.samples.outreach.owner.OwnerRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {
	@Autowired
	EventRepository eventRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	OwnerRepository ownerRepository;

	private final Logger logger = LoggerFactory.getLogger(EventController.class);

	/**
	 * This method gets all the Events for a specific company in the Event
	 * Repository if a company requests it. otherwise if it is a standard user then
	 * it will retrieve a list of all companies they are subscribed to so that they
	 * can view their events THIS IS A GET METHOD, Path = /events/getAll
	 * 
	 * @return Iterable<Product> This returns the list of Event Objects.
	 */

	public List<Event> getRelevantEvents(Company[] event) {

		Iterable<Event> compResults = eventRepository.findAll();
		List<Event> events = new ArrayList<Event>();
		// if the events are created by a company then proceed with retrieving all of
		// their created events
		for (Event current : compResults) {
			// if event is the companies, add it to the list
			if (current.getUsername().equals(event[0].getUsername())) {
				events.add(current);
			}
		}
		// return the list of events the company has made (may be empty)
		return events;
	}

	/**
	 * This method finds all the events within the event Repository. THIS IS A GET
	 * METHOD, Path = /events
	 * 
	 * @return List<Events> This returns the list of events within the Repository.
	 */
	public List<Event> getAllEvents() {
		logger.info("Entered into Controller Layer");
		List<Event> results = eventRepository.findAll();
		// find all the events
		logger.info("Number of Records Fetched:" + results.size());
		// print to console the number of events
		return results;
		// return all the events
	}

	/**
	 * This method finds the given Id event object within the event Repository. THIS
	 * IS A GET METHOD, Path = /{event}
	 * 
	 * @param String event
	 * @return Events This returns the single event by id within the Repository.
	 */
	public Event findEventById(String eventname) {
		logger.info("Entered into Controller Layer");
		List<Event> results = eventRepository.findAll();
		// find all events
		eventname = eventname.toString().trim();
		// given event name
		for (Event current : results) {
			// iterate through all events
			if (current.getEventname().trim().equals(eventname)) {
				// if the event name matches the given event name
				return current;
				// return the current event
			}

		}
		return null;
		// NOT FOUND
	}

	/**
	 * This method deletes all the event objects made by a specific company.
	 * requires confirmation of deletion THIS IS A POST METHOD, Path = /deleteall
	 * 
	 * @return void
	 */

	public void deleteAll() {
		System.out.println(this.getClass().getSimpleName() + " - Delete all events is invoked.");
		// print to the console
		eventRepository.deleteAll();
		// this deletes tall the event objects in the repo
	}

	/**
	 * This method deletes the event object by ID within the Event Repository. THIS
	 * IS A POST METHOD, Path = /delete/{id}
	 * 
	 * @param int ID
	 * @return void
	 */
	public void deleteEventById(int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Delete event by id is invoked.");
		// print to the console

		Optional<Event> event = eventRepository.findById(id);
		// find the event by the ID
		if (!event.isPresent())
			// this checks if the event is in the repository
			throw new Exception("Could not find event with id- " + id);

		eventRepository.deleteById(id);
		// delete the event by ID
	}

}
