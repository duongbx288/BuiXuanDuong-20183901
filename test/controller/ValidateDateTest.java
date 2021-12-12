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
		"11/11/2022, 11/11/2021, true",
		"11/12/2021, 11/11/2021, true",
		"12/11/2021, 11/11/2021, true",
		"11/11/2021, 11/11/2021, false",
		"11/11/2020, 11/11/2021, false",
		"11/10/2021, 11/11/2021, false",
		"10/11/2021, 11/11/2021, false",
		"          , 11/11/2021, false",
		"          ,           , false"
		
	})
	
	void test(Date expectedDate, Date currDate , boolean expect) {
		boolean isValid = placeRushOrderController.validateDate(expectedDate, currDate);
		assertEquals(isValid, expect);
	}

}
