package org.springframework.samples.outreach.owner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.samples.outreach.company.Company;
import org.springframework.samples.outreach.company.CompanyRepository;
import org.springframework.samples.outreach.prize.Prize;
import org.springframework.samples.outreach.qr.Product;
import org.springframework.samples.outreach.subscription.Subscription;

/**
 * Mockito Test for Owner Controller
 * 
 * @author creimers
 * @author kschrock
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoControllerTest {

	@InjectMocks
	private OwnerController OwnerController;

	@Mock
	private OwnerRepository OwnerRepository;

	@Mock
	private CompanyRepository companyRepository;

	Owner current = new Owner();

	@Before(value = "")
	public void init() throws Exception {
		MockitoAnnotations.initMocks(this);

		// set EmployeeDAO mock object
		OwnerController = mock(OwnerController.class);

		// create an user object

		current.setId(12);
		current.setAddress("Mountain View, CA");
		current.setFirstName("Google");
		current.setLastName("Lastname");
		current.setUsername("googler");
		current.setPassord("data");
		// current.setPoints("111");

		OwnerController.deleteEmployeeById(12);

		System.out.println("HELLO");
		System.out.println(current);

	}

	/**
	 * This method Tests createPrize function
	 * 
	 * @return void
	 */
	@Test
	public void createPrize() {
		Prize prize = new Prize();
		prize.setCompanyName("Google");
		prize.setCost(100);// points
		prize.setDiscount(25);// points off for members
		prize.setId(1);
		prize.setQty(17);

		System.out.println("Created Prize Object: " + prize.toString());
		System.out.println("CompanyName: " + prize.getCompanyName());
		System.out.println("Cost: " + prize.getCost());
		System.out.println("QTY: " + prize.getQty());
		System.out.println("ID: " + prize.getId());

		assertEquals("Google", prize.getCompanyName()); // This checks the prize's Company Name
		assertEquals(25, prize.getDiscount());// This checks the prize's Discount
		assertEquals(100, prize.getCost()); // This checks the prize's Costs
		assertEquals(17, prize.getQty()); // This checks the prize's Quantity

		System.out.println("Create Prize Test");
		System.out.println("------------------------------------------\n\n\n");
	}

	/**
	 * This method Tests createSubscripition function
	 * 
	 * @return void
	 */
	@Test
	public void createQRCode() {

		Product qrCode = new Product();
		qrCode.setCompany("YouTube");
		qrCode.setId(2);
		qrCode.setPoints(111);
		qrCode.setQuantity(7);

		System.out.println("Created QrCode Object:" + qrCode);
		System.out.println("QrCode Company:" + qrCode.getCompany());
		System.out.println("QrCode Points:" + qrCode.getPoints());
		System.out.println("QrCode Quanity:" + qrCode.getQuantity());

		assertEquals(111, (int) qrCode.getPoints()); // This checks the Qr Points
		assertEquals("YouTube", qrCode.getCompany().trim()); // This checks the Company of the Qr
		assertEquals(7, qrCode.getQuantity()); // This checks the Qr quantity.

		System.out.println("Create Qr-Code Test");
		System.out.println("------------------------------------------\n\n\n");
	}

	/**
	 * This method Tests createSubscripition function
	 * 
	 * @return void
	 */
	@Test
	public void createSubscription() {
		Owner user = new Owner();
		user.setFirstName("Kordell");
		user.setUsername("Kordell Username");
		Company company = new Company();
		company.setCompanyName("Google");
		company.setUsername("Google Username");
		Subscription subscription = new Subscription();
		subscription.setCompany(company);
		subscription.setOwner(user);
		subscription.setPoints(10);
		subscription.setID(1);

		System.out.println("Created Subscription Object: " + subscription);
		System.out.println("Subscription CompanyName: " + subscription.getCompany().getCompanyName());
		System.out.println("Subscription UserFirstName: " + subscription.getOwner().getFirstName());

		assertEquals("Google", subscription.getCompany().getCompanyName()); // This checks the subscription company name
																			// that is linked to owner
		assertEquals("Kordell", subscription.getOwner().getFirstName());// This checks the subscription's owner's Name
		assertEquals(10, (int) subscription.getpoints()); // This checks the owner's points for this current
															// subscription

		System.out.println("Create Subscription Test");
		System.out.println("------------------------------------------\n\n\n");

	}

	/**
	 * This method Tests addPoints to Owners Subscription function
	 * 
	 * @return void
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void addPoints() {
		Owner user = new Owner();
		user.setFirstName("Kordell");
		user.setUsername("Kordell Username");
		Company company = new Company();
		company.setCompanyName("Google");
		company.setUsername("Google Username");
		Subscription subscription = new Subscription();
		subscription.setCompany(company);
		subscription.setOwner(user);
		subscription.setPoints(10);
		subscription.setID(1);

		Product qrCode = new Product();
		qrCode.setCompany("Google");
		qrCode.setId(1);
		qrCode.setPoints(777);
		qrCode.setQuantity(1);

		System.out.println("Before Added Points: " + subscription.getpoints());
		subscription.setPoints(qrCode.getPoints() + subscription.getpoints());
		System.out.println(subscription.getOwner().getFirstName());
		System.out.println(subscription.getCompany().getCompanyName());
		System.out.println("After Added Points: " + subscription.getpoints());

		assertEquals(787, (int) subscription.getpoints()); // This checks that is added the right amount to the current
															// subscription
		System.out.println("Add Points Test");

		System.out.println("------------------------------------------\n\n\n");
	}

	/**
	 * This method Tests by deleting a user in the Mock Repo.
	 * 
	 * @return void
	 */
	@Test
	public void deleteEmployeeTest() {

		Owner user = new Owner();
		user.setId(12);

		Owner user2 = new Owner();
		user2.setId(11);
		user2.setFirstName("KORDELL");

		when(OwnerRepository.findAll()).thenReturn(Stream.of(user, user2).collect(Collectors.toList()));

		java.util.List<Owner> results = OwnerController.getAllOwners();

		System.out.println(results);
		OwnerRepository.deleteById(11);
		try {
			OwnerController.deleteEmployeeById(11);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		}
		OwnerRepository.deleteAll();
		OwnerController.deleteAll();
		OwnerController.deleteAll();
		OwnerRepository.flush();
		try {
			OwnerRepository.deleteAll();
			OwnerController.deleteAll();
			OwnerRepository.flush();

		} catch (Exception e) {
			// TODO Auto-generated catch block

		}
		System.out.println("HELLO");

		System.out.println(results);

		System.out.println("HELLO");

		System.out.println("HELLO Test");
		System.out.println("------------------------------------------\n\n\n");

		// assertEquals("REMOVED", status);
	}

	/**
	 * This method Tests by creating a user and fills in the parameters in the Mock
	 * Repo. Then this checks the creating a user into the DB by returning a string.
	 * 
	 * @return void
	 */
	@Test
	public void testingUser() {
		Owner user = new Owner();
		user.setId(117);
		user.setFirstName("Kordell");
		user.setLastName("Schrock");
		// user.setPoints("10");
		user.setPassord("Password");
		user.setUsername("Username");
		user.setAddress("Address");
		user.setTelephone("555-555-5555");
		System.out.println(user);

		assertEquals("Kordell", user.getFirstName()); // This checks the Users First name
		assertEquals("Schrock", user.getLastName()); // This checks the Users Last name
		assertEquals("Password", user.getpassword()); // This checks the Users Password
		assertEquals(117, (int) user.getId()); // This checks the Users ID
		assertEquals("Address", user.getAddress()); // This checks the Users Address
		System.out.println("Creating User Test");
		System.out.println("------------------------------------------\n\n\n");
	}

	/**
	 * This method Tests by Creating 2 null users, with no parameters created in the
	 * Mock Repo. There should be 2 users total in the DB.
	 * 
	 * @return void
	 */
	@Test
	public void getUsers() {
		Owner user = new Owner();

		Owner user2 = new Owner();

		when(OwnerRepository.findAll()).thenReturn(Stream.of(user, user2).collect(Collectors.toList()));

		assertEquals(2, OwnerController.getAllOwners().size()); // Created 2 Users in 'mock' repository

		System.out.println("Get Users Test");
		System.out.println("------------------------------------------\n\n\n");
	}

	/**
	 * This method Tests by Creating a user, with username = Username and password =
	 * Password in the Mock Repo. This shows to logins, first one matches and passes
	 * the second one does not match and fails.
	 * 
	 * @return void
	 */
	@Test
	public void loginTest() {
		Owner user = new Owner();
		user.setFirstName("Kordell");
		user.setLastName("Schrock");
		// user.setPoints("10");
		user.setPassord("Password");
		user.setUsername("Username");
		user.setAddress("Address");
		user.setTelephone("555-555-5555");
		user.setId(117);
		when(OwnerRepository.findAll()).thenReturn(Stream.of(user).collect(Collectors.toList()));
		System.out.println("Login 1"); // SHOULD BE TURE
		System.out.println(OwnerController.loginOwner("Username", "Password"));

		assertEquals("{verify=true}".trim(), OwnerController.loginOwner("Username", "Password").toString().trim());

		System.out.println("Login 2");// SHOULD BE FALSE
		assertEquals("{verify=false}".trim(), OwnerController.loginOwner("Wrong", "Wrong").toString().trim());
		System.out.println(OwnerController.loginOwner("Wrong", "Wrong"));

		System.out.println("Login Test");
		System.out.println("------------------------------------------\n\n\n");

	}

	/**
	 * This method Tests getAll function
	 * 
	 * @return void
	 */
	@Test
	public void getAllAccountTest() {
		java.util.List<Owner> list = new ArrayList<Owner>();
		Owner acctOne = new Owner();
		Owner acctTwo = new Owner();
		Owner acctThree = new Owner();

		list.add(acctOne);
		list.add(acctTwo);
		list.add(acctThree);

		when(OwnerRepository.findAll()).thenReturn(list);

		java.util.List<Owner> acctList = OwnerController.getAllOwners();

		assertEquals(3, acctList.size());
		verify(OwnerRepository, times(1)).findAll();
		System.out.println("GET ALL Test");
		System.out.println("------------------------------------------\n\n\n");
	}

	/**
	 * This method Tests deleteAll function
	 * 
	 * @return void
	 */
	@Test
	public void deleteAllUsers() {
		Owner user = new Owner();

		Owner user2 = new Owner();

		Owner newBook = new Owner();

		String current = "New Owner " + user.getFirstName() + " Saved";

		// when(OwnerController.createEmployee(user)).thenReturn(current);

		// Mockito.when(OwnerController.createEmployee(user)).thenReturn(current);

		// when(OwnerRepository.findAll()).thenReturn(Stream.of(user,
		// user2).collect(Collectors.toList()));
		// when(OwnerRepository.save(user)).thenReturn((Owners)
		// OwnerController.getAllOwners());

		// OwnerRepository.deleteAll();

		// System.out.println(OwnerController.findOwnerById(1));

		System.out.println(OwnerController.getAllOwners().size());
		int size = OwnerController.getAllOwners().size();
		assertEquals(0, size); // Since all users are deleted return 0

		System.out.println("Delete All Test");
		System.out.println("------------------------------------------\n\n\n");

	}

}
