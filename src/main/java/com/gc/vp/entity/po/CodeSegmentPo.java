package com.gc.vp.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 代码片段
 * </p>
 *
 * @author conggao
 * @since 2022-11-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("vp_code_segment")
public class CodeSegmentPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("type")
    private Integer type;

    @TableField("category_id")
    private Integer categoryId;

    @TableField("title")
    private String title;

    @TableField("language")
    private String language;

    @TableField("content")
    private String content;

    /**
     * 创建人的用户id
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField("update_user")
    private String updateUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @TableField("del_flag")
    private Integer delFlag;


}
