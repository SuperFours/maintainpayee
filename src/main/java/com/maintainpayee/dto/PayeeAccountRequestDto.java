package com.maintainpayee.dto;

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
	private String accountNumber;
	private String name;
	private String nickName;
	private Boolean isFavorite;

}
