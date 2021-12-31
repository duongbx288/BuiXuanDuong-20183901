package controller;

public interface ShippingFeeCalculator {
    public default int calculateShippingFee(float amount){
		return 0;
    }

    public default int calculateRushShippingFee(float amount, int distance, int weight) {
    	return 0;
    }
    
}
