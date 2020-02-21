package org.springframework.samples.outreach.qr;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.outreach.events.Event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * Simple JavaBean domain object representing an Product.
 * This contains the fields to create an QR code.
 * @author creimers
 * @author kschrock
 */
@Entity
@Table(name = "product")
public class Product {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private Integer id;

	 @CreationTimestamp
	 private LocalDateTime createDateTime;
	 
	@Column(name = "expireDateTime")
	@NotFound(action = NotFoundAction.IGNORE)
	private LocalDateTime expireDateTime;	   
	@Column(name = "expireDate")
	@NotFound(action = NotFoundAction.IGNORE)
	private LocalDate expireDate;
	@Column(name = "expireTime")
	@NotFound(action = NotFoundAction.IGNORE)
	private LocalTime expireTime;
	

	@ElementCollection
    @CollectionTable(name="listOfUsers")
    private  Set <String> user;
	
	@Column(name = "event")
    @NotFound(action = NotFoundAction.IGNORE)
	private String event ="none";
	
	@Column(name = "company")
    @NotFound(action = NotFoundAction.IGNORE)
	private String company;
	@Column(name = "baseURL")
    @NotFound(action = NotFoundAction.IGNORE)
	private String baseURL;
	@Column(name = "points")
    @NotFound(action = NotFoundAction.IGNORE)
	private int points;
	@Column(name = "quantity")
    @NotFound(action = NotFoundAction.IGNORE)
	private int quantity;
	
	public Integer getId() {
		return id;
	}

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getExpireDateTime() {
		return expireDateTime;
	}

	public void setExpireDateTime(LocalDateTime expireDateTime) {
		this.expireDateTime = expireDateTime;
	}

	public LocalDate getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(LocalDate expireDate) {
		this.expireDate = expireDate;
	}

	public LocalTime getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(LocalTime expireTime) {
		this.expireTime = expireTime;
	}

	public Set<String> getUser() {
		return user;
	}

	public void setUser(String username) {
		this.user.add(username);
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
	
}
