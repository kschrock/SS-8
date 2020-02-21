package org.springframework.samples.outreach.qr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for QR code data management.
 * 
 * @author creimers
 * @author kschrock
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
