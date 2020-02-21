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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.prize.Prize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Owner Controller for Consumers and Companies Logic Actions
 * 
 * @author creimers
 * @author kschrock
 */
@RestController
public class OwnerController {

	@Autowired
	OwnerService service;

	/**
	 * This method creates and adds a prize to the Prize Repository. THIS IS A POST
	 * METHOD, Path = /prize/add
	 * 
	 * @return HashMap<String, String> This returns JSON data of "verify", "Added".
	 */
	@RequestMapping(value = "/addPrize", method = RequestMethod.POST)
	public HashMap<String, String> createPrize(@RequestBody Prize newprize) {
		return service.createPrize(newprize);
	}

	/**
	 * This method finds all the Prize objects within the Prize Repository. THIS IS
	 * A GET METHOD, Path = /prize FOR TESTING PURPOSES ONLY(?)
	 * 
	 * @return List<Prize> This returns the list of prizes within the Repository.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/getAll/Prizes")
	public List<Prize> getAllCompanies() {
		return service.getAllCompanies();
	}

	/**
	 * This method is for creating a Prize IS A POST METHOD, Path =
	 * /redeem/{companyName}/{prizeName}/{username}/{Quantity}
	 * 
	 * @param String username, String companyName, String Quantity
	 * @return Hash map
	 */
	@RequestMapping(value = "/redeem/{companyName}/{prizeName}/{username}/{Quantity}", method = RequestMethod.POST)
	public HashMap<String, String> redeemPrizes(@PathVariable("companyName") String companyName,
			@PathVariable("prizeName") String prizeName, @PathVariable("username") String username,
			@PathVariable("Quantity") String Quantity) {
		return service.redeemPrizes(companyName, prizeName, username, Quantity);
	}

	/**
	 * This method creates and add a User to the Owners Repository. THIS IS A POST
	 * METHOD, Path = /owners/add
	 * 
	 * @return HashMap<String, String> This returns JSON data of "verify", "Added".
	 */
	@RequestMapping(value = "/owners/add", method = RequestMethod.POST)
	public HashMap<String, String> createEmployee(@RequestBody Owner newUser) {
		return service.createEmployee(newUser);
	}

	/**
	 * This method adds the given points to the given user. This searches through
	 * the Repository to find the user and give them the given amount of points to
	 * them and updating the Repository. THIS IS A POST METHOD, Path =
	 * /owners/addpoints/{points}/{username}
	 * 
	 * @param int    Points
	 * @param String Username
	 * @return HashMap<String, String> This returns JSON data of "verify", "Added"
	 *         || "verify", "NotFound".
	 */
	@RequestMapping(value = "/owners/addpoints/{points}/{company}/{username}", method = RequestMethod.POST)
	public HashMap<String, String> addPoints(@PathVariable("points") int points,
			@PathVariable("company") String company, @PathVariable("username") String username) {

		return service.addPoints(points, company, username);
	}

	/**
	 * This method finds all the Owner objects within the Owner Repository. THIS IS
	 * A GET METHOD, Path = /owners
	 * 
	 * @return List<Owners> This returns the list of owners within the Repository.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/owners")
	public List<Owner> getAllOwners() {
		return service.getAllOwners();
	}

	/**
	 * gets paid status
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/owners/{username}/{password}/paid")
	public boolean userPaysApp(@PathVariable("username") String username, @PathVariable("password") String password) {
		return service.userPaysApp(username, password);
	}

	/**
	 * This method is for a User to Pay company. This takes a username and password
	 * then changes the paidstatus of this user's company. IS A GET METHOD, Path =
	 * /owners/{username}/{password}/{company}/paid
	 * 
	 * @param String username, String password
	 * @return boolean
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/owners/{username}/{password}/{company}/paid")
	public boolean userPaysCompany(@PathVariable("username") String username, @PathVariable("password") String password,
			@PathVariable("company") String company) {
		return service.userPaysCompany(username, password, company);
	}

	/**
	 * This method finds the given Id Owner object within the Owner Repository. THIS
	 * IS A GET METHOD, Path = /owners/{username}
	 * 
	 * @param String Username
	 * @return Owners This returns the single owner by id within the Repository.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/owners/{username}")
	public Owner findOwnerById(@PathVariable("username") String username) {
		return service.findOwnerById(username);
	}

	/**
	 * This method tries to Login with the given Username and Password. It does this
	 * by searching through all Owner Objects within the Owner Repository. THIS IS A
	 * GET METHOD, Path = /owners/login/{username}/{password}
	 * 
	 * @param String Username
	 * @param String Password
	 * @return Map<String, String> This returns "verify", "true" || "verify",
	 *         "false".
	 */
	@RequestMapping(value = "/owners/login/{username}/{password}", method = RequestMethod.GET)
	public Map<String, String> loginOwner(@PathVariable("username") String username,
			@PathVariable("password") String password) {
		return service.loginOwner(username, password);
	}

	/**
	 * This method deletes all the Owner objects within the Owner Repository. THIS
	 * IS A POST METHOD, Path = /owners/deleteall
	 * 
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/owners/deleteall")
	public void deleteAll() {
		service.deleteAll();
	}

	/**
	 * This method deletes the ID Owner object within the Owner Repository. THIS IS
	 * A POST METHOD, Path = /owners/delete/{id}
	 * 
	 * @param int ID
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/owners/delete/{id}")
	public void deleteEmployeeById(@PathVariable int id) throws Exception {
		service.deleteEmployeeById(id);
	}
}
