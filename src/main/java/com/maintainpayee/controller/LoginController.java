package com.maintainpayee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maintainpayee.constant.AppConstant;
import com.maintainpayee.dto.LoginDto;
import com.maintainpayee.dto.LoginResponseDto;
import com.maintainpayee.service.*;

import lombok.extern.slf4j.Slf4j;

/**
 * @description This is the login controller that is used for user login purpose
 *              Input is given for this method is username and password. Then
 *              user can login the application.
 * 
 * @author Janani
 * @since 17/12/2019
 */
@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class LoginController {

	@Autowired
	LoginService loginService;

	/**
	 * @description This is the login user method,it is used to login the
	 *              application.In that, we used username and password for the
	 *              inputs. In that we sent login dto with username and password
	 *              along with status code and message.
	 * 
	 * @param userLoginDto it has username and password fields.
	 * @return LoginResponseDto it has the user details like message and status
	 *         codes and user credentials.
	 */

	@PostMapping
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) {
		log.info("User login to the appliocation");
		LoginResponseDto responseDto = loginService.login(loginDto);
		if (responseDto.getStatus().equals(AppConstant.SUCCESS)) {
			responseDto.setStatusCode(HttpStatus.OK.value());
		} else {
			responseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
		}

		return new ResponseEntity<>(responseDto, HttpStatus.OK);

	}

}
