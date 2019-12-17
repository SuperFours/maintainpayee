package com.maintainpayee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maintainpayee.dto.FavouritePayeeAccountResponseDto;
import com.maintainpayee.service.PayeeAccountService;

/**
 * @description Payee Account Controller -> get the all favourite payee list,
 *              get the payee account
 * 
 * @author Janani.V
 * @since 17-12-2019
 *
 */
@RestController
@RequestMapping("/payees")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PayeeAccountController {

	@Autowired
	PayeeAccountService payeeAccountService;

	@GetMapping("/{accountId}")
	public ResponseEntity<FavouritePayeeAccountResponseDto> getAllFavPayees() {
		FavouritePayeeAccountResponseDto favouritePayeeAccountResponse = payeeAccountService.getAllFavouriteAccounts();
		favouritePayeeAccountResponse.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(favouritePayeeAccountResponse, HttpStatus.OK);
	}

}
