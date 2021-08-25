package com.handemdowns.persistence.dao;

import com.handemdowns.persistence.model.AdReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AdReportRepository extends JpaRepository<AdReport, Long> {
}