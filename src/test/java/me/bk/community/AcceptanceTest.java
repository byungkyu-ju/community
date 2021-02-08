package me.bk.community;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {
	@LocalServerPort
	int port;

	@BeforeEach
	@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
	public void setUp() {
		RestAssured.port = port;
	}
}
