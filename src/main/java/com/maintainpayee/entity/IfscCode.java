package com.maintainpayee.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

/**
 * @description : ifsc code table we can maintain all the ifsc codes as the
 *              master table.
 * 
 * @author Govindasamy.C
 * @since 17-12-2019
 *
 */
@Getter
@Setter
@Entity
public class IfscCode {

	private String code;
	private String bankName;
	private String branchName;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "ifscCode")
	private Set<PayeeAccount> payeeAccount;
}
