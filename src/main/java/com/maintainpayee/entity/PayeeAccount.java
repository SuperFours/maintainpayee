package com.maintainpayee.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * @description payeeAccount table we can maintain the payee accounts by ifsc
 *              code and customer wise.
 * @author Govindasamy.C
 * @since 17-12-2019
 *
 */
@Setter
@Getter
@Entity
public class PayeeAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customerId;
	@ManyToOne
	@JoinColumn(name = "ifsc_code")
	private IfscCode ifscCode;
	private String accountNumber;
	private String name;
	private String nickName;
	private Boolean isFavorite;
}
