package org.springframework.samples.outreach.subscription;

import java.util.HashMap;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.owner.Owner;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("subscription")
public class SubscriptionController {

	@Autowired
	SubscriptionService service;

	/**
	 * This method unsubscribes a user from a company. THIS IS A POST METHOD, Path =
	 * /subscription/subscribe
	 * 
	 * @return HashMap<String, String> This returns JSON data of "verify",
	 *         "unSubscribed".
	 */

	@RequestMapping(method = RequestMethod.POST, path = "/unsubscribe")
	public HashMap<String, String> unsubscribe(@RequestBody Object[] arr) {
		// arr at index 0 to string to grab the substring from '=' to the end of the
		// string
		// 2 Object arrays are passed as a requestbody and the owner username and
		// company username is in each of these respectively
		String ownername = arr[0].toString().substring(arr[0].toString().indexOf('=') + 1,
				arr[0].toString().length() - 1);
		String cmpname = arr[1].toString().substring(arr[1].toString().indexOf('=') + 1,
				arr[1].toString().length() - 1);
		service.unsubscribe(ownername, cmpname);
		HashMap<String, String> map = new HashMap<>();
		map.put("verify", "unSubscribed");
		return map;
	}

	/**
	 * This method subscribes a user to a company. THIS IS A POST METHOD, Path =
	 * /subscription/subscribe
	 * 
	 * @return HashMap<String, String> This returns JSON data of "verify",
	 *         "Subscribed".
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/subscribe")
	public HashMap<String, String> sub(@RequestBody Object[] arr) {
		// arr at index 0 to string to grab the substring from '=' to the end of the
		// string
		// 2 Object arrays are passed as a requestbody and the owner username and
		// company username is in each of these respectively
		String ownername = arr[0].toString().substring(arr[0].toString().indexOf('=') + 1,
				arr[0].toString().length() - 1);
		String cmpname = arr[1].toString().substring(arr[1].toString().indexOf('=') + 1,
				arr[1].toString().length() - 1);
		service.subscribeToCompany(ownername, cmpname);
		HashMap<String, String> map = new HashMap<>();
		map.put("verify", "Subscribed");
		return map;
	}

	/**
	 * Finds a list of companies the user is subscribed to
	 * 
	 * @param owner
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/findSubscriptions")
	public ResponseEntity<String> findUserSubscriptions(@RequestBody Owner owner) throws JSONException {

		return service.findUserSubscriptions(owner.getUsername());
	}
}
