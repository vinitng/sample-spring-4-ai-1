package com.example.demo.repository;

import com.example.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.accounts")
    List<Customer> findAllWithAccounts();

    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.accounts WHERE c.externalRefId = :externalRefId")
    Optional<Customer> findByExternalRefIdWithAccounts(@Param("externalRefId") String externalRefId);

    Optional<Customer> findByExternalRefId(String externalRefId);
}