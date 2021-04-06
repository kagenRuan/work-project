package com.ruan.yuanyuan.sharding_jdbc_example.repository;

import com.ruan.yuanyuan.sharding_jdbc_example.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {
}
