package com.bonds.bondsmanagement;

import com.bonds.BondsManagementApplication;
import com.bonds.model.Application;
import com.bonds.model.Coupon;
import com.bonds.model.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BondsManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BondsManagementApplicationTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();


	@Autowired
	private TestRestTemplate template;



	@Test
	public void testApplicationShouldBeCreated() {
		final String method="/applications/save";
		Application application = new Application();
		Customer customer = new Customer(1l,"Elvin","Aliyev",31);
		Coupon coupon = new Coupon(101l,5.0,5,new BigDecimal("100"));

		application.setCoupon(coupon);
		application.setCustomer(customer);
		application.setTerm(10);
		application.setPaidAmount(coupon.getPrice().multiply(new BigDecimal("10")));
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Application> entity = new HttpEntity<Application>(application, headers);

		ResponseEntity<Application> response = template.exchange(createURLWithPort(method),
				HttpMethod.POST,
				entity,
				Application.class);

		Assert.assertTrue((response.getStatusCode()).equals(HttpStatus.CREATED));
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:"+ port + uri;
	}

}
