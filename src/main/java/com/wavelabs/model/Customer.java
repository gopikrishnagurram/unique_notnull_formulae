package com.wavelabs.model;
/**
 * Entity to represents customers in relational database. 
 * @author gopikrishnag
 * @since 04-02-2017
 */
public class Customer {

	private int id;
	private String name;
	private String phone;
	private String mail;
	private double bill;
	private double billAfterDiscount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public double getBill() {
		return bill;
	}

	public void setBill(double bill) {
		this.bill = bill;
	}

	public double getBillAfterDiscount() {
		return billAfterDiscount;
	}

	public void setBillAfterDiscount(double billAfterDiscount) {
		this.billAfterDiscount = billAfterDiscount;
	}

}
