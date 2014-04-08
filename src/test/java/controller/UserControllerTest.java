package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import mgo.controller.UserController;
import mgo.model.Gender;
import mgo.model.User;
import mgo.persistence.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	private User joe = new User("Joe", "secret", "Jones", Gender.Male);
	private List<User> joes;
	
	@Mock
	private UserRepository repo;

	private UserController controller = new UserController();

	@Before
	public void setUp() {
		controller.setRepository(repo);
		joes = new ArrayList<User>();
		joes.add(joe);
	}

	@Test
	public void testCheckReturnsOK() throws Exception {
		when(repo.findByUsername("Joe")).thenReturn(joe);

		String result = (String) controller.authenticate(joe);
		assertTrue(result.contains("Welcome"));
	}
	
	@Test
	public void testCheckFailsAuthentication() throws Exception {
		when(repo.findByUsername("Joe")).thenReturn(null);

		String result = (String) controller.authenticate(joe);
		assertTrue(result.contains("403"));
	}
	
	@Test
	public void testFindUserByLastNameAndGender() throws Exception {
		
		when(repo.findByLastNameAndGender("Jones", Gender.Male)).thenReturn(joes);
				
		List<User> result = controller.findUsers(joe.getLastName(), joe.getGender());		
		assertEquals(joe, result.get(0));				
	}
	
}
