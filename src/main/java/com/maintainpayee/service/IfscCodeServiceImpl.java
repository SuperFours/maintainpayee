package com.maintainpayee.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maintainpayee.constant.AppConstant;
import com.maintainpayee.controller.IfscCodeController;
import com.maintainpayee.dto.IfscCodeDto;
import com.maintainpayee.dto.IfscCodeResponseDto;
import com.maintainpayee.entity.IfscCode;
import com.maintainpayee.repository.IfscCodeRepository;

/**
 * Ifsc code service impl class, In this class we can able get the ifsc code
 * detail actions of get list of ifsc codes and get the particular ifsc code
 * detail.
 * 
 * @author Govindasamy.C
 * @since 17-12-2019
 * @version V1.1
 *
 */
@Service
public class IfscCodeServiceImpl implements IfscCodeService {
	public static final Logger logger = LoggerFactory.getLogger(IfscCodeController.class);

	@Autowired
	IfscCodeRepository ifscCodeRepository;

	/**
	 * get the ifsc code details such as ifsc code, bank name and branch name based
	 * on the ifsc code search bu user.
	 * 
	 * @param param value is code by user search ifsc code.
	 * @return returning the detail of the ifsc code along with statuscode and
	 *         message for response purpose.
	 * @see responseDto get the ifsc code details.
	 */
	@Override
	public IfscCodeResponseDto getIfscCode(String code) {
		logger.info("get the Ifsc code...");
		IfscCodeResponseDto ifscCodeResponseDto = new IfscCodeResponseDto();
		//Check the ifsc code is present or not.
		Optional<IfscCode> ifscCode = ifscCodeRepository.findByCode(code);
		if (ifscCode.isPresent()) {
			IfscCodeDto ifscCodeDto = new IfscCodeDto();
			ifscCodeDto.setIfscCode(ifscCode.get().getCode());
			ifscCodeDto.setBankName(ifscCode.get().getBankName());
			ifscCodeDto.setBranchName(ifscCode.get().getBranchName());
			ifscCodeResponseDto.setIfscCode(ifscCodeDto);
			ifscCodeResponseDto.setMessage(AppConstant.SUCCESS);
		} else {
			ifscCodeResponseDto.setMessage(AppConstant.NO_RECORD_FOUND);
		}
		return ifscCodeResponseDto;
	}
}
