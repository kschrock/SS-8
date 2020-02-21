package org.springframework.samples.outreach.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Owner Controller for Companies Logic Actions
 * 
 * @author creimers
 * @author kschrock
 */

@Service
public class CompanyService {
	
	@Autowired
	CompanyRepository companyRepository;
	
	private final Logger logger = LoggerFactory.getLogger(CompanyController.class);

	/**
	 * This method creates and add a Company to the Company Repository. THIS IS A
	 * POST METHOD, Path = /company/add
	 * 
	 * @return HashMap<String, String> This returns JSON data of "verify", "Added".
	 */
	public HashMap<String, String> createCompany(Company newcomp) {
		HashMap<String, String> map = new HashMap<>();
		// create hash map for return value
		System.out.println(this.getClass().getSimpleName() + " - Create new Company method is invoked.");
		// print to the console
		if (companyRepository.findCompanyByUsername(newcomp.getUsername()) == null) {
			// if the company find by username is null then save the company object.
			companyRepository.save(newcomp);
			// save the company object to the repository
			map.put("verify", "Added");
			// update return value
		}
		return map;
	}
	
	/**
	 * This method finds all the company objects within the company Repository. THIS
	 * IS A GET METHOD, Path = /company FOR TESTING PURPOSES ONLY(?)
	 * 
	 * @return List<Owners> This returns the list of owners within the Repository.
	 */
	public List<Company> getAllCompanies() {
		logger.info("Entered into Controller Layer");
		List<Company> results = companyRepository.findAll();
		// get all the companies in the repository
		logger.info("Number of Records Fetched:" + results.size());
		// output to the console
		return results;
		// return the list of the companies in the repository
	}

	/**
	 * This method finds the given Id Owner object within the Owner Repository. THIS
	 * IS A GET METHOD, Path = /owners/{companyName}
	 * 
	 * @param String companyName
	 * @return Owners This returns the single owner by id within the Repository.
	 */
	public Company findOwnerById(String companyName) {
		
	logger.info("Entered into Controller Layer");

	List<Company> results = companyRepository.findAll();
	// find all the companies

	companyName = companyName.toString().trim();
	// given companyName to match
	for (Company current : results) {
		// iterate through all the companies
		if (current.getCompanyName().trim().equals(companyName)) {
			// this matches the Company Name to the given company name
			return current;
			// return the current company
		}

	}
	return null;
	// NOT FOUND
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
	public Map<String, String> loginOwner(String username,String password) {

		username = username.toString().trim();
		// given username to match
		password = password.toString().trim();
		// given password to match
		Company currentCompany = companyRepository.findCompanyByUsername(username);
		// find the current Company in the repo

		HashMap<String, String> map = new HashMap<>();
		// create Hash Map for return value

		if (username.equals(currentCompany.getUsername()) && password.equals(currentCompany.getPassword())) {
			// this matches the given username and password to the current Company
			map.put("verify", "true");
			// update return value
			return map;
		}

		map.put("verify", "false");
		// update return value
		return map;
	}
	

	/**
	 * This method deletes all the Owner objects within the Owner Repository. THIS
	 * IS A POST METHOD, Path = /owners/deleteall
	 * 
	 * @return void
	 */
	public void deleteAll() {
		System.out.println(this.getClass().getSimpleName() + " - Delete all employees is invoked.");
		companyRepository.deleteAll();
		// this deletes tall the company objects in the repo
	}
	
	/**
	 * This method deletes the ID Owner object within the Owner Repository. THIS IS
	 * A POST METHOD, Path = /company/delete/{id}
	 * 
	 * @param int ID
	 * @return void
	 */
	public void deleteEmployeeById(int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Delete employee by id is invoked.");

		Optional<Company> emp = companyRepository.findById(id);
		// find the company by ID
		if (!emp.isPresent())
			// company is not present/found
			throw new Exception("Could not find employee with id- " + id);

		companyRepository.deleteById(id);
		// if this is found then delete it by ID
	}

}
