package com.best.electronics;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BestElectronicsApplicationTest {

	@Test
	void contextLoads() {

	}

	@Test
	void firstMethodTest(){
		String appName = BestElectronicsApplication.firstMethod();
		assertEquals("Best Electronics", appName);
	}

}
