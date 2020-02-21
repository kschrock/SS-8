package org.springframework.samples.outreach.websockets;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.samples.outreach.company.Company;
import org.springframework.samples.outreach.events.Event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class SocketEventSubscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnoreProperties("subs") // prevent circular dependency with JSON deserializing
	private Event event;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnoreProperties("subs") // prevent circular dependency with JSON deserializing
	private Company company;

	public Company getCompany() {
		// java util for set
		return this.company;
		// Getter for password
	}

	public Event getEvent() {
		return this.event;
//	        //gets status of company payment
	}

	public Company setCompany(Company company) {
		// java util for set
		return this.company = company;
	}

	public Event setEvent(Event event) {
		return this.event = event;
//	       
	}

	public int getID() {
		return this.id;
	}

	public int setID(int id) {
		return this.id = id;
	}

	public SocketEventSubscription() {

	}
}
