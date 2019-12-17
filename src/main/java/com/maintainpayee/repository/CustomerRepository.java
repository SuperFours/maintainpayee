package com.maintainpayee.repository;

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

}
