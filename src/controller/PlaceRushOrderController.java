package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.exception.InvalidDeliveryInfoException;
import common.exception.MediaNotAvailableException;
import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;

public class PlaceRushOrderController extends BaseController{
			
	 /**
     * For logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceRushOrderController.class.getName());
    
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
        if(!validateDate(expectedDate, currDate)) {
        	throw new InvalidDeliveryInfoException("Chosen date is invalid");
        }
        if(!validateProvince(province)) {
        	throw new InvalidDeliveryInfoException("Chosen province does not support Rush Order");
        }
    }
    
    /**
     * Kiem tra thoi gian yeu cau giao hang cua khach hang co hop le hay ko
     * @param expectedDate: thoi gian khach hang chon de giao hang
     * @param currDate: thoi gian ma khach hang dat 
     * @return boolean
     */
    public boolean validateDate(Date expectedDate, Date currDate) { 
    	if (expectedDate != null && currDate != null) {
    	if (expectedDate.after(currDate)) return true;	
    	}
    	return false;
    }

    /**
     * Kiem tra tinh thanh co ho tro dat hang nhanh hay khong
     * @param province: tinh thanh khach hang chon
     * @return boolean
     */
    public boolean validateProvince(String province) {
       	String[] pattArray = {"^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠ"
    			+ "ẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏố"
    			+ "ồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ ]+$",
    			"Hà Nội"};
    	Pattern pattern;
    	boolean result = true;
    	
    	if(province != null && !province.isBlank()) {
    		
    		for(int i = 0; i < pattArray.length; i++) {
    			pattern = Pattern.compile(pattArray[i]);
    			result = pattern.matcher(province).matches();
    			if(!result) {
    				break;
    			}
    		}
    		
    	} else result = false;
    	return result;
    }
    
    /**
     * This method calculates the shipping fees of the rush order
     * @param order: don hangs
     * @param initFee: chi phi hang hoa ban dau
     * @param distance: khoang cach giao hang
     * @return fees: gia tien cua don hang dat nhanh
     */
    public int calculateShippingFee(Order order, int initFee, int distance){
        int fees = (int)( ( (distance*10)/100 ) * initFee );
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
}
