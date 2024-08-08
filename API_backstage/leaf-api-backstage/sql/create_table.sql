//用户表

create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    userName     varchar(256) charset latin1                           null comment '用户昵称',
    userAccount  varchar(256) charset latin1                           not null comment '账号',
    userRole     varchar(256) charset latin1 default 'user'            not null comment '用户角色：user / admin',
    userPassword varchar(512) charset latin1                           not null comment '密码',
    createTime   datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint                     default 0                 not null comment '是否删除',
    accessKey    varchar(512)                                          null,
    secretKey    varchar(512)                                          null,
    constraint uni_userAccount
        unique (userAccount)
)
    comment '用户' collate = utf8_bin;
//接口表
create table interface_info
(
    id             bigint auto_increment comment '主键'
        primary key,
    name           varchar(256)                       not null comment '名称',
    description    varchar(256)                       null comment '描述',
    url            varchar(512)                       not null comment '接口地址',
    requestParams  mediumtext                         not null comment '请求参数',
    requestHeader  mediumtext                         null comment '请求头',
    responseHeader mediumtext                         null comment '响应头',
    status         int      default 0                 not null comment '接口状态（0-关闭，1-开启）',
    method         varchar(256)                       not null comment '请求类型',
    userId         bigint                             not null comment '创建人',
    createTime     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete       tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
)
    comment '接口信息' collate = utf8mb4_unicode_ci;

//用户调用接口表
create table user_interface_info
(
    id              bigint auto_increment comment '主键'
        primary key,
    userId          bigint                             not null comment '调用用户 id',
    interfaceInfoId bigint                             not null comment '接口 id',
    totalNum        int      default 0                 not null comment '总调用次数',
    leftNum         int      default 0                 not null comment '剩余调用次数',
    status          int      default 0                 not null comment '0-正常，1-禁用',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete        tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
)
    comment '用户调用接口关系' collate = utf8_bin;

