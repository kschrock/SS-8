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
package org.springframework.samples.outreach.owner;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
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
@Table(name = "owner")
public class Owner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer id;

	@Column(name = "first_name")
	@NotFound(action = NotFoundAction.IGNORE)
	private String firstName;

	@Column(name = "last_name")
	@NotFound(action = NotFoundAction.IGNORE)
	private String lastName;

	@Column(name = "address")
	@NotFound(action = NotFoundAction.IGNORE)
	String address;

	@Column(name = "telephone")
	@NotFound(action = NotFoundAction.IGNORE)
	private String telephone;

	@Column(name = "user_name")
	@NotFound(action = NotFoundAction.IGNORE)
	String username;

	@Column(name = "pass_word")
	@NotFound(action = NotFoundAction.IGNORE)
	private String password;

	@Column(name = "paid")
	@NotFound(action = NotFoundAction.IGNORE)
	private String paid = "false";

	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnoreProperties("owner") // prevent circular dependency with JSON deserializing
	private Set<Subscription> subscriptions;

	public Integer getId() {
		return id;
		// Getter for ID of User
	}

	public void setId(Integer id) {
		this.id = id;
		// Setter for ID of User
	}

	public boolean isNew() {
		return this.id == null;
	}

	public String getFirstName() {
		return this.firstName;
		// Getter for FirstName of User
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
		// Setter for FirstName of User
	}

	public String getPaidStatus() {
		return this.paid;
		// Getter for pay status of User
	}

	public void setPaid(String paid) {
		this.paid = paid;
		// Setter for pay status of User
	}

	public String getLastName() {
		return this.lastName;
		// Getter for LastName of User
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
		// Setter for LastName of User
	}

	public String getAddress() {
		return this.address;
		// Getter for Address of User
	}

	public void setAddress(String address) {
		this.address = address;
		// Setter for Address
	}

	public String getTelephone() {
		return this.telephone;
		// Getter for Telephone Number
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
		// Setter for Telephone Number
	}

	public String getUsername() {
		return this.username;
		// Getter for username
	}

	public void setUsername(String username) {
		this.username = username;
		// Setter for username
	}

	public String getpassword() {
		return this.password;
		// Getter for password
	}

	public void setPassord(String password) {
		this.password = password;
		// Setter for password
	}

	public Set<Subscription> getSubscriptions() {
		return this.subscriptions;
		// Getter for password
	}

	public void setSubscriptions(Set<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
		// Setter for subscriptions
	}

}
