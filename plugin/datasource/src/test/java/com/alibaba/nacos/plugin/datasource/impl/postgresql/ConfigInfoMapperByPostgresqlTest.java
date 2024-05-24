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

import com.alibaba.nacos.common.utils.NamespaceUtil;
import com.alibaba.nacos.plugin.datasource.constants.ContextConstant;
import com.alibaba.nacos.plugin.datasource.constants.DataSourceConstant;
import com.alibaba.nacos.plugin.datasource.constants.FieldConstant;
import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

public class ConfigInfoMapperByPostgresqlTest {
    
    private ConfigInfoMapperByPostgresql configInfoMapperByPostgresql;
    
    private final Object[] emptyObjs = new Object[] {};
    
    int startRow = 0;
    
    int pageSize = 5;
    
    String appName = "appName";
    
    String groupId = "groupId";
    
    String tenantId = "tenantId";
    
    String id = "123";
    
    long lastMaxId = 1234;
    
    List<Long> ids = Lists.newArrayList(1L, 2L, 3L, 5L, 144L);
    
    Timestamp startTime = new Timestamp(System.currentTimeMillis());
    
    Timestamp endTime = new Timestamp(System.currentTimeMillis());
    
    MapperContext context;
    
    @Before
    public void setUp() throws Exception {
        configInfoMapperByPostgresql = new ConfigInfoMapperByPostgresql();
        
        context = new MapperContext(startRow, pageSize);
        context.putWhereParameter(FieldConstant.APP_NAME, appName);
        context.putWhereParameter(FieldConstant.TENANT_ID, tenantId);
        context.putWhereParameter(FieldConstant.ID, id);
        context.putWhereParameter(FieldConstant.START_TIME, startTime);
        context.putWhereParameter(FieldConstant.END_TIME, endTime);
        context.putWhereParameter(FieldConstant.IDS, ids);
        context.putWhereParameter(FieldConstant.PAGE_SIZE, pageSize);
        context.putWhereParameter(FieldConstant.LAST_MAX_ID, lastMaxId);
        
    }
    
    @Test
    public void testFindConfigMaxId() {
        MapperResult mapperResult = configInfoMapperByPostgresql.findConfigMaxId(null);
        Assert.assertEquals(mapperResult.getSql(), "SELECT MAX(id) FROM config_info");
    }
    
