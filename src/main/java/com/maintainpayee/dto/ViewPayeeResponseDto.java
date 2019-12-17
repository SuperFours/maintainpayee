package com.maintainpayee.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ViewPayeeResponseDto {

	private String message;
	private Integer statusCode;
	
	private ViewPayeeDto payee;
}
