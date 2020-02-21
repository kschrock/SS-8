package org.springframework.samples.outreach.subscription;

import java.util.HashMap;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.samples.outreach.company.Company;
import org.springframework.samples.outreach.owner.Owner;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Entity;

@Entity
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnoreProperties("subscriptions") // prevent circular dependency with JSON deserializing
	private Owner owner;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnoreProperties("subscriptions") // prevent circular dependency with JSON deserializing
	private Company company;

	private double points = 0.0;

	public Company getCompany() {
		// java util for set
		return this.company;
		// Getter for password
	}

	public Owner getOwner() {
		return this.owner;
//	        //gets status of company payment
	}

	public Company setCompany(Company company) {
		// java util for set
		return this.company = company;
	}

	public Owner setOwner(Owner user) {
		return this.owner = user;
//	       
	}

	public double getpoints() {
		return this.points;
//	        //gets status of points
	}

	public double setPoints(double d) {
		return this.points = d;
	}

	public int getID() {
		return this.id;
	}

	public int setID(int id) {
		return this.id = id;
	}

	public Subscription() {

	}
}
