package com.maintainpayee.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maintainpayee.constant.AppConstant;
import com.maintainpayee.dto.IfscCodeDto;
import com.maintainpayee.dto.IfscCodeResponseDto;
import com.maintainpayee.entity.IfscCode;
import com.maintainpayee.repository.IfscCodeRepository;

/**
 * @dsecription Ifsc code service impl class, In this class we can able get the
 *              ifsc code detail actions of get list of ifsc codes and get the
 *              particular ifsc code detail.
 * @author Govindasamy.C
 * @since 17-12-2019
 *
 */
@Service
public class IfscCodeServiceImpl implements IfscCodeService {

	@Autowired
	IfscCodeRepository ifscCodeRepository;

	/**
	 * @description get the ifsc code details such as ifsc code, bank name and
	 *              branch name based on the ifsc code search bu user.
	 * @param param value is code by user search ifsc code.
	 * @return returning the detail of the ifsc code along with statuscode and
	 *         message for response purpose.
	 */
	@Override
	public IfscCodeResponseDto getIfscCode(String code) {
		IfscCodeResponseDto ifscCodeResponseDto = new IfscCodeResponseDto();
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
