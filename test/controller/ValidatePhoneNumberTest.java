package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidatePhoneNumberTest {
	
	private ValidateController placeOrderController;
	@BeforeEach
	void setUp() throws Exception {
		placeOrderController = new ValidateController();
	}

	@ParameterizedTest
	@CsvSource({
		"0911234567, true",
		"01242357345, true",
		"1123456789, false",
		"01234, false",
		"abc, false",
		"09238586222, false",
		"09123 71234, false",
		", false",
		"'          ', false",

	})
	 
	/*@Test*/
	void test(String phone, boolean expect) {
		boolean isValid = placeOrderController.validatePhoneNumber(phone);
		assertEquals(isValid, expect);
	}

}
