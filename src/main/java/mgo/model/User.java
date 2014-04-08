package mgo.model;

import org.springframework.data.annotation.Id;


public class User {

	@Id
	private  String id;
	private  String username;
	private  String password;
	private String lastName;
	private Gender gender;

	

	public User(String username, String password, String lastName, Gender gender) {
		super();
		this.username = username;
		this.password = password;
		this.lastName = lastName;
		this.gender = gender;
	}

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	public String getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public Gender getGender() {
		return gender;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", lastName=" + lastName + ", gender=" + gender
				+ "]";
	}	
}
