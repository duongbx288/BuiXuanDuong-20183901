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
	 * Work to do: 	   
	 * 		   placeRushOrder(cart: Cart, deliveryInfo: String, deliveryStruction: String, requireDate: date)
	 */
		
	 /**
     * For logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceRushOrderController.class.getName());

    /**
     * This method checks the avalibility for Rush Order of the products in the Cart
     * @throws SQLException
     */
    public void placeRushOrder(Date requiredDate, String address) throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct(); //--> 'Cart' object is static*
        if(!validateDate(requiredDate)) {
        	throw new InvalidDeliveryInfoException("Chosen date is invalid");
        }
        if(!validateAddress(address)) {
        	throw new InvalidDeliveryInfoException("Address does not support Rush Order");
        }
    }

    public void processRushDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Rush Order Delivery Info");
        LOGGER.info(info.toString());
        validateRushDeliveryInfo(info);
    }
    
    
    public void validateRushDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
    	
    }
    
    /**
     * Kiem tra dia chi khach hang nhap vao co ho tro giao hang nhanh hay ko
     * @param address
     * @return
     */
    public boolean validateAddress(String address) {
    	String[] pattArray = {"hanoi", "ha noi", "haf noi", "hn"};
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
    
    public boolean validateDate(Date date) {
    	Date currDate = Calendar.getInstance().getTime();
    	if (date.after(currDate)) return true;	
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
