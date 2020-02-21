package org.springframework.samples.outreach.subscription;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.outreach.company.Company;
import org.springframework.samples.outreach.company.CompanyRepository;
import org.springframework.samples.outreach.owner.Owner;
import org.springframework.samples.outreach.owner.OwnerRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

	@Autowired
	OwnerRepository ownersRepository;

	@Autowired
	CompanyRepository companyRepository;

	/* subscription methods begin */
	/**
	 * This method subscribes a user to a company. THIS IS A POST METHOD, Path =
	 * /owners/subscribe
	 * 
	 * @return HashMap<String, String> This returns JSON data of "verify",
	 *         "Subscribed".
	 */
	public HashMap<String, String> subscribeToCompany(String ownerpass, String companypass) {
		HashMap<String, String> map = new HashMap<>();
		// create hash map for return value
		System.out.println(this.getClass().getSimpleName() + " - Subscribe method is invoked.");
		// print to the console

		Owner currentOwner = ownersRepository.findOwnerByUsername(ownerpass);
		// find the current Owner in the repo
		Company company = companyRepository.findCompanyByUsername(companypass);
		// find the company in the Repo
		for (Subscription subscription : company.getSubscriptions()) {
			// iterate through all the subscriptions
			if (subscription.getOwner().getId() == currentOwner.getId() && subscription.getSubscribedStatus() == true) {
				// this checks the User if they are already subscripted
				map.put("verify", "Already Subscribed");
				return map;
			}
			if (subscription.getOwner().getId() == currentOwner.getId()
					&& subscription.getSubscribedStatus() == false) {
				// this checks the User if they are already subscripted
				subscription.setSubscribedStatus(true);
				map.put("verify", "Subscribed Again");
				ownersRepository.flush();
				// update repo
				companyRepository.flush();
				// update repo
				return map;
			}

		}
		Owner owner = ownersRepository.findById(currentOwner.getId()).get();

		Subscription subscription = new Subscription();
		// this creates a new subscription
		subscription.setCompany(company);
		// this sets the subscription Company
		subscription.setOwner(owner);
		// this sets the subscription Owner
		owner.getSubscriptions().add(subscription);
		// this adds the subscription to Owner
		subscription.setSubscribedStatus(true);
		// set subscribed status to true

		company.getSubscriptions().add(subscription);
		// this adds the subscription to Company
		ownersRepository.save(owner);
		// save to Repo
		companyRepository.save(company);
		// save to Repo
		/* end subscription logic */
		map.put("verify", "Subscribed");
		// update return value
		return map;

	}

	/**
	 * This method finds the given Owner and return the current Subscriptions json
	 * array objects. Each Object contains CompanyName, Company Id, and Current
	 * Company Points . IS A GET METHOD, Path = /owners/{username}/findSubscriptions
	 * 
	 * @param String Username
	 * @return Json Array Object
	 */

	public HashMap<String, String> unsubscribe(String username, String cmpUserName) {
		HashMap<String, String> map = new HashMap<>();
		// create hash map for return value
		System.out.println(this.getClass().getSimpleName() + " - Subscribe method is invoked.");
		// print to the console

		Owner currentOwner = ownersRepository.findOwnerByUsername(username);
		// find the current Owner in the repo
		Company company = companyRepository.findCompanyByUsername(cmpUserName);
		// find the company in the Repo
		for (Subscription subscription : company.getSubscriptions()) {
			// iterate through all the subscriptions
			if (subscription.getOwner().getId() == currentOwner.getId() && subscription.getSubscribedStatus() == true) {
				// this checks the User if they are already subscripted
				subscription.setSubscribedStatus(false);
				map.put("verify", "unSubscribed");
				System.out.println(subscription.getSubscribedStatus());
				ownersRepository.flush();
				// update repo
				companyRepository.flush();
				// update repo
				return map;
			}
		}
		return map;

	}

	/**
	 * This method finds the given Owner and return the current Subscriptions json
	 * array objects. Each Object contains CompanyName, Company Id, and Current
	 * Company Points . IS A GET METHOD, Path = /owners/{username}/findSubscriptions
	 * 
	 * @param String Username
	 * @return Json Array Object
	 */

	public ResponseEntity<String> findUserSubscriptions(String username) throws JSONException {
		username = username.toString().trim();
		Owner currentUser = ownersRepository.findOwnerByUsername(username);
		// this finds the current Owner in Repo
		JSONArray currentSubscriptionArray = new JSONArray();
		// this creates Json array for the subscriptions

		if (currentUser.getUsername().trim().equals(username)) {
			// compare the given username to the current Owner object
			for (Subscription subscription : currentUser.getSubscriptions()) {
				// this iterates through all the subscriptions for the current Owner
				if (subscription.getSubscribedStatus() == true) {
					// this checks if they are currently subscribed***
					JSONObject companyObject = new JSONObject();
					// create companyObject for each iteration
					companyObject.put("CompanyId", subscription.getCompany().getId());
					// add the Company Id for each iteration
					companyObject.put("Company", subscription.getCompany().getCompanyName());
					// add the Company Name for each iteration
					companyObject.put("CompanyUserPoints", subscription.getpoints());
					// add the Current Company Points for this Current Owner Object each iteration
					Company comp = subscription.getCompany();
					companyObject.put("CompanyUsername", comp.getUsername());
					// add the current company username for this Current Owner Object
					currentSubscriptionArray.put(companyObject);
					// add the Current Company Object to the Current Json Subscripton Array
				}
			}
		}

		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		// this changes the send type of Spring to send Json data.

		System.out.println(currentSubscriptionArray);
		// output to console log, to make sure everything is correct
		JSONObject subscriptionObject = new JSONObject();
		// create return json object
		subscriptionObject.put("UserSubscriptions", currentSubscriptionArray);
		// name the json array to UserSubscriptions and add the Current Json
		// Subscirption Array

		System.out.println(this.getClass().getSimpleName() + " - Check subscriptions method is invoked.");

		return new ResponseEntity<String>(subscriptionObject.toString(), httpHeaders, HttpStatus.OK);
		// this returns the json object

	}

}
