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
	 * 			   checkRushOrderAvailability(cart :Cart?)
	 * 			   checkRushOrderAddress(address: String)
	 * 			   placeRushOrder(cart: Cart, deliveryInfo: String, deliveryStruction: String, requireDate: date)
	 */
	
	/**
	 * add name, phone, city, address to delivery info in Rush Order delivery form
	 */
	
	
	   /**
     * Just for logging purpose
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

    /**
     * This method creates the new Rush Order based on the Cart
     * @return Rush Order
     * @throws SQLException
     */
    public Order createRushOrder() throws SQLException{
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), 
                                                   cartMedia.getQuantity(), 
                                                   cartMedia.getPrice());    
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }
    
    /**
   * The method validates the info
   * @param info
   * @throws InterruptedException
   * @throws IOException
   */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
    	
    }
    
    public boolean validatePhoneNumber(String phoneNumber) {
    	if(phoneNumber.length() != 10) return false;
    	if(phoneNumber.indexOf('0') != 0) return false;
    	
    	try{
    		Integer.parseInt(phoneNumber);
    	} catch (NumberFormatException e) {
    		return false;
    	}
    	
    	return true;
    }
    
    public boolean validateName(String name) {
    	return false;
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
