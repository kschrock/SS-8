package org.springframework.samples.outreach.qr;

import org.springframework.samples.outreach.qr.Product;


/**
 * iterates through products
 * @author creimers
 * @author kschrock
 */
public interface ProductService {

	public Iterable<Product> findAll();

}
