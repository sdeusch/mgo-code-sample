package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import sun.controller.UserController;
import sun.model.Gender;
import sun.model.User;
import sun.persistence.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	private User joe = new User("Joe", "secret", "Jones", Gender.Male);
	private List<User> joes;

	@Mock
	private UserRepository repo;

	private UserController controller = new UserController();
	private Pageable pageableSpec = new PageRequest(0, 10, new Sort(
			Sort.Direction.ASC, "lastName"));

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
	public void testFindUserByLastName() throws Exception {

		when(repo.findByLastName("Jones",pageableSpec)).thenReturn(joes);

		Iterable<User> result = controller.findUsers(joe.getLastName(),
				joe.getGender(),0,10);
		assertEquals(joe, result.iterator().next());
	}

}
