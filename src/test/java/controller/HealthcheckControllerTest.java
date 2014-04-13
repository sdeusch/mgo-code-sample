package controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import sun.controller.HealthcheckController;
import sun.healthcheck.MongoDBHealthCheck;


@RunWith(MockitoJUnitRunner.class)
public class HealthcheckControllerTest {
	
		@Mock
		private MongoDBHealthCheck mongoCheck;
		private HealthcheckController healthCheckController;
		
		@Before
		public void setUp() {
			healthCheckController = new HealthcheckController();
			healthCheckController.setMongoCheck(mongoCheck);
		}

		@Test
		public void testCheckReturnsOK() {
			when(mongoCheck.checkStatus()).thenReturn(true);
			
			String result = (String) healthCheckController.checkHealth();
			assertTrue(result.contains("ALL-OK"));
		}
	
		@Test
		public void testCheckReturnsNOTOK() {
			when(mongoCheck.checkStatus()).thenReturn(false);
			
			String result = (String) healthCheckController.checkHealth();
			assertTrue(result.contains("NOT-OK"));
		}
	
}
