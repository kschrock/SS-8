package org.springframework.samples.outreach.owner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.company.CompanyRepository;
import org.springframework.samples.outreach.prize.Prize;
import org.springframework.samples.outreach.prize.PrizeRepository;
import org.springframework.samples.outreach.subscription.Subscription;
import org.springframework.stereotype.Service;

/**
 * Owner Controller for Consumers and Companies Logic Actions
 * 
 * @author creimers
 * @author kschrock
 */

@Service
public class OwnerService {

	@Autowired
	OwnerRepository ownersRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	PrizeRepository prizeRepository;

	private final Logger logger = LoggerFactory.getLogger(OwnerController.class);

	/**
	 * This method creates and adds a prize to the Prize Repository. THIS IS A POST
	 * METHOD, Path = /prize/add
	 * 
	 * @return HashMap<String, String> This returns JSON data of "verify", "Added".
	 */
	public HashMap<String, String> createPrize(Prize newprize) {
		HashMap<String, String> map = new HashMap<>();
		System.out.println(this.getClass().getSimpleName() + " - Create new Prize method is invoked.");
		// print to the console
		prizeRepository.save(newprize);
		// save the prize
		prizeRepository.flush();
		// update the repos
		map.put("verify", "Added");
		// update return value

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
		List<Prize> results = prizeRepository.findAll();
		logger.info("Number of Records Fetched:" + results.size());
		return results;
	}

	/**
	 * This method is for creating a Prize IS A POST METHOD, Path =
	 * /redeem/{companyName}/{prizeName}/{username}/{Quantity}
	 * 
	 * @param String username, String companyName, String Quantity
	 * @return Hash map
	 */
	public HashMap<String, String> redeemPrizes(String companyName, String prizeName, String username,
			String Quantity) {

		username = username.toString().trim();
		// given usersname
		HashMap<String, String> map = new HashMap<>();
		// create hash map for return value
		int quantity = Integer.parseInt(Quantity);
		// quantity to buy
		int pointsCost = prizeRepository.findPrizeByPrizename(prizeName).getCost();
		// this finds the point cost of the prize found in the Repository
		Owner currentUser = ownersRepository.findOwnerByUsername(username);
		// find the user in the repository

		if (username.toString().trim().equals(currentUser.getUsername())) {
			// this matches the given username to the current User Username

			for (Subscription subscription : currentUser.getSubscriptions()) {
				// iterate through all subscriptions for the current User
				if (subscription.getCompany().getCompanyName().trim().equals(companyName.trim())) {
					// this checks current Company Name matches the given company name.
					double totalCost = 0;
					// total cost of the prize redemption
					double discount = 1;
					// discount
					if (subscription.getCompany().getPaidStatus() == true) {
						// this checks the paid status of the current Company Subscription of the User
						discount = prizeRepository.findPrizeByPrizename(prizeName).getDiscount();
						// this finds the discount for the current Prize since they paid
						discount = discount * quantity;
						// apply for all the items redeeming
						totalCost = pointsCost * quantity - discount;
						// get the total cost by subtracting the points off from the total
					}
					if (subscription.getCompany().getPaidStatus() == false) {
						// this checks the paid status of the current Company Subscription of the User
						totalCost = pointsCost * quantity;
						// since the paid status is false, no discount
						// and find toal cost
					}
					System.out.println(totalCost);
					// print to the console log
					String output = "Not Enough " + subscription.getCompany().getCompanyName() + " Points";
					if (subscription.getpoints() - totalCost < 0) {
						// this checks if the user can purchase the prize
						map.put("verify", output);
						// return message of not Enough COMPANYNAME points
						return map;
					}
					if (prizeRepository.findPrizeByPrizename(prizeName).getQty() - quantity < 0) {
						// this checks if there are any prizes lefts
						map.put("verify", "Not Enough Prizes Left");
						return map;
					}
					System.out.println(subscription.getpoints() - totalCost + "MATH");
					double leftOverPoints = subscription.setPoints(subscription.getpoints() - totalCost);
					// this finds the points left over after purchase
					System.out.println(leftOverPoints + "Left Over");
					// output to console
					subscription.setPoints(leftOverPoints);
					// update the subscriptions left over points
					String userAddress = "You have " + quantity + " " + prizeName + ". Being sent to "
							+ currentUser.getAddress();
					// send userAddress
					map.put("verify", userAddress);
					ownersRepository.flush();
					// updates changes
					companyRepository.flush();
					// updates changes

					int origQty = prizeRepository.findPrizeByPrizename(prizeName).getQty();
					// find the original prize quantity
					prizeRepository.findPrizeByPrizename(prizeName).setQty(origQty - quantity);
					// update the prize quantity
					prizeRepository.flush();
					// update change to qty

					return map;
				}

			}

		}

		map.put("verify", "NotFound");
		return map;
	}

