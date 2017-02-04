package com.wavelabs.service;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wavelabs.model.Customer;
import com.wavelabs.utility.Helper;
/**
 * This class provides static method to create {@link Customer} object to persistent. 
 * @author gopikrishnag
 * @since  04-02-2017
 */
public class PersistenceService {

	/**
	 * creates Customer domain object and persists it.
	 * @param id
	 * 			 of employee
	 * @param name
	 * 			 of employee
	 * @param mail
	 * 			of employee
	 * @param phone
	 * 		    of employee
	 * @param bill
	 * 			of employee
	 */
	public static void createCustomer(int id,String name,String mail,String phone,double bill)
	{

		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		Customer c1 = new Customer();
		c1.setId(id);
		c1.setName(name);
		c1.setMail(mail);
		c1.setBill(bill);
		c1.setPhone(phone);
		session.save(c1);
		tx.commit();
		session.close();
	}
}