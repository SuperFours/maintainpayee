package com.maintainpayee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maintainpayee.constant.AppConstant;
import com.maintainpayee.dto.FavouritePayeeAccountResponseDto;
import com.maintainpayee.dto.PayeeAccountRequestDto;
import com.maintainpayee.dto.ResponseDto;
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

	@GetMapping
	public ResponseEntity<FavouritePayeeAccountResponseDto> getAllFavPayees() {
		FavouritePayeeAccountResponseDto favouritePayeeAccountResponse = payeeAccountService.getAllFavouriteAccounts();
		favouritePayeeAccountResponse.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(favouritePayeeAccountResponse, HttpStatus.OK);
	}

	/**
	 * @description this method is used to create the new payee in respective DB
	 * @param PayeeRequestDto object set of input fields to create payee
	 * @return ResponseDto object contains response message and status
	 * @throws NotFoundException
	 */
	@PostMapping("/accounts")
	public ResponseEntity<ResponseDto> addPayee(@RequestBody PayeeAccountRequestDto payeeRequestDto)
			throws NotFoundException {

		log.info("creating new payee");
		ResponseDto responseDto = payeeAccountService.createPayee(payeeRequestDto);
		if (responseDto != null) {
			responseDto.setStatusCode(HttpStatus.OK.value());
		}
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}

	@DeleteMapping("/accounts/{id}") 
	public ResponseEntity<ResponseDto> deletePayee(Integer id){
		ResponseDto response=payeeAccountService.deleteAccount(id);
		if (response.getStatus().equals(AppConstant.DELETE_SUCCESS)) {
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
	public ResponseEntity<ResponseDto> updatePayee(@RequestBody PayeeAccountRequestDto payeeRequestDto, @PathVariable Integer id) throws NotFoundException {

		log.info("update payee");
		ResponseDto responseDto = payeeAccountService.updatePayee(payeeRequestDto, id);
		if (responseDto != null) {
			responseDto.setStatusCode(HttpStatus.OK.value());
		}
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}
}
