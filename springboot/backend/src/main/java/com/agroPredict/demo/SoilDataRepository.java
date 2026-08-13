package com.agroPredict.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SoilDataRepository extends JpaRepository<SoilTable, Long> {
}