    @Test
    public void testFindAllDataIdAndGroup() {
        MapperResult mapperResult = configInfoMapperByPostgresql.findAllDataIdAndGroup(null);
        Assert.assertEquals(mapperResult.getSql(), "SELECT DISTINCT data_id, group_id FROM config_info");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), emptyObjs);
    }
    
    @Test
    public void testFindConfigInfoByAppCountRows() {
        MapperResult mapperResult = configInfoMapperByPostgresql.findConfigInfoByAppCountRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT count(*) FROM config_info WHERE tenant_id LIKE ? AND app_name = ?");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId, appName});
    }
    
    @Test
    public void testFindConfigInfoByAppFetchRows() {
        MapperResult mapperResult = configInfoMapperByPostgresql.findConfigInfoByAppFetchRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT id,data_id,group_id,tenant_id,app_name,content FROM config_info WHERE tenant_id LIKE ? AND app_name= ? OFFSET "
                        + startRow + " LIMIT " + pageSize);
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId, appName});
    }
    
    @Test
    public void testConfigInfoLikeTenantCount() {
        MapperResult mapperResult = configInfoMapperByPostgresql.configInfoLikeTenantCount(context);
        Assert.assertEquals(mapperResult.getSql(), "SELECT count(*) FROM config_info WHERE tenant_id LIKE ?");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId});
    }
    
    @Test
    public void testGetTenantIdList() {
        MapperResult mapperResult = configInfoMapperByPostgresql.getTenantIdList(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT tenant_id FROM config_info WHERE tenant_id != '" + NamespaceUtil.getNamespaceDefaultId()
                        + "' GROUP BY tenant_id OFFSET " + startRow + " LIMIT " + pageSize);
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), emptyObjs);
    }
    
    @Test
    public void testGetGroupIdList() {
        MapperResult mapperResult = configInfoMapperByPostgresql.getGroupIdList(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT group_id FROM config_info WHERE tenant_id ='' GROUP BY group_id OFFSET " + startRow + " LIMIT "
                        + pageSize);
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), emptyObjs);
    }
    
    @Test
    public void testFindAllConfigKey() {
        MapperResult mapperResult = configInfoMapperByPostgresql.findAllConfigKey(context);
        Assert.assertEquals(mapperResult.getSql(), " SELECT data_id,group_id,app_name  FROM ( "
                + " SELECT id FROM config_info WHERE tenant_id LIKE ? ORDER BY id OFFSET " + context.getStartRow() + " LIMIT "
                + context.getPageSize() + " )" + " g, config_info t WHERE g.id = t.id  ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId});
    }
    
    @Test
    public void testFindAllConfigInfoBaseFetchRows() {
        MapperResult mapperResult = configInfoMapperByPostgresql.findAllConfigInfoBaseFetchRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT t.id,data_id,group_id,content,md5 FROM ( SELECT id FROM config_info ORDER BY id OFFSET " + context.getStartRow() + " LIMIT "
                        + context.getPageSize() + " ) g, config_info t  WHERE g.id = t.id ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), emptyObjs);
    }
    
    @Test
    public void testFindAllConfigInfoFragment() {
        //with content
        context.putContextParameter(ContextConstant.NEED_CONTENT, "true");
        
        MapperResult mapperResult = configInfoMapperByPostgresql.findAllConfigInfoFragment(context);
        Assert.assertEquals(
                "SELECT id,data_id,group_id,tenant_id,app_name,content,md5,gmt_modified,type,encrypted_data_key "
                        + "FROM config_info WHERE id > ? ORDER BY id ASC OFFSET " + startRow + " LIMIT " + pageSize,
                mapperResult.getSql());
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {id});
        
        context.putContextParameter(ContextConstant.NEED_CONTENT, "false");
        MapperResult mapperResult2 = configInfoMapperByPostgresql.findAllConfigInfoFragment(context);
        Assert.assertEquals("SELECT id,data_id,group_id,tenant_id,app_name,md5,gmt_modified,type,encrypted_data_key "
                        + "FROM config_info WHERE id > ? ORDER BY id ASC OFFSET " + startRow + " LIMIT " + pageSize,
                mapperResult2.getSql());
        Assert.assertArrayEquals(mapperResult2.getParamList().toArray(), new Object[] {id});
    }
    
    @Test
    public void testFindChangeConfig() {
        MapperResult mapperResult = configInfoMapperByPostgresql.findChangeConfig(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT id, data_id, group_id, tenant_id, app_name,md5, gmt_modified, encrypted_data_key FROM config_info WHERE gmt_modified >= ? and id > ? ORDER BY id OFFSET 0 LIMIT ? ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {startTime, lastMaxId, pageSize});
    }
    
    @Test
    public void testFindChangeConfigCountRows() {
        
        MapperResult mapperResult = configInfoMapperByPostgresql.findChangeConfigCountRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT count(*) FROM config_info WHERE  1=1  AND app_name = ?  AND gmt_modified >=?  AND gmt_modified <=? ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {appName, startTime, endTime});
    }
    
    @Test
    public void testFindChangeConfigFetchRows() {
        Object lastMaxId = 100;
        context.putWhereParameter(FieldConstant.LAST_MAX_ID, lastMaxId);
        MapperResult mapperResult = configInfoMapperByPostgresql.findChangeConfigFetchRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT id,data_id,group_id,tenant_id,app_name,type,md5,gmt_modified FROM config_info "
                        + "WHERE  1=1  AND tenant_id = ?  AND app_name = ?  AND gmt_modified >=?  AND gmt_modified <=?  AND id > "
                        + lastMaxId + " ORDER BY id ASC OFFSET " + startRow + " LIMIT " + pageSize);
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(),
                new Object[] {tenantId, appName, startTime, endTime});
    }
    
    @Test
    public void testListGroupKeyMd5ByPageFetchRows() {
        MapperResult mapperResult = configInfoMapperByPostgresql.listGroupKeyMd5ByPageFetchRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT t.id,data_id,group_id,tenant_id,app_name,md5,type,gmt_modified,encrypted_data_key FROM "
                        + "( SELECT id FROM config_info ORDER BY id OFFSET 0 LIMIT 5 ) g, config_info t WHERE g.id = t.id");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), emptyObjs);
        
    }
    
    @Test
    public void testFindAllConfigInfo4Export() {
        MapperResult mapperResult = configInfoMapperByPostgresql.findAllConfigInfo4Export(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT id,data_id,group_id,tenant_id,app_name,content,type,md5,gmt_create,gmt_modified,src_user,"
                        + "src_ip,c_desc,c_use,effect,c_schema,encrypted_data_key FROM config_info WHERE  id IN (?, ?, ?, ?, ?) ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), ids.toArray());
        
        context.putWhereParameter(FieldConstant.IDS, null);
        mapperResult = configInfoMapperByPostgresql.findAllConfigInfo4Export(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT id,data_id,group_id,tenant_id,app_name,content,type,md5,gmt_create,gmt_modified,src_user,"
                        + "src_ip,c_desc,c_use,effect,c_schema,encrypted_data_key FROM config_info WHERE  tenant_id = ?  AND app_name= ? ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId, appName});
    }
    
    @Test
    public void testFindConfigInfoBaseLikeCountRows() {
        MapperResult mapperResult = configInfoMapperByPostgresql.findConfigInfoBaseLikeCountRows(context);
        Assert.assertEquals(mapperResult.getSql(), "SELECT count(*) FROM config_info WHERE  1=1 AND tenant_id='' ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), emptyObjs);
    }
    
    @Test
    public void testFindConfigInfoBaseLikeFetchRows() {
        MapperResult mapperResult = configInfoMapperByPostgresql.findConfigInfoBaseLikeFetchRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT id,data_id,group_id,tenant_id,content FROM config_info WHERE  1=1 AND tenant_id=''  OFFSET "
                        + startRow + " LIMIT " + pageSize);
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), emptyObjs);
    }
    
    @Test
    public void testFindConfigInfo4PageCountRows() {
        MapperResult mapperResult = configInfoMapperByPostgresql.findConfigInfo4PageCountRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT count(*) FROM config_info WHERE  tenant_id=?  AND app_name=? ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId, appName});
    }
    
    @Test
    public void testFindConfigInfo4PageFetchRows() {
        MapperResult mapperResult = configInfoMapperByPostgresql.findConfigInfo4PageFetchRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT id,data_id,group_id,tenant_id,app_name,content,type,encrypted_data_key FROM config_info"
                        + " WHERE  tenant_id=?  AND app_name=?  OFFSET " + startRow + " LIMIT " + pageSize);
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId, appName});
    }
    
    @Test
    public void testFindConfigInfoBaseByGroupFetchRows() {
        context.putWhereParameter(FieldConstant.GROUP_ID, groupId);
        MapperResult mapperResult = configInfoMapperByPostgresql.findConfigInfoBaseByGroupFetchRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT id,data_id,group_id,content FROM config_info WHERE group_id=? AND tenant_id=? OFFSET " + startRow
                        + " LIMIT " + pageSize);
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {groupId, tenantId});
    }
    
    @Test
    public void testFindConfigInfoLike4PageCountRows() {
        MapperResult mapperResult = configInfoMapperByPostgresql.findConfigInfoLike4PageCountRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT count(*) FROM config_info WHERE  tenant_id LIKE ?  AND app_name = ? ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId, appName});
    }
    
    @Test
    public void testFindConfigInfoLike4PageFetchRows() {
        MapperResult mapperResult = configInfoMapperByPostgresql.findConfigInfoLike4PageFetchRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT id,data_id,group_id,tenant_id,app_name,content,encrypted_data_key FROM config_info "
                        + "WHERE  tenant_id LIKE ?  AND app_name = ?  OFFSET " + startRow + " LIMIT " + pageSize);
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId, appName});
    }
    
    @Test
    public void testFindAllConfigInfoFetchRows() {
        MapperResult mapperResult = configInfoMapperByPostgresql.findAllConfigInfoFetchRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT t.id,data_id,group_id,tenant_id,app_name,content,md5  FROM (  SELECT id FROM config_info "
                        + "WHERE tenant_id LIKE ? ORDER BY id OFFSET ? LIMIT ? ) g, config_info t  WHERE g.id = t.id ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId, startRow, pageSize});
    }
    
    @Test
    public void testFindConfigInfosByIds() {
        MapperResult mapperResult = configInfoMapperByPostgresql.findConfigInfosByIds(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT id,data_id,group_id,tenant_id,app_name,content,md5 FROM config_info WHERE id IN (?, ?, ?, ?, ?) ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), ids.toArray());
    }
    
    @Test
    public void testRemoveConfigInfoByIdsAtomic() {
        MapperResult mapperResult = configInfoMapperByPostgresql.removeConfigInfoByIdsAtomic(context);
        Assert.assertEquals(mapperResult.getSql(), "DELETE FROM config_info WHERE id IN (?, ?, ?, ?, ?) ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), ids.toArray());
    }
    
    @Test
    public void testGetTableName() {
        String sql = configInfoMapperByPostgresql.getTableName();
        Assert.assertEquals(sql, TableConstant.CONFIG_INFO);
    }
    
    @Test
    public void testGetDataSource() {
        String sql = configInfoMapperByPostgresql.getDataSource();
        Assert.assertEquals(sql, DataSourceConstant.POSTGRESQL);
    }
    
    @Test
    public void testUpdateConfigInfoAtomicCas() {
        String newContent = "new Content";
        String newMD5 = "newMD5";
        String srcIp = "1.1.1.1";
        Object srcUser = "nacos";
        Object time = new Timestamp(System.currentTimeMillis());
        Object appNameTmp = "newAppName";
        Object desc = "description";
        Object use = "use";
        Object effect = "effect";
        Object type = "type";
        Object schema = "schema";
        String encryptedDataKey = "ey456789";
        context.putUpdateParameter(FieldConstant.CONTENT, newContent);
        context.putUpdateParameter(FieldConstant.MD5, newMD5);
        context.putUpdateParameter(FieldConstant.SRC_IP, srcIp);
        context.putUpdateParameter(FieldConstant.SRC_USER, srcUser);
        context.putUpdateParameter(FieldConstant.GMT_MODIFIED, time);
        context.putUpdateParameter(FieldConstant.APP_NAME, appNameTmp);
        context.putUpdateParameter(FieldConstant.C_DESC, desc);
        context.putUpdateParameter(FieldConstant.C_USE, use);
        context.putUpdateParameter(FieldConstant.EFFECT, effect);
        context.putUpdateParameter(FieldConstant.TYPE, type);
        context.putUpdateParameter(FieldConstant.C_SCHEMA, schema);
        context.putUpdateParameter(FieldConstant.ENCRYPTED_DATA_KEY, encryptedDataKey);
        Object dataId = "dataId";
        Object group = "group";
        Object md5 = "md5";
        
        context.putWhereParameter(FieldConstant.DATA_ID, dataId);
        context.putWhereParameter(FieldConstant.GROUP_ID, group);
        context.putWhereParameter(FieldConstant.TENANT_ID, tenantId);
        context.putWhereParameter(FieldConstant.MD5, md5);
        
        MapperResult mapperResult = configInfoMapperByPostgresql.updateConfigInfoAtomicCas(context);
        Assert.assertEquals(mapperResult.getSql(), "UPDATE config_info SET "
                + "content=?, md5 = ?, src_ip=?,src_user=?,gmt_modified=?, app_name=?,c_desc=?,"
                + "c_use=?,effect=?,type=?,c_schema=?,encrypted_data_key=? "
                + "WHERE data_id=? AND group_id=? AND tenant_id=? AND (md5=? OR md5 IS NULL OR md5='')");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(),
                new Object[] {newContent, newMD5, srcIp, srcUser, time, appNameTmp, desc, use, effect, type, schema,
                        encryptedDataKey, dataId, group, tenantId, md5});
    }
}
