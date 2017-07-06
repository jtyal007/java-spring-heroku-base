package com.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id // this is required for all Entities which acts as the primary key
	@GeneratedValue(strategy = GenerationType.AUTO) // the strategy used to generate the ID
	@Column(name = "customer_id")
	private int id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	
	// Example of JPA/Hibernate mapping directly from the database
	@OneToOne  //  (optional=false)optional is for inner or outer join, default is true
	@JoinColumn( name="address_id" )
	private Address address;
	
	protected Customer() {
		// Used by JPA
	}
	
	public Customer(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	@Override
	public String toString() {
		return String.format("Customer[id=%d, firstName='%s', lastName='%s', email='%s', address='%s']", id, firstName, lastName, email, address.getAddress());
	}

	
}
