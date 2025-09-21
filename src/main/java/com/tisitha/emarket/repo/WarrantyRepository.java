package com.tisitha.emarket.repo;

import com.tisitha.emarket.model.Warranty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarrantyRepository extends JpaRepository<Warranty,Long> {
}
