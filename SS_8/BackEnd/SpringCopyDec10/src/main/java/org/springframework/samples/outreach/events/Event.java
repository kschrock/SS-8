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
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.json.JSONObject;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.outreach.*;
import org.springframework.samples.outreach.company.Company;
import org.springframework.samples.outreach.qr.Product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.micrometer.core.lang.NonNull;

/**
 * Simple JavaBean domain object representing an event.
 * This contains the fields to create an event.
 * @author creimers
 * @author kschrock
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
@Table(name = "events")
public class Event {

	 public Event() {

	}

	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private Integer id;

	
		@Column(name = "companyName")
    	@NotFound(action = NotFoundAction.IGNORE) 
		String companyname;
		
		@Column(name = "username")
    	@NotFound(action = NotFoundAction.IGNORE) 
		String username;
	
	 	@Column(name = "event_name")
	    @NotFound(action = NotFoundAction.IGNORE) 
	    String eventname;
	 
	    @Column(name = "location")
	    @NotFound(action = NotFoundAction.IGNORE) 
	    String location;

	    @Column(name = "date")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String date;

		@Column(name = "time")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String time;
	    
		  @OneToMany(fetch = FetchType.EAGER, cascade = {
		   		CascadeType.PERSIST,
		   		CascadeType.MERGE
		  })
		 @NotFound(action = NotFoundAction.IGNORE)
		 @JsonIgnoreProperties("events") // prevent circular dependency with JSON deserializing
		 private Set<Product> products;
		  
		 
		  
		  //if something breaks, try commenting this out
	    @ManyToOne(fetch = FetchType.EAGER, cascade = {
	    		CascadeType.PERSIST,
	    		CascadeType.MERGE
	    })
	    @NotFound(action = NotFoundAction.IGNORE)
	    @JsonIgnoreProperties("company") // prevent circular dependency with JSON deserializing
	    private Company company;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getEventname() {
			return eventname;
		}

		public void setEventname(String eventname) {
			this.eventname = eventname;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getCompanyname() {
			return companyname;
		}

		public void setCompanyname(String companyname) {
			this.companyname = companyname;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public Set<Product> getProducts() {
			return products;
		}

		public void setProducts(Set<Product> products) {
			this.products = products;
		}


}