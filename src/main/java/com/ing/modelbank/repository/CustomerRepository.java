package com.ing.modelbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;
import com.ing.modelbank.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	public Optional<Customer> findByEmailAndPassword(String email, String password);

	public Optional<Customer> findByEmailAndMobileNo(String email, Long mobileNo);

	public Optional<Customer> findByEmail(String email);

}
