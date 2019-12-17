package com.maintainpayee.service;

import org.springframework.stereotype.Service;

import com.maintainpayee.dto.IfscCodeResponseDto;

@Service
public class IfscCodeServiceImpl implements IfscCodeService{

	@Autowired
	
	@Override
	public IfscCodeResponseDto getIfscCode(String code) {
		return null;
	}

}
