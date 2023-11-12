package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void fail() throws InterruptedException {
		TimeUnit.MINUTES.sleep(5);
		assertTrue(true);
	}

}
