<p align = "center">
<img src="doc/NacosPlus.png" width="28%" height="28%" />
<br><br>
<b>NacosPlus，为信创国产数据库爱心发电。 </b>
<br><br>Star⭐一下(右上角)，持续版本维护的动力😊
<br><br>Watch一下，遇到问题提Issues可实时接收问题进展
<br><br>Fork一下，完善和特殊场景适配后，记得Pull Request为信创添砖加瓦
<br><br>
<a title="GitHub Watchers" target="_blank" href="https://github.com/dylan-tao/nacos-plus/watchers"><img src="https://img.shields.io/github/watchers/dylan-tao/nacos-plus.svg?label=Watchers&style=social"></a>  
<a title="GitHub Stars" target="_blank" href="https://github.com/dylan-tao/nacos-plus/stargazers"><img src="https://img.shields.io/github/stars/dylan-tao/nacos-plus.svg?label=Stars&style=social"></a>  
<a title="GitHub Forks" target="_blank" href="https://github.com/dylan-tao/nacos-plus/network/members"><img src="https://img.shields.io/github/forks/dylan-tao/nacos-plus.svg?label=Forks&style=social"></a>  
<a title="Author GitHub Followers" target="_blank" href="https://github.com/dylan-tao"><img src="https://img.shields.io/github/followers/dylan-tao.svg?label=Followers&style=social"></a>
<br><br>
<a title="Releases" target="_blank" href="https://github.com/dylan-tao/nacos-plus/releases"><img src="https://img.shields.io/github/release/dylan-tao/nacos-plus.svg?style=flat-square"></a>
<a title="Release Date" target="_blank" href="https://github.com/dylan-tao/nacos-plus/releases"><img src="https://img.shields.io/github/release-date/dylan-tao/nacos-plus.svg?style=flat-square&color=99CCFF"></a>
<a title="Code Size" target="_blank" href="https://github.com/dylan-tao/nacos-plus"><img src="https://img.shields.io/github/languages/code-size/dylan-tao/nacos-plus.svg?style=flat-square"></a>
<a title="Apache2" target="_blank" href="https://www.apache.org/licenses/LICENSE-2.0.html"><img src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg"></a>
<br>
</p>

