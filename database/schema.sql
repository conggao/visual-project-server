create database `visual-proj` character set utf8mb4 collate utf8mb4_unicode_ci;
use `visual-proj`;
create table vp_datasource
(
    id          int auto_increment
        primary key,
    name        varchar(255)                         not null,
    comment     text                                 null,
    create_time timestamp  default CURRENT_TIMESTAMP not null,
    create_user varchar(50)                          not null,
    update_time timestamp  default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    update_user varchar(50)                          not null,
    enable      tinyint(1) default 1                 not null
);

create table vp_user
(
    id          bigint auto_increment comment '主键'
        primary key,
    user_name   varchar(64) default 'NULL' not null comment '用户名',
    nick_name   varchar(64) default 'NULL' not null comment '昵称',
    password    varchar(64) default 'NULL' not null comment '密码',
    enable      char        default '0'    null comment '账号状态（0停用 1正常）',
    email       varchar(64)                null comment '邮箱',
    phone       varchar(32)                null comment '手机号',
    sex         char                       null comment '用户性别（0男，1女，2未知）',
    avatar      varchar(128)               null comment '头像',
    user_type   char        default '1'    not null comment '用户类型（0管理员，1普通用户）',
    create_user varchar(50)                null comment '创建人的用户id',
    create_time datetime                   null comment '创建时间',
    update_user varchar(50)                null comment '更新人',
    update_time datetime                   null comment '更新时间',
    del_flag    int         default 0      null comment '删除标志（0代表未删除，1代表已删除）'
)
    comment '用户表';

create table vp_code_category
(
    id          int auto_increment
        primary key,
    parent_id   int default 0 not null,
    name        varchar(50)           not null,
    create_user varchar(50)   null comment '创建人的用户id',
    create_time datetime      null comment '创建时间',
    update_user varchar(50)   null comment '更新人',
    update_time datetime      null comment '更新时间',
    del_flag    int default 0 null comment '删除标志（0代表未删除，1代表已删除）'
)
    comment '代码分类';

create table vp_code_segment
(
    id          int auto_increment
        primary key,
    category_id int           not null,
    content     text          not null,
    create_user varchar(50)   null comment '创建人的用户id',
    create_time datetime      null comment '创建时间',
    update_user varchar(50)   null comment '更新人',
    update_time datetime      null comment '更新时间',
    del_flag    int default 0 null comment '删除标志（0代表未删除，1代表已删除）'
)
    comment '代码片段';

