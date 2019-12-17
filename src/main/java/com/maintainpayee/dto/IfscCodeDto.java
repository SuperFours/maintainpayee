package com.maintainpayee.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @description ifscCodeDto class we can able to get and set the ifsc code
 *              details for the purpose of response.
 * @author Govindasamy.C
 * @since 17-12-2019
 *
 */
@Getter
@Setter
public class IfscCodeDto {

	private String ifscCode;
	private String bankName;
	private String branchName;

}
