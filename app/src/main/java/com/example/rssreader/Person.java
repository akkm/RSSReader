package com.example.rssreader;

/**
 * ListView の一行分のデータを保持するクラスです。
 * @author KeishinYokomaku
 */
public class Person {
	private final String name;
	private final int age;

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	/**
	 * 名前を返します
	 */
	public String getName() {
		return name;
	}

	/**
	 * 年齢を返します
	 */
	public int getAge() {
		return age;
	}
}
