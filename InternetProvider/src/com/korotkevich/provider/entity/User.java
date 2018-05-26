package com.korotkevich.provider.entity;

import java.util.Arrays;

public class User {
	private int id;
	private String login;
	private char[] password;
	private String name;
	private String surname;
	private String email;
	private UserRole role;
	private UserStatus status;
	private String registrationDate;
	private String birthDate;
	private String avatarPath;
	
	/**
	 * Default constructor
	 */
	public User(){
	}
	
	/**
	 * Constructor with login
	 * @param login
	 */
	public User(String login){
		this.login = login;
	}
	
	/**
	 * Constructor with id
	 * @param id
	 */
	public User(int id){
		this.id = id;
	}
	
	/**
	 * Constructor with id, login
	 * @param id
	 * @param login
	 */
	public User(int id, String login){
		this.id = id;
		this.login = login;
	}
	
	/**
	 * Constructor with login, password
	 * @param login
	 * @param password
	 */
	public User(String login, char[] password){
		this.login = login;
		this.password = password;
	}
	
	/**
	 * Constructor with id, login, password, name, surname, email, birthDate 
	 * @param id
	 * @param login
	 * @param password
	 * @param name
	 * @param surname
	 * @param email
	 * @param birthDate
	 */
	public User(int id, String login, char[] password, String name, String surname, String email, String birthDate) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.birthDate = birthDate;
	}
	
	/**
	 * Constructor with id, login, name, surname, email, birthDate , status
	 * @param id
	 * @param login
	 * @param name
	 * @param surname
	 * @param email
	 * @param birthDate
	 * @param status
	 */
	public User(int id, String login, String name, String surname, String email, String birthDate, UserStatus status) {
		this.id = id;
		this.login = login;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.birthDate = birthDate;
		this.status = status;
	}
	
	/**
	 * Constructor with id, login, password, name, surname, email, birthDate , status
	 * @param id
	 * @param login
	 * @param password
	 * @param name
	 * @param surname
	 * @param email
	 * @param birthDate
	 * @param status
	 */
	public User(int id, String login, char[] password, String name, String surname, String email, String birthDate, UserStatus status) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.birthDate = birthDate;
		this.status = status;
	}
	
	/**
	 * Constructor with id, login, password, name, surname, email, birthDate , status, avatarPath
	 * @param id
	 * @param login
	 * @param name
	 * @param surname
	 * @param email
	 * @param birthDate
	 * @param status
	 * @param avatarPath
	 */
	public User(int id, String login, String name, String surname, String email, String birthDate, UserStatus status, String avatarPath) {
		this.id = id;
		this.login = login;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.birthDate = birthDate;
		this.status = status;
		this.avatarPath = avatarPath;
	}
	
	/**
	 * Constructor with id, login, name, surname, email, role, status, registrationDate, birthDate, avatarPath
	 * @param id
	 * @param login
	 * @param name
	 * @param surname
	 * @param email
	 * @param role
	 * @param status
	 * @param registrationDate
	 * @param birthDate
	 */
	public User(int id, String login, String name, String surname, String email, UserRole role,
			UserStatus status, String registrationDate, String birthDate) {
		this.id = id;
		this.login = login;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.role = role;
		this.status = status;
		this.registrationDate = registrationDate;
		this.birthDate = birthDate;
	}
	
	/**
	 * Constructor with id, login, name, surname, email, role, status, registrationDate, birthDate, avatarPath
	 * @param id
	 * @param login
	 * @param name
	 * @param surname
	 * @param email
	 * @param role
	 * @param status
	 * @param registrationDate
	 * @param birthDate
	 * @param avatarPath
	 */
	public User(int id, String login, String name, String surname, String email, UserRole role,
			UserStatus status, String registrationDate, String birthDate, String avatarPath) {
		this.id = id;
		this.login = login;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.role = role;
		this.status = status;
		this.registrationDate = registrationDate;
		this.birthDate = birthDate;
		this.avatarPath = avatarPath;
	}
	
	/**
	 * Constructor with id, login, password, name, surname, email, role, status, registrationDate, birthDate
	 * @param id
	 * @param login
	 * @param password
	 * @param name
	 * @param surname
	 * @param email
	 * @param role
	 * @param status
	 * @param registrationDate
	 * @param birthDate
	 */
	public User(int id, String login, char[] password, String name, String surname, String email, UserRole role,
			UserStatus status, String registrationDate, String birthDate) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.role = role;
		this.status = status;
		this.registrationDate = registrationDate;
		this.birthDate = birthDate;
	}

	/**
	 * Get the id
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the login
	 * @return login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Set the login 
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Get the password
	 * @return password
	 */
	public char[] getPassword() {
		return password;
	}

	/**
	 * Set the password 
	 * @param password
	 */
	public void setPassword(char[] password) {
		this.password = password;
	}

	/**
	 * Get the name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the surname
	 * @return surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Set the surname 
	 * @param surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Get the email
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the email 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get the role
	 * @return role
	 */
	public UserRole getRole() {
		return role;
	}

	/**
	 * Set the role 
	 * @param role
	 */
	public void setRole(UserRole role) {
		this.role = role;
	}

	/**
	 * Get the status
	 * @return status
	 */
	public UserStatus getStatus() {
		return status;
	}

	/**
	 * Set the status 
	 * @param status
	 */
	public void setStatus(UserStatus status) {
		this.status = status;
	}

	/**
	 * Get the registrationDate 
	 * @return registrationDate
	 */
	public String getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * Set the registrationDate 
	 * @param registrationDate
	 */
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * Get the birthDate  
	 * @return birthDate
	 */
	public String getBirthDate() {
		return birthDate;
	}

	/**
	 * Set the birthDate  
	 * @param birthDate
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	/**
	 * Get the avatar  
	 * @return avatar
	 */
	public String getAvatarPath() {
		return avatarPath;
	}

	/**
	 * Set the avatar  
	 * @param avatar
	 */
	public void setAvatarPath(String avatar) {
		this.avatarPath = avatar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(password);
		result = prime * result + ((registrationDate == null) ? 0 : registrationDate.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (!Arrays.equals(password, other.password))
			return false;
		if (registrationDate == null) {
			if (other.registrationDate != null)
				return false;
		} else if (!registrationDate.equals(other.registrationDate))
			return false;
		if (role != other.role)
			return false;
		if (status != other.status)
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + Arrays.toString(password) + ", name=" + name
				+ ", surname=" + surname + ", email=" + email + ", role=" + role + ", status=" + status
				+ ", registrationDate=" + registrationDate + ", birthDate=" + birthDate + "]";
	}
	
}
