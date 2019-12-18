package com.maintainpayee.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccountDto {
	private Integer userId;
	private Long accountNumber;
	private String accountType;
	private String userName;

}
