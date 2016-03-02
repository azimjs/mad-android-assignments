/*
 * Assignment In Class 4.
 * HeavyWork.java
 * Programmers { Mike Hofmeister, Azim Saiyed, Timothy Shay }
 */

package com.example.inclass4;

import java.util.Random;

public class HeavyWork {
	static final int COUNT = 9000000;
	static double getNumber(){
		double num = 0;
		Random rand = new Random();
		for(int i=0;i<COUNT; i++){
			num = num + rand.nextDouble();
		}
		return num / ((double) COUNT);
	}
}