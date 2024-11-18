/* SQLINES DEMO *** -2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * keyword: type\resource
 */
CREATE TABLE config_info (
                             id number(19) NOT NULL,
                             data_id varchar2(255 char) NOT NULL,
                             group_id varchar2(128 char) DEFAULT NULL,
                             content clob NOT NULL,
                             md5 varchar2(32 char) DEFAULT NULL,
                             gmt_create timestamp(0) DEFAULT SYSTIMESTAMP NOT NULL,
                             gmt_modified timestamp(0) DEFAULT SYSTIMESTAMP NOT NULL,
                             src_user clob,
                             src_ip varchar2(50 char) DEFAULT NULL,
                             app_name varchar2(128 char) DEFAULT NULL,
                             tenant_id varchar2(128 char) DEFAULT '',
                             c_desc varchar2(256 char) DEFAULT NULL,
                             c_use varchar2(64 char) DEFAULT NULL,
                             effect varchar2(64 char) DEFAULT NULL,
                             type varchar2(64 char) DEFAULT NULL,
                             c_schema clob,
                             encrypted_data_key clob DEFAULT NULL,
                             PRIMARY KEY (id),
                             CONSTRAINT uk_configinfo_datagrouptenant UNIQUE  (data_id,group_id,tenant_id)
)  ;
COMMENT ON TABLE config_info IS 'config_info';
CREATE SEQUENCE config_info_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER config_info_seq_tr
    BEFORE INSERT ON config_info FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT config_info_seq.NEXTVAL INTO :NEW.id FROM DUAL;
END;
/
COMMENT ON COLUMN config_info.id IS 'id';
COMMENT ON COLUMN config_info.data_id IS 'data_id';
COMMENT ON COLUMN config_info.group_id IS 'group_id';
COMMENT ON COLUMN config_info.content IS 'content';
COMMENT ON COLUMN config_info.md5 IS 'md5';
COMMENT ON COLUMN config_info.gmt_create IS '创建时间';
COMMENT ON COLUMN config_info.gmt_modified IS '修改时间';
COMMENT ON COLUMN config_info.src_user IS 'source user';
COMMENT ON COLUMN config_info.src_ip IS 'source ip';
COMMENT ON COLUMN config_info.app_name IS 'app_name';
COMMENT ON COLUMN config_info.tenant_id IS '租户字段';
COMMENT ON COLUMN config_info.c_desc IS 'configuration description';
COMMENT ON COLUMN config_info.c_use IS 'configuration usage';
COMMENT ON COLUMN config_info.effect IS '配置生效的描述';
COMMENT ON COLUMN config_info.type IS '配置的类型';
COMMENT ON COLUMN config_info.c_schema IS '配置的模式';
COMMENT ON COLUMN config_info.encrypted_data_key IS '密钥';


CREATE TABLE config_info_aggr (
                                  id number(19) NOT NULL,
                                  data_id varchar2(255 char) NOT NULL,
                                  group_id varchar2(128 char) NOT NULL,
                                  datum_id varchar2(255 char) NOT NULL,
                                  content clob NOT NULL,
                                  gmt_modified timestamp(0) NOT NULL,
                                  app_name varchar2(128 char) DEFAULT NULL,
                                  tenant_id varchar2(128 char) DEFAULT '',
                                  PRIMARY KEY (id),
                                  CONSTRAINT uk_cfgifaggr_dgtd UNIQUE  (data_id,group_id,tenant_id,datum_id)
)  ;
COMMENT ON TABLE config_info_aggr IS '增加租户字段';
CREATE SEQUENCE config_info_aggr_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER config_info_aggr_seq_tr
    BEFORE INSERT ON config_info_aggr FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT config_info_aggr_seq.NEXTVAL INTO :NEW.id FROM DUAL;
END;
/
COMMENT ON COLUMN config_info_aggr.id IS 'id';
COMMENT ON COLUMN config_info_aggr.data_id IS 'data_id';
COMMENT ON COLUMN config_info_aggr.group_id IS 'group_id';
COMMENT ON COLUMN config_info_aggr.datum_id IS 'datum_id';
COMMENT ON COLUMN config_info_aggr.content IS '内容';
COMMENT ON COLUMN config_info_aggr.gmt_modified IS '修改时间';
COMMENT ON COLUMN config_info_aggr.app_name IS 'app_name';
COMMENT ON COLUMN config_info_aggr.tenant_id IS '租户字段';


