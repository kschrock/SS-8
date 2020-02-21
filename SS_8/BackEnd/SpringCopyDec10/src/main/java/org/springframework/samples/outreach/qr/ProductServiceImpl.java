package org.springframework.samples.outreach.qr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.samples.outreach.qr.Product;
import org.springframework.samples.outreach.qr.ProductRepository;

/**
 * implements the findall function for productservice
 * @author creimers
 * @author kschrock
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Iterable<Product> findAll() {
		return productRepository.findAll();
	}

}
