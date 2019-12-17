package com.maintainpayee.service;

import com.maintainpayee.dto.IfscCodeResponseDto;

public interface IfscCodeService {

	public IfscCodeResponseDto getIfscCode(String code);
}