CREATE TABLE config_info_beta (
                                  id number(19) NOT NULL,
                                  data_id varchar2(255 char) NOT NULL,
                                  group_id varchar2(128 char) NOT NULL,
                                  app_name varchar2(128 char) DEFAULT NULL,
                                  content clob NOT NULL,
                                  beta_ips varchar2(1024 char) DEFAULT NULL,
                                  md5 varchar2(32 char) DEFAULT NULL,
                                  gmt_create timestamp(0) DEFAULT SYSTIMESTAMP NOT NULL,
                                  gmt_modified timestamp(0) DEFAULT SYSTIMESTAMP NOT NULL,
                                  src_user clob,
                                  src_ip varchar2(50 char) DEFAULT NULL,
                                  tenant_id varchar2(128 char) DEFAULT '',
                                  encrypted_data_key clob DEFAULT NULL,
                                  PRIMARY KEY (id),
                                  CONSTRAINT uk_cfgifbt_dgt UNIQUE (data_id,group_id,tenant_id)
)  ;
COMMENT ON TABLE config_info_beta IS 'config_info_beta';
CREATE SEQUENCE config_info_beta_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER config_info_beta_seq_tr
    BEFORE INSERT ON config_info_beta FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT config_info_beta_seq.NEXTVAL INTO :NEW.id FROM DUAL;
END;
/
COMMENT ON COLUMN config_info_beta.id IS 'id';
COMMENT ON COLUMN config_info_beta.data_id IS 'data_id';
COMMENT ON COLUMN config_info_beta.group_id IS 'group_id';
COMMENT ON COLUMN config_info_beta.app_name IS 'app_name';
COMMENT ON COLUMN config_info_beta.content IS 'content';
COMMENT ON COLUMN config_info_beta.beta_ips IS 'betaIps';
COMMENT ON COLUMN config_info_beta.md5 IS 'md5';
COMMENT ON COLUMN config_info_beta.gmt_create IS '创建时间';
COMMENT ON COLUMN config_info_beta.gmt_modified IS '修改时间';
COMMENT ON COLUMN config_info_beta.src_user IS 'source user';
COMMENT ON COLUMN config_info_beta.src_ip IS 'source ip';
COMMENT ON COLUMN config_info_beta.tenant_id IS '租户字段';
COMMENT ON COLUMN config_info_beta.encrypted_data_key IS '密钥';


CREATE TABLE config_info_tag (
                                 id number(19) NOT NULL,
                                 data_id varchar2(255 char) NOT NULL,
                                 group_id varchar2(128 char) NOT NULL,
                                 tenant_id varchar2(128 char) DEFAULT '',
                                 tag_id varchar2(128 char) NOT NULL,
                                 app_name varchar2(128 char) DEFAULT NULL,
                                 content clob NOT NULL,
                                 md5 varchar2(32 char) DEFAULT NULL,
                                 gmt_create timestamp(0) DEFAULT SYSTIMESTAMP NOT NULL,
                                 gmt_modified timestamp(0) DEFAULT SYSTIMESTAMP NOT NULL,
                                 src_user clob,
                                 src_ip varchar2(50 char) DEFAULT NULL,
                                 PRIMARY KEY (id),
                                 CONSTRAINT uk_cit_dgtt UNIQUE  (data_id,group_id,tenant_id,tag_id)
)  ;
COMMENT ON TABLE config_info_tag IS 'config_info_tag';
CREATE SEQUENCE config_info_tag_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER config_info_tag_seq_tr
    BEFORE INSERT ON config_info_tag FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT config_info_tag_seq.NEXTVAL INTO :NEW.id FROM DUAL;
