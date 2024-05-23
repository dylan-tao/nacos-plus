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

package com.alibaba.nacos.plugin.datasource.impl.sqlserver;

import com.alibaba.nacos.plugin.datasource.constants.DataSourceConstant;
import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TenantInfoMapperBySqlServerTest {
    
    private TenantInfoMapperBySqlServer tenantInfoMapperBySqlServer;
    
    @Before
    public void setUp() throws Exception {
        tenantInfoMapperBySqlServer = new TenantInfoMapperBySqlServer();
    }
    
    @Test
    public void testGetTableName() {
        String tableName = tenantInfoMapperBySqlServer.getTableName();
        Assert.assertEquals(tableName, TableConstant.TENANT_INFO);
    }
    
    @Test
    public void testGetDataSource() {
        String dataSource = tenantInfoMapperBySqlServer.getDataSource();
        Assert.assertEquals(dataSource, DataSourceConstant.SQLSERVER);
    }
}