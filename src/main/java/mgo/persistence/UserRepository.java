package mgo.persistence;

import java.util.List;

import mgo.model.Gender;
import mgo.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface UserRepository extends MongoRepository<User, String> {

    public User findByUsername(String username);
    
    // don't show the password 
    @Query(value="{ 'lastName' : ?0 }", fields="{ 'username' : 1, 'lastName' : 1, 'gender' : 1}")
    public List<User> findByLastName(String lastName);
    
    // don't show the password
    @Query(value="{ 'gender' : ?0 }", fields="{ 'username' : 1, 'lastName' : 1, 'gender' : 1}")
    public List<User> findByGender(Gender gndr);
   
    @Query(value="{ 'gender' : ?0 }", fields="{ 'username' : 1, 'lastName' : 1, 'gender' : 1}")
    public List<User> findByLastNameAndGender(String lastName, Gender gender);
    
}