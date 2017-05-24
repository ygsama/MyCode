package g.domain;

import java.util.Set;

import javax.persistence.Entity;

@Entity
public class User {

	private String userName;
	private String address;
	private Set billingDetails;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Set getBillingDetails() {
		return billingDetails;
	}
	public void setBillingDetails(Set billingDetails) {
		this.billingDetails = billingDetails;
	}

}
