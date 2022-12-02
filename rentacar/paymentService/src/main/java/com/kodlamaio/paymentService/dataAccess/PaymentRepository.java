package com.kodlamaio.paymentService.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.paymentService.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {

}
