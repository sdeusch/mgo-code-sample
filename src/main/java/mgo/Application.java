package mgo;

import mgo.model.Gender;
import mgo.model.User;
import mgo.persistence.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application implements CommandLineRunner {

  
    @Autowired
	private UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		initializeMongoDBUserCollection();		
	}

	/*
	 * This creates a few users for testing 
	 */
	private void initializeMongoDBUserCollection() {
		repository.deleteAll();

		// save a couple of Users
		repository.save(new User("demo", "password", "lastname", Gender.Unknown));
		repository.save(new User("Alice", "secret", "Smith", Gender.Female));
		repository.save(new User("Bob", "secret", "Smith", Gender.Male));		
		repository.save(new User("Tom", "2xa23!", "Oglesby", Gender.Male));
	}

}
