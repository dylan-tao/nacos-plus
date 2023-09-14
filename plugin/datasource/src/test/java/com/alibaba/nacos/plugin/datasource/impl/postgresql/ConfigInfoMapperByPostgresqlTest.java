/*
 * Copyright 1999-2022 Alibaba Group Holding Ltd.
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
 */

package com.alibaba.nacos.plugin.datasource.impl.postgresql;

import com.alibaba.nacos.plugin.datasource.constants.DataSourceConstant;
import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class ConfigInfoMapperByPostgresqlTest {
    
    private ConfigInfoMapperByPostgresql configInfoMapperByPostgresql;
    
    @Before
    public void setUp() throws Exception {
        configInfoMapperByPostgresql = new ConfigInfoMapperByPostgresql();
    }
    
    @Test
    public void testFindConfigMaxId() {
        String sql = configInfoMapperByPostgresql.findConfigMaxId();
        Assert.assertEquals(sql, "SELECT MAX(id) FROM config_info");
    }
    
    @Test
    public void testFindAllDataIdAndGroup() {
        String sql = configInfoMapperByPostgresql.findAllDataIdAndGroup();
        Assert.assertEquals(sql, "SELECT DISTINCT data_id, group_id FROM config_info");
    }
    
    @Test
    public void testFindConfigInfoByAppCountRows() {
        String sql = configInfoMapperByPostgresql.findConfigInfoByAppCountRows();
        Assert.assertEquals(sql, "SELECT count(*) FROM config_info WHERE tenant_id LIKE ? AND app_name= ?");
    }
    
    @Test
    public void testFindConfigInfoByAppFetchRows() {
        String sql = configInfoMapperByPostgresql.findConfigInfoByAppFetchRows(0, 5);
        Assert.assertEquals(sql,
                "SELECT id,data_id,group_id,tenant_id,app_name,content FROM config_info WHERE tenant_id LIKE ? AND app_name= ? LIMIT 0,5");
    }
    
    @Test
    public void testConfigInfoLikeTenantCount() {
        String sql = configInfoMapperByPostgresql.configInfoLikeTenantCount();
        Assert.assertEquals(sql, "SELECT count(*) FROM config_info WHERE tenant_id LIKE ?");
    }
    
    @Test
    public void testGetTenantIdList() {
        String sql = configInfoMapperByPostgresql.getTenantIdList(0, 5);
        Assert.assertEquals(sql,
                "SELECT tenant_id FROM config_info WHERE tenant_id != '' GROUP BY tenant_id LIMIT 0,5");
    }
    
    @Test
    public void testGetGroupIdList() {
        String sql = configInfoMapperByPostgresql.getGroupIdList(0, 5);
        Assert.assertEquals(sql, "SELECT group_id FROM config_info WHERE tenant_id ='' GROUP BY group_id LIMIT 0,5");
    }
    
    @Test
    public void testFindAllConfigKey() {
        String sql = configInfoMapperByPostgresql.findAllConfigKey(0, 5);
        Assert.assertEquals(sql, " SELECT data_id,group_id,app_name  FROM ( "
                + " SELECT id FROM config_info WHERE tenant_id LIKE ? ORDER BY id LIMIT 0,5 )"
                + " g, config_info t WHERE g.id = t.id  ");
    }
    
    @Test
    public void testFindAllConfigInfoBaseFetchRows() {
        String sql = configInfoMapperByPostgresql.findAllConfigInfoBaseFetchRows(0, 5);
        Assert.assertEquals(sql,
                "SELECT t.id,data_id,group_id,content,md5 FROM ( SELECT id FROM config_info ORDER BY id LIMIT ?,?"
                        + "  )  g, config_info t  WHERE g.id = t.id ");
    }
    
    @Test
    public void testFindAllConfigInfoFragment() {
        String sql = configInfoMapperByPostgresql.findAllConfigInfoFragment(0, 5);
        Assert.assertEquals(sql,
                "SELECT id,data_id,group_id,tenant_id,app_name,content,md5,gmt_modified,type,encrypted_data_key "
                        + "FROM config_info WHERE id > ? ORDER BY id ASC LIMIT 0,5");
    }
    
    @Test
    public void testFindChangeConfig() {
        String sql = configInfoMapperByPostgresql.findChangeConfig();
        Assert.assertEquals(sql,
                "SELECT data_id, group_id, tenant_id, app_name, content, gmt_modified,encrypted_data_key "
                        + "FROM config_info WHERE gmt_modified >= ? AND gmt_modified <= ?");
    }
    
    @Test
    public void testFindChangeConfigCountRows() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sql = configInfoMapperByPostgresql.findChangeConfigCountRows(new HashMap<>(), timestamp, timestamp);
        Assert.assertEquals(sql,
                "SELECT count(*) FROM config_info WHERE  1=1  AND gmt_modified >=?  AND gmt_modified <=? ");
    }
    
    @Test
    public void testFindChangeConfigFetchRows() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sql = configInfoMapperByPostgresql.findChangeConfigFetchRows(new HashMap<>(), timestamp, timestamp, 0, 5,
                100);
        Assert.assertEquals(sql,
                "SELECT id,data_id,group_id,tenant_id,app_name,content,type,md5,gmt_modified FROM config_info "
                        + "WHERE  1=1  AND gmt_modified >=?  AND gmt_modified <=?  AND id > 100 ORDER BY id ASC LIMIT 0,5");
    }
    
    @Test
    public void testListGroupKeyMd5ByPageFetchRows() {
        String sql = configInfoMapperByPostgresql.listGroupKeyMd5ByPageFetchRows(0, 5);
        Assert.assertEquals(sql,
                "SELECT t.id,data_id,group_id,tenant_id,app_name,md5,type,gmt_modified,encrypted_data_key FROM "
                        + "( SELECT id FROM config_info ORDER BY id LIMIT 0,5 ) g, config_info t WHERE g.id = t.id");
    }
    
    @Test
    public void testFindAllConfigInfo4Export() {
        String sql = configInfoMapperByPostgresql.findAllConfigInfo4Export(new ArrayList<>(), new HashMap<>());
        Assert.assertEquals(sql,
                "SELECT id,data_id,group_id,tenant_id,app_name,content,type,md5,gmt_create,gmt_modified,src_user,"
                        + "src_ip,c_desc,c_use,effect,c_schema,encrypted_data_key FROM config_info WHERE  tenant_id= ? ");
    }
    
    @Test
    public void testFindConfigInfoBaseLikeCountRows() {
        String sql = configInfoMapperByPostgresql.findConfigInfoBaseLikeCountRows(new HashMap<>());
        Assert.assertEquals(sql, "SELECT count(*) FROM config_info WHERE  1=1 AND tenant_id='' ");
    }
    
    @Test
    public void testFindConfigInfoBaseLikeFetchRows() {
        String sql = configInfoMapperByPostgresql.findConfigInfoBaseLikeFetchRows(new HashMap<>(), 0, 5);
        Assert.assertEquals(sql,
                "SELECT id,data_id,group_id,tenant_id,content FROM config_info WHERE  1=1 AND tenant_id=''  LIMIT 0,5");
    }
    
    @Test
    public void testFindConfigInfo4PageCountRows() {
        String sql = configInfoMapperByPostgresql.findConfigInfo4PageCountRows(new HashMap<>());
        Assert.assertEquals(sql, "SELECT count(*) FROM config_info WHERE  tenant_id=? ");
    }
    
    @Test
    public void testFindConfigInfo4PageFetchRows() {
        String sql = configInfoMapperByPostgresql.findConfigInfo4PageFetchRows(new HashMap<>(), 0, 5);
        Assert.assertEquals(sql,
                "SELECT id,data_id,group_id,tenant_id,app_name,content,type,encrypted_data_key FROM config_info"
                        + " WHERE  tenant_id=?  LIMIT 0,5");
    }
    
    @Test
    public void testFindConfigInfoBaseByGroupFetchRows() {
        String sql = configInfoMapperByPostgresql.findConfigInfoBaseByGroupFetchRows(0, 5);
        Assert.assertEquals(sql,
                "SELECT id,data_id,group_id,content FROM config_info WHERE group_id=? AND tenant_id=? LIMIT 0,5");
    }
    
    @Test
    public void testFindConfigInfoLike4PageCountRows() {
        String sql = configInfoMapperByPostgresql.findConfigInfoLike4PageCountRows(new HashMap<>());
        Assert.assertEquals(sql, "SELECT count(*) FROM config_info WHERE  tenant_id LIKE ? ");
    }
    
    @Test
    public void testFindConfigInfoLike4PageFetchRows() {
        String sql = configInfoMapperByPostgresql.findConfigInfoLike4PageFetchRows(new HashMap<>(), 0, 5);
        Assert.assertEquals(sql,
                "SELECT id,data_id,group_id,tenant_id,app_name,content,encrypted_data_key FROM config_info "
                        + "WHERE  tenant_id LIKE ?  LIMIT 0,5");
    }
    
    @Test
    public void testFindAllConfigInfoFetchRows() {
        String sql = configInfoMapperByPostgresql.findAllConfigInfoFetchRows(0, 5);
        Assert.assertEquals(sql,
                "SELECT t.id,data_id,group_id,tenant_id,app_name,content,md5  FROM (  SELECT id FROM config_info "
                        + "WHERE tenant_id LIKE ? ORDER BY id LIMIT ?,? ) g, config_info t  WHERE g.id = t.id ");
    }
    
    @Test
    public void testFindConfigInfosByIds() {
        String sql = configInfoMapperByPostgresql.findConfigInfosByIds(5);
        Assert.assertEquals(sql,
                "SELECT ID,data_id,group_id,tenant_id,app_name,content,md5 FROM config_info WHERE id IN (?, ?, ?, ?, ?) ");
    }
    
    @Test
    public void testRemoveConfigInfoByIdsAtomic() {
        String sql = configInfoMapperByPostgresql.removeConfigInfoByIdsAtomic(5);
        Assert.assertEquals(sql, "DELETE FROM config_info WHERE id IN (?, ?, ?, ?, ?) ");
    }
    
    @Test
    public void testGetTableName() {
        String sql = configInfoMapperByPostgresql.getTableName();
        Assert.assertEquals(sql, TableConstant.CONFIG_INFO);
    }
    
    @Test
    public void testGetDataSource() {
        String sql = configInfoMapperByPostgresql.getDataSource();
        Assert.assertEquals(sql, DataSourceConstant.MYSQL);
    }
    
    @Test
    public void testUpdateConfigInfoAtomicCas() {
        String sql = configInfoMapperByPostgresql.updateConfigInfoAtomicCas();
        Assert.assertEquals(sql, "UPDATE config_info SET "
                + "content=?, md5 = ?, src_ip=?,src_user=?,gmt_modified=?, app_name=?,c_desc=?,c_use=?,effect=?,type=?,c_schema=? "
                + "WHERE data_id=? AND group_id=? AND tenant_id=? AND (md5=? OR md5 IS NULL OR md5='')");
    }
}