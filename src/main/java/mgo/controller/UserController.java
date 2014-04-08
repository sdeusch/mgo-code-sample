package mgo.controller;

import java.net.UnknownHostException;
import java.util.List;

import mgo.model.Gender;
import mgo.model.User;
import mgo.persistence.UserRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	private static final Logger LOG = Logger.getLogger(UserController.class);

	@Autowired
	private UserRepository repository;

	/**
	 * Authenticates a user against user collection in MongoDB 
	 * 
	 * @param user - JSON object for user authentication
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
    public @ResponseBody List<User> findUsers(@RequestParam(value="lastName", required=false) String lastName,
    		@RequestParam(value="gender", required=false) Gender gender) {
		
		/**
		 * This section needs more work, possibly introducing 
		 */
		if(lastName!=null && gender !=null) {
			return repository.findByLastNameAndGender(lastName, gender);
		}
		
		if(lastName!=null) {
			return repository.findByLastName(lastName);
		}
		
		if(gender!=null) {
			return repository.findByGender(gender);
		}
		
		// or else return everything - test only 
		return repository.findAll();
	}

	public void setRepository(UserRepository repository) {
		this.repository = repository;
	}
		
}
