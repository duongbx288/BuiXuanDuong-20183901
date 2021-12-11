package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateAddressTest {

	private PlaceRushOrderController placeRushOrderController;
	@BeforeEach
	void setUp() throws Exception {
		placeRushOrderController = new PlaceRushOrderController();
	}

	@ParameterizedTest
	@CsvSource({
		"Hanoi, true",
		"where is it, false",
		"Ha Noi , true",
		"112 Hai Bà Trưng Haf Nội, false",
		"112 Đống Đa, Hà Nội, true",
		"Noi Ha, 123 to 2 pho van hoa, false",
		"248, To 4, Ngo 22, Phuong Hai Ba Trung, tp.HN, true",
		"113, to 2, phuong truong thi, false"
	})
	
	//@Test
	void test(String address, boolean expect) {
		boolean isValid = placeRushOrderController.validateAddress(address);
		assertEquals(isValid, expect);
	}

}
