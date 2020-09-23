package com.ruan.yuanyuan.sharding_jdbc_example.repository;

import com.ruan.yuanyuan.sharding_jdbc_example.entity.BOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BOrderRepository extends JpaRepository<BOrder,Long> {
}
