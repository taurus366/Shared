package org.system.shared.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.system.shared.model.entity.CurrencyEntity;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {

    List<CurrencyEntity> findAllByActive(boolean active);
}
