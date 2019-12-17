package com.maintainpayee.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maintainpayee.constant.AppConstant;
import com.maintainpayee.dto.FavouritePayeeAccountDto;
import com.maintainpayee.dto.FavouritePayeeAccountResponseDto;
import com.maintainpayee.entity.PayeeAccount;
import com.maintainpayee.repository.PayeeAccountRepository;

@Service
public class PayeeAccountServiceImpl implements PayeeAccountService {

	@Autowired
	PayeeAccountRepository payeeAccountRepository;

	@Override
	public FavouritePayeeAccountResponseDto getAllFavouriteAccounts() {
		FavouritePayeeAccountResponseDto response=new FavouritePayeeAccountResponseDto();
		List<PayeeAccount> payees =payeeAccountRepository.findAll();
		List<FavouritePayeeAccountDto> payeeAccountDto = payees.stream()
				.map(this::convertEntityToDto).collect(Collectors.toList());
		response.setPayees(payeeAccountDto);
		response.setMessage(AppConstant.SUCCESS);
		return response;
	}

	private FavouritePayeeAccountDto convertEntityToDto(PayeeAccount payeeAccount) {
		FavouritePayeeAccountDto favouritePayeeAccountDto = new FavouritePayeeAccountDto();
		BeanUtils.copyProperties(payeeAccount, favouritePayeeAccountDto);
		return favouritePayeeAccountDto;
	
	}
}
