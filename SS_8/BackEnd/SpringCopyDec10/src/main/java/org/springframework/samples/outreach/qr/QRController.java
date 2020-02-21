package org.springframework.samples.outreach.qr;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.samples.outreach.qr.ZXingHelper;
import org.springframework.samples.outreach.subscription.Subscription;
import org.springframework.samples.outreach.company.CompanyRepository;
import org.springframework.samples.outreach.owner.Owner;
import org.springframework.samples.outreach.owner.OwnerRepository;
import org.springframework.samples.outreach.qr.ProductService;
/**
 * This is the QR Contoller that Generates the QR codes with the Correct info Company, Content, Points, Quantity left and ID.
 * @author creimers
 * @author kschrock
 */
@Controller
@RequestMapping("product")
public class QRController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	ProductRepository productRepo;
	 
	@Autowired
	OwnerRepository ownersRepository;    
	  
	@Autowired
	CompanyRepository companyRepository;

	//Product p1;
	  private final Logger logger = LoggerFactory.getLogger(QRController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(/*ModelMap modelMap*/) {
//		modelMap.put("products", productService.findAll());
		return "product/index";
	}
	
	//get individual qr code
	@RequestMapping(value = "/qrcode/{id}", method = RequestMethod.GET)
	public void qrcodeView(@PathVariable("id") Integer id,
			HttpServletResponse response) throws Exception{
		response.setContentType("image/png");
		
		List<Product> results = productRepo.findAll();
		
		for (Product current : results) {

			if (current.getId() == id) {
				OutputStream outputStream = response.getOutputStream();
				outputStream.write(ZXingHelper.getQRCodeImage(current.getBaseURL(), 200, 200));
				outputStream.flush();
			}

		}
	}

	 /**
	   * THIS METHOD CREATES THE QR CODES AND WRTIES THEM INTO THE DATABASE. 
	   * This is only used for generation qr codes from the WorkBench.
	   * THIS IS A GET METHOD, Path = qrcode/{id}
	   * @param STRING ID
	   * @return Void
	   */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public HashMap<String, String> qrcode(@RequestBody Product codegen, 
			HttpServletResponse response) throws Exception {
		//THIS METHOD CREATES THE QR CODES AND WRTIES THEM INTO THE DATABASE
		LocalDateTime test = LocalDateTime.now();
		HashMap<String, String> map = new HashMap<>();
		response.setContentType("image/png");
		codegen.setCreateDateTime(LocalDateTime.now());
		codegen.setExpireDateTime(LocalDateTime.of(codegen.getExpireDate(),codegen.getExpireTime()));
		 //This gets all the current Data in the Repository
		 productRepo.save(codegen);
		 OutputStream outputStream = response.getOutputStream();		
//		 String baseURL = "http://localhost:8080/Qr";
		 String baseURL = "http://coms-309-ss-8.misc.iastate.edu:8080/Qr";
		 //This is our Base URL for the QR Codes
		 
		 		//add to the base URL
				baseURL = baseURL +"/" + codegen.getCompany();
				baseURL = baseURL +"/" + codegen.getId();
				codegen.setBaseURL(baseURL);
				//add to the base URL
				productRepo.save(codegen);
				//add points
				outputStream.write(ZXingHelper.getQRCodeImage(baseURL, 200, 200));
				//Write the content to the QR code
				outputStream.flush();
				//Update the Repository

		//Update the Repository
		outputStream.close();
		//Close the Steam
		return map;
	}

	
	/**
	   * THIS method sets the given quantity to the correct QR code. 
	   * It does this by going the the Qr Repository and finds the
	   * correct Qr code from the Parameters it was given. 
	   * THIS IS A GET METHOD, Path = product/{company}/{ id}/{ quant}
	   * @param String ID
	   * @param String Company
	   * @param String Quantity
	   * @return Map<String, String>, "verify", "Added" || "verify", "false"
	   */
	 @RequestMapping(value = "/{company}/{ id}/{ quant}", method = RequestMethod.GET)
	    public Map<String, String> setQuantity( @PathVariable("company") String company, @PathVariable(" id") Integer id, @PathVariable(" quant") int quant) {
	    //THIS METHOD SETS THE QUANTIY OF THE QR CODE AND UPDATES THE REPOISTORY 
		 
		 company = company.toString().trim();
        HashMap<String, String> map = new HashMap<>();
        int currentId1 = 0;
		 try {
			 currentId1 = id;
		 }
		 catch (NumberFormatException e)
		 {
			 currentId1 = 0; //not found
		 }
		 
		 int currentquantity = 0;
		 try {
			 currentquantity = quant;
		 }
		 catch (NumberFormatException e)
		 {
			 currentquantity = 0; //not found
		 }
		
        Iterable<Product> yeet = productService.findAll(); //gets all the product types of qrcodes
       
		 for (Product current : yeet)
		 {
			 String currentCompany = current.getCompany().toString().trim();
			 if(company.toString().trim().equals(currentCompany)) {
				 
				if(current.getId() == currentId1) {
					 
					 map.put("verify", "Added");

	        		 current.setQuantity(currentquantity); //set current points to current user
	        		
	        		 productRepo.flush(); //update DataBase
	        		 return map;
	        		 
				}

			 }
	           
	    } 

        map.put("verify", "false");
        return map;
    }
	 
	 /**
	   * This can be used once the user scans the code and gets the id and company. This 
	   * will return json data of points to add to user once the user gets back the info
	   * they can confirm then sends another post to the owner repo to update their points.
	   * THIS IS A POST METHOD, Path = product/{company}/{id}
	   * @param String ID
	   * @param String Company
	   * @return HashMap<String, String>, "points", "No More Scans Left" || "points", "Not Found" || ex. "points", "123"
	   */
	 @RequestMapping(value= "/{company}/{id}/{username}", method= RequestMethod.POST) ///{points}/{quantity}
		public HashMap<String, String> findCode(@PathVariable("id") Integer id, 
				@PathVariable("company") String company,
				@PathVariable("username") String username) { //to add points to user who scans the code

		//This can be used once the user scans the code and gets the id and company. This will return json data of points to add to user
		 //once the user gets back the info they can confirm then sends another post to the owner repo to update their points.
		
		 company = company.toString().trim();
	        HashMap<String, String> map = new HashMap<>();
	        int currentId1 = 0;
			 try {
				 currentId1 = id;
			 }
			 catch (NumberFormatException e)
			 {
				 currentId1 = 0; //not found
			 }
			 
			//System.out.println(currentId1);
	        Iterable<Product> yeet = productService.findAll(); //gets all the product types of qrcodes
	     
	        
	       
			 for (Product current : yeet)
			 {	 
					if(current.getId() == currentId1) {
						 	// current.get
						if(current.getQuantity() < 1 /* || time is expired */) {
							
								map.put("points", "No More Scans Left");
						        return map;
						}
						//decrease quantity if there are codes left
						int newquantity = current.getQuantity() -1 ;
						//add points to user
						List<Owner> results = ownersRepository.findAll();
						
						  for(Owner currentOwner : results) {
							  String currentUsername = currentOwner.getUsername().toString().trim();
					        	
					        	if(username.toString().trim().equals(currentUsername))
					        	{
					        		
					        		 for(Subscription subscription: currentOwner.getSubscriptions()) {
					        				if(subscription.getCompany().getCompanyName().trim().equals(company.trim())) {
					        					 double temp = 0;
					        	        		 double currentPoints;
					        	        		
					        	        		 try {
					        	        			 currentPoints =  subscription.getpoints();
					        	        					 //Integer.parseInt(subscription.getCompany().getPoints());
					        	        		 }
					        	        		 catch (NumberFormatException e)
					        	        		 {
					        	        			 currentPoints = 0; //not found
					        	        		 }
					        	        		 temp = currentPoints + (current.getPoints()); //add total points
					        					System.out.println(currentPoints + "Before Amount");
					        					System.out.println(temp + "After Amount");
					        					subscription.setPoints(temp);
					        					//subscription.setID(1);
					        					 map.put("verify", "addedPoints!!");
					        					 ownersRepository.flush(); // updates changes
					        					 companyRepository.flush();
//					        					 return map;
					        				}
					        				//its getting the owner by id and adding it to the list of owners in the company
					        			
					        		 }
					        		 ownersRepository.flush(); // updates changes
					        	
					        	}
					        }
						  	//end add points
						
						  
						  current.setQuantity(newquantity); //set current points to current user
		        		 
						  String points = current.getPoints() +"";
		        		 
		        		
		        		 map.put("qr consumed:", "verified");
		        		
		        		
		        		 productRepo.flush(); //update DataBase
		        		
		        		 return map;
		        		 
					}

				 }

	        map.put("points", "Not Found");
	        return map;
		
		}
	
}
