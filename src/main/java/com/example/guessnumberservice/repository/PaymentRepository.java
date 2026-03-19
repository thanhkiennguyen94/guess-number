package com.example.guessnumberservice.repository;

import com.example.guessnumberservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByTxnRef(String txnRef);
    @Modifying
    @Query("""
    UPDATE Payment p
    SET p.status = 'SUCCESS'
    WHERE p.txnRef = :txnRef AND p.status = 'PENDING'
""")
    int updateStatusIfPending(String txnRef);
}