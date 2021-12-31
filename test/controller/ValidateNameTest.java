package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateNameTest {
	private ValidateController placeOrderController;
	@BeforeEach
	void setUp() throws Exception {
		placeOrderController = new ValidateController();
	}

	@ParameterizedTest
	@CsvSource({
		"johnceaa, true",
		"Xuan Duong, true",
		"Xuân Dương, true",
		"Nguyen van a, true",
		"nguyen 33 a, false",
		"$#12fa, false",
		"132 @#, false",
		"àd124124fad, false",
		"()ADa, false",
		"()asdasd123, false",
		"eurus 123, false",
		"michele #$, false",
		"'       ', false",
		",false"
	})

	void test(String name, boolean expect) {
		boolean isValid = placeOrderController.validateName(name);
		assertEquals(isValid, expect);
	}

}
