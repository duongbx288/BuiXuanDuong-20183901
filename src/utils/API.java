package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;

/**
 * Class cung cap cac phuong thuc giup gui requét len sẻvẻ va nhan du lieu tra ve
 * Date: 8/12/2021
 * @author:
 * @version 1.0
 */
public class API {

	
	/**
	 * Thuoc tinh giup format ngay theo dinh dang
	 */
	public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/**
	 * Thuoc tinh giup log thong tin ra console
	 */
	
	private static Logger LOGGER = Utils.getLogger(Utils.class.getName());

	/**
	 * Phuong thuc giup goi toi cac API dang GET
	 * @param url: đường dẫn tới server cần request
	 * @param token: đoạn mã băm cần cung cấp để xác thực người dùng
	 * @return response: phản hồi từ server (dạng string)
	 * @throws Exception
	 */
	public static String get(String url, String token) throws Exception {
		
		//phan 1: setup
		HttpURLConnection conn = setupConnection(url, "GET", token);
		
		// phan 2: doc du lieu tra ve tu server
		String respone = readRespone(conn);
		
		return respone;
	}

	int var;

	/**
	 * Phuong thuc giup goi toi cac API dang POST (thanh toan,...)
	 * @param url: đường dẫn tới server cần request
	 * @param data: dữ liệu đưa lên server để xử lý (dạng JSON)
	 * @return response: phản hồi từ server (dạng string)
	 * @throws IOException
	 */
	public static String post(String url, String data
	, String token
	) throws IOException {
		//cho phep PATCH protocol
		allowMethods("PATCH");
		
		// phan 1: setup
		HttpURLConnection conn = setupConnection(url, "GET", token);
		
		// phan 2: gui du lieu
		Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write(data);
		writer.close();
		
		// phan 3: doc du lieu gui ve tu server
		String respone = readRespone(conn);
		
		return respone;
	}

	/**
	 * Thiet lap connection toi server
	 * @param url: duong dan toi server can request
	 * @param method: giao thuc api
	 * @param token: doan ma bam can cung cap de xac thuc nguoi dung
	 * @return connection
	 * @throws IOException
	 */
	private static HttpURLConnection setupConnection(String url, String method, String token) throws IOException {
		LOGGER.info("Request URL: " + url + "\n");
		URL line_api_url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		return conn;
	}
	
	/**
	 * Phuong thuc doc du lieu tra ve tu server
	 * @param conn: connection to server
	 * @return respone: phan hoi tra ve tu server
	 * @throws IOException
	 */
	private static String readRespone(HttpURLConnection conn) throws IOException {
		BufferedReader in;
		String inputLine;
		if(conn.getResponseCode() / 100 == 2) {
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder respone = new StringBuilder(); // su dung String Builder cho viec toi uu ve mat bo nho
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		respone.append(inputLine + "\n");
		in.close();
		LOGGER.info("Respone Info: " + respone.substring(0, respone.length() - 1).toString());
		return respone.substring(0, respone.length() - 1).toString();
	}
	
	
	
	/**
	 * Phương thức cho phép gọi các loại giao thức PI khác nhau như PATCH, PUT,.. (chi hoat dong voi Java11)
	 * @deprecated chi hoat dong voi Java <= 11
	 * @param methods: giao thuc can cho cho phep(PATCH, PUT..)
	 * 
	 */
	private static void allowMethods(String... methods) {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
			methodsField.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);

			methodsField.set(null/* static field */, newMethods);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

}
