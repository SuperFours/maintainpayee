package com.maintainpayee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maintainpayee.constant.AppConstant;
import com.maintainpayee.dto.FavouritePayeeAccountResponseDto;
import com.maintainpayee.dto.PayeeAccountRequestDto;
import com.maintainpayee.dto.ResponseDto;
import com.maintainpayee.dto.ViewPayeeResponseDto;
import com.maintainpayee.service.PayeeAccountService;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * @description Payee Account Controller -> get the all favourite payee list,
 *              get the payee account
 * 
 * @author Janani.V
 * @since 17-12-2019
 *
 */
@RestController
@RequestMapping("/payee/accounts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class PayeeAccountController {

	@Autowired
	PayeeAccountService payeeAccountService;

	@GetMapping("/{loginId}/payees")
	public ResponseEntity<FavouritePayeeAccountResponseDto> getAllFavPayees(@PathVariable String loginId) {
		FavouritePayeeAccountResponseDto favouritePayeeAccountResponse = payeeAccountService
				.getAllFavouriteAccounts(loginId);
		if (favouritePayeeAccountResponse.getMessage().equals(AppConstant.SUCCESS)) {
			favouritePayeeAccountResponse.setStatusCode(HttpStatus.OK.value());
		} else {
			favouritePayeeAccountResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		}
		return new ResponseEntity<>(favouritePayeeAccountResponse, HttpStatus.OK);
	}

	/**
	 * get the payee detail by id
	 * 
	 * @param payeeId
	 * @return view detail of the payee details via dto.
	 */
	@GetMapping("/{payeeId}")
	public ResponseEntity<ViewPayeeResponseDto> getPayeeDetail(@PathVariable Integer payeeId) {

		ViewPayeeResponseDto responseDto = payeeAccountService.getPayeeAccount(payeeId);
		if (responseDto.getMessage().equals(AppConstant.SUCCESS)) {
			responseDto.setStatusCode(HttpStatus.OK.value());
		} else {
			responseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
		}
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	/**
	 * @description this method is used to create the new payee in respective DB
	 * @param PayeeRequestDto object set of input fields to create payee
	 * @return ResponseDto object contains response message and status
	 * @throws NotFoundException
	 */
	@PostMapping
	public ResponseEntity<ResponseDto> addPayee(@RequestBody PayeeAccountRequestDto payeeRequestDto)
			throws NotFoundException {

		log.info("creating new payee");
		ResponseDto responseDto = payeeAccountService.createPayee(payeeRequestDto);
		if (responseDto != null) {
			responseDto.setStatusCode(HttpStatus.OK.value());
		}
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDto> deletePayee(Integer id) {
		ResponseDto response = payeeAccountService.deleteAccount(id);
		if (response.getMessage().equals(AppConstant.DELETE_SUCCESS)) {
			response.setStatusCode(HttpStatus.OK.value());
		} else {
			response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		}

		return new ResponseEntity<>(HttpStatus.OK);

	}

	/**
	 * @description this method is used to update the new payee in respective DB
	 * @param PayeeRequestDto object set of input fields to update the payee
	 * @return ResponseDto object contains response message and status
	 */
	@PutMapping("/{id}")
	public ResponseEntity<ResponseDto> updatePayee(@RequestBody PayeeAccountRequestDto payeeRequestDto,
			@PathVariable Integer id) throws NotFoundException {

		log.info("update payee");
		ResponseDto responseDto = payeeAccountService.updatePayee(payeeRequestDto, id);
		if (responseDto != null) {
			responseDto.setStatusCode(HttpStatus.OK.value());
		}
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}
}
