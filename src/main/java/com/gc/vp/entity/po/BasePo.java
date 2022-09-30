package com.gc.vp.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
@MappedSuperclass
@Data
@NoArgsConstructor
public class BasePo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "create_user", nullable = false)
    private String createUser;
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @Column(name = "update_user", nullable = false)
    private String updateUser;
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    @Column(name = "enable")
    private Boolean enable;

    public BasePo(String createUser, String updateUser, Boolean enable) {
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.enable = enable;
    }
}
