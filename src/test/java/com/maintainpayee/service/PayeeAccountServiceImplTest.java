package com.maintainpayee.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.maintainpayee.constant.AppConstant;
import com.maintainpayee.dto.FavouritePayeeAccountResponseDto;
import com.maintainpayee.dto.PayeeAccountRequestDto;
import com.maintainpayee.dto.ResponseDto;
import com.maintainpayee.dto.ViewPayeeResponseDto;
import com.maintainpayee.entity.Customer;
import com.maintainpayee.entity.IfscCode;
import com.maintainpayee.entity.PayeeAccount;
import com.maintainpayee.repository.CustomerRepository;
import com.maintainpayee.repository.IfscCodeRepository;
import com.maintainpayee.repository.PayeeAccountRepository;

import javassist.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
public class PayeeAccountServiceImplTest {

	@InjectMocks
	PayeeAccountServiceImpl payeeAccountServiceImpl;

	@Mock
	PayeeAccountRepository payeeAccountRepository;
	@Mock
	CustomerRepository customerRepository;

	@Mock
	IfscCodeRepository ifscCodeRepository;

	@Mock
	RestTemplate restTemplate;

	PayeeAccountRequestDto payeeAccountRequestDto = new PayeeAccountRequestDto();
	ResponseDto responseDto = new ResponseDto();

	Customer customer = new Customer();
	IfscCode ifscCode = new IfscCode();

	PayeeAccount payeeAccount = new PayeeAccount();
	FavouritePayeeAccountResponseDto response = new FavouritePayeeAccountResponseDto();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		payeeAccountRequestDto.setAccountNumber("098765432");
		payeeAccountRequestDto.setCustomerId("1");
		payeeAccountRequestDto.setIfscCode("ICIC000027");
		payeeAccountRequestDto.setIsFavorite(false);
		payeeAccountRequestDto.setName("Raghu Ram");
		payeeAccountRequestDto.setNickName("ram");

		customer.setId(1);
		customer.setPhoneNumber("8147170027");
		customer.setFirstName("Raghu");
		customer.setLastName("Ram");

		ifscCode.setCode("ICIC000027");
		ifscCode.setBankName("ICICI Bank");
		ifscCode.setBranchName("BTM Layout");

