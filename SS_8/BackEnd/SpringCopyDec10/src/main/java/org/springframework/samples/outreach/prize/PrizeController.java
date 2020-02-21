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

import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.company.CompanyRepository;
import org.springframework.samples.outreach.owner.Owner;
import org.springframework.samples.outreach.owner.OwnerRepository;
import org.springframework.samples.outreach.subscription.Subscription;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
	PrizeRepository prizeRepository;

	@Autowired
	OwnerRepository ownersRepository;

	@Autowired
	CompanyRepository companyRepository;

	private final Logger logger = LoggerFactory.getLogger(PrizeController.class);

	/**
	 * This method creates and adds a prize to the Prize Repository. THIS IS A POST
	 * METHOD, Path = /prize/add
	 * 
	 * @return HashMap<String, String> This returns JSON data of "verify", "Added".
	 */
	@RequestMapping(value = "/addPrize", method = RequestMethod.POST)
	public HashMap<String, String> createPrize(@RequestBody Prize newprize) {
		HashMap<String, String> map = new HashMap<>();
		System.out.println(this.getClass().getSimpleName() + " - Create new Prize method is invoked.");
		prizeRepository.save(newprize);
		prizeRepository.flush();
		map.put("verify", "Added");
		return map;

	}

	// consume method (redeem prize)
	/**
	 * Redeems a prize from the store Searches the store for the prize the user
	 * wants, figures out how many points it costs, deducts the points total from
	 * the user and decrements qty of productleft THIS IS A POST METHOD, Path =
	 * /prize/redeem/{prizeName}/{username}
	 * 
	 * @param String prizeName
	 * @param String Username
	 * @return HashMap<String, String> This returns JSON data of "verify", "Added"
	 *         || "verify", "NotFound".
	 */
	@RequestMapping(value = "/redeem/{companyUsername}/{prizeName}/{username}/{Quantity}", method = RequestMethod.POST)
	public HashMap<String, String> redeemPrizes(@PathVariable("companyUsername") String companyUsername,
			@PathVariable("prizeName") String prizeName, @PathVariable("username") String username,
			@PathVariable("Quantity") String Quantity) {
		// This can be used once the user gets back the info from the other repo and
		// confirms the points and sends it back to server.
		username = username.toString().trim();

		List<Owner> results = ownersRepository.findAll();

		HashMap<String, String> map = new HashMap<>();

		int quantity = Integer.parseInt(Quantity);

		// first get point cost
		int pointsCost = prizeRepository.findPrizeByPrizename(prizeName).getCost(); // gets proper info

		// pass points cost in to deduct from users points
		for (Owner current : results) {
			String currentUsername = current.getUsername().toString().trim();

			if (username.toString().trim().equals(currentUsername)) {
				for (Subscription subscription : current.getSubscriptions()) {
					if (subscription.getCompany().getUsername().trim().equals(companyUsername.trim())) {
						int totalCost = 0;

						totalCost = pointsCost * quantity; // add total points
						if (subscription.getpoints() - totalCost < 0) {
							map.put("verify", "Not Enough Points");
							return map;
						}
						if (prizeRepository.findPrizeByPrizename(prizeName).getQty() - quantity < 0) {
							map.put("verify", "Not Enough Prizes Left");
							return map;
						}
						subscription.setPoints(subscription.getpoints() - totalCost);
						// subscription.setID(1);
						String userAddress = "You have " + quantity + " " + prizeName + ". Being sent to "
								+ current.getAddress();
						map.put("verify", userAddress);
						ownersRepository.flush(); // updates changes
						companyRepository.flush();
						// get current quantity

						int origQty = prizeRepository.findPrizeByPrizename(prizeName).getQty();
						// update with current qty -1
						prizeRepository.findPrizeByPrizename(prizeName).setQty(origQty - quantity);

						prizeRepository.flush(); // update change to qty

						return map;
					}
					// its getting the owner by id and adding it to the list of owners in the
					// company
				}

			}
		}

		map.put("verify", "NotFound");
		return map;

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
		System.out.println(this.getClass().getSimpleName() + " - Edit Prize method is invoked.");
		HashMap<String, String> map = new HashMap<>();
		// changes cost
		int costChange = modPrize.getCost(); // gets proper info
		prizeRepository.findPrizeByPrizename(modPrize.getPrizename()).setCost(costChange);
		// changes qty
		int qtyChange = modPrize.getQty(); // gets proper info
		prizeRepository.findPrizeByPrizename(modPrize.getPrizename()).setQty(qtyChange);
		// changes prizename
		// just delete prize and re add it with new name instead

		prizeRepository.flush();
		map.put("verify", "edited");
		return map;
	}

	/**
	 * This method finds all the Prize objects within the Prize Repository. THIS IS
	 * A GET METHOD, Path = /prize FOR TESTING PURPOSES ONLY(?)
	 * 
	 * @return List<Prize> This returns the list of prizes within the Repository.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/getAll/Prizes")
	public List<Prize> getAllCompanies() {
		logger.info("Entered into Controller Layer");
		List<Prize> results = prizeRepository.findAll();
		logger.info("Number of Records Fetched:" + results.size());
		return results;
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
		logger.info("Entered into Controller Layer");
		// Optional<Owners> results = companyRepository.findById(id);j
		List<Prize> results = prizeRepository.findAll();
		prizeName = prizeName.toString().trim();
		for (Prize current : results) {

			if (current.getPrizename().trim().equals(prizeName)) {
				return current;
			}

		}
		return null; // NOT FOUND
	}

	/**
	 * This method deletes all the Prize objects within the Prize Repository. THIS
	 * IS A POST METHOD, Path = /prize/deleteall
	 * 
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/prize/deleteall")
	public void deleteAll() {
		System.out.println(this.getClass().getSimpleName() + " - Delete all prizes is invoked.");
		prizeRepository.deleteAll();
	}

	// TODO
	// may need to delete by name instead of id
	/**
	 * This method deletes the ID Prize object within the Prize Repository. THIS IS
	 * A POST METHOD, Path = /company/delete/{id}
	 * 
	 * @param int ID
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/prize/delete/{id}")
	public void deletePrizeById(@PathVariable int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Delete Prize by id is invoked.");

		Optional<Prize> prize = prizeRepository.findById(id);
		if (!prize.isPresent())
			throw new Exception("Could not find prize with id- " + id);

		prizeRepository.deleteById(id);
	}

}
