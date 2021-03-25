package com.h2Database.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class H2Constants {

	public static final Map<String, List<String>> PEAK_HOURS = new HashMap<String, List<String>>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("MONDAY",new ArrayList<String>(Arrays.asList("07:00", "10:30", "17:00", "20:00")));
			put("TUESDAY",new ArrayList<String>(Arrays.asList("07:00", "10:30", "17:00", "20:00")));
			put("WEDNESDAY",new ArrayList<String>(Arrays.asList("07:00", "10:30", "17:00", "20:00")));
			put("THURSDAY",new ArrayList<String>(Arrays.asList("07:00", "10:30", "17:00", "20:00")));
			put("FRIDAY",new ArrayList<String>(Arrays.asList("07:00", "10:30", "17:00", "20:00")));
			put("SATURDAY",new ArrayList<String>(Arrays.asList("09:00", "11:00", "18:00", "22:00")));
			put("SUNDAY",new ArrayList<String>(Arrays.asList("09:00", "11:00", "18:00", "22:00")));			
		}
	};
	
	public static final Map<String, Integer> ZONE_DAILY_CAP_RATES = new HashMap<String, Integer>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("11",100);
			put("12",120);
			put("21",120);
			put("22",80);
		}
	};
	
	public static final Map<String, Integer> ZONE_WEEKLY_CAP_RATES = new HashMap<String, Integer>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("11",500);
			put("12",600);
			put("21",600);
			put("22",400);
		}
	};
}
