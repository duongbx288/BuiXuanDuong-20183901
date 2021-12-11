package views.screen.rushorder;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

import controller.PlaceOrderController;
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
	private LocalDate myDate;
	private HashMap<String, String> orderInfo;
	PlaceOrderController pController;
	
	public RushOrderScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
		super(stage, screenPath);
		this.order = order;

	}
	
	//@Override
    //public void initialize(URL location, ResourceBundle resources) {
	//	orderInfo = order.getDeliveryInfo();
	//	for(String key: orderInfo.keySet()) {
	//		info.setText(key + ":" + orderInfo.get(key)+".");
	//	}
	//}
	
	
	@FXML
	void confirmRushOrder(MouseEvent event) throws IOException, InterruptedException, SQLException{
		if(date.getValue() != null) {
			myDate = date.getValue();
			String myFormattedDate = myDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
		
			System.out.println(myFormattedDate);
			System.out.println(myDate.toString());
			
			Invoice invoice = ((PlaceOrderController) getBController()).createInvoice(order);
			BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
			InvoiceScreenHandler.setPreviousScreen(this);
			InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
			InvoiceScreenHandler.setScreenTitle("Invoice Screen");
			InvoiceScreenHandler.setBController(getBController());
			InvoiceScreenHandler.show();
		}
	}
	//confirmRushOrder
}
