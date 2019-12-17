package com.maintainpayee;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MaintainPayeeApplicationTest {

	@Test
	public void applicationTest() {
		MaintainPayeeApplication.main(new String[] {});
	    assertTrue(true);
	}
}
