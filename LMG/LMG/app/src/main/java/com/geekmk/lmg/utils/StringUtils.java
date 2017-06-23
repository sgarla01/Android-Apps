package com.geekmk.lmg.utils;

import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by Mani on 10/06/17.
 */

public class StringUtils {

    private StringUtils(){}

    private static DecimalFormat df2 = new DecimalFormat(".##");

    public static boolean isNotEmpty(String input){
        return input!=null && !input.isEmpty();
    }

    public static double toDouble(String string){
        if(isNotEmpty(string)){
            try {
                return Double.parseDouble(string);
            }catch (Exception e){
                Log.e(StringUtils.class.getName(),"Exception parsing product price to double"+ string);
                return 0;
            }
        }

        return 0;
    }

    public static String getDecimalWIthTwoPrecision(double input){
        return df2.format(input);
    }

}
