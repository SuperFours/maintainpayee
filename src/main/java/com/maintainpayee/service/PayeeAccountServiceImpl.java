package com.maintainpayee.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.maintainpayee.constant.AppConstant;
import com.maintainpayee.dto.FavouritePayeeAccountDto;
import com.maintainpayee.dto.FavouritePayeeAccountResponseDto;
import com.maintainpayee.dto.PayeeAccountRequestDto;
import com.maintainpayee.dto.ResponseDto;
import com.maintainpayee.dto.UserAccountResponseDto;
import com.maintainpayee.dto.ViewPayeeDto;
import com.maintainpayee.dto.ViewPayeeResponseDto;
import com.maintainpayee.entity.Customer;
import com.maintainpayee.entity.IfscCode;
import com.maintainpayee.entity.PayeeAccount;
import com.maintainpayee.repository.CustomerRepository;
import com.maintainpayee.repository.IfscCodeRepository;
import com.maintainpayee.repository.PayeeAccountRepository;

import javassist.NotFoundException;

/**
 * <h1>Payee Account Service Impl</h1> This class as used for implement the get
 * all payee list based on the account number, get the account detail, add
 * payee, update payee and delete payee of the payee accounts.
 * 
 * @author Govindasamy.C
 * @since 17-12-2019
 * @version V1.1
 *
 */
@Service
@Transactional
public class PayeeAccountServiceImpl implements PayeeAccountService {
	public static final Logger logger = LoggerFactory.getLogger(PayeeAccountServiceImpl.class);

	@Autowired
	PayeeAccountRepository payeeAccountRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	IfscCodeRepository ifscCodeRepository;

	/**
	 * Get the favorite the payee accounts based on the customer login.
	 * 
	 * @param loginId user login id for the input purpose.
	 * @return payeeAccountDto return the all payee account details based on the
	 *         customer login.
	 * @see return the all favorite accounts based on the customer.
	 */
	@Override
	public FavouritePayeeAccountResponseDto getAllFavouriteAccounts(String userId) {

		FavouritePayeeAccountResponseDto response = new FavouritePayeeAccountResponseDto();
		Optional<Customer> customer = customerRepository.findByPhoneNumber(userId);
		if (customer.isPresent()) {
			List<PayeeAccount> payees = payeeAccountRepository.findByCustomerIdId(customer.get().getId());
			List<FavouritePayeeAccountDto> payeeAccountDto = payees.stream().map(this::convertEntityToDto)
					.collect(Collectors.toList());
			response.setPayees(payeeAccountDto);
			response.setMessage(AppConstant.SUCCESS);
		} else {
			response.setMessage(AppConstant.NO_CUSTOMERS_FOUND);

		}

		return response;
	}

