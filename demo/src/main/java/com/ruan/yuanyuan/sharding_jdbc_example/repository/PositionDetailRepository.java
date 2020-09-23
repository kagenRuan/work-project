package com.ruan.yuanyuan.sharding_jdbc_example.repository;

import com.ruan.yuanyuan.sharding_jdbc_example.entity.PositionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionDetailRepository extends JpaRepository<PositionDetail,Long> {
}
