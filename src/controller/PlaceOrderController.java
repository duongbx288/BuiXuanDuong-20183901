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
public class PlaceOrderController extends BaseController implements ShippingFeeCalculator{

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());
    private ValidateController v = new ValidateController();

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
    	if(v.validatePhoneNumber(info.get("phone")) && v.validateName(info.get("name"))
    	&& v.validateAddress(info.get("address"))){
    		
    		LOGGER.info("Validate delivery info:");
    		LOGGER.info("phone: "+ info.get("phone") + ", name:" + info.get("name") + 
    				    ",address: " + info.get("address"));
    	} 
    	else throw new InvalidDeliveryInfoException("Some info is invalid");
    }
    
    

    

    /**
     * This method calculates the shipping fees of order
     * @param order
     * @return shippingFee
     */
    @Override
    public int calculateShippingFee(float amount){
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * amount );
        LOGGER.info("Order Amount: " + amount + " -- Shipping Fees: " + fees);
        return fees;
    }
}