	/**
	 * This method creates and add a User to the Owners Repository. THIS IS A POST
	 * METHOD, Path = /owners/add
	 * 
	 * @return HashMap<String, String> This returns JSON data of "verify", "Added".
	 */
	public HashMap<String, String> createEmployee(Owner newUser) {
		HashMap<String, String> map = new HashMap<>();
		// create hash map for return value

		if (ownersRepository.findOwnerByUsername(newUser.getUsername()) != null) {
			// user is already found in the repo
			map.put("verify", "Already Exists");
			// update json return value for the map
			return map;
		}
		if (ownersRepository.findOwnerByUsername(newUser.getUsername()) == null) {
			// this checks for duplicates. It will not add anything if the user exists
			ownersRepository.save(newUser);
			// user is not found in the repo, so the we save it to the repo
			map.put("verify", "Added");
			// update json return value for the map
		}
		System.out.println(this.getClass().getSimpleName() + " - Create new User method is invoked.");
		// print to the console
		ownersRepository.flush();
		// update the ownersRepository
		return map;

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
	public HashMap<String, String> addPoints(int points, String company, String username) {
		username = username.toString().trim();
		// given usersname
		HashMap<String, String> map = new HashMap<>();
		// create hashmap for return value
		Owner currentUser = ownersRepository.findOwnerByUsername(username);
		// find the user in the repository

		if (username.toString().trim().equals(currentUser.getUsername())) {
			// match the given username to the current owner
			for (Subscription subscription : currentUser.getSubscriptions()) {
				// iterate through all the subscriptions for the Current User
				if (subscription.getCompany().getCompanyName().trim().equals(company.trim())) {
					// match the current Company to to the given company
					double temp = 0;
					// temp variable
					double currentPoints;
					// current Points for the current Company

					try {
						currentPoints = subscription.getpoints();
						// get the current points and give it to current Points
					} catch (NumberFormatException e) {
						currentPoints = 0;
						// not found
					}
					temp = currentPoints + points;
					// add total points
					subscription.setPoints(temp);
					// update the companys current Points
					map.put("verify", "addedPoints!!");
					// update return value
					ownersRepository.flush();
					// updates changes
					companyRepository.flush();
					return map;
				}
			}

			ownersRepository.flush(); // updates changes
			return map;

		}

		map.put("verify", "NotFound");
		return map;
	}

	/**
	 * This method finds all the Owner objects within the Owner Repository. THIS IS
	 * A GET METHOD, Path = /owners
	 * 
	 * @return List<Owners> This returns the list of owners within the Repository.
	 */
	public List<Owner> getAllOwners() {
		logger.info("Entered into Controller Layer");
		List<Owner> results = ownersRepository.findAll();
		logger.info("Number of Records Fetched:" + results.size());
		return results;
	}

	/**
	 * gets paid status
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean userPaysApp(String username, String password) {
		username = username.toString().trim();
		// given usersname
		password = password.toString().trim();
		// given password
		Owner currentUser = ownersRepository.findOwnerByUsername(username);
		// find the user in the repository

		if (currentUser.getUsername().trim().equals(username) && currentUser.getpassword().trim().equals(password)) {
			// match the given username and password to the current owner
			currentUser.setPaid("true");
			ownersRepository.flush();
			// update repo
			return true;
			// return value

		}

		return false;
		// return false

	}

	/**
	 * This method is for a User to Pay company. This takes a username and password
	 * then changes the paidstatus of this user's company. IS A GET METHOD, Path =
	 * /owners/{username}/{password}/{company}/paid
	 * 
	 * @param String username, String password
	 * @return boolean
	 */
	public boolean userPaysCompany(String username, String password, String company) {
		username = username.toString().trim();
		// given usersname
		password = password.toString().trim();
		// given password
		Owner currentUser = ownersRepository.findOwnerByUsername(username);
		// find the user in the repository
		if (currentUser.getUsername().trim().equals(username) && currentUser.getpassword().trim().equals(password)) {
			// match the given username and password to the current owner
			for (Subscription subscription : currentUser.getSubscriptions()) {
				// iterate through all current user subscriptions
				if (subscription.getCompany().getCompanyName().trim().equals(company.trim())) {
					// if the current company is the same as given company, set paid status to true.
					subscription.getCompany().setPaidStatus(true);
					ownersRepository.flush();
					// update repo
					companyRepository.flush();
					// update repo
					return true;
				}
			}

		}

		return false;
	}

	/**
	 * This method finds the given Id Owner object within the Owner Repository. THIS
	 * IS A GET METHOD, Path = /owners/{username}
	 * 
	 * @param String Username
	 * @return Owners This returns the single owner by id within the Repository.
	 */
	public Owner findOwnerById(String username) {
		logger.info("Entered into Controller Layer");
		username = username.toString().trim();
		Owner currentUser = ownersRepository.findOwnerByUsername(username);
		// this finds the current Owner in Repo
		if (currentUser.getUsername().trim().equals(username)) {
			// match the given usrename to the current Owner's username
			return currentUser;
			// if matches then return the current Owner Object

		}
		return null; // NOT FOUND
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
	public Map<String, String> loginOwner(String username, String password) {
		username = username.toString().trim();
		password = password.toString().trim();
		Owner currentUser = ownersRepository.findOwnerByUsername(username);
		// this finds the current Owner in Repo
		HashMap<String, String> map = new HashMap<>();
		// this is the return json data
		String currentUsername = currentUser.getUsername().toString().trim();
		// current Username to match
		String currentPassword = currentUser.getpassword().toString().trim();
		// current Password to match
		if (username.equals(currentUsername) && password.equals(currentPassword)) {
			// this compares the inputs if they are correct.
			map.put("verify", "true");
			// correctly logins
			return map;
		}

		map.put("verify", "false");
		// unsuccessful login
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
		ownersRepository.deleteAll();
		// delete all the owners in the repository
	}

	/**
	 * This method deletes the ID Owner object within the Owner Repository. THIS IS
	 * A POST METHOD, Path = /owners/delete/{id}
	 * 
	 * @param int ID
	 * @return void
	 */
	public void deleteEmployeeById(int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Delete employee by id is invoked.");
		ownersRepository.deleteById(id);
		// delete owner by ID
	}

}
