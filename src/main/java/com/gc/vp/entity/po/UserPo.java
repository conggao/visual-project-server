package com.gc.vp.entity.po;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_po")
@SQLDelete(sql = "update vp_datasource set status=0 where id=?")
@Where(clause = "status=1")
@Data
public class UserPo extends BasePo {
    @Column(name = "user_name")
    private String username;
    private String password;
}