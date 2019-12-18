package com.maintainpayee.dto;

import javax.validation.constraints.NotBlank;

import com.maintainpayee.constant.AppConstant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PayeeAccountRequestDto {

	private String customerId;
	private String ifscCode;

	@NotBlank(message = AppConstant.FIRST_NAME_MANDATORY)
	private String firstName;
	@NotBlank(message = AppConstant.ACCOUNT_NUMBER_MANDATORY)
	private String accountNumber;
	private String name;
	private String nickName;
	private Boolean isFavorite;

}
