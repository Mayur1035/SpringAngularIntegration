package com.stackroute.keepnote.utils;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class TestUtil {
	
	public static void main (String args[]) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(1534000540));
		System.out.println(c.getWeekYear());
		
		Calendar date = Calendar.getInstance();
		long t= date.getTimeInMillis()+(10 * 60000);
		Date afterAddingTenMins=new Date(t);
		Date date1 = new Date ();
		System.out.println(date1.getTime());
		System.out.println(afterAddingTenMins.getTime());
		
		System.out.println(LocalDateTime.now());
		
	}

}
