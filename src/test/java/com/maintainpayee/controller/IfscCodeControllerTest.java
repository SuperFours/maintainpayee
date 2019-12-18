package com.maintainpayee.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.maintainpayee.constant.AppConstant;
import com.maintainpayee.dto.IfscCodeDto;
import com.maintainpayee.dto.IfscCodeResponseDto;
import com.maintainpayee.service.IfscCodeService;

@RunWith(SpringJUnit4ClassRunner.class)
public class IfscCodeControllerTest {
	
	@InjectMocks
	IfscCodeController ifscCodeController;
	
	@Mock
	IfscCodeService ifscCodeService;
	
	IfscCodeResponseDto response=new IfscCodeResponseDto();
	IfscCodeDto responseDto=new IfscCodeDto();
	
	
	
	
	@Test
	public void testGetIfscCode() {
		responseDto.setBankName("ICICI");
		responseDto.setBranchName("banglore");
		responseDto.setIfscCode("ICIC123");
		response.setIfscCode(responseDto);
		response.setMessage(AppConstant.SUCCESS);
		
		Mockito.when(ifscCodeService.getIfscCode("ICIC123")).thenReturn(response);
		ResponseEntity<IfscCodeResponseDto>  responses=ifscCodeController.getIfscCode("ICIC123");
		assertEquals(AppConstant.SUCCESS, responses.getBody().getMessage());
		
	}

	@Test
	public void testGetIfscCodeNegative() {
		responseDto.setBankName("ICICI");
		responseDto.setBranchName("banglore");
		responseDto.setIfscCode("ICIC123");
		response.setIfscCode(responseDto);
		response.setMessage(AppConstant.NO_RECORD_FOUND);
		response.setStatusCode(404);
		
		Mockito.when(ifscCodeService.getIfscCode(null)).thenReturn(response);
		ResponseEntity<IfscCodeResponseDto>  responses=ifscCodeController.getIfscCode(null);
		assertEquals(AppConstant.NO_RECORD_FOUND, responses.getBody().getMessage());
		assertEquals(404, responses.getBody().getStatusCode());
		
	}
	

}
