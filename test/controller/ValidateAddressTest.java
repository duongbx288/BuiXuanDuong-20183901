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
		"'123, Hanoi', true",
		"'123, 412 to 2, phuong truong thi', true",
		"&*(()*()#, false",
		"'#%## ba dinh, dong da', false",
		"'12/240 to 2, phuong hai ba trung', true",
		"'12\1, 45@ ha noi', false"
	})
	
	//@Test
	void test(String address, boolean expect) {
		boolean isValid = placeRushOrderController.validateAddress(address);
		System.out.println(isValid);
		assertEquals(isValid, expect);
	}

}
