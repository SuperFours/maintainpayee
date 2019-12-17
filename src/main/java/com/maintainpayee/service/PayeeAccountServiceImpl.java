package com.maintainpayee.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maintainpayee.constant.AppConstant;
import com.maintainpayee.dto.FavouritePayeeAccountDto;
import com.maintainpayee.dto.FavouritePayeeAccountResponseDto;
import com.maintainpayee.dto.PayeeAccountRequestDto;
import com.maintainpayee.dto.ResponseDto;
import com.maintainpayee.entity.Customer;
import com.maintainpayee.entity.IfscCode;
import com.maintainpayee.entity.PayeeAccount;
import com.maintainpayee.repository.CustomerRepository;
import com.maintainpayee.repository.PayeeAccountRepository;

@Service
public class PayeeAccountServiceImpl implements PayeeAccountService {

	@Autowired
	PayeeAccountRepository payeeAccountRepository;

	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	IfscCodeRepository ifscCodeRepository;


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
	
	
	/**
	 * @description this method is used to create the new payee in respective DB
	 * @param PayeeAccountRequestDto object set of input fields to create payee
	 * @return ResponseDto object contains response message and status
	 */
	@Override
	public ResponseDto createPayee(PayeeAccountRequestDto payeeRequestDto) {

		ResponseDto responseDto = new ResponseDto();

		PayeeAccount payeeAccount = new PayeeAccount();

		Optional<PayeeAccount> payeeAccountResponse = payeeAccountRepository
				.findByAccountNumber(payeeRequestDto.getAccountNumber());
		
		Optional<Customer>  customerResponse = customerRepository.findById(payeeRequestDto.getCustomerId());
		Optional<IfscCode>  ifscCodeResponse = ifscCodeRepository.findByCode(payeeRequestDto.getIfscCode());
		
		
		if (!payeeAccountResponse.isPresent()) {
			responseDto.setMessage(AppConstant.PAYEE_ALREADY_EXIST);
		} else {
			
			payeeAccount.setCustomerId(customerResponse.get());
			payeeAccount.setIfscCode(ifscCodeResponse.get());
			
			BeanUtils.copyProperties(payeeRequestDto, payeeAccount);

			payeeAccountRepository.save(payeeAccount);

			responseDto.setMessage(AppConstant.SUCCESS);
		}

		return responseDto;
	}
}
