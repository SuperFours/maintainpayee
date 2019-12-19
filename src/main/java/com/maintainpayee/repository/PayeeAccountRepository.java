package com.maintainpayee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maintainpayee.entity.PayeeAccount;

@Repository
public interface PayeeAccountRepository extends JpaRepository<PayeeAccount, Integer> {

	PayeeAccount findPayeeAccountById(Integer id);

	Optional<PayeeAccount> findByAccountNumberAndCustomerIdId(String accountNumber, Integer customerId);

	PayeeAccount deletePayeeAccountById(Integer id);

	List<PayeeAccount> findByCustomerIdId(Integer customerId);

	@Query("Select count(p) from PayeeAccount p where p.customerId.phoneNumber = ?1")
	Integer getPayeeAccountCount(String userId);
}
