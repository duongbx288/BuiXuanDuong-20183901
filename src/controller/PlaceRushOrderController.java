package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import common.exception.InvalidDeliveryInfoException;
import entity.cart.Cart;

public class PlaceRushOrderController extends BaseController implements ShippingFeeCalculator{
			
	 /**
     * For logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceRushOrderController.class.getName());
    private ValidateController v = new ValidateController();
    
    /**
     * This method checks the avalibility for Rush Order of the available info and the products in the cart
     * @param expectedDate : Ngay khach hang mong muon san pham duoc giao
     * @param currDate : Ngay ma khach hang bat dau dat
     * @param province : Tinh thanh khach hang muon dat hang nhanh
     * @throws SQLException
     */
    public void placeRushOrder(Date expectedDate, Date currDate, String province) throws SQLException{
		LOGGER.info("Processing Rush order info:");
		LOGGER.info("Expected Date: "+ expectedDate + ", setDate:" + currDate + 
				    ",province: " + province);
		
        Cart.getCart().checkAvailabilityOfProduct(); 
        if(!v.validateDate(expectedDate, currDate)) {
        	throw new InvalidDeliveryInfoException("Chosen date is invalid");
        }
        if(!v.validateProvince(province)) {
        	throw new InvalidDeliveryInfoException("Chosen province does not support Rush Order");
        }
    }
    

    
    /**
     * This method calculates the shipping fees of the rush order
     * @param amount: tong tien cua don hang khi chua tinh tien ship
     * @param weight: khoi luong cua san pham nang nhat trong gio hang
     * @param distance: khoang cach giao hang
     * @return fees: gia tien cua don hang dat nhanh
     */
    @Override
    public int calculateRushShippingFee(float amount, int distance, int weight){
        int fees = (int)( ( (distance*10)/100 ) * amount + weight * 0.5);
        LOGGER.info("Order Amount: " + amount + " -- Shipping Fees: " + fees);
        return fees;
    }
}
