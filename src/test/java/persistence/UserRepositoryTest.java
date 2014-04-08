package persistence;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import mgo.model.Gender;
import mgo.model.User;
import mgo.persistence.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

	private User sue = new User("Sue", "secret", "Smith", Gender.Female);
	private User joe = new User("Joe", "secret", "Jones", Gender.Male);
	private List<User> people = new ArrayList<User>();
	private List<User> smiths = new ArrayList<User>();
	private List<User> males = new ArrayList<User>();
	
	@Mock
	private UserRepository repo;

	@Before
	public void setUp() {
		people.add(sue);
		people.add(joe);
		smiths.add(sue);
		males.add(joe);
	}

	@Test
	public void testFindByUsername() {
		when(repo.findByUsername("Sue")).thenReturn(sue);

		User find = repo.findByUsername("Sue");
		assertEquals(sue, find);
	}

	@Test
	public void testFindByLastName() {
		when(repo.findByLastName("Smith")).thenReturn(smiths);

		List<User> find = repo.findByLastName("Smith");
		assertEquals(sue, find.get(0));
	}

	@Test
	public void testFindByGender() {
		when(repo.findByGender(Gender.Male)).thenReturn(males);

		List<User> find = repo.findByGender(Gender.Male);
		assertEquals(joe, find.get(0));
	}

	@Test
	public void testFindByLastNameAndGender() {
		when(repo.findByLastNameAndGender(Mockito.any(String.class), Mockito.any(Gender.class))).thenReturn(people);

		List<User> find = repo.findByLastNameAndGender("Smith", Gender.Female);
		assertEquals(sue, find.get(0));
	}

}
