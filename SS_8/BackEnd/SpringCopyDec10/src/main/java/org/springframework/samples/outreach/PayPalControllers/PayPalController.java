package org.springframework.samples.outreach.PayPalControllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.owner.Owner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import org.springframework.samples.outreach.owner.*;

@Controller
public class PayPalController {

	@Autowired
	PayPalService service;

	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";

	private String username = "";
	private String password = "";

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@PostMapping("/pay")
	public String payment(@ModelAttribute("order") Order order) {
		try {


			username = order.getPrice();
			password = order.getCurrency();
			System.out.print(username);
			System.out.print(password);
			order.setCurrency("USD");

			order.setDescription("Fee to use Project OutReach");
			Payment payment = service.createPayment(10.00, order.getCurrency(), order.getMethod(), order.getIntent(),
					order.getDescription(), "http://localhost:8080/" + CANCEL_URL,
					"http://localhost:8080/" + SUCCESS_URL);
			for (Links link : payment.getLinks()) {
				if (link.getRel().equals("approval_url")) {
					return "redirect:" + link.getHref();
				}
			}

		} catch (PayPalRESTException e) {

			e.printStackTrace();
		}
		return "redirect:/";
	}

	@GetMapping(value = CANCEL_URL)
	public String cancelPay() {
		return "cancel";
	}

	@GetMapping(value = SUCCESS_URL)
	public String successPay(@RequestParam("paymentId") String paymentId, String payerId) {
		System.out.println(paymentId);
		System.out.println(payerId);
		payerId = "NDLPRNJ8SS5ZJ"; // User in PayPal Account
		try {
			Payment payment = service.executePayment(paymentId, payerId);
			System.out.println(payment.toJSON());
			if (payment.getState().equals("approved")) {

				return "redirect:/owners/" + username + "/" + password + "/paid"; // update user to payed * Confirmation
																					// page

			}
		} catch (PayPalRESTException e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/";
	}

}
