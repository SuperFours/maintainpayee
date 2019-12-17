package com.maintainpayee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maintainpayee.entity.Customer;

/**
 * 
 * @author akuthota.raghu
 *
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	Optional<Customer> findByPhoneNumber(String userId);
}
