package com.maintainpayee.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Ifsc code controller, In this Controller we can able get the ifsc code detail
 * actions of get list of ifsc codes and get the particular ifsc code detail.
 * 
 * @author Govindasamy.C
 * @since 17-12-2019
 * @version V1.1
 * 
 */
@RestController
@RequestMapping("/ifsc/codes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class IfscCodeController {
	public static final Logger logger = LoggerFactory.getLogger(IfscCodeController.class);

	@Autowired
	IfscCodeService ifscCodeService;

	/**
	 * Get the ifsc code detail based on the user enter the ifsc code.
	 * 
	 * @return IfscCodeResponseDto values we can set the detail of the ifsc code,
	 *         bank name and branchName along with success message and status code.
	 * @see response of the ifsc details
	 */
	@GetMapping("/{code}")
	public ResponseEntity<IfscCodeResponseDto> getIfscCode(@PathVariable String code) {
		logger.info("Get ifsc code detail...", code);
		IfscCodeResponseDto ifscCodeResponseDto = ifscCodeService.getIfscCode(code);
		if (ifscCodeResponseDto.getMessage().equals(AppConstant.SUCCESS)) {
			ifscCodeResponseDto.setStatusCode(HttpStatus.OK.value());
		} else {
			ifscCodeResponseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
		}
		return new ResponseEntity<>(ifscCodeResponseDto, HttpStatus.OK);
	}
}