END;
/
COMMENT ON COLUMN config_info_tag.id IS 'id';
COMMENT ON COLUMN config_info_tag.data_id IS 'data_id';
COMMENT ON COLUMN config_info_tag.group_id IS 'group_id';
COMMENT ON COLUMN config_info_tag.tenant_id IS 'tenant_id';
COMMENT ON COLUMN config_info_tag.tag_id IS 'tag_id';
COMMENT ON COLUMN config_info_tag.app_name IS 'app_name';
COMMENT ON COLUMN config_info_tag.content IS 'content';
COMMENT ON COLUMN config_info_tag.md5 IS 'md5';
COMMENT ON COLUMN config_info_tag.gmt_create IS '创建时间';
COMMENT ON COLUMN config_info_tag.gmt_modified IS '修改时间';
COMMENT ON COLUMN config_info_tag.src_user IS 'source user';
COMMENT ON COLUMN config_info_tag.src_ip IS 'source ip';


CREATE TABLE config_tags_relation (
                                      id number(19) NOT NULL,
                                      tag_name varchar2(128 char) NOT NULL,
                                      tag_type varchar2(64 char) DEFAULT NULL,
                                      data_id varchar2(255 char) NOT NULL,
                                      group_id varchar2(128 char) NOT NULL,
                                      tenant_id varchar2(128 char) DEFAULT '',
                                      nid number(19) NOT NULL,
                                      PRIMARY KEY (nid),
                                      CONSTRAINT uk_ctr_cit UNIQUE  (id,tag_name,tag_type)
)  ;
COMMENT ON TABLE config_tags_relation IS 'config_tag_relation';
CREATE SEQUENCE config_tags_relation_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER config_tags_relation_seq_tr
    BEFORE INSERT ON config_tags_relation FOR EACH ROW
    WHEN (NEW.nid IS NULL)
BEGIN
    SELECT config_tags_relation_seq.NEXTVAL INTO :NEW.nid FROM DUAL;
END;
/
COMMENT ON COLUMN config_tags_relation.id IS 'id';
COMMENT ON COLUMN config_tags_relation.tag_name IS 'tag_name';
COMMENT ON COLUMN config_tags_relation.tag_type IS 'tag_type';
COMMENT ON COLUMN config_tags_relation.data_id IS 'data_id';
COMMENT ON COLUMN config_tags_relation.group_id IS 'group_id';
COMMENT ON COLUMN config_tags_relation.tenant_id IS 'tenant_id';
COMMENT ON COLUMN config_tags_relation.nid IS 'nid, 自增长标识';
CREATE INDEX idx_tenant_id ON config_tags_relation (tenant_id);


CREATE TABLE group_capacity (
                                id number(19) NOT NULL,
                                group_id varchar2(128 char) DEFAULT '' NOT NULL,
                                quota number(10) DEFAULT '0' NOT NULL,
                                usage number(10) DEFAULT '0' NOT NULL,
                                max_size number(10) DEFAULT '0' NOT NULL,
                                max_aggr_count number(10) DEFAULT '0' NOT NULL,
                                max_aggr_size number(10) DEFAULT '0' NOT NULL,
                                max_history_count number(10) DEFAULT '0' NOT NULL,
                                gmt_create timestamp(0) DEFAULT SYSTIMESTAMP NOT NULL,
                                gmt_modified timestamp(0) DEFAULT SYSTIMESTAMP NOT NULL,
                                PRIMARY KEY (id),
                                CONSTRAINT uk_group_id UNIQUE (group_id)
)  ;
COMMENT ON TABLE group_capacity IS '集群、各Group容量信息表';
CREATE SEQUENCE group_capacity_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER group_capacity_seq_tr
    BEFORE INSERT ON group_capacity FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT group_capacity_seq.NEXTVAL INTO :NEW.id FROM DUAL;
END;
/
COMMENT ON COLUMN group_capacity.id IS '主键ID';
COMMENT ON COLUMN group_capacity.group_id IS 'Group ID，空字符表示整个集群';
COMMENT ON COLUMN group_capacity.quota IS '配额，0表示使用默认值';
COMMENT ON COLUMN group_capacity.usage IS '使用量';
COMMENT ON COLUMN group_capacity.max_size IS '单个配置大小上限，单位为字节，0表示使用默认值';
COMMENT ON COLUMN group_capacity.max_aggr_count IS '聚合子配置最大个数，，0表示使用默认值';
COMMENT ON COLUMN group_capacity.max_aggr_size IS '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值';
COMMENT ON COLUMN group_capacity.max_history_count IS '最大变更历史数量';
COMMENT ON COLUMN group_capacity.gmt_create IS '创建时间';
COMMENT ON COLUMN group_capacity.gmt_modified IS '修改时间';


