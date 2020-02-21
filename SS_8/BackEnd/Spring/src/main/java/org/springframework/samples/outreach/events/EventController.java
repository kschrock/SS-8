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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.samples.outreach.company.Company;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to map events
 * 
 * @author creimers
 * @author kschrock
 */
@RestController
@RequestMapping("events")
class EventController {

	@Autowired
	EventService service;

	/**
	 * This method gets all the Events for a specific company in the Event
	 * Repository. THIS IS A GET METHOD, Path = /events/getAll/{company}
	 * 
	 * @return Iterable<Product> This returns the list of Event Objects.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/getAll")
	public List<Event> getAllCompCodes(@RequestBody Company[] event) {

		return service.getRelevantEvents(event);
	}

	/**
	 * This method finds all the events within the event Repository. THIS IS A GET
	 * METHOD, Path = /events
	 * 
	 * @return List<Events> This returns the list of events within the Repository.
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Event> getAllEvents() {

		return service.getAllEvents();
	}

	/**
	 * This method finds the given Id event object within the event Repository. THIS
	 * IS A GET METHOD, Path = /{event}
	 * 
	 * @param String event
	 * @return Events This returns the single event by id within the Repository.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{eventname}")
	public Event findEventById(@PathVariable("eventname") String eventname) {
		return service.findEventById(eventname);
	}

	/**
	 * This method deletes all the event objects made by a specific company.
	 * requires confirmation of deletion THIS IS A POST METHOD, Path = /deleteall
	 * 
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/deleteall")
	public void deleteAll() {
		service.deleteAll();
	}

	/**
	 * This method deletes the event object by ID within the Event Repository. THIS
	 * IS A POST METHOD, Path = /delete/{id}
	 * 
	 * @param int ID
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/delete/{id}")
	public void deleteEventById(@PathVariable int id) throws Exception {
		service.deleteEventById(id);
	}
}
