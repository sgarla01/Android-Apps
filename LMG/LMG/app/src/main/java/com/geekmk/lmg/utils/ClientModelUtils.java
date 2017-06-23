package com.geekmk.lmg.utils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mani on 10/06/17.
 */

public class ClientModelUtils {

    private ClientModelUtils(){}

    /**
     * @param key  the key for which you want the data to obtain
     * @param map  Map that contains the information to be obtained
     * */
    public static String getString(Map<String,Object> map, String key){

        if(map ==null || key == null){
            return "";
        }else{

            Object value = map.get(key);
            if(value!=null && value instanceof String){
                return (String)value;
            }else{
                return "";
            }

        }
    }

    public static int getIntFromString(Map<String,Object> map, String key){

        String val = getString(map,key);
        if(StringUtils.isNotEmpty(val)){
            try{
                return Integer.parseInt(val);
            }catch (Exception e){
                return 0;
            }
        }else{
            return 0;
        }

    }


    /**
     * @param key  the key for which you want the data to obtain
     * @param map  Map that contains the information to be obtained
     * */
    public static boolean getBoolean(Map<String,Object> map, String key){

        if(map ==null || key == null){
            return false;
        }else{
            Object value = map.get(key);
            if(value!=null && value instanceof Boolean){
                return (boolean)value;
            }else if(value != null && value instanceof Integer){
                return (int)value == 1;
            }else if(value !=null && value instanceof String){
                return value.equals("1") ;
            }else{
                return false;
            }
        }
    }



    /**
     * @param key  the key for which you want the data to obtain
     * @param map  Map that contains the information to be obtained
     * */
    public static int getInt(Map<String,Object> map, String key){

        if(map ==null || key == null){
            return 0;
        }else{

            Object value = map.get(key);
            if(value!=null && value instanceof Integer){
                return (int)value;
            }else{
                return 0;
            }
        }
    }

    /**
     * @param key  the key for which you want the data to obtain
     * @param map  Map that contains the information to be obtained
     * */
    public static Double getDouble(LinkedHashMap<String,Object> map, String key){

        if(map ==null || key == null){
            return 0.0;
        }else{

            Object value = map.get(key);
            if(value!=null && value instanceof Double){
                return round((Double)value,2);
            }else{
                return 0.0;
            }
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }



    @SuppressWarnings("unchecked")
    public static List<Object> getDataFromResponseAsList(
            Map<String,Object> response,String key
    ){
        try {
            return (List<Object>)
                    response.get(key);
        }
        catch (Exception e){
            return Collections.emptyList();
        }

    }







    @SuppressWarnings("unchecked")
    public static List<String> getModelAsListOfString(Map<String,Object> modelData,
                                                      String key){
        try {
            return (List<String>)modelData.get(key);
        }catch (Exception e){
            return Collections.emptyList();
        }
    }


    @SuppressWarnings("unchecked")
    public static Map<String,Object> getResponseAsMap(Map<String,Object> modelData,
                                                      String key){
        try {
            return (Map<String, Object>) modelData.get(key);
        }catch (Exception e){
            return Collections.emptyMap();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Object> getModelAsList(Map<String, Object> modelData,
                                              String key) {
        try {
            return (List<Object>)modelData.get(key);
        }catch (Exception e){
            return Collections.emptyList();
        }
    }

}
