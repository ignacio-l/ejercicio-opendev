package com.opendev.Peugeot.repository;

import com.opendev.Peugeot.model.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IAutoRepository extends JpaRepository<Auto, Long> {
    List<Auto> findByMarca(String marca);
}
