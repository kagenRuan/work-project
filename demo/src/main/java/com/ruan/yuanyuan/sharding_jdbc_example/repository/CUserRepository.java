package com.ruan.yuanyuan.sharding_jdbc_example.repository;

import com.ruan.yuanyuan.sharding_jdbc_example.entity.CUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CUserRepository extends JpaRepository<CUser,Long> {

    public List<CUser> findByPwd(String pwd);

}