[English Document](https://github.com/dylan-tao/nacos-plus/blob/main/README_en-US.md) 

### 支持的数据库类型列表:

 *✅ 已支持   ⌛ 计划中   ❌ 暂无计划*

| 数据库类型         |    服务端版本    |     客户端版本     | 是否支持 | NacosPlus版本 |
|:--------------|:-----------:|:-------------:|:----:|:-----------:|
| Mysql/MariaDB |  `5.7.36`   |   `8.0.33`    |  ✅   | `2.2.0.1+`  |
| PostgreSQL    |   `90204`   |   `42.5.1`    |  ✅   | `2.2.0.1+`  |
| OpenGauss     |   `3.0.0`   |    `2.0.0`    |  ✅   | `2.2.0.2+`  |
| GaussDB       |   `2.1.0`   |    `2.0.0`    |  ✅   | `2.2.0.2+`  |
| Oracle        | `11.2.0.1`  |  `11.2.0.4`   |  ✅   | `2.3.2.1+`  |
| DM DBMS       | `8.1.3.100` |  `8.1.3.140`  |  ✅   | `2.3.2.2+`  |
| Sql Server    |   `2019`    | `12.4.2.jre8` |  ✅   | `2.3.2.3+`  |
| OceanBase     |     `-`     |      `-`      |  ⌛   | `2.3.2.7+`  |

### 制成品
```
nacos-2.x.x\distribution\target\nacos-server-2.x.x.tar
nacos-2.x.x\distribution\target\nacos-server-2.x.x.zip
```
### SQL脚本
#### {数据库类型}-schema.sql在制成品的conf文件夹下
```
nacos-2.x.x\distribution\target\nacos-server-2.x.x.tar\conf\{db}-schema.sql
nacos-2.x.x\distribution\target\nacos-server-2.x.x.zip\conf\{db}-schema.sql
```
### 数据库连接配置项
```
#### 如果使用MySQL或MariaDB作为数据源:
#spring.datasource.platform=mysql
#db.pool.config.driver-class-name=com.mysql.cj.jdbc.Driver
#db.url.0=jdbc:mysql://127.0.0.1:3306/nacos_server?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
#db.user.0=nacos
#db.password.0=123456

##### 如果使用PostgreSQL或OpenGauss或GaussDB作为数据源:
#spring.sql.init.platform=postgresql
#db.pool.config.driver-class-name=org.postgresql.Driver
#db.url.0=jdbc:postgresql://127.0.0.1:5432/nacos_server
#db.user.0=nacos
#db.password.0=123456

##### 如果使用Oracle作为数据源:
#spring.sql.init.platform=oracle
#db.pool.config.driver-class-name=oracle.jdbc.OracleDriver
#db.url.0=jdbc:oracle:thin:@//127.0.0.1:1521/nacos_server
#db.user.0=nacos
#db.password.0=123456
#db.testQuery=select 1 from dual

##### 如果使用DM(达梦)作为数据源:
### 警告: db.user.0必须配置为NACOS_SERVER并创建此数据库账号
#spring.sql.init.platform=dm
#db.pool.config.driver-class-name=dm.jdbc.driver.DmDriver
#db.url.0=jdbc:dm://127.0.0.1:5236/nacos_server?keyWords=type,usage,role,action,password&useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8
#db.user.0=NACOS_SERVER
#db.password.0=123456
#db.testQuery=select 1

##### 如果使用SqlServer作为数据源:
#spring.sql.init.platform=sqlserver
#db.pool.config.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
#db.url.0=jdbc:sqlserver://127.0.0.1:1433;DatabaseName=nacos_server;trustServerCertificate=true
#db.user.0=nacos
#db.password.0=123456
#db.testQuery=select 1
```

### 从提供的制成品包启动

**第一步: 下载二进制包**

你可以从 [最终的版本](https://github.com/dylan-tao/nacos-plus/releases) 下载最新的制成品包.  

以发布的 `nacos-server-2.x.x.zip` 作为例子:
```sh
unzip nacos-server-2.x.x.zip
cd nacos/bin 
``` 

**第二步: 启动服务**

在**Linux/Unix/Mac**平台上，运行以下命令以集群模式启动服务: 
```sh
sh startup.sh -m cluster
```

在**Windows**平台上，运行以下命令以集群模式启动服务；或者也可以双击“start.cmd”以单机模式启动服务
```
startup.cmd -m cluster
```

### 根据源码本地打包制成品包
```
mvn -Prelease-nacos -Dmaven.test.skip=true clean install -U
```

### 常见问题解答
> [从问题列表中自助获取[FAQ-x]解答，点击我！！！](https://github.com/dylan-tao/nacos-plus/issues)

### 谁正在使用？
<p align = "center">
<img src="customer/yidong.png" width="102" />
<img src="customer/caixun.png" width="102" />
<img src="customer/xinwang.png" width="102" />
<img src="customer/dongguan.png" width="102" />
<img src="customer/yinzhou.png" width="102" />
<img src="customer/jilin.png" width="102" />
</p>
<p align = "center">
<img src="customer/huaxia.png" width="102" />
<img src="customer/luzhou.png" width="102" />
<img src="customer/tailong.png" width="102" />
<img src="customer/xingye.png" width="102" />
<img src="customer/minsheng.png" width="102" />
<img src="customer/shengjing.png" width="102" />
</p>
<p align = "center">
<img src="customer/biyadi.png" width="102" />
<img src="customer/beiqi.png" width="102" />
<img src="customer/kelaisile.png" width="102" />
<img src="customer/weilai.png" width="102" />
<img src="customer/weipinhui.png" width="102" />
<img src="customer/qingnianyoupin.png" width="102" />
</p>
<p align = "center">
<img src="customer/taibao.png" width="102" />
<img src="customer/samoye.png" width="102" />
<img src="customer/fengbang.png" width="102" />
<img src="customer/lazada.png" width="102" />
<img src="customer/zhongtie.png" width="102" />
<img src="customer/haerbin.png" width="102" />
</p>

### 紧急支援

作者的微信二维码只提供紧急支持。您必须在寻求帮助之前提交ISSUE，否则将不能给予任何支持。

<p align = "center">
<img src="doc/AuthorWechat.png" width="52%" height="52%" />
</p>
