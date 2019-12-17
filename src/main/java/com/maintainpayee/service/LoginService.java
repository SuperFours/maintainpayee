package com.maintainpayee.service;

import com.maintainpayee.dto.LoginDto;
import com.maintainpayee.dto.LoginResponseDto;

@FunctionalInterface
public interface LoginService {

	public LoginResponseDto login(LoginDto userLoginDto);

}
