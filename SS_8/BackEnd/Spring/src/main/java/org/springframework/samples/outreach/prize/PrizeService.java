package org.springframework.samples.outreach.prize;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.company.CompanyRepository;
import org.springframework.samples.outreach.owner.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PrizeService {

	private final Logger logger = LoggerFactory.getLogger(PrizeController.class);

	@Autowired
	PrizeRepository prizeRepository;

	@Autowired
	OwnerRepository ownersRepository;

	@Autowired
	CompanyRepository companyRepository;
	
	/**
	 * This method creates and adds a prize to the Prize Repository. THIS IS A POST
	 * METHOD, Path = /prize/add
	 * 
	 * @return HashMap<String, String> This returns JSON data of "verify", "Added".
	 */
	public HashMap<String, String> createPrize(Prize newprize) {
		HashMap<String, String> map = new HashMap<>();
		// create hash map for return value
		System.out.println(this.getClass().getSimpleName() + " - Create new Prize method is invoked.");
		// print to the console
		prizeRepository.save(newprize);
		// save the prize to the repository
		prizeRepository.flush();
		// update repository
		map.put("verify", "Added");
		// add return value
		return map;
		// return map
	}
	
	/**
	 * edits prize information
	 * 
	 * @param id
	 * @param modPrize
	 * @return
	 */
	
	public HashMap<String, String> editPrize(int id, Prize modPrize) {
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
	public List<Prize> getAllCompanies() {
		logger.info("Entered into Controller Layer");
		// print to the console
		List<Prize> results = prizeRepository.findAll();
		// get all the prizes in the repository
		logger.info("Number of Records Fetched:" + results.size());
		// print to the console
		return results;
		// return the list of prizes
	}
	
	/**
	 * This method finds the given Id Prize object within the Prize Repository. THIS
	 * IS A GET METHOD, Path = /prizes/{prizeName}
	 * 
	 * @param String companyName
	 * @return Prizes This returns the single prize by id within the Repository.
	 */
	public Prize findOwnerById(String prizeName) {
		logger.info("Entered into Controller Layer");
		// print to the console
		List<Prize> results = prizeRepository.findAll();
		// this gets the results all the prizes from the repostory
		prizeName = prizeName.toString().trim();
		// given prize name to match
		for (Prize current : results) {
			// this iterates through all the prizes in the repository
			if (current.getPrizename().trim().equals(prizeName)) {
				// this checks if the current prize name matches the given prize name
				return current;
				// this returns the current prize
			}

		}
		return null;
		// NOT FOUND
	}
	
	/**
	 * This method deletes all the Prize objects within the Prize Repository. THIS
	 * IS A POST METHOD, Path = /prize/deleteall
	 * 
	 * @return void
	 */
	public void deleteAll() {
		System.out.println(this.getClass().getSimpleName() + " - Delete all prizes is invoked.");
		//print to the console
		prizeRepository.deleteAll();
		//deletes all the prizes in the repository
	}
	
	/**
	 * This method deletes the ID Prize object within the Prize Repository. THIS IS
	 * A POST METHOD, Path = /company/delete/{id}
	 * 
	 * @param int ID
	 * @return void
	 */
	public void deletePrizeById(int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Delete Prize by id is invoked.");
		//print to the console

		Optional<Prize> prize = prizeRepository.findById(id);
		if (!prize.isPresent())
			// this checks if the current prize is not in repository
			throw new Exception("Could not find prize with id- " + id);

		prizeRepository.deleteById(id);
		// this deletes the prize from repostory by ID
	}
}
