package sun.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import sun.model.Gender;
import sun.model.User;


public interface UserRepository extends PagingAndSortingRepository<User, String> {

    // this method shows all 
    public User findByUsername(String username);
    
    // don't show the password 
    @Query(value="{ 'lastName' : ?0 }", fields="{ 'username' : 1, 'lastName' : 1, 'gender' : 1}")
    public List<User> findByLastName(String lastName, Pageable pageable);
    
    @Query(value="{ 'gender' : ?0 }", fields="{ 'username' : 1, 'lastName' : 1, 'gender' : 1}")
    public List<User> findByGender(Gender gndr, Pageable pageable);
}