package com.wavelabs.service;

import org.hibernate.Session;
import org.hibernate.internal.util.xml.XmlDocument;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.wavelabs.metadata.HbmFileMetaData;
import com.wavelabs.metadata.PropertyAttributes;
import com.wavelabs.metadata.XmlDocumentBuilder;
import com.wavelabs.model.Customer;
import com.wavelabs.service.PersistenceService;
import com.wavelabs.tableoperations.CRUDTest;
import com.wavelabs.utility.Helper;

/**
 * Provides unit test cases to {@link PersistenceService} methods.
 * 
 * @author gopikrishnag
 * @since 2017-04-02
 */
public class PersistanceServiceTest {

	private HbmFileMetaData customerHbm = null;
	private CRUDTest crud = null;

	/**
	 * <p>
	 * Initializes {@link HbmFileMetaData}, {@link CRUDTest} Class objects. This
	 * objects useful through out all unit test cases.
	 * </p>
	 */
	@BeforeTest
	public void intillization() {
		XmlDocumentBuilder xmb = new XmlDocumentBuilder();
		XmlDocument xd = xmb.getXmlDocumentObject("src/main/resources/com/wavelabs/model/Customer.hbm.xml");
		customerHbm = new HbmFileMetaData(xd, Helper.getSessionFactory());
		crud = new CRUDTest(Helper.getSessionFactory(), Helper.getConfiguration(), Helper.getSession());
	}

	/**
	 * <h1>Tests the unique attribute value for customer phone property mapping
	 * </h1>
	 * <ul>
	 * <li>{@code unique="true"} test case will pass</li>
	 * <li>{@code unique="false"} test case will fail</li>
	 * </ul>
	 * 
	 */
	@Test(priority = 1, description = "checks unique attribute value for phone property")
	public void testUnique() {
		Assert.assertEquals(customerHbm.getPropertyAttributeValue("phone", PropertyAttributes.unique), "true",
				"phone property unique attribute is not matched ");
	}

	/**
	 * <h1>Test not-null attribute value for customer mail property mapping</h1>
	 * <ul>
	 * <li>{@code not-null="true"} test case will pass</li>
	 * <li>{@code not-null="false"} test case will fail</li>
	 * </ul>
	 * <p>
	 * If testUnique fails, then test case will fail
	 * </p>
	 */
	@Test(priority = 2, description = "checks not-null attribute value for mail property", dependsOnMethods = "testUnique")
	public void testNotNull() {
		Assert.assertEquals(customerHbm.getPropertyAttributeValue("mail", PropertyAttributes.notnull), "true",
				"mail property not-null attribute is not matched ");
	}

	/**
	 * Tests Formulae attribute value for biffAfterDiscount property .
	 * <ul>
	 * <li>{@code formulae="bill-100"} test will pass</li>
	 * <li>{@code formulae="any other operation"} test will fail</li>
	 * </ul>
	 * <p>
	 * if testNotNull test case fails then this test will skip
	 * </p>
	 */
	@Test(priority = 3, description = "checks formula attribute value for billAfterDiscount property and formula attribute is configured or not", dependsOnMethods = "testNotNull")
	public void testForFormulae() {
		Assert.assertEquals(customerHbm.getPropertyAttributeValue("billAfterDiscount", PropertyAttributes.formula),
				"bill-100", "billAfterDiscount formula attribute value not matched ");
	}

	/**
	 * Tests createCustomer method in PersistenceService method persisting
	 * customer objects or not, If not test case will fail
	 * <p>
	 * This test case depends on testForFormulae
	 * </p>
	 */
	@Test(priority = 4, dependsOnMethods = "testForFormulae", description = "checks CreateCustomer in PersistenceService class inserting values or not")
	public void testCreateCustomer() {
		PersistenceService.createCustomer(1, "gopi", "gopi@gmail.com", "9032118864", 100);
		PersistenceService.createCustomer(2, "raja", "raja@gmail.com", "9133213559", 200);
		crud.setSession(Helper.getSession());
		Assert.assertEquals(crud.isRecordInserted(Customer.class, 1), true,
				"createCustomer method fails to insert record in table");
		Assert.assertEquals(crud.isRecordInserted(Customer.class, 2), true,
				"createCustomer method fails to insert record in table");
	}

	/**
	 * Tests Customer getting exact discount or not, If discount is not matched
	 * then discount will fail.
	 * <p>
	 * This test case depends on testCreateCustomer
	 * </p>
	 */
	@Test(priority = 5, dependsOnMethods = "testCreateCustomer", description = "checks Customer getting correct discount or not")
	public void testBillAfterDiscount() {
		Session session = Helper.getSession();
		Customer c = (Customer) session.get(Customer.class, 2);
		Assert.assertEquals(c.getBillAfterDiscount(), c.getBill() - 100, "Discount value is not matched with expected");
	}
}