CREATE TABLE his_config_info (
                                 id number(19) NOT NULL,
                                 nid number(19) NOT NULL,
                                 data_id varchar2(255 char) NOT NULL,
                                 group_id varchar2(128 char) NOT NULL,
                                 app_name varchar2(128 char) DEFAULT NULL,
                                 content clob NOT NULL,
                                 md5 varchar2(32 char) DEFAULT NULL,
                                 gmt_create timestamp(0) DEFAULT SYSTIMESTAMP NOT NULL,
                                 gmt_modified timestamp(0) DEFAULT SYSTIMESTAMP NOT NULL,
                                 src_user clob,
                                 src_ip varchar2(50 char) DEFAULT NULL,
                                 op_type char(10 char) DEFAULT NULL,
                                 tenant_id varchar2(128 char) DEFAULT '',
                                 encrypted_data_key clob DEFAULT NULL,
                                 PRIMARY KEY (nid)
)  ;
COMMENT ON TABLE his_config_info IS '多租户改造';
CREATE SEQUENCE his_config_info_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER his_config_info_seq_tr
    BEFORE INSERT ON his_config_info FOR EACH ROW
    WHEN (NEW.nid IS NULL)
BEGIN
    SELECT his_config_info_seq.NEXTVAL INTO :NEW.nid FROM DUAL;
END;
/
COMMENT ON COLUMN his_config_info.id IS 'id';
COMMENT ON COLUMN his_config_info.nid IS 'nid, 自增标识';
COMMENT ON COLUMN his_config_info.data_id IS 'data_id';
COMMENT ON COLUMN his_config_info.group_id IS 'group_id';
COMMENT ON COLUMN his_config_info.app_name IS 'app_name';
COMMENT ON COLUMN his_config_info.content IS 'content';
COMMENT ON COLUMN his_config_info.md5 IS 'md5';
COMMENT ON COLUMN his_config_info.gmt_create IS '创建时间';
COMMENT ON COLUMN his_config_info.gmt_modified IS '修改时间';
COMMENT ON COLUMN his_config_info.src_user IS 'source user';
COMMENT ON COLUMN his_config_info.src_ip IS 'source ip';
COMMENT ON COLUMN his_config_info.op_type IS 'operation type';
COMMENT ON COLUMN his_config_info.tenant_id IS '租户字段';
COMMENT ON COLUMN his_config_info.encrypted_data_key IS '密钥';
CREATE INDEX idx_gmt_create ON his_config_info (gmt_create);
CREATE INDEX idx_gmt_modified ON his_config_info (gmt_modified);
CREATE INDEX idx_did ON his_config_info (data_id);


CREATE TABLE tenant_capacity (
                                 id number(19) NOT NULL,
                                 tenant_id varchar2(128 char) DEFAULT '' NOT NULL,
                                 quota number(10) DEFAULT '0' NOT NULL,
                                 usage number(10) DEFAULT '0' NOT NULL,
                                 max_size number(10) DEFAULT '0' NOT NULL,
                                 max_aggr_count number(10) DEFAULT '0' NOT NULL,
                                 max_aggr_size number(10) DEFAULT '0' NOT NULL,
                                 max_history_count number(10) DEFAULT '0' NOT NULL,
                                 gmt_create timestamp(0) DEFAULT SYSTIMESTAMP NOT NULL,
                                 gmt_modified timestamp(0) DEFAULT SYSTIMESTAMP NOT NULL,
                                 PRIMARY KEY (id),
                                 CONSTRAINT uk_tenant_id UNIQUE (tenant_id)
)  ;
COMMENT ON TABLE tenant_capacity IS '租户容量信息表';
CREATE SEQUENCE tenant_capacity_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER tenant_capacity_seq_tr
    BEFORE INSERT ON tenant_capacity FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT tenant_capacity_seq.NEXTVAL INTO :NEW.id FROM DUAL;
