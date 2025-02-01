package com.union.brainrush.service;

import java.util.HashMap;

public class Player {
	public static int fPlayerMark = 3;
	public static int sPlayerMark = 3;
	public static int tPlayerMark = 3;
	public static HashMap<Integer, Integer> playerMark(){
		 HashMap<Integer, Integer> rankMap = new HashMap<>();
	        rankMap.put(fPlayerMark,0);
	        rankMap.put(sPlayerMark,1);
	        rankMap.put(tPlayerMark,2);
		return rankMap;
	}
}