	/**
	 * Convert payee entity values to favorite payee dto with param values
	 * 
	 * @param payeeAccount entity for the purpose of convert values to dto.
	 * @return favoritePayeeAccountDto return the dto response values.
	 * @see favroitePayeeAccountDto response of the converting payee account
	 *      details.
	 */
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
		//Check the payee account count if its reach maximum limit or not.
		Integer payeeCount = payeeAccountRepository.getPayeeAccountCount(payeeRequestDto.getCustomerId());
		if (payeeCount < 10) {
			Optional<Customer> customerResponse = customerRepository.findByPhoneNumber(payeeRequestDto.getCustomerId());
			Optional<IfscCode> ifscCodeResponse = ifscCodeRepository.findByCode(payeeRequestDto.getIfscCode());
			if (customerResponse.isPresent() && ifscCodeResponse.isPresent()) {
				// Check the payee account number with customer if it is present or not.
				Optional<PayeeAccount> payeeAccountResponse = payeeAccountRepository.findByAccountNumberAndCustomerIdId(
						payeeRequestDto.getAccountNumber(), customerResponse.get().getId());
				if (!payeeAccountResponse.isPresent()) {
					// Call Rest Template for checking whether account number is exists or not.
					RestTemplate restTemplate = new RestTemplate();
					String endPointUrl = AppConstant.CHECK_ACCOUNT_NUMBER + payeeRequestDto.getAccountNumber();
					ResponseEntity<UserAccountResponseDto> getAccountNumber = restTemplate.getForEntity(endPointUrl,
							UserAccountResponseDto.class);
					UserAccountResponseDto restTemplateResponse = getAccountNumber.getBody();
					if (restTemplateResponse.getStatusCode().equals(AppConstant.SUCCESS_CODE)) {
						payeeAccount.setCustomerId(customerResponse.get());
						payeeAccount.setIfscCode(ifscCodeResponse.get());

						// Bean Util Conversion for Dto to entity
						BeanUtils.copyProperties(payeeRequestDto, payeeAccount);
						payeeAccount.setIsFavorite(false);
						payeeAccountRepository.save(payeeAccount);
						responseDto.setMessage(AppConstant.SUCCESS);
					} else {
						throw new NotFoundException(AppConstant.NO_ACCOUNT_NUMBER_FOUND);
					}
				} else {
					responseDto.setMessage(AppConstant.PAYEE_ALREADY_EXIST);
				}
			} else {
				throw new NotFoundException(AppConstant.NO_RECORD_FOUND);
			}
		} else {
			responseDto.setMessage(AppConstant.PAYEE_REACHED_MAXIMUM);
		}
		return responseDto;
	}

	/**
	 * Delete the payee detail based on the payee id
	 * 
	 * @param id pass the id value based on the user choosing the payee accont
	 *           detail.
	 * @return responseDto set the response for sucess/failure cases.
	 * @see responseDto response params
	 */
	@Override
	public ResponseDto deleteAccount(Integer id) {
		logger.info("delete the payee account by id", id);
		ResponseDto responseDto = new ResponseDto();
		PayeeAccount payeeAccount = payeeAccountRepository.findPayeeAccountById(id);
		Optional<PayeeAccount> isPayeeAccount = Optional.ofNullable(payeeAccount);
		if (isPayeeAccount.isPresent()) {
			payeeAccountRepository.deleteById(id);
			responseDto.setMessage(AppConstant.DELETE_SUCCESS);
		} else {
			responseDto.setMessage(AppConstant.NO_ACCOUNT_FOUND);
		}
		return responseDto;
	}

	/**
	 * @description this method is used to update the new payee in respective DB
	 * @param PayeeAccountRequestDto object set of input fields, id integer to
	 *                               update payee
	 * @return ResponseDto object contains response message and status
	 */
	@Override
	public ResponseDto updatePayee(PayeeAccountRequestDto newPayee, Integer id) throws NotFoundException {
		logger.info("update the payee account by id", id);
		ResponseDto responseDto = new ResponseDto();
		Optional<Customer> customerResponse = customerRepository.findByPhoneNumber(newPayee.getCustomerId());
		Optional<IfscCode> ifscCodeResponse = ifscCodeRepository.findByCode(newPayee.getIfscCode());
		if (customerResponse.isPresent() && ifscCodeResponse.isPresent()) {
			Optional<PayeeAccount> payeeAccountOptional = payeeAccountRepository.findById(id);
			if (payeeAccountOptional.isPresent()) {
				// Call Rest Template for checking whether account number is exists or not.
				RestTemplate restTemplate = new RestTemplate();
				String endPointUrl = AppConstant.CHECK_ACCOUNT_NUMBER + newPayee.getAccountNumber();
				ResponseEntity<UserAccountResponseDto> getAccountNumber = restTemplate.getForEntity(endPointUrl,
						UserAccountResponseDto.class);
				UserAccountResponseDto restTemplateResponse = getAccountNumber.getBody();
				if (restTemplateResponse.getStatusCode().equals(AppConstant.SUCCESS_CODE)) {
					PayeeAccount payeeAccount = payeeAccountOptional.get();
					payeeAccount.setAccountNumber(newPayee.getAccountNumber());
					payeeAccount.setIsFavorite(newPayee.getIsFavorite());
					payeeAccount.setName(newPayee.getName());
					payeeAccount.setNickName(newPayee.getNickName());
					payeeAccountRepository.save(payeeAccount);

					responseDto.setStatus(AppConstant.SUCCESS);
					responseDto.setMessage(AppConstant.PAYEE_UPDATED);

				} else {
					throw new NotFoundException(AppConstant.NO_ACCOUNT_NUMBER_FOUND);
				}
			}
		} else {
			throw new NotFoundException(AppConstant.NO_RECORD_FOUND);
		}
		return responseDto;
	}

	/**
	 * Get the payee detail by id
	 * 
	 * @param payeeId get the particular payee detail based on the payee account id.
	 * @return view detail of the payee details via dto.
	 */
	@Override
	public ViewPayeeResponseDto getPayeeAccount(Integer payeeId) {
		logger.info("update the payee account by id", payeeId);
		ViewPayeeResponseDto responseDto = new ViewPayeeResponseDto();
		Optional<PayeeAccount> payeeAccount = payeeAccountRepository.findById(payeeId);
		if (payeeAccount.isPresent()) {
			ViewPayeeDto viewPayeeDto = new ViewPayeeDto();
			viewPayeeDto.setAccountNumber(payeeAccount.get().getAccountNumber());
			viewPayeeDto.setIfscCode(payeeAccount.get().getIfscCode().getCode());
			viewPayeeDto.setNickName(payeeAccount.get().getNickName());
			viewPayeeDto.setPayeeId(payeeAccount.get().getId());
			viewPayeeDto.setIsFavorite(payeeAccount.get().getIsFavorite());
			responseDto.setPayee(viewPayeeDto);
			responseDto.setMessage(AppConstant.SUCCESS);
		} else {
			responseDto.setMessage(AppConstant.NO_RECORD_FOUND);
		}
		return responseDto;
	}
}