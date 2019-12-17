package com.maintainpayee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.maintainpayee.entity.IfscCode;

public interface IfscCodeRepository extends JpaRepository<IfscCode, Integer> {

	Optional<IfscCode> findByCode(String code);

}
