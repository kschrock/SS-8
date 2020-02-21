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

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.company.CompanyRepository;
import org.springframework.samples.outreach.owner.OwnerRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Owner Controller for Companies Logic Actions
 * 
 * @author creimers
 * @author kschrock
 */
@RestController
@RequestMapping("prize")
class PrizeController {

	@Autowired
	PrizeService service;

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
	 * edits prize information
	 * 
	 * @param id
	 * @param modPrize
	 * @return
	 */
	// edit prize info
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public HashMap<String, String> editPrize(@PathVariable int id, @RequestBody Prize modPrize) {
		return service.editPrize(id, modPrize);
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
	 * This method finds the given Id Prize object within the Prize Repository. THIS
	 * IS A GET METHOD, Path = /prizes/{prizeName}
	 * 
	 * @param String companyName
	 * @return Prizes This returns the single prize by id within the Repository.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{prizeName}")
	public Prize findOwnerById(@PathVariable("prizeName") String prizeName) {
		return service.findOwnerById(prizeName);
	}

	/**
	 * This method deletes all the Prize objects within the Prize Repository. THIS
	 * IS A POST METHOD, Path = /prize/deleteall
	 * 
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/prize/deleteall")
	public void deleteAll() {
		service.deleteAll();
	}

	/**
	 * This method deletes the ID Prize object within the Prize Repository. THIS IS
	 * A POST METHOD, Path = /company/delete/{id}
	 * 
	 * @param int ID
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/prize/delete/{id}")
	public void deletePrizeById(@PathVariable int id) throws Exception {
		service.deletePrizeById(id);
	}
}
