package persistence;

import static org.junit.Assert.assertEquals;
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

import sun.model.Gender;
import sun.model.User;
import sun.persistence.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

	private User sue = new User("Sue", "secret", "Smith", Gender.Female);
	private User joe = new User("Joe", "secret", "Jones", Gender.Male);
	private List<User> people = new ArrayList<User>();
	private List<User> smiths = new ArrayList<User>();
	private List<User> males = new ArrayList<User>();
	private Pageable pageableSpec = new PageRequest(0, 10, new Sort(
			Sort.Direction.ASC, "lastName"));
	
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
		when(repo.findByLastName("Smith",pageableSpec)).thenReturn(smiths);

		List<User> find = repo.findByLastName("Smith",pageableSpec);
		assertEquals(sue, find.get(0));
	}

	@Test
	public void testFindByGender() {		
		when(repo.findByGender(Gender.Male,pageableSpec)).thenReturn(males);

		List<User> find = repo.findByGender(Gender.Male,pageableSpec);
		assertEquals(joe, find.get(0));
	}

}
