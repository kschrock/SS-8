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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Owner Controller for Companies Logic Actions
 * 
 * @author creimers
 * @author kschrock
 */
@RestController
@RequestMapping("company")
class CompanyController {

	@Autowired
	CompanyService service;

	private final Logger logger = LoggerFactory.getLogger(CompanyController.class);

	/**
	 * This method creates and add a Company to the Company Repository. THIS IS A
	 * POST METHOD, Path = /company/add
	 * 
	 * @return HashMap<String, String> This returns JSON data of "verify", "Added".
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public HashMap<String, String> createCompany(@RequestBody Company newcomp) {
		return service.createCompany(newcomp);
	}

	/**
	 * This method finds all the company objects within the company Repository. THIS
	 * IS A GET METHOD, Path = /company FOR TESTING PURPOSES ONLY(?)
	 * 
	 * @return List<Owners> This returns the list of owners within the Repository.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/getAll")
	public List<Company> getAllCompanies() {
		return service.getAllCompanies();
	}

	/**
	 * This method finds the given Id Owner object within the Owner Repository. THIS
	 * IS A GET METHOD, Path = /owners/{companyName}
	 * 
	 * @param String companyName
	 * @return Owners This returns the single owner by id within the Repository.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{companyName}")
	public Company findOwnerById(@PathVariable("companyName") String companyName) {
		return service.findOwnerById(companyName);
	}

	/**
	 * This method tries to Login with the given Username and Password. It does this
	 * by searching through all Company Objects within the Company Repository. THIS
	 * IS A GET METHOD, Path = /owners/login/{username}/{password}
	 * 
	 * @param String Username
	 * @param String Password
	 * @return Map<String, String> This returns "verify", "true" || "verify",
	 *         "false".
	 */
	@RequestMapping(value = "/login/{username}/{password}", method = RequestMethod.GET)
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
	 * A POST METHOD, Path = /company/delete/{id}
	 * 
	 * @param int ID
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/owners/delete/{id}")
	public void deleteEmployeeById(@PathVariable int id) throws Exception {
		service.deleteEmployeeById(id);
	}

}
