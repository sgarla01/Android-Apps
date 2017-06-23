package com.geekmk.lmg.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mani on 11/06/17.
 */

public class CurrencyConverter {

    private static CurrencyConverter currencyConverter;


    private Map<String,Double> currencyMap;


    /**
     * Create private constructor
     */
    private CurrencyConverter() {
        currencyMap = new HashMap<>();
    }



    /**
     * Create a static method to get instance.
     */
    public static CurrencyConverter getInstance(){
        if(currencyConverter == null){
            currencyConverter = new CurrencyConverter();
        }
        return currencyConverter;
    }


    public BigDecimal exchange(String fromCurrency,String toCurrency,BigDecimal amount){
        if(fromCurrency.equalsIgnoreCase(toCurrency)){
            return amount;
        }else{
            String rateCode = getRateCode(fromCurrency,toCurrency);
            double rate = getRate(rateCode);
            return amount.multiply(BigDecimal.valueOf(rate));
        }
    }

    public void setRate(String fromCurrency, String toCurrency, double rate){
        String rateCode = getRateCode(fromCurrency,toCurrency);
        currencyMap.put(rateCode,rate);
    }

    public void updateRate(String fromCurrency, String toCurrency, double newRate){
        String rateCode = getRateCode(fromCurrency,toCurrency);
        Double rate = currencyMap.get(rateCode);
        if(rate!=null){
            currencyMap.put(rateCode,newRate);
        }
    }

    private double getRate(String rateCode){
        return currencyMap.get(rateCode);
    }

    private String getRateCode(String fromCurrency, String toCurrency){

        StringBuilder rateCode = new StringBuilder();

        rateCode.append(fromCurrency.charAt (0)).append(toCurrency.charAt(0));

        return rateCode.toString().toLowerCase ();
    }


    public boolean isConversionAvailable(String fromCurrency,String toCurrency){
        String rateCode = getRateCode(fromCurrency,toCurrency);
        return currencyMap.containsKey(rateCode);
    }

}
