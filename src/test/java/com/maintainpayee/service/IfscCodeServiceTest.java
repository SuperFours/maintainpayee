package com.maintainpayee.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.maintainpayee.constant.AppConstant;
import com.maintainpayee.dto.IfscCodeDto;
import com.maintainpayee.dto.IfscCodeResponseDto;
import com.maintainpayee.entity.IfscCode;
import com.maintainpayee.repository.IfscCodeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class IfscCodeServiceTest {
	
	@InjectMocks
	IfscCodeServiceImpl ifscCodeServiceImpl;
	
	@Mock
	IfscCodeRepository ifscCodeRepository;

	IfscCode ifsc=new IfscCode();
	IfscCodeDto ifscCodeDto = new IfscCodeDto();
	IfscCodeResponseDto response=new IfscCodeResponseDto();
	
	@Test
	public void testGetIfscCode() {
		ifscCodeDto.setBankName("Icici");
		ifscCodeDto.setBranchName("banglore");
		ifscCodeDto.setIfscCode("Icic123");
		response.setIfscCode(ifscCodeDto);
		response.setMessage(AppConstant.SUCCESS);
		
		Mockito.when(ifscCodeRepository.findByCode("ICICI123")).thenReturn(Optional.of(ifsc));
		IfscCodeResponseDto response=ifscCodeServiceImpl.getIfscCode("ICICI123");
		assertEquals(AppConstant.SUCCESS, response.getMessage());
	}
	
	
	@Test
	public void testGetIfscCodeNegative() {
		response.setMessage(AppConstant.NO_RECORD_FOUND);
		Mockito.when(ifscCodeRepository.findByCode("Icic123")).thenReturn(null);
		IfscCodeResponseDto response=ifscCodeServiceImpl.getIfscCode(null);
		assertEquals(AppConstant.NO_RECORD_FOUND, response.getMessage());
	}
}
