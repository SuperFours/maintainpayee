package com.maintainpayee.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

/**
 * @description : customer table we can maintain the customer details based on
 *              the user accounts and we can also easily maintain the payee
 *              accounts.
 * 
 * @author Govindasamy.C
 * @since 17-12-2019
 *
 */
@Entity
@Getter
@Setter
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String firstName;
	private String lastName;
	private LocalDate dob;
	private String email;
	private String phoneNumber;
	private String password;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customerId", orphanRemoval = true)
	private Set<PayeeAccount> payeeAccount;
}
