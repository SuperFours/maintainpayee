package com.maintainpayee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maintainpayee.constant.AppConstant;
import com.maintainpayee.dto.IfscCodeResponseDto;
import com.maintainpayee.service.IfscCodeService;

/**
 * @description Ifsc code controller, In this Controller we can able get the
 *              ifsc code detail actions of get list of ifsc codes and get the
 *              particular ifsc code detail.
 * @author Govindasamy.C
 * @since 17-12-2019
 *
 */
@RestController
@RequestMapping("/ifsc/codes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class IfscCodeController {

	@Autowired
	IfscCodeService ifscCodeService;

	/**
	 * @description get the ifsc code detail based on the user enter the ifsc code.
	 * @return IfscCodeResponseDto values we can set the detail of the ifsc code,
	 *         bank name and branchName along with sucess message and status code.
	 */
	@GetMapping("/{code}")
	public ResponseEntity<IfscCodeResponseDto> getIfscCode(@PathVariable String code) {
		IfscCodeResponseDto ifscCodeResponseDto = ifscCodeService.getIfscCode(code);
		if(ifscCodeResponseDto.getMessage().equals(AppConstant.SUCCESS)) {
			ifscCodeResponseDto.setStatusCode(HttpStatus.OK.value());
		}else {
			ifscCodeResponseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
		}
		return new ResponseEntity<>(ifscCodeResponseDto, HttpStatus.OK);
	}
}