END;
/
COMMENT ON COLUMN tenant_capacity.id IS '主键ID';
COMMENT ON COLUMN tenant_capacity.tenant_id IS 'Tenant ID';
COMMENT ON COLUMN tenant_capacity.quota IS '配额，0表示使用默认值';
COMMENT ON COLUMN tenant_capacity.usage IS '使用量';
COMMENT ON COLUMN tenant_capacity.max_size IS '单个配置大小上限，单位为字节，0表示使用默认值';
COMMENT ON COLUMN tenant_capacity.max_aggr_count IS '聚合子配置最大个数';
COMMENT ON COLUMN tenant_capacity.max_aggr_size IS '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值';
COMMENT ON COLUMN tenant_capacity.max_history_count IS '最大变更历史数量';
COMMENT ON COLUMN tenant_capacity.gmt_create IS '创建时间';
COMMENT ON COLUMN tenant_capacity.gmt_modified IS '修改时间';


CREATE TABLE tenant_info (
                             id number(19) NOT NULL,
                             kp varchar2(128 char) NOT NULL,
                             tenant_id varchar2(128 char) default '',
                             tenant_name varchar2(128 char) default '',
                             tenant_desc varchar2(256 char) DEFAULT NULL,
                             create_source varchar2(32 char) DEFAULT NULL,
                             gmt_create number(19) NOT NULL,
                             gmt_modified number(19) NOT NULL,
                             PRIMARY KEY (id),
                             CONSTRAINT uk_t_i_kti UNIQUE  (kp,tenant_id)
)  ;
COMMENT ON TABLE tenant_info IS 'tenant_info';
CREATE SEQUENCE tenant_info_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER tenant_info_seq_tr
    BEFORE INSERT ON tenant_info FOR EACH ROW
    WHEN (NEW.id IS NULL)
BEGIN
    SELECT tenant_info_seq.NEXTVAL INTO :NEW.id FROM DUAL;
END;
/
COMMENT ON COLUMN tenant_info.id IS 'id';
COMMENT ON COLUMN tenant_info.kp IS 'kp';
COMMENT ON COLUMN tenant_info.tenant_id IS 'tenant_id';
COMMENT ON COLUMN tenant_info.tenant_name IS 'tenant_name';
COMMENT ON COLUMN tenant_info.tenant_desc IS 'tenant_desc';
COMMENT ON COLUMN tenant_info.create_source IS 'create_source';
COMMENT ON COLUMN tenant_info.gmt_create IS '创建时间';
COMMENT ON COLUMN tenant_info.gmt_modified IS '修改时间';
CREATE INDEX idx_tenant_id2 ON tenant_info (tenant_id);


CREATE TABLE users (
                       username varchar2(50 char) NOT NULL PRIMARY KEY,
                       password varchar2(500 char) NOT NULL,
                       enabled char(1) NOT NULL
);
COMMENT ON COLUMN users.username IS 'username';
COMMENT ON COLUMN users.password IS 'password';
COMMENT ON COLUMN users.enabled IS 'enabled';


CREATE TABLE roles (
                       username varchar2(50 char) NOT NULL,
                       role varchar2(50 char) NOT NULL,
                       CONSTRAINT idx_user_role UNIQUE  (username, role)
);
COMMENT ON COLUMN roles.username IS 'username';
COMMENT ON COLUMN roles.role IS 'role';


CREATE TABLE permissions (
                             role varchar2(50 char) NOT NULL,
                             resources varchar2(128 char) NOT NULL,
                             action varchar2(8 char) NOT NULL,
                             CONSTRAINT uk_r_p UNIQUE  (role,resources,action)
);
COMMENT ON COLUMN permissions.role IS 'role';
COMMENT ON COLUMN permissions.resources IS 'resources';
COMMENT ON COLUMN permissions.action IS 'action';


INSERT INTO users (username, password, enabled) VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

INSERT INTO roles (username, role) VALUES ('nacos', 'ROLE_ADMIN');
