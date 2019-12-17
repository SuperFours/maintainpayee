package com.maintainpayee.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ViewPayeeDto {
	
	private Integer payeeId;
	private String name;
	private String nickName;
	private String accountNumber;
	private String ifscCode;
	private Boolean isFavorie;

}
