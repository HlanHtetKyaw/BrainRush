package com.union.brainrush.model;

import java.util.HashMap;

public class QuestionFormat {
	int id;
	String question;
	HashMap<String, String> ans;
	String rightAns;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public HashMap<String, String> getAns() {
		return ans;
	}

	public void setAns(HashMap<String, String> ans) {
		this.ans = ans;
	}

	public String getRightAns() {
		return rightAns;
	}

	public void setRightAns(String rightAns) {
		this.rightAns = rightAns;
	}

}
