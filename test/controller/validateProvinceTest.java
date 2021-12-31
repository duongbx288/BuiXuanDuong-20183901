package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class validateProvinceTest {
	private ValidateController placeRushOrderController;
	@BeforeEach
	void setUp() throws Exception {
		placeRushOrderController = new ValidateController();
	}

	@ParameterizedTest
	@CsvSource({
		"Hà Nội, true",
		"1234, false",
		"$##%, false",
		"Cao Bang, false",
		"Đồng Nai, false",
		", false",
		"'  ', false",
	})
	
	void test(String province, boolean expect) {
		boolean isValid = placeRushOrderController.validateProvince(province);
		assertEquals(isValid, expect);
	}

}
