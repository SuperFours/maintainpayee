package com.maintainpayee.service;

import com.maintainpayee.dto.FavouritePayeeAccountResponseDto;
import com.maintainpayee.dto.PayeeAccountRequestDto;
import com.maintainpayee.dto.ResponseDto;
import com.maintainpayee.dto.ViewPayeeResponseDto;

import javassist.NotFoundException;

public interface PayeeAccountService {
	
	public FavouritePayeeAccountResponseDto getAllFavouriteAccounts(String customerId);
	
	public ViewPayeeResponseDto getPayeeAccount(Integer payeeId);
	
	public ResponseDto createPayee(PayeeAccountRequestDto payeeRequestDto) throws NotFoundException;

	ResponseDto deleteAccount(Integer id);
	
	public ResponseDto updatePayee(PayeeAccountRequestDto payeeRequestDto, Integer id) throws NotFoundException;

}
