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
		"12/12/2021, 11/11/1111, true",
		"11/11/2000, 12/12/2020, false",
		"5/3/2022, 1/1/1111, true",
		"11/1/2022, 12/1/1223, true",
		"20/1/2020, 12/3/2021, false",
		"20/1/2020, 20/1/2020, false"
	})
	
	void test(Date expectedDate, Date currDate , boolean expect) {
		boolean isValid = placeRushOrderController.validateDate(expectedDate, currDate);
		assertEquals(isValid, expect);
	}

}
