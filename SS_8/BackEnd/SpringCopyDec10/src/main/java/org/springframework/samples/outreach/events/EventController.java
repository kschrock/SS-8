/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.outreach.events;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.samples.outreach.company.Company;
import org.springframework.samples.outreach.company.CompanyRepository;
import org.springframework.samples.outreach.owner.OwnerRepository;
import org.springframework.samples.outreach.qr.Product;
import org.springframework.samples.outreach.owner.Owner;
import org.springframework.samples.outreach.websockets.*;

import org.springframework.samples.outreach.events.Event;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import javax.websocket.Session;

/**
 * Controller to map events
 * @author creimers
 * @author kschrock
 */
@RestController
@RequestMapping("events")
class EventController {

    @Autowired
    EventRepository eventRepository;
    
    @Autowired
    CompanyRepository companyRepository;

    private final Logger logger = LoggerFactory.getLogger(EventController.class);
    
  
    /**
	   * This method gets all the Events for a specific company in the Event Repository.
	   * THIS IS A GET METHOD, Path = /events/getAll/{company} 
	   * @return Iterable<Product> This returns the list of Event Objects.
	   */
	    @RequestMapping(method = RequestMethod.GET, path = "/getAll/{company}")
	    public List<Event> getAllCompCodes(@PathVariable("company") String company) {
	       
	    	Iterable<Event> results = eventRepository.findAll();
	    	List<Event> events = new ArrayList();
	    	
	    	for(Event current : results) {
	    		
	    		if(current.getCompanyname().equals(company)) {
	    			events.add(current);
	    		}
	    	}
	        return events;
	    }
	    
    /**
	   * This method finds all the events within the event Repository.
	   * THIS IS A GET METHOD, Path = /events
	   * @return List<Events> This returns the list of events within the Repository.
	   */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getAllEvents() {
        logger.info("Entered into Controller Layer");
        List<Event> results = eventRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }

    
    /**
	   * This method finds the given Id event object within the event Repository.
	   * THIS IS A GET METHOD, Path = /{event}
	   * @param String event
	   * @return Events This returns the single event by id within the Repository.
	   */
  @RequestMapping(method = RequestMethod.GET, path = "/{eventname}")
  public Event findEventById(@PathVariable("eventname") String eventname) {
      logger.info("Entered into Controller Layer");
      List<Event> results = eventRepository.findAll();
      eventname = eventname.toString().trim();
      for(Event current : results) {
      	
      	if(current.getEventname().trim().equals(eventname)) {
      		return current;
      	}
      	
      }
      return null; //NOT FOUND
  }
  
    /**
	   * This method deletes all the event objects made by a specific company.
	   * requires confirmation of deletion
	   * THIS IS A POST METHOD, Path = /deleteall
	   * @return void
	   */
    @RequestMapping( method= RequestMethod.POST, path= "/deleteall")
	public void deleteAll() {
		System.out.println(this.getClass().getSimpleName() + " - Delete all events is invoked.");
		boolean confirmed = false;
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		if(input == "yes" || input == "y") {
			confirmed =true;
		}
		if(confirmed)
		eventRepository.deleteAll();
		else if(!confirmed)
			System.out.print("okay");
		sc.close();
	}
    
    /**
	   * This method deletes the event object by ID within the Event Repository.
	   * THIS IS A POST METHOD, Path = /delete/{id}
	   * @param int ID
	   * @return void
	   */
    @RequestMapping( method= RequestMethod.POST, value= "/delete/{id}")
	public void deleteEventById(@PathVariable int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Delete event by id is invoked.");

		Optional<Event> event =  eventRepository.findById(id);
		if(!event.isPresent())
			throw new Exception("Could not find event with id- " + id);

		eventRepository.deleteById(id);
	}

}
