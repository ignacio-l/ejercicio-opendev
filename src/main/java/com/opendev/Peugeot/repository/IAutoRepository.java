package com.opendev.Peugeot.repository;

import com.opendev.Peugeot.model.Auto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IAutoRepository extends JpaRepository<Auto, Long> {
}
