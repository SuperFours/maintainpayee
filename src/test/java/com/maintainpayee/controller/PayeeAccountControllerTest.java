package com.maintainpayee.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maintainpayee.constant.AppConstant;
import com.maintainpayee.dto.FavouritePayeeAccountDto;
import com.maintainpayee.dto.FavouritePayeeAccountResponseDto;
import com.maintainpayee.dto.PayeeAccountRequestDto;
import com.maintainpayee.dto.ResponseDto;
import com.maintainpayee.dto.ViewPayeeDto;
import com.maintainpayee.dto.ViewPayeeResponseDto;
import com.maintainpayee.entity.PayeeAccount;
import com.maintainpayee.exception.CustomExceptionHandler;
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
	FavouritePayeeAccountResponseDto response = new FavouritePayeeAccountResponseDto();
	FavouritePayeeAccountDto favDto = new FavouritePayeeAccountDto();
	List<FavouritePayeeAccountDto> favDtos = new ArrayList<>();

	ViewPayeeResponseDto viewDtos = new ViewPayeeResponseDto();
	ViewPayeeDto viewDto = new ViewPayeeDto();

	private MockMvc mockMvc;

	PayeeAccount payeeAccount = new PayeeAccount();

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(payeeAccountController).build();

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
	public void testAddPayeeInvalidData() throws Exception {

		WebRequest webrequest = null;
		payeeAccountRequestDto.setAccountNumber(null);

		// MvcResult for mockmvc performed
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/payee/accounts").content(asJsonString(payeeAccountRequestDto))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		assertThat(result).isNotNull();

		new CustomExceptionHandler().handleException(result.getResolvedException(), webrequest);
	}

	@Test
	public void testUpdatePayee() throws NotFoundException {
		responseDto.setStatus(AppConstant.SUCCESS);
		responseDto.setMessage(AppConstant.OPERATION_SUCCESS);

		when(payeeAccountService.updatePayee(payeeAccountRequestDto, payeeAccount.getId())).thenReturn(responseDto);

		ResponseEntity<ResponseDto> response = payeeAccountController.updatePayee(payeeAccountRequestDto,
				payeeAccount.getId());
		assertEquals(AppConstant.SUCCESS, response.getBody().getStatus());
		assertEquals(HttpStatus.OK.value(), response.getBody().getStatusCode());
		assertEquals(AppConstant.OPERATION_SUCCESS, response.getBody().getMessage());
	}

	@Test
	public void testGetAllFavPayees() {
		favDto.setAccountNumber("ICICI123");
		favDto.setBankName("ICICI");
		favDto.setBranchName("banglore");
		favDto.setId(1);
		favDto.setIsFavorite(true);
		favDto.setNickName("Jaanu");
		favDtos.add(favDto);
		response.setMessage(AppConstant.SUCCESS);
		response.setPayees(favDtos);

		Mockito.when(payeeAccountService.getAllFavouriteAccounts(Mockito.any())).thenReturn(response);
		ResponseEntity<FavouritePayeeAccountResponseDto> responses = payeeAccountController.getAllFavPayees("Jaanu");
		assertEquals(AppConstant.SUCCESS, responses.getBody().getMessage());
		assertEquals(200, responses.getBody().getStatusCode());
	}

	@Test
	public void testGetPayeeDetail() {
		viewDto.setAccountNumber("ICICI123");
		viewDto.setIfscCode("ICIC4567");
		viewDto.setIsFavorite(true);
		viewDto.setNickName("Jaanu");
		viewDto.setPayeeId(1);
		viewDtos.setPayee(viewDto);
		viewDtos.setMessage(AppConstant.SUCCESS);
		viewDtos.setStatusCode(200);

		Mockito.when(payeeAccountService.getPayeeAccount(123)).thenReturn(viewDtos);
		ResponseEntity<ViewPayeeResponseDto> response = payeeAccountController.getPayeeDetail(123);
		assertEquals(AppConstant.SUCCESS, response.getBody().getMessage());
		assertEquals(200, response.getBody().getStatusCode());
	}

	@Test
	public void testDeletePayee() {
		responseDto.setMessage(AppConstant.DELETE_SUCCESS);
		responseDto.setStatusCode(200);

		Mockito.when(payeeAccountService.deleteAccount(123)).thenReturn(responseDto);
		ResponseEntity<ResponseDto> reponses = payeeAccountController.deletePayee(123);
		assertEquals(AppConstant.DELETE_SUCCESS, reponses.getBody().getMessage());
		assertEquals(200, reponses.getBody().getStatusCode());
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static String asJsonString(final Object obj) throws Exception {
		return new ObjectMapper().writeValueAsString(obj);
	}
}
