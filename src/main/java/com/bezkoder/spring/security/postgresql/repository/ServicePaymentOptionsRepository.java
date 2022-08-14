package com.bezkoder.spring.security.postgresql.repository;

import com.bezkoder.spring.security.postgresql.models.ServicePaymentOptions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicePaymentOptionsRepository  extends JpaRepository<ServicePaymentOptions, Long> {
}
