package com.gc.vp.entity.po;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vp_datasource")
@SQLDelete(sql = "update vp_datasource set enable=0 where id=?")
@Where(clause = "enable=1")
@Data
public class DataSourcePo extends BasePo {

    @Column(name = "name")
    private String name;
    @Column(name = "comment")
    private String comment;

    public DataSourcePo(){
        super();
    }
    public DataSourcePo(String createUser, String updateUser) {
        super(createUser, updateUser, true);
    }

    public DataSourcePo(String name, String createUser, String updateUser) {
        super(createUser, updateUser, true);
        this.name = name;
    }
}
