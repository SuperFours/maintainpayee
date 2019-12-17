package com.maintainpayee.service;

import com.maintainpayee.dto.FavouritePayeeAccountResponseDto;

public interface PayeeAccountService {
	
	public FavouritePayeeAccountResponseDto getAllFavouriteAccounts();
	
	public ResponseDto createPayee(PayeeAccountRequestDto payeeRequestDto);

}
