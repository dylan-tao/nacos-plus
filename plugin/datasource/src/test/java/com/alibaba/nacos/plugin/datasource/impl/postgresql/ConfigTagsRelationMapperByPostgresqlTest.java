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

import java.util.HashMap;

public class ConfigTagsRelationMapperByPostgresqlTest {
    
    private ConfigTagsRelationMapperByPostgresql configTagsRelationMapperByPostgresql;
    
    @Before
    public void setUp() throws Exception {
        configTagsRelationMapperByPostgresql = new ConfigTagsRelationMapperByPostgresql();
    }
    
    @Test
    public void testFindConfigInfo4PageCountRows() {
        String sql = configTagsRelationMapperByPostgresql.findConfigInfoLike4PageCountRows(new HashMap<>(), 5);
        Assert.assertEquals(sql,
                "SELECT count(*) FROM config_info  a LEFT JOIN config_tags_relation b ON a.id=b.id  WHERE  "
                        + "a.tenant_id LIKE ?  AND b.tag_name IN (?, ?, ?, ?, ?) ");
    }
    
    @Test
    public void testFindConfigInfo4PageFetchRows() {
        String sql = configTagsRelationMapperByPostgresql.findConfigInfo4PageFetchRows(new HashMap<>(), 5, 0, 5);
        Assert.assertEquals(sql,
                "SELECT a.id,a.data_id,a.group_id,a.tenant_id,a.app_name,a.content FROM config_info  a LEFT JOIN "
                        + "config_tags_relation b ON a.id=b.id WHERE  a.tenant_id=?  AND b.tag_name IN (?, ?, ?, ?, ?) OFFSET 0 LIMIT 5");
    }
    
    @Test
    public void testFindConfigInfoLike4PageCountRows() {
        String sql = configTagsRelationMapperByPostgresql.findConfigInfoLike4PageCountRows(new HashMap<>(), 5);
        Assert.assertEquals(sql, "SELECT count(*) FROM config_info  a LEFT JOIN config_tags_relation b ON a.id=b.id  "
                + "WHERE  a.tenant_id LIKE ?  AND b.tag_name IN (?, ?, ?, ?, ?) ");
    }
    
    @Test
    public void tsetFindConfigInfoLike4PageFetchRows() {
        String sql = configTagsRelationMapperByPostgresql.findConfigInfoLike4PageFetchRows(new HashMap<>(), 5, 0, 5);
        Assert.assertEquals(sql,
                "SELECT a.id,a.data_id,a.group_id,a.tenant_id,a.app_name,a.content FROM config_info a LEFT JOIN"
                        + " config_tags_relation b ON a.id=b.id  WHERE  a.tenant_id LIKE ?  AND b.tag_name IN (?, ?, ?, ?, ?) OFFSET 0 LIMIT 5");
    }
    
    @Test
    public void testGetTableName() {
        String tableName = configTagsRelationMapperByPostgresql.getTableName();
        Assert.assertEquals(tableName, TableConstant.CONFIG_TAGS_RELATION);
    }
    
    @Test
    public void testGetDataSource() {
        String dataSource = configTagsRelationMapperByPostgresql.getDataSource();
        Assert.assertEquals(dataSource, DataSourceConstant.POSTGRESQL);
    }
}