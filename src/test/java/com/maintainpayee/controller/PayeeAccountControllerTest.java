package com.maintainpayee.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.maintainpayee.constant.AppConstant;
import com.maintainpayee.dto.PayeeAccountRequestDto;
import com.maintainpayee.dto.ResponseDto;
import com.maintainpayee.entity.PayeeAccount;
import com.maintainpayee.service.PayeeAccountService;

import javassist.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
public class PayeeAccountControllerTest {

	@InjectMocks
	PayeeAccountController payeeAccountController;

	@Mock
	PayeeAccountService payeeAccountService;

	PayeeAccountRequestDto payeeAccountRequestDto = new PayeeAccountRequestDto();
	ResponseDto responseDto = new ResponseDto();

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

		payeeAccount.setId(1);
	}
	
	@Test
	public void testAddPayee() throws NotFoundException {
		
		responseDto.setStatus(AppConstant.SUCCESS);
		responseDto.setStatusCode(HttpStatus.OK.value());
		responseDto.setMessage(AppConstant.OPERATION_SUCCESS);
		
		when(payeeAccountService.createPayee(payeeAccountRequestDto)).thenReturn(responseDto);
		ResponseEntity<ResponseDto> response = payeeAccountController.addPayee(payeeAccountRequestDto);
		assertEquals(AppConstant.OPERATION_SUCCESS, response.getBody().getMessage());
	}
	
	@Test
	public void testUpdatePayee() throws NotFoundException {
		responseDto.setStatus(AppConstant.SUCCESS);
		responseDto.setMessage(AppConstant.OPERATION_SUCCESS);

		when(payeeAccountService.updatePayee(payeeAccountRequestDto, payeeAccount.getId())).thenReturn(responseDto);

		ResponseEntity<ResponseDto> response = payeeAccountController.updatePayee(payeeAccountRequestDto, payeeAccount.getId());
		assertEquals(AppConstant.SUCCESS, response.getBody().getStatus());
		assertEquals(HttpStatus.OK.value(), response.getBody().getStatusCode());
		assertEquals(AppConstant.OPERATION_SUCCESS, response.getBody().getMessage());
	}
}
