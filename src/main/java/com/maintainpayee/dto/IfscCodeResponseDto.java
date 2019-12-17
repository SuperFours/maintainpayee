package com.maintainpayee.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @description IfscCodeResponseDto - For the purpose of set the final response
 *              of the get the ifsc code detail.
 * @author Govindasamy.C
 * @since 17-12-2019
 *
 */
@Getter
@Setter
public class IfscCodeResponseDto {

	private Integer statusCode;
	private String message;

	private IfscCodeDto ifscCode;

}
