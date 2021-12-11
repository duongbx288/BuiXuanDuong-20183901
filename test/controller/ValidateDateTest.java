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
		"12/12/2021, 12/12/3020, false",
		"12/11/2021, 4/11/2021, true",
		"12/11/2021, 12/4/2021, true"
	})
	
	void test(Date expectedDate, Date currDate , boolean expect) {
		boolean isValid = placeRushOrderController.validateDate(expectedDate, currDate);
		assertEquals(isValid, expect);
	}

}
