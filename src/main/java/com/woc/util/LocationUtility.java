package com.woc.util;

public class LocationUtility {
  
    public static double distance(String[] location1, String[] location2) 
    { 
    	double lat1 = Double.valueOf(location1[0]); 
        double lat2= Double.valueOf(location2[0]); 
        double lon1= Double.valueOf(location1[1]); 
        double lon2= Double.valueOf(location2[1]); 
        lon1 = Math.toRadians(lon1); 
        lon2 = Math.toRadians(lon2); 
        lat1 = Math.toRadians(lat1); 
        lat2 = Math.toRadians(lat2); 
  
        // Haversine formula  
        double dlon = lon2 - lon1;  
        double dlat = lat2 - lat1; 
        double a = Math.pow(Math.sin(dlat / 2), 2) 
                 + Math.cos(lat1) * Math.cos(lat2) 
                 * Math.pow(Math.sin(dlon / 2),2); 
              
        double c = 2 * Math.asin(Math.sqrt(a)); 
  
        // Radius of earth in kilometers.
        double r = 6371; 
  
        // calculate the result 
        return(c * r); 
    }  

}