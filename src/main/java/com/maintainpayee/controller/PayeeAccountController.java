package com.maintainpayee.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * <h1>Payee Account Controller</h1> This class as used for implement the get
 * all payee list based on the account number, get the account detail, add
 * payee, update payee and delete payee of the payee accounts.
 * 
 * @author Janani.V
 * @since 17-12-2019
 * @version V1.1
 */
@RestController
@RequestMapping("/payee/accounts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class PayeeAccountController {
	public static final Logger logger = LoggerFactory.getLogger(PayeeAccountController.class);

	@Autowired
	PayeeAccountService payeeAccountService;

	/**
	 * get the favorite the payee accounts based on the customer login.
	 * 
	 * @param loginId user login id for the input purpose.
	 * @return payeeAccountDto return the all payee account details based on the
	 *         customer login.
	 */
	@GetMapping("/{loginId}/payees")
	public ResponseEntity<FavouritePayeeAccountResponseDto> getAllFavPayees(@PathVariable String loginId) {
		logger.info("get all the favorite payees...");
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
	 * @param payeeId get the particular payee detail based on the payee account id.
	 * @return view detail of the payee details via dto.
	 */
	@GetMapping("/{payeeId}")
	public ResponseEntity<ViewPayeeResponseDto> getPayeeDetail(@PathVariable Integer payeeId) {
		logger.info("get payee detail...");
		ViewPayeeResponseDto responseDto = payeeAccountService.getPayeeAccount(payeeId);
		if (responseDto.getMessage().equals(AppConstant.SUCCESS)) {
			responseDto.setStatusCode(HttpStatus.OK.value());
		} else {
			responseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
		}
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	/**
	 * This method is used to create the new payee in respective DB
	 * 
	 * @param PayeeRequestDto object set of input fields to create payee
	 * @return ResponseDto object contains response message and status
	 * @throws NotFoundException
	 */
	@PostMapping
	public ResponseEntity<ResponseDto> addPayee(@RequestBody PayeeAccountRequestDto payeeRequestDto)
			throws NotFoundException {
		log.info("creating new payee");
		ResponseDto responseDto = payeeAccountService.createPayee(payeeRequestDto);
		if (responseDto.getMessage().equals(AppConstant.SUCCESS)) {
			responseDto.setStatusCode(HttpStatus.OK.value());
		} else {
			responseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
		}

		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}

	/**
	 * delete the payee detail based on the id
	 * 
	 * @param id pass the id value based on the user choosing the payee accont
	 *           detail.
	 * @return responseDto set the response for sucess/failure cases.
	 * @see responseDto response params
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDto> deletePayee(@PathVariable Integer id) {
		logger.info("delete the payee based on the id...");
		ResponseDto response = payeeAccountService.deleteAccount(id);
		if (response.getMessage().equals(AppConstant.DELETE_SUCCESS)) {
			response.setStatusCode(HttpStatus.OK.value());
		} else {
			response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		}

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	/**
	 * This method is used to update the new payee in respective DB
	 * 
	 * @param PayeeRequestDto object set of input fields to update the payee
	 * @return ResponseDto object contains response message and status
	 * @see responseDto response params
	 */
	@PutMapping("/{id}")
	public ResponseEntity<ResponseDto> updatePayee(@RequestBody PayeeAccountRequestDto payeeRequestDto,
			@PathVariable Integer id) throws NotFoundException {
		log.info("update the payee detail");
		ResponseDto responseDto = payeeAccountService.updatePayee(payeeRequestDto, id);
		if (responseDto != null) {
			responseDto.setStatusCode(HttpStatus.OK.value());
		}
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}
}
