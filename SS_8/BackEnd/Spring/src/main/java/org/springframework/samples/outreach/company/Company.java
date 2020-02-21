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
package org.springframework.samples.outreach.company;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.samples.outreach.events.Event;
import org.springframework.samples.outreach.subscription.Subscription;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Simple JavaBean domain object representing an owner. This contains the fields
 * to create an owner.
 * 
 * @author creimers
 * @author kschrock
 */
@Entity
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer id;

	@Column(name = "company_name")
	@NotFound(action = NotFoundAction.IGNORE)
	private String companyName;

	@Column(name = "address")
	@NotFound(action = NotFoundAction.IGNORE)
	String address;

	@Column(name = "user_name")
	@NotFound(action = NotFoundAction.IGNORE)
	String username;

	@Column(name = "telephone")
	@NotFound(action = NotFoundAction.IGNORE)
	private String telephone;

	@Column(name = "pass_word")
	@NotFound(action = NotFoundAction.IGNORE)
	private String password;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { // added during meeting
			CascadeType.PERSIST, CascadeType.MERGE })
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnoreProperties("prize") // prevent circular dependency with JSON deserializing
	private Company company;

	@OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnoreProperties("company") // prevent circular dependency with JSON deserializing
	private Set<Subscription> subscriptions;
	// java util version of set

	@Column(name = "isPaid")
	@NotFound(action = NotFoundAction.IGNORE)
	private boolean isPaid;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnoreProperties("company") // prevent circular dependency with JSON deserializing
	private Set<Event> events;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(Set<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public boolean getPaidStatus() {
		return isPaid;
	}

	public void setPaidStatus(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

}
