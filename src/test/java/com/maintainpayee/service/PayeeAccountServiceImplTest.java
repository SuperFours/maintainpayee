package com.maintainpayee.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.maintainpayee.constant.AppConstant;
import com.maintainpayee.dto.PayeeAccountRequestDto;
import com.maintainpayee.dto.ResponseDto;
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

	PayeeAccountRequestDto payeeAccountRequestDto = new PayeeAccountRequestDto();
	ResponseDto responseDto = new ResponseDto();

	Customer customer = new Customer();
	IfscCode ifscCode = new IfscCode();

	PayeeAccount payeeAccount = new PayeeAccount();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		payeeAccountRequestDto.setAccountNumber("098765432");
		payeeAccountRequestDto.setCustomerId("1");
		payeeAccountRequestDto.setIfscCode("ICIC000027");
		payeeAccountRequestDto.setIsFavorite(false);
		payeeAccountRequestDto.setName("Raghu Ram");
		payeeAccountRequestDto.setNickName("ram");
		
		payeeAccount.setId(2);
		payeeAccount.setCustomerId(customer);

		customer.setId(1);
		customer.setPhoneNumber("8147170027");
		customer.setFirstName("Raghu");
		customer.setLastName("Ram");
		
		ifscCode.setCode("ICIC000027");
		ifscCode.setBankName("ICICI Bank");
		ifscCode.setBranchName("BTM Layout");
	}

	@Test
	public void testCreatePayeePayeeAlreadyExist() throws NotFoundException {
		
		when(payeeAccountRepository.getPayeeAccountCount(payeeAccountRequestDto.getCustomerId())).thenReturn(1);
		
		when(customerRepository.findByPhoneNumber(payeeAccountRequestDto.getCustomerId())).thenReturn(Optional.of(customer));
		
		when(ifscCodeRepository.findByCode(payeeAccountRequestDto.getIfscCode())).thenReturn(Optional.of(ifscCode));
		
		when(payeeAccountRepository.findByAccountNumberAndCustomerIdId(
				payeeAccountRequestDto.getAccountNumber(), customer.getId())).thenReturn(Optional.of(payeeAccount));
		
		ResponseDto responseDto = payeeAccountServiceImpl.createPayee(payeeAccountRequestDto);
		assertEquals(AppConstant.PAYEE_ALREADY_EXIST, responseDto.getMessage());
	}
	
	
	@Test
	public void testCreatePayee() throws NotFoundException {
		
		when(payeeAccountRepository.getPayeeAccountCount(payeeAccountRequestDto.getCustomerId())).thenReturn(2);
		
		when(customerRepository.findByPhoneNumber(payeeAccountRequestDto.getCustomerId())).thenReturn(Optional.of(customer));
		
		when(ifscCodeRepository.findByCode(payeeAccountRequestDto.getIfscCode())).thenReturn(Optional.of(ifscCode));
		
		when(payeeAccountRepository.findByAccountNumberAndCustomerIdId(
				payeeAccountRequestDto.getAccountNumber(), 2)).thenReturn(Optional.of(payeeAccount));
		
		ResponseDto responseDto = payeeAccountServiceImpl.createPayee(payeeAccountRequestDto);
		assertEquals(AppConstant.SUCCESS, responseDto.getMessage());
	}
	
	@Test
	public void testUpdatePayee() throws NotFoundException {
		
		when(customerRepository.findByPhoneNumber(payeeAccountRequestDto.getCustomerId())).thenReturn(Optional.of(customer));
		
		when(ifscCodeRepository.findByCode(payeeAccountRequestDto.getIfscCode())).thenReturn(Optional.of(ifscCode));
		
		when(payeeAccountRepository.findById(1)).thenReturn(Optional.of(payeeAccount));
		
		ResponseDto responseDto = payeeAccountServiceImpl.updatePayee(payeeAccountRequestDto, 1);
		assertEquals(AppConstant.PAYEE_UPDATED, responseDto.getMessage());
	}
}
