package com.maintainpayee.dto;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class FavouritePayeeAccountDto {
	 
	private Integer id;
	private String accountNumber;
	private String nickName;
	private Boolean isFavorite;
	private String bankName;
	private String branchName;

}
