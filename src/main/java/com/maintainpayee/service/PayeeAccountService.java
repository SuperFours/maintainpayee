package com.maintainpayee.service;

import com.maintainpayee.dto.FavouritePayeeAccountResponseDto;
import com.maintainpayee.dto.PayeeAccountRequestDto;
import com.maintainpayee.dto.ResponseDto;

public interface PayeeAccountService {
	
	public FavouritePayeeAccountResponseDto getAllFavouriteAccounts();
	
	public ResponseDto createPayee(PayeeAccountRequestDto payeeRequestDto);

}
