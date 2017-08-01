package application.workshop.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({ @NamedQuery(name = "Worker.findAllOrdered", query = "SELECT w FROM Worker w ORDER BY w.lastName ASC"),
		@NamedQuery(name = "Worker.findByLastName", query = "SELECT w FROM Worker w WHERE w.lastName = :lastName"),
		@NamedQuery(name = "Worker.findWorkerToLogin", query = "SELECT w FROM Worker w WHERE w.username = :username AND w.password = :password") })
@Entity
public class Worker implements Serializable {
	private static final long serialVersionUID = 7749124523799484448L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, length = 255)
	private String firstName;

	@Column(nullable = false, length = 255)
	private String lastName;

	@Column(nullable = false, length = 255)
	private String address;

	@Column(nullable = false, length = 255)
	private String emailAddress;

	@Column(nullable = false)
	private Integer phone;

	@Column(nullable = false, length = 20, unique = true)
	private String username;

	@Column(nullable = false, length = 50)
	private String password;

	@ElementCollection
	@CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))
	@Column(name = "role")
	private List<String> roles = new LinkedList<>();

	public Worker() {
	}
	
	public Worker(String role) {
		this.roles.add(role);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.reset();
		messageDigest.update(password.getBytes());
		byte[] digest = messageDigest.digest();
		BigInteger bigInt = new BigInteger(1, digest);
		password = bigInt.toString(16);
		this.password = password;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRole(String role) {
		this.roles.add(role);
	}

	@Override
	public String toString() {
		return lastName;
	}
}
