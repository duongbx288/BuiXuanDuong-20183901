package controller;

import java.util.Date;
import java.util.regex.Pattern;

public class ValidateController {
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
    
}
