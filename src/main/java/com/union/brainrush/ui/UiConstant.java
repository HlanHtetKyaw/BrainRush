package com.union.brainrush.ui;

public enum UiConstant {
	WIDTH(1024),HEIGHT(768);
	private final int value;
	UiConstant(int value){
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}
