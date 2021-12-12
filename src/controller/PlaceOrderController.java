package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entity.cart.Cart;
import entity.cart.CartMedia;
import common.exception.InvalidDeliveryInfoException;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import views.screen.popup.PopupScreen;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * @author nguyenlm
 */
public class PlaceOrderController extends BaseController{

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the avalibility of product when user click PlaceOrder button
     * @throws SQLException
     */
    public void placeOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     * @return Order: order duoc tao tu thong tin gio hang
     * @throws SQLException
     */
    public Order createOrder() throws SQLException{
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
     * @param order: order cua khach hang
     * @return Invoice: hoa don duoc tao tu order cua khach hang
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * @param info: thong tin ma nguoi dung dua vao
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
   * @param info: info provided by customers
   * @throws InterruptedException	
   * @throws IOException
   */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
    	if(validatePhoneNumber(info.get("phone")) && validateName(info.get("name"))
    	&& validateAddress(info.get("address"))){
    		
    		LOGGER.info("Validate delivery info:");
    		LOGGER.info("phone: "+ info.get("phone") + ", name:" + info.get("name") + 
    				    ",address: " + info.get("address"));
    	} 
    	else throw new InvalidDeliveryInfoException("Some info is invalid");
    }
    
    
    /**
     * The method checks whether the phone number is valid or not
     * @param phoneNumber: so dien thoai ma nguoi dung nhap vao
     * @return true: phone number is valid
     * @return false: phone number is not valid
     */
    public boolean validatePhoneNumber(String phoneNumber) {
    	if(phoneNumber != null) {
    		
    		if(phoneNumber.length() == 10) {
    			if(phoneNumber.indexOf('0') != 0) return false;
    			try{
    				Integer.parseInt(phoneNumber);
    			} catch (NumberFormatException e) {
    				return false;
    			}
    		} else if(phoneNumber.length() == 11) {
    			if(phoneNumber.indexOf('0') != 0 && phoneNumber.indexOf('1') != 1) return false;
    			try{
    				Integer.parseInt(phoneNumber);
    			} catch (NumberFormatException e) {
    				return false;
    			}
    		} else return false;
    		
    	} else return false;
    	return true;
    }
    
    /**
     * The method checks whether the name customers provided is valid or not
     * @param name: ten ma khach hang nhap vao
     * @return true : thong tin ten hop le
     * @return false: thong tin ten khong hop le
     */
    public boolean validateName(String name) {
    	String[] pattArray = {"^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠ"
    			+ "ẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ ]+$"};
    	Pattern pattern;
    	boolean result = true;
    	
    	if(name != null && !name.isBlank()) {
    		
    		for(int i = 0; i < pattArray.length; i++) {
    			pattern = Pattern.compile(pattArray[i]);
    			result = pattern.matcher(name).matches();
    			if(!result) {
    				break;
    			}
    		}
    	} else result = false;
    	
    	return result;
    }
    
    
    
    /**
     * The method checks whether the address customers provided is valid or not
     * @param address: dia chi ma khach hang nhap
     * @return boolean
     */
    public boolean validateAddress(String address) {
    	String barrier = "^[a-zA-Z_0-9_\\,\\.\\/\\_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢ"
    			+ "ẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈ"
    			+ "ỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ ]+$";
    	Pattern pattern;
    	
    	if(address != null && !address.isBlank()) {
    	
    		pattern = Pattern.compile(barrier);
				if(pattern.matcher(address).matches()) {
					return true;  	
				} else return false;
    	}
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
