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

    
    public void validateRushDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
    	
    }
    
    /**
     * Kiem tra dia chi khach hang nhap vao co ho tro giao hang nhanh hay ko
     * @param address
     * @return
     */
    public boolean validateAddress(String address) {
    	String[] pattArray = {"hanoi", "ha noi", "haf noi", "hn", "Ha Noi"};
    	Pattern pattern;
    	boolean result = false;
    	
    	for(int i = 0; i < pattArray.length; i++) {
    		pattern = Pattern.compile(pattArray[i], Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    		result = pattern.matcher(address).matches();
    		if(result) {
    			break;
    		}
    	}
    	
    	return result;
    }
    
    public boolean validateDate(Date expectedDate, Date date) {
    	Date currDate = Calendar.getInstance().getTime();
    	if (expectedDate.after(date)) return true;	
    	return false;
    }

    /**
     * This method calculates the shipping fees of order
     * @param order
     * @return shippingFee
     */
    public int calculateShippingFee(Order order){
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
}
