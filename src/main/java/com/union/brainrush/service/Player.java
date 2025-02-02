package com.union.brainrush.service;

import java.util.HashMap;

public class Player {
	public static int fPlayerMark = 0;
	public static int sPlayerMark = 0;
	public static int tPlayerMark = 0;
	public static String fPlayerAns;
	public static String sPlayerAns;
	public static String tPlayerAns;
	public static String rightAns;
	public static String sentMessage;
	public static int playerQuantity = 0;
	public static HashMap<String, Integer> playerMark(){
		 HashMap<String, Integer> rankMap = new HashMap<>();
	        rankMap.put("fPlayerMark",0);
	        rankMap.put("sPlayerMark",1);
	        rankMap.put("tPlayerMark",2);
		return rankMap;
	}
	public static void resetPlayerMark() {
		fPlayerMark = 0;
		sPlayerMark = 0;
		tPlayerMark = 0;
	}
}
