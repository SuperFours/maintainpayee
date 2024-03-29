package com.maintainpayee.constant;

/**
 * @description AppConstant is used for only we are maintaining the argcoded
 *              values in the whole application.
 * @author Janani.V
 * @since 17-12-2019
 */
public class AppConstant {
	
	private AppConstant() {

	}

	// Httpstatus success and failure messages for common.
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAILURE";
	public static final String NO_RECORD_FOUND = "No Records Found";

	//ErrorCodes
	public static final Integer SUCCESS_CODE = 200;

	
	public static final String LOGIN_SUCCESS_MESSAGE = "Login Success";
	public static final String LOGIN_ERROR_MESSAGE = "Login failed";

	public static final String FAVOURITE="is-favourite";
	
	public static final String PAYEE_ALREADY_EXIST = "Beneficinary already exists";
	public static final String PAYEE_REACHED_MAXIMUM = "You can added maximum upto 10 favorite beneficinaries";
	
	//delete 
	public static final String DELETE_SUCCESS = "Payee Account Deleted Successfully";
	public static final String NO_ACCOUNT_FOUND = "No Payee Records Found";
	public static final String NO_CUSTOMERS_FOUND = "No Customers Found";
	

	public static final String NO_ACCOUNT_NUMBER_FOUND = "No Account Number Found";
	
	//Check account number
	public static final String CHECK_ACCOUNT_NUMBER = "http://localhost:8080/retailbanking/users/accounts/search/";
	
	public static final String OPERATION_SUCCESS = "Operation Success";
	public static final String PAYEE_UPDATED = "Beneficinary Updated Successfully";

	//Validate Message
	public static final String FIRST_NAME_MANDATORY = "FirstName is mandatory";
	public static final String ACCOUNT_NUMBER_MANDATORY = "Account Number is mandatory";

	
}