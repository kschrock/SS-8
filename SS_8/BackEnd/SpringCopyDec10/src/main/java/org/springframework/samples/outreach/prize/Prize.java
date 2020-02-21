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
package org.springframework.samples.outreach.prize;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.outreach.company.*;
import org.springframework.samples.outreach.events.*;
import org.springframework.samples.outreach.owner.Owner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.samples.outreach.company.Company;

/**
 * Simple JavaBean domain object representing an owner. This contains the fields
 * to create an owner.
 * 
 * @author creimers
 * @author kschrock
 */
@Entity
@Table(name = "prize")
public class Prize {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer id;

	@Column(name = "prizename")
	@NotFound(action = NotFoundAction.IGNORE)
	private String prizename;

	@Column(name = "cost")
	@NotFound(action = NotFoundAction.IGNORE)
	private int cost;

	@Column(name = "qty")
	@NotFound(action = NotFoundAction.IGNORE)
	private int qty;

	@Column(name = "discount")
	@NotFound(action = NotFoundAction.IGNORE)
	private int pointsOff;

	@Column(name = "companyName") // perhaps N/A
	@NotFound(action = NotFoundAction.IGNORE)
	private String companyName = "";

	@OneToMany(fetch = FetchType.EAGER, cascade = { // updated
			CascadeType.PERSIST, CascadeType.MERGE })
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnoreProperties("company") // prevent circular dependency with JSON deserializing
	private Set<Prize> prizes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrizename() {
		return prizename;
	}

	public void setPrizename(String prizename) {
		this.prizename = prizename;
	}

	public int getDiscount() {
		return pointsOff;
	}

	public void setDiscount(int discount) {
		this.pointsOff = discount;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String company) {
		this.companyName = company;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getPointsOff() {
		return pointsOff;
	}

	public void setPointsOff(int pointsOff) {
		this.pointsOff = pointsOff;
	}

	public Set<Prize> getPrizes() {
		return prizes;
	}

	public void setPrizes(Set<Prize> prizes) {
		this.prizes = prizes;
	}

}