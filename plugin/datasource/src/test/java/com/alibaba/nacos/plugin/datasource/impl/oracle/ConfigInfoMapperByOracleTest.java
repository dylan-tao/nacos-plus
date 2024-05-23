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

package com.alibaba.nacos.plugin.datasource.impl.oracle;

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

public class ConfigInfoMapperByOracleTest {
    
    private ConfigInfoMapperByOracle configInfoMapperByOracle;
    
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
        configInfoMapperByOracle = new ConfigInfoMapperByOracle();
        
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
        MapperResult mapperResult = configInfoMapperByOracle.findConfigMaxId(null);
        Assert.assertEquals(mapperResult.getSql(), "SELECT MAX(id) FROM config_info");
    }
    
    @Test
    public void testFindAllDataIdAndGroup() {
        MapperResult mapperResult = configInfoMapperByOracle.findAllDataIdAndGroup(null);
        Assert.assertEquals(mapperResult.getSql(), "SELECT DISTINCT data_id, group_id FROM config_info");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), emptyObjs);
    }
    
    @Test
    public void testFindConfigInfoByAppCountRows() {
        MapperResult mapperResult = configInfoMapperByOracle.findConfigInfoByAppCountRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT count(*) FROM config_info WHERE tenant_id LIKE ? AND app_name = ?");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId, appName});
    }
    
    @Test
    public void testFindConfigInfoByAppFetchRows() {
        MapperResult mapperResult = configInfoMapperByOracle.findConfigInfoByAppFetchRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT * FROM (SELECT id,data_id,group_id,tenant_id,app_name,content, ROWNUM as rnum FROM config_info WHERE tenant_id LIKE ? AND app_name= ?) WHERE  rnum >= "+(startRow+1)+" and "+pageSize+" >= rnum");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId, appName});
    }
    
    @Test
    public void testConfigInfoLikeTenantCount() {
        MapperResult mapperResult = configInfoMapperByOracle.configInfoLikeTenantCount(context);
        Assert.assertEquals(mapperResult.getSql(), "SELECT count(*) FROM config_info WHERE tenant_id LIKE ?");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId});
    }
    
    @Test
    public void testGetTenantIdList() {
        MapperResult mapperResult = configInfoMapperByOracle.getTenantIdList(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT * FROM (SELECT tenant_id, ROWNUM as rnum FROM config_info WHERE tenant_id != '' GROUP BY tenant_id) WHERE  rnum >= "+(startRow+1)+" and "+pageSize+" >= rnum");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), emptyObjs);
    }
    
    @Test
    public void testGetGroupIdList() {
        MapperResult mapperResult = configInfoMapperByOracle.getGroupIdList(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT * FROM (SELECT group_id, ROWNUM as rnum FROM config_info WHERE tenant_id ='' GROUP BY group_id)  WHERE  rnum >= "+(startRow+1)+" and "+pageSize+" >= rnum");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), emptyObjs);
    }
    
    @Test
    public void testFindAllConfigKey() {
        MapperResult mapperResult = configInfoMapperByOracle.findAllConfigKey(context);
        Assert.assertEquals(mapperResult.getSql(), " SELECT data_id,group_id,app_name  FROM (  SELECT * FROM (SELECT id, ROWNUM as rnum FROM config_info WHERE tenant_id LIKE ? ORDER BY id) WHERE  rnum >= "+(startRow+1)+" and "+pageSize+" >= rnum ) g, config_info t WHERE g.id = t.id  ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId});
    }
    
    @Test
    public void testFindAllConfigInfoBaseFetchRows() {
        MapperResult mapperResult = configInfoMapperByOracle.findAllConfigInfoBaseFetchRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT t.id,data_id,group_id,content,md5 FROM (SELECT * FROM ( SELECT id, ROWNUM as rnum FROM config_info ORDER BY id) WHERE  rnum >= "+(startRow+1)+" and "+context.getPageSize()+" >= rnum)  g, config_info t  WHERE g.id = t.id ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), emptyObjs);
    }
    
    @Test
    public void testFindAllConfigInfoFragment() {
        //with content
        context.putContextParameter(ContextConstant.NEED_CONTENT, "true");
        
        MapperResult mapperResult = configInfoMapperByOracle.findAllConfigInfoFragment(context);
        Assert.assertEquals(
                "SELECT * FROM (SELECT id,data_id,group_id,tenant_id,app_name,content,md5,gmt_modified,type,encrypted_data_key , ROWNUM as rnum FROM config_info WHERE id > ? ORDER BY id ASC)  WHERE  rnum >= "+(startRow+1)+" and "+pageSize+" >= rnum ",
                mapperResult.getSql());
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {id});
        
        context.putContextParameter(ContextConstant.NEED_CONTENT, "false");
        MapperResult mapperResult2 = configInfoMapperByOracle.findAllConfigInfoFragment(context);
        Assert.assertEquals("SELECT * FROM (SELECT id,data_id,group_id,tenant_id,app_name,md5,gmt_modified,type,encrypted_data_key , ROWNUM as rnum FROM config_info WHERE id > ? ORDER BY id ASC)  WHERE  rnum >= "+(startRow+1)+" and "+pageSize+" >= rnum ",
                mapperResult2.getSql());
        Assert.assertArrayEquals(mapperResult2.getParamList().toArray(), new Object[] {id});
    }
    
    @Test
    public void testFindChangeConfig() {
        MapperResult mapperResult = configInfoMapperByOracle.findChangeConfig(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT * FROM (SELECT id, data_id, group_id, tenant_id, app_name, content, gmt_modified,encrypted_data_key, ROWNUM as rnum FROM config_info WHERE gmt_modified >= ? AND id > ? ORDER BY id) WHERE rnum <= ?");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {startTime, lastMaxId, pageSize});
    }
    
    @Test
    public void testFindChangeConfigCountRows() {
        
        MapperResult mapperResult = configInfoMapperByOracle.findChangeConfigCountRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT count(*) FROM config_info WHERE  1=1  AND app_name = ?  AND gmt_modified >=?  AND gmt_modified <=? ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {appName, startTime, endTime});
    }
    
    @Test
    public void testFindChangeConfigFetchRows() {
        Object lastMaxId = 100;
        context.putWhereParameter(FieldConstant.LAST_MAX_ID, lastMaxId);
        MapperResult mapperResult = configInfoMapperByOracle.findChangeConfigFetchRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT * FROM (SELECT id,data_id,group_id,tenant_id,app_name,content,type,md5,gmt_modified, ROWNUM as rnum FROM config_info WHERE  1=1  AND tenant_id = ?  AND app_name = ?  AND gmt_modified >=?  AND gmt_modified <=?  AND id > 100 ORDER BY id ASC) WHERE  rnum >= "+(startRow+1)+" and "+pageSize+" >= rnum ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(),
                new Object[] {tenantId, appName, startTime, endTime});
    }
    
    @Test
    public void testListGroupKeyMd5ByPageFetchRows() {
        MapperResult mapperResult = configInfoMapperByOracle.listGroupKeyMd5ByPageFetchRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT t.id,data_id,group_id,tenant_id,app_name,md5,type,gmt_modified,encrypted_data_key FROM (SELECT * FROM ( SELECT id, ROWNUM as rnum FROM config_info ORDER BY id)  WHERE  rnum >= 1 and 5 >= rnum  ) g, config_info t WHERE g.id = t.id");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), emptyObjs);
        
    }
    
    @Test
    public void testFindAllConfigInfo4Export() {
        MapperResult mapperResult = configInfoMapperByOracle.findAllConfigInfo4Export(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT id,data_id,group_id,tenant_id,app_name,content,type,md5,gmt_create,gmt_modified,src_user,"
                        + "src_ip,c_desc,c_use,effect,c_schema,encrypted_data_key FROM config_info WHERE  id IN (?, ?, ?, ?, ?) ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), ids.toArray());
        
        context.putWhereParameter(FieldConstant.IDS, null);
        mapperResult = configInfoMapperByOracle.findAllConfigInfo4Export(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT id,data_id,group_id,tenant_id,app_name,content,type,md5,gmt_create,gmt_modified,src_user,"
                        + "src_ip,c_desc,c_use,effect,c_schema,encrypted_data_key FROM config_info WHERE  tenant_id = ?  AND app_name= ? ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId, appName});
    }
    
    @Test
    public void testFindConfigInfoBaseLikeCountRows() {
        MapperResult mapperResult = configInfoMapperByOracle.findConfigInfoBaseLikeCountRows(context);
        Assert.assertEquals(mapperResult.getSql(), "SELECT count(*) FROM config_info WHERE  1=1 AND tenant_id='' ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), emptyObjs);
    }
    
    @Test
    public void testFindConfigInfoBaseLikeFetchRows() {
        MapperResult mapperResult = configInfoMapperByOracle.findConfigInfoBaseLikeFetchRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT * FROM (SELECT id,data_id,group_id,tenant_id,content, ROWNUM as rnum FROM config_info WHERE  1=1 AND tenant_id='' )  WHERE  rnum >= "+(startRow+1)+" and "+pageSize+" >= rnum ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), emptyObjs);
    }
    
    @Test
    public void testFindConfigInfo4PageCountRows() {
        MapperResult mapperResult = configInfoMapperByOracle.findConfigInfo4PageCountRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT count(*) FROM config_info WHERE  tenant_id=?  AND app_name=? ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId, appName});
    }
    
    @Test
    public void testFindConfigInfo4PageFetchRows() {
        MapperResult mapperResult = configInfoMapperByOracle.findConfigInfo4PageFetchRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT * FROM (SELECT id,data_id,group_id,tenant_id,app_name,content,type,encrypted_data_key, ROWNUM as rnum FROM config_info WHERE  tenant_id=?  AND app_name=? )   WHERE  rnum >= "+(startRow+1)+" and "+pageSize+" >= rnum ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId, appName});
    }
    
    @Test
    public void testFindConfigInfoBaseByGroupFetchRows() {
        context.putWhereParameter(FieldConstant.GROUP_ID, groupId);
        MapperResult mapperResult = configInfoMapperByOracle.findConfigInfoBaseByGroupFetchRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT * FROM (SELECT id,data_id,group_id,content, ROWNUM as rnum FROM config_info WHERE group_id=? AND tenant_id=?)   WHERE  rnum >= " + (startRow+1) +" and " + pageSize + " >= rnum ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {groupId, tenantId});
    }
    
    @Test
    public void testFindConfigInfoLike4PageCountRows() {
        MapperResult mapperResult = configInfoMapperByOracle.findConfigInfoLike4PageCountRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT count(*) FROM config_info WHERE  tenant_id LIKE ?  AND app_name = ? ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId, appName});
    }
    
    @Test
    public void testFindConfigInfoLike4PageFetchRows() {
        MapperResult mapperResult = configInfoMapperByOracle.findConfigInfoLike4PageFetchRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT * FROM (SELECT id,data_id,group_id,tenant_id,app_name,content,encrypted_data_key, ROWNUM as rnum FROM config_info WHERE  tenant_id LIKE ?  AND app_name = ?  )  WHERE  rnum >= "+(startRow+1)+" and "+pageSize+" >= rnum ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId, appName});
    }
    
    @Test
    public void testFindAllConfigInfoFetchRows() {
        MapperResult mapperResult = configInfoMapperByOracle.findAllConfigInfoFetchRows(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT t.id,data_id,group_id,tenant_id,app_name,content,md5  FROM (SELECT * FROM (  SELECT id, ROWNUM as rnum FROM config_info WHERE tenant_id LIKE ? ORDER BY id) WHERE  rnum >= ? and ? >= rnum) g, config_info t  WHERE g.id = t.id ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), new Object[] {tenantId, startRow+1, pageSize});
    }
    
    @Test
    public void testFindConfigInfosByIds() {
        MapperResult mapperResult = configInfoMapperByOracle.findConfigInfosByIds(context);
        Assert.assertEquals(mapperResult.getSql(),
                "SELECT id,data_id,group_id,tenant_id,app_name,content,md5 FROM config_info WHERE id IN (?, ?, ?, ?, ?) ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), ids.toArray());
    }
    
    @Test
    public void testRemoveConfigInfoByIdsAtomic() {
        MapperResult mapperResult = configInfoMapperByOracle.removeConfigInfoByIdsAtomic(context);
        Assert.assertEquals(mapperResult.getSql(), "DELETE FROM config_info WHERE id IN (?, ?, ?, ?, ?) ");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(), ids.toArray());
    }
    
    @Test
    public void testGetTableName() {
        String sql = configInfoMapperByOracle.getTableName();
        Assert.assertEquals(sql, TableConstant.CONFIG_INFO);
    }
    
    @Test
    public void testGetDataSource() {
        String sql = configInfoMapperByOracle.getDataSource();
        Assert.assertEquals(sql, DataSourceConstant.ORACLE);
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
        
        MapperResult mapperResult = configInfoMapperByOracle.updateConfigInfoAtomicCas(context);
        Assert.assertEquals(mapperResult.getSql(), "UPDATE config_info SET "
                + "content=?, md5 = ?, src_ip=?,src_user=?,gmt_modified=?, app_name=?,c_desc=?,"
                + "c_use=?,effect=?,type=?,c_schema=?,encrypted_data_key=? "
                + "WHERE data_id=? AND group_id=? AND tenant_id=? AND (md5=? OR md5 IS NULL OR md5='')");
        Assert.assertArrayEquals(mapperResult.getParamList().toArray(),
                new Object[] {newContent, newMD5, srcIp, srcUser, time, appNameTmp, desc, use, effect, type, schema,
                        encryptedDataKey, dataId, group, tenantId, md5});
    }
}
