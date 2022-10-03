package com.gc.vp.repository;

import com.gc.vp.entity.po.DataSourcePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSourceRepository extends JpaRepository<DataSourcePo, Integer> {

}
