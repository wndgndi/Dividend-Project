package com.example.dividendproject.persist.repository;

import com.example.dividendproject.persist.entity.DividendEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DividendRepository extends JpaRepository<DividendEntity, Long> {
    List<DividendEntity> findAllByCompanyId(Long Id);

    boolean existsByCompanyIdAndDate(Long companyId, LocalDateTime date);
}
