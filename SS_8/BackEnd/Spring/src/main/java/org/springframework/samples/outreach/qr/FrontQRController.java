package org.springframework.samples.outreach.qr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller for the qrcodes.
 * 
 * @author creimers
 * @author kschrock
 */
@RestController
public class FrontQRController {

	@Autowired
	private ProductService service;

	/**
	 * This method gets you the qr code with the id you pass to it
	 * 
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	// get individual qr code
	@RequestMapping(value = "/qrcode/{id}", method = RequestMethod.GET)
	public void qrcodeView(@PathVariable("id") Integer id, HttpServletResponse response) throws Exception {
		service.qrcodeView(id, response);
	}

	/**
	 * THIS METHOD CREATES THE QR CODES AND WRTIES THEM INTO THE DATABASE. This is
	 * only used for generation qr codes from the WorkBench. THIS IS A GET METHOD,
	 * Path = qrcode/{id}
	 * 
	 * @param STRING ID
	 * @return Void
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public HashMap<String, String> qrcode(@RequestBody Product codegen, HttpServletResponse response) throws Exception {
		return service.qrcode(codegen, response);
	}

	/**
	 * This method deletes a code by its ID within the Product Repository. THIS IS A
	 * POST METHOD, Path = /qrcode/delete/{id}
	 * 
	 * @param int ID
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/qrcode/delete/{id}")
	public void deleteEmployeeById(@PathVariable int id) throws Exception {

		service.deleteEmployeeById(id);
	}

	/**
	 * This method gets all the Codes for a specific company in the Product
	 * Repository. THIS IS A GET METHOD, Path = /Work
	 * 
	 * @return Iterable<Product> This returns the list of QR code Objects.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/qrcode/getAll/{username}")
	public List<Product> getAllCompCodes(String username) {

		return service.getAllCompCodes(username);
	}

	/**
	 * This method sets the given quantity to the Correct QR Code and Update it
	 * within the Product Repository. THIS IS A POST METHOD, Path = /{company}/{
	 * id}/{ quant}
	 * 
	 * @param Company
	 * @param ID
	 * @param Quanity
	 * @return Map<String, String> This returns "Quantity", "Added" or "Quantity",
	 *         "Did Not Add".
	 */
	@RequestMapping(value = "/{company}/{ id}/{ quant}", method = RequestMethod.POST)
	public Map<String, String> setQuantity(@PathVariable("company") String company, @PathVariable(" id") String id,
			@PathVariable("quant") String quant) {

		return service.setQuantity(company, id, quant);
	}

	/**
	 * This can be used once the user scans the code and gets the id and company.
	 * This will return json data of points to add to user once the user gets back
	 * the info they can confirm then sends another post to the owner repo to update
	 * their points. THIS IS A GET METHOD, Path = /{company}/{id}
	 * 
	 * @param Company
	 * @param ID
	 * @return HashMap<String, String> This returns "points", "No More Scans Left"
	 *         || "points", "Not Found" || ex. "points", "123".
	 */
	@RequestMapping(value = "Qr/{company}/{id}/{username}", method = RequestMethod.POST)
	public HashMap<String, String> consumeCode(@PathVariable("id") String id, @PathVariable("company") String company,
			@PathVariable("username") String username) {

		return service.consumeCode(id, company, username);
	}

}