		payeeAccount.setId(2);
		payeeAccount.setCustomerId(customer);
		payeeAccount.setIfscCode(ifscCode);
	}

	@Test
	public void testCreatePayeePayeeAlreadyExist() throws NotFoundException {

		when(payeeAccountRepository.getPayeeAccountCount(payeeAccountRequestDto.getCustomerId())).thenReturn(1);

		when(customerRepository.findByPhoneNumber(payeeAccountRequestDto.getCustomerId()))
				.thenReturn(Optional.of(customer));

		when(ifscCodeRepository.findByCode(payeeAccountRequestDto.getIfscCode())).thenReturn(Optional.of(ifscCode));

		when(payeeAccountRepository.findByAccountNumberAndCustomerIdId(payeeAccountRequestDto.getAccountNumber(),
				customer.getId())).thenReturn(Optional.of(payeeAccount));

		ResponseDto responseDto = payeeAccountServiceImpl.createPayee(payeeAccountRequestDto);
		assertEquals(AppConstant.PAYEE_ALREADY_EXIST, responseDto.getMessage());
	}

	@Test
	public void testGetAllFavouriteAccounts() {
		payeeAccount.setAccountNumber("ICICI1234");
		ifscCode.setBankName("ICICI");
		payeeAccount.setIfscCode(ifscCode);
		List<PayeeAccount> payees = new ArrayList<>();
		payees.add(payeeAccount);

		Mockito.when(customerRepository.findByPhoneNumber("Janani")).thenReturn(Optional.of(customer));
		Mockito.when(payeeAccountRepository.findByCustomerIdId(1)).thenReturn(payees);

		response.setMessage(AppConstant.SUCCESS);
		FavouritePayeeAccountResponseDto payeeAccountDto = payeeAccountServiceImpl.getAllFavouriteAccounts("Janani");
		assertEquals(AppConstant.SUCCESS, payeeAccountDto.getMessage());
	}

	@Test
	public void testGetAllFavouriteAccountsnegative() {
		payeeAccount.setAccountNumber("ICICI1234");
		List<PayeeAccount> payees = new ArrayList<>();
		payees.add(payeeAccount);

		Mockito.when(customerRepository.findByPhoneNumber("Janani")).thenReturn(Optional.of(customer));
		Mockito.when(payeeAccountRepository.findByCustomerIdId(1)).thenReturn(payees);

		response.setMessage(AppConstant.SUCCESS);
		FavouritePayeeAccountResponseDto payeeAccountDto = payeeAccountServiceImpl.getAllFavouriteAccounts(null);
		assertEquals(AppConstant.NO_CUSTOMERS_FOUND, payeeAccountDto.getMessage());
	}

	@Test
	public void testDeleteAccount() {
		ResponseDto response = new ResponseDto();
		response.setMessage(AppConstant.DELETE_SUCCESS);

		Mockito.when(payeeAccountRepository.findPayeeAccountById(123)).thenReturn(payeeAccount);
		ResponseDto responses = payeeAccountServiceImpl.deleteAccount(123);
		assertEquals(AppConstant.DELETE_SUCCESS, responses.getMessage());
	}

	@Test
	public void testDeleteAccountNegative() {
		response.setMessage(AppConstant.NO_ACCOUNT_FOUND);

		Mockito.when(payeeAccountRepository.findPayeeAccountById(123)).thenReturn(null);
		ResponseDto responses = payeeAccountServiceImpl.deleteAccount(null);
		assertEquals(AppConstant.NO_ACCOUNT_FOUND, responses.getMessage());
	}

	@Test(expected = NotFoundException.class)
	public void testCreatePayeeForException() throws NotFoundException {
		when(payeeAccountRepository.getPayeeAccountCount(payeeAccountRequestDto.getCustomerId())).thenReturn(2);
		when(customerRepository.findByPhoneNumber(payeeAccountRequestDto.getCustomerId()))
				.thenReturn(Optional.of(customer));
		when(ifscCodeRepository.findByCode(payeeAccountRequestDto.getIfscCode())).thenReturn(Optional.of(ifscCode));
		when(payeeAccountRepository.findByAccountNumberAndCustomerIdId(payeeAccountRequestDto.getAccountNumber(), 2))
				.thenReturn(Optional.of(payeeAccount));
		payeeAccountServiceImpl.createPayee(payeeAccountRequestDto);
	}
	
	@Test(expected = NotFoundException.class)
	public void testCreatePayeeForException1() throws NotFoundException {
		when(payeeAccountRepository.getPayeeAccountCount(payeeAccountRequestDto.getCustomerId())).thenReturn(2);
		when(customerRepository.findByPhoneNumber(payeeAccountRequestDto.getCustomerId()))
				.thenReturn(Optional.ofNullable(null));
		when(ifscCodeRepository.findByCode(payeeAccountRequestDto.getIfscCode())).thenReturn(Optional.of(ifscCode));
		payeeAccountServiceImpl.createPayee(payeeAccountRequestDto);
	}
	
	@Test
	public void testCreatePayeeForCheckPayeeCount() throws NotFoundException {
		when(payeeAccountRepository.getPayeeAccountCount(payeeAccountRequestDto.getCustomerId())).thenReturn(11);
		ResponseDto result = payeeAccountServiceImpl.createPayee(payeeAccountRequestDto);
		assertEquals(AppConstant.PAYEE_REACHED_MAXIMUM, result.getMessage());
	}

	@Test(expected = NotFoundException.class)
	public void testUpdatePayee() throws NotFoundException {
		when(customerRepository.findByPhoneNumber(payeeAccountRequestDto.getCustomerId()))
				.thenReturn(Optional.of(customer));
		when(ifscCodeRepository.findByCode(payeeAccountRequestDto.getIfscCode())).thenReturn(Optional.of(ifscCode));
		when(payeeAccountRepository.findById(1)).thenReturn(Optional.of(payeeAccount));
		payeeAccountServiceImpl.updatePayee(payeeAccountRequestDto, 1);
	}
	
	@Test(expected = NotFoundException.class)
	public void testUpdatePayeeForException1() throws NotFoundException {
		when(payeeAccountRepository.getPayeeAccountCount(payeeAccountRequestDto.getCustomerId())).thenReturn(2);
		when(customerRepository.findByPhoneNumber(payeeAccountRequestDto.getCustomerId()))
				.thenReturn(Optional.ofNullable(null));
		when(ifscCodeRepository.findByCode(payeeAccountRequestDto.getIfscCode())).thenReturn(Optional.of(ifscCode));
		payeeAccountServiceImpl.updatePayee(payeeAccountRequestDto, 1);
	}

	@Test
	public void testGetPayeeAccount() {
		when(payeeAccountRepository.findById(1)).thenReturn(Optional.of(payeeAccount));
		ViewPayeeResponseDto response = payeeAccountServiceImpl.getPayeeAccount(1);
		assertEquals(AppConstant.SUCCESS, response.getMessage());
	}

	@Test
	public void testGetPayeeAccountForNegative() {
		when(payeeAccountRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		ViewPayeeResponseDto response = payeeAccountServiceImpl.getPayeeAccount(1);
		assertEquals(AppConstant.NO_RECORD_FOUND, response.getMessage());
	}

}
