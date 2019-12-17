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
import com.maintainpayee.repository.IfscCodeRepository;
import com.maintainpayee.repository.PayeeAccountRepository;

import javassist.NotFoundException;

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
		FavouritePayeeAccountResponseDto response = new FavouritePayeeAccountResponseDto();
		List<PayeeAccount> payees = payeeAccountRepository.findAll();
		List<FavouritePayeeAccountDto> payeeAccountDto = payees.stream().map(this::convertEntityToDto)
				.collect(Collectors.toList());
		response.setPayees(payeeAccountDto);
		response.setMessage(AppConstant.SUCCESS);
		return response;
	}

	private FavouritePayeeAccountDto convertEntityToDto(PayeeAccount payeeAccount) {
		FavouritePayeeAccountDto favouritePayeeAccountDto = new FavouritePayeeAccountDto();
		BeanUtils.copyProperties(payeeAccount, favouritePayeeAccountDto);
		favouritePayeeAccountDto.setBankName(payeeAccount.getIfscCode().getBankName());
		favouritePayeeAccountDto.setBranchName(payeeAccount.getIfscCode().getBranchName());
		return favouritePayeeAccountDto;

	}

	/**
	 * @description this method is used to create the new payee in respective DB
	 * @param PayeeAccountRequestDto object set of input fields to create payee
	 * @return ResponseDto object contains response message and status
	 * @throws NotFoundException
	 */
	@Override
	public ResponseDto createPayee(PayeeAccountRequestDto payeeRequestDto) throws NotFoundException {
		ResponseDto responseDto = new ResponseDto();
		PayeeAccount payeeAccount = new PayeeAccount();
		Optional<Customer> customerResponse = customerRepository.findById(payeeRequestDto.getCustomerId());
		Optional<IfscCode> ifscCodeResponse = ifscCodeRepository.findByCode(payeeRequestDto.getIfscCode());
		if (customerResponse.isPresent() && ifscCodeResponse.isPresent()) {

			Optional<PayeeAccount> payeeAccountResponse = payeeAccountRepository.findByAccountNumberAndCustomerIdId(
					payeeRequestDto.getAccountNumber(), customerResponse.get().getId());
			if (!payeeAccountResponse.isPresent()) {
				payeeAccount.setCustomerId(customerResponse.get());
				payeeAccount.setIfscCode(ifscCodeResponse.get());
				
				//Bean Util Conversion for Dto to entity
				BeanUtils.copyProperties(payeeRequestDto, payeeAccount);
				payeeAccount.setIsFavorite(false);
				payeeAccountRepository.save(payeeAccount);
				responseDto.setMessage(AppConstant.SUCCESS);
			} else {
				responseDto.setMessage(AppConstant.PAYEE_ALREADY_EXIST);
			}

		} else {
			throw new NotFoundException(AppConstant.NO_RECORD_FOUND);
		}

		return responseDto;
	}
}
