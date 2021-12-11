package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateDateTest {

	private PlaceRushOrderController placeRushOrderController;
	@BeforeEach
	void setUp() throws Exception {
		placeRushOrderController = new PlaceRushOrderController();
	}

	@ParameterizedTest
	@CsvSource({
		"12/12/2021, true",
		"11/11/2000, false",
		"5/3/2022, true",
		"11-1-2022, false"
	})
	
	void test(Date date, boolean expect) {
		boolean isValid = placeRushOrderController.validateDate(date);
		assertEquals(isValid, expect);
	}

}
