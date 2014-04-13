package sun.controller;

import java.net.UnknownHostException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.model.Gender;
import sun.model.User;
import sun.persistence.UserRepository;

@Controller
public class UserController {

	private static final Logger LOG = Logger.getLogger(UserController.class);

	@Autowired
	private UserRepository repository;

	/**
	 * Authenticates a user against user collection in MongoDB
	 * 
	 * @param user
	 *            - JSON object for user authentication
	 * @return either a Welcome message on success or 403 error
	 * @throws UnknownHostException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object authenticate(@RequestBody User user)
			throws UnknownHostException {
		LOG.debug("User send is " + user);

		if (user != null) {
			User dbUser = repository.findByUsername(user.getUsername());
			if (dbUser != null
					&& user.getPassword().equals(dbUser.getPassword())) {
				return String.format("Welcome %s!", user.getUsername());
			}
		}

		return String.format("Access Denied - Status 403");
	}

	@RequestMapping("/users")
	public @ResponseBody List<User> findUsers(
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "gender", required = false) Gender gender,
			@RequestParam(value= "start", required = false, defaultValue = "0") Integer start,
			@RequestParam(value = "n", required = false, defaultValue = "10") Integer n) {

		/*
		 * This section could be refactored using QueryBuilder API
		 * for now using UserRepository which is provided by Spring Data, less flexible
		 * Added Pagination on 4/9
		 */
		if (lastName != null) {
			return repository.findByLastName(lastName,constructPageable(start, n));
		}

		if (gender != null) {
			return repository.findByGender(gender,constructPageable(start, n));
		}

		// or else return everything - test only
		return repository.findAll(constructPageable(start, n)).getContent();
	}

	/*
	 * Building Pagination, always sort by 'lastName' asc. 
	 */
	private Pageable constructPageable(int startN, int resultsN) {
		Pageable pageableSpec = new PageRequest(startN, resultsN, new Sort(Sort.Direction.ASC, "lastName"));
        return pageableSpec;
	}
	
	public void setRepository(UserRepository repository) {
		this.repository = repository;
	}

	
}
