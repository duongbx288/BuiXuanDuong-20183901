package views.screen.rushorder;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import common.exception.InvalidDeliveryInfoException;
import controller.PlaceOrderController;
import controller.PlaceRushOrderController;
import entity.invoice.Invoice;
import entity.order.Order;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import views.screen.shipping.ShippingScreenHandler;

public class RushOrderScreenHandler extends BaseScreenHandler {

	@FXML
	private TextField info;
	
	@FXML
	private TextField instruction;
	
	@FXML
	private DatePicker date;
	
	@FXML
	private Button back;
	
	private Order order;
	private HashMap<String, String> orderInfo;
	PlaceRushOrderController pController = new PlaceRushOrderController();
	
	/** Khởi tạo màn hình Rush Order Screen
	 * @param stage
	 * @param screenPath
	 * @param order
	 * @throws IOException
	 */
	public RushOrderScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
		super(stage, screenPath);
		this.order = order;

	}
	
	//initializable
	
	/**
	 * Đưa màn hình về màn hình trước đó khi bấm vào nút 'Back'
	 * @param event
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws SQLException
	 */
	@FXML
	void toPreviousScreen(MouseEvent event) throws IOException, InterruptedException, SQLException{
		ShippingScreenHandler ShippingScreenHandler = new ShippingScreenHandler(this.stage, Configs.SHIPPING_SCREEN_PATH, order);
		ShippingScreenHandler.setPreviousScreen(this);
		ShippingScreenHandler.setHomeScreenHandler(homeScreenHandler);
		ShippingScreenHandler.setScreenTitle("Shipping Screen");
		ShippingScreenHandler.setBController(getBController());
		ShippingScreenHandler.show();
	}
	
	/**
	 * Đưa màn hình tới màn hình hóa đơn thanh toán sau khi kiểm tra các thông tin khách hàng nhập đã hợp lệ hay chưa
	 * @param event
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws SQLException
	 */
	@FXML
	void confirmRushOrder(MouseEvent event) throws IOException, InterruptedException, SQLException{
		if(date.getValue() != null) {
	    	Date currDate = Calendar.getInstance().getTime();
	    	LocalDate myDate = date.getValue();
	    	Instant instant = Instant.from(myDate.atStartOfDay(ZoneId.systemDefault()));
	    	Date expectedDate = Date.from(instant);
	    	orderInfo = order.getDeliveryInfo();

			try {
				// process and validate delivery info
				String province = orderInfo.get("province");
				pController.placeRushOrder(expectedDate, currDate, province);
			} catch (InvalidDeliveryInfoException e) {
				throw new InvalidDeliveryInfoException(e.getMessage());
			}
			
			Invoice invoice = getBController().createInvoice(order);
			BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
			InvoiceScreenHandler.setPreviousScreen(this);
			InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
			InvoiceScreenHandler.setScreenTitle("Invoice Screen");
			InvoiceScreenHandler.setBController(getBController());
			InvoiceScreenHandler.show();
		}
		else {
			throw new InvalidDeliveryInfoException("Delivery date has not been chosen yet");
		}
	}
	//confirmRushOrder
	
	public PlaceOrderController getBController(){
		return (PlaceOrderController) super.getBController();
	}
}
