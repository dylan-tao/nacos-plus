<p align = "center">
<img src="doc/NacosPlus.png" width="28%" height="28%" />
<br><br>
<b>NacosPlus，为信创国产数据库爱心发电。</b>
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

### Support the database type:

 **✅ supported   ⌛ planning   ❌ not planned**

| Database Type         | Database Version | Database Client Version | Supported | NacosPlus Version |
|:----------------------|:----------------:|:-----------------------:|:---------:|:-----------------:|
| Mysql/MariaDB         |     `5.7.36`     |        `8.0.33`         |     ✅     |    `2.2.0.1+`     |
| PostgreSQL            |     `90204`      |        `42.5.1`         |     ✅     |    `2.2.0.1+`     |
| OpenGauss             |     `3.0.0`      |         `2.0.0`         |     ✅     |    `2.2.0.2+`     |
| GaussDB               |     `2.1.0`      |         `2.0.0`         |     ✅     |    `2.2.0.2+`     |
| Oracle                |    `11.2.0.1`    |       `11.2.0.4`        |     ✅     |    `2.3.2.1+`     |
| DM DBMS               |   `8.1.3.100`    |       `8.1.3.140`       |     ✅     |    `2.3.2.2+`     |
| Sql Server            |      `2019`      |      `12.4.2.jre8`      |     ✅     |    `2.3.2.3+`     |
| OceanBase             |       `-`        |           `-`           |     ⌛     |    `2.3.2.7+`     |

### Manufactured Product
```
nacos-2.x.x\distribution\target\nacos-server-2.x.x.tar
nacos-2.x.x\distribution\target\nacos-server-2.x.x.zip
```
### SQL Schema
#### {db}-schema.sql is in the config directory of the manufactured product
```
nacos-2.x.x\distribution\target\nacos-server-2.x.x.tar\conf\{db}-schema.sql
nacos-2.x.x\distribution\target\nacos-server-2.x.x.zip\conf\{db}-schema.sql
```
### DB Connection Configuration
```
### If use MySQL or MariaDB as datasource:
#spring.datasource.platform=mysql
#db.pool.config.driver-class-name=com.mysql.cj.jdbc.Driver
#db.url.0=jdbc:mysql://127.0.0.1:3306/nacos_server?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
#db.user.0=nacos
#db.password.0=123456

##### If use PostgreSQL or OpenGauss or GaussDB as datasource:
#spring.sql.init.platform=postgresql
#db.pool.config.driver-class-name=org.postgresql.Driver
#db.url.0=jdbc:postgresql://127.0.0.1:5432/nacos_server
#db.user.0=nacos
#db.password.0=123456

##### If use Oracle as datasource:
#spring.sql.init.platform=oracle
#db.pool.config.driver-class-name=oracle.jdbc.OracleDriver
#db.url.0=jdbc:oracle:thin:@//127.0.0.1:1521/nacos_server
#db.user.0=nacos
#db.password.0=123456
#db.testQuery=select 1 from dual

##### If use DM as datasource:
### WARNING: db.user.0 must be NACOS_SERVER, create this account
#spring.sql.init.platform=dm
#db.pool.config.driver-class-name=dm.jdbc.driver.DmDriver
#db.url.0=jdbc:dm://127.0.0.1:5236/nacos_server?keyWords=type,usage,role,action,password&useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8
#db.user.0=NACOS_SERVER
#db.password.0=123456
#db.testQuery=select 1

##### If use SqlServer as datasource:
#spring.sql.init.platform=sqlserver
#db.pool.config.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
#db.url.0=jdbc:sqlserver://127.0.0.1:1433;DatabaseName=nacos_server;trustServerCertificate=true
#db.user.0=nacos
#db.password.0=123456
#db.testQuery=select 1
```

### Start by the provided startup package

**Step 1: Download the binary package**

You can download the package from the [latest stable release](https://github.com/dylan-tao/nacos-plus/releases).  

Take release `nacos-server-2.x.x.zip` for example:
```sh
unzip nacos-server-2.x.x.zip
cd nacos/bin 
``` 

**Step 2: Start Server**

On the **Linux/Unix/Mac** platform, run the following command to start server with cluster mode: 
```sh
sh startup.sh -m cluster
```

On the **Windows** platform, run the following command to start server with cluster mode.  Alternatively, you can also double-click the `startup.cmd` to run standalone NacosServer.
```
startup.cmd -m cluster
```

### Package ReBuild
```
mvn -Prelease-nacos -Dmaven.test.skip=true clean install -U
```

### FAQ
> [Get 【FAQ-x】 list from issues, Click me !!!](https://github.com/dylan-tao/nacos-plus/issues)

### Who is using it?
<img src="customer/xinwang.png" width="15%" height="20%" />
<img src="customer/huaxia.png" width="15%" height="20%" />
<img src="customer/minsheng.png" width="15%" height="20%" />
<img src="customer/dongguan.png" width="15%" height="20%" />
<img src="customer/yinzhou.png" width="15%" height="20%" />
<img src="customer/jilin.png" width="15%" height="20%" />
<img src="customer/luzhou.png" width="15%" height="20%" />
<img src="customer/tailong.png" width="15%" height="20%" />
<img src="customer/xingye.png" width="15%" height="20%" />
<img src="customer/haerbin.png" width="15%" height="20%" />
<img src="customer/shengjing.png" width="15%" height="20%" />
<img src="customer/biyadi.png" width="15%" height="20%" />
<img src="customer/beiqi.png" width="15%" height="20%" />
<img src="customer/kelaisile.png" width="15%" height="20%" />
<img src="customer/weilai.png" width="15%" height="20%" />
<img src="customer/weipinhui.png" width="15%" height="20%" />
<img src="customer/qingnianyoupin.png" width="15%" height="20%" />
<img src="customer/taibao.png" width="15%" height="20%" />
<img src="customer/samoye.png" width="15%" height="20%" />
<img src="customer/fengbang.png" width="15%" height="20%" />
<img src="customer/lazada.png" width="15%" height="20%" />
<img src="customer/zhongtie.png" width="15%" height="20%" />

### Emergency Support

The author's WeChat QR code only provides emergency support. You must submit ISSUE before seeking help, otherwise the answer will not be supported.

<p align = "center">
<img src="doc/AuthorWechat.png" width="52%" height="52%" />
</p>
