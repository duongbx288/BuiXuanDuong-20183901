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
     * @param address : Dia chi ma khach hang chon
     * @throws SQLException
     */
    public void placeRushOrder(Date expectedDate, Date currDate, String address) throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct(); 
        if(!validateDate(expectedDate, currDate)) {
        	throw new InvalidDeliveryInfoException("Chosen date is invalid");
        }
        if(!validateAddress(address)) {
        	throw new InvalidDeliveryInfoException("Address does not support Rush Order");
        }
    }
    
    /**
     * Kiem tra dia chi khach hang nhap vao co ho tro giao hang nhanh hay ko
     * @param address: dia chi khach hang nhap vao
     * @return boolean
     */
    public boolean validateAddress(String address) {
    	String barrier = "^[a-zA-Z_0-9_\\,\\.\\/\\_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢ"
    			+ "ẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ ]+$";
    	Pattern pattern;
    	
    	if(address != null) {
    	
    		pattern = Pattern.compile(barrier);
				if(pattern.matcher(address).matches()) {
					String[] pattArray = {"(?s).*\\hanoi\\b.*\\HaNoi\\b.*\\Ha noi\\b.*"};
					boolean result = false;
					/**
	    				for(int i = 0; i < pattArray.length; i++) {
	    				pattern = Pattern.compile(pattArray[i], Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
	    				result = pattern.matcher(address).matches();
	    				if(result) {
	    				break;
	    			}*/
					return true;  	
				} else return false;
    	}
    	return false;
    }
    
    
    /**
     * Kiem tra thoi gian yeu cau giao hang cua khach hang co hop le hay ko
     * @param expectedDate: thoi gian khach hang chon de giao hang
     * @param currDate: thoi gian ma khach hang dat 
     * @return boolean
     */
    public boolean validateDate(Date expectedDate, Date currDate) { 
    	if (expectedDate.after(currDate)) return true;	
    	return false;
    }

    /**
     * This method calculates the shipping fees of the rush order
     * @param order: don hang
     * @param initFee: chi phi hang hoa ban dau
     * @param distance: khoang cach giao hang
     * @return fees: gia tien cua don hang dat nhanh
     */
    public int calculateShippingFee(Order order, int initFee, int distance){
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
}
