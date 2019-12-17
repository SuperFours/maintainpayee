package com.maintainpayee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maintainpayee.entity.PayeeAccount;
@Repository
public interface PayeeAccountRepository extends JpaRepository<PayeeAccount, Integer> {
	
	PayeeAccount findPayeeAccountById(Integer id);
	
	Optional<PayeeAccount> findByAccountNumber(String accountNumber);

}
