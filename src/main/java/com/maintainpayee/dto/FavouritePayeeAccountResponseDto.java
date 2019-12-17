package com.maintainpayee.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavouritePayeeAccountResponseDto {
	
	private String message;
	private Integer statusCode;
	
	List<FavouritePayeeAccountDto> payees;

}
