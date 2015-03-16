/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, January - March 2015
 */

package com.dc0d.iiridarts.venture.client.handlers;

import java.text.NumberFormat;

public class Clock {
	NumberFormat formatter;
	long hours, minutes, seconds;
	
	public Clock(int hour, int minute, int second) {
		this.hours = hour;
		this.minutes = minute;
		this.seconds = second;
	}
	
	public Clock() {
		this.hours = 0;
		this.minutes = 0;
		this.seconds = 0;
	}
	
	public Clock(Clock clock) {
		this.hours = clock.hours;
		this.minutes = clock.minutes;
		this.seconds = clock.seconds;
	}
	
	public void set (int hours, int minutes, int seconds)
	{
	    if (0 <= hours && hours < 24)

    	this.hours = hours;

	    else

    	hours = 0;

	    if (0 <= minutes && minutes < 60)

	    this.minutes = minutes;

	    else

    	this.minutes = 0;

	    if (0 <= seconds && seconds < 60)

    	this.seconds = seconds;

	    else

	    this.seconds = 0;
	}
	
	public void tick() {
		this.seconds += 1;
	    //add overflow to minutes from seconds
	    this.minutes +=(int)(this.seconds/60);
	    //update seconds 
	    this.seconds = this.seconds % 60;
	    //add overflow to minutes from seconds
	    this.hours +=(int)(this.minutes/60);
	    //update minutes
	    this.minutes = this.minutes % 60;
	    //adjust hours
	    this.hours = this.hours %24;
	}
	
	public void add(int second) {
		this.seconds += second;
	    //add overflow to minutes from seconds
	    this.minutes +=(int)(this.seconds/60);
	    //update seconds 
	    this.seconds = this.seconds % 60;
	    //add overflow to minutes from seconds
	    this.hours +=(int)(this.minutes/60);
	    //update minutes
	    this.minutes = this.minutes % 60;
	    //adjust hours
	    this.hours = this.hours %24;
	}
	
	public String getTimeString(boolean padding) {
		if (padding){
			return String.format("%02d", this.hours) + ":" + String.format("%02d", this.minutes) + ":" + String.format("%02d", this.seconds);
		} else {
			return this.hours + ":" + this.minutes + ":" + this.seconds;
		}
	}
}
