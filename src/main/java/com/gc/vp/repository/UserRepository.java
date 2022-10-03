package com.gc.vp.repository;

import com.gc.vp.entity.po.UserPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserPo, Integer> {
    UserPo findByUsername(String userName);

}
