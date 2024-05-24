<p align = "center">
<img src="doc/Nacos_Logo.png" width="22%" height="22%" />
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
<br>
<a title="Releases" target="_blank" href="https://github.com/dylan-tao/nacos-plus/releases"><img src="https://img.shields.io/github/release/dylan-tao/nacos-plus.svg?style=flat-square"></a>
<a title="Release Date" target="_blank" href="https://github.com/dylan-tao/nacos-plus/releases"><img src="https://img.shields.io/github/release-date/dylan-tao/nacos-plus.svg?style=flat-square&color=99CCFF"></a>
<a title="Code Size" target="_blank" href="https://github.com/dylan-tao/nacos-plus"><img src="https://img.shields.io/github/languages/code-size/dylan-tao/nacos-plus.svg?style=flat-square"></a>
<a title="Apache2" target="_blank" href="https://www.apache.org/licenses/LICENSE-2.0.html"><img src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg"></a>
<br>
<a title="GitHub Commits" target="_blank" href="https://github.com/dylan-tao/nacos-plus/commits/master"><img src="https://img.shields.io/github/commit-activity/m/dylan-tao/nacos-plus.svg?style=flat-square"></a>
<a title="Last Commit" target="_blank" href="https://github.com/dylan-tao/nacos-plus/commits/master"><img src="https://img.shields.io/github/last-commit/dylan-tao/nacos-plus.svg?style=flat-square&color=FF9900"></a>
<a title="GitHub Pull Requests" target="_blank" href="https://github.com/dylan-tao/nacos-plus/pulls"><img src="https://img.shields.io/github/issues-pr-closed/dylan-tao/nacos-plus.svg?style=flat-square&color=FF9966"></a>
<a title="Downloads" target="_blank" href="https://github.com/dylan-tao/nacos-plus/releases"><img src="https://img.shields.io/github/downloads/dylan-tao/nacos-plus/total.svg?style=flat-square"></a>
<br>
</p>

### Support the database type:

 **✅ supported   ⌛ planning   ❌ not planned**

| Database Type           | Database Version | Database Client Version | Supported | NacosPlus Version |
|:------------------------|:----------------:|:-----------------------:|:---------:|:-----------------:|
| Mysql/MariaDB           |     `5.7.36`     |        `8.0.33`         |     ✅     |    `2.2.0.1+`     |
| PostgreSQL              |     `90204`      |        `42.5.1`         |     ✅     |    `2.2.0.1+`     |
| OpenGauss               |     `3.0.0`      |         `2.0.0`         |     ✅     |    `2.2.0.2+`     |
| GaussDB                 |     `2.1.0`      |         `2.0.0`         |     ✅     |    `2.2.0.2+`     |
| Oracle                  |    `11.2.0.1`    |       `11.2.0.4`        |     ✅     |    `2.3.2.1+`     |
| DM DBMS                 |   `8.1.3.100`    |       `8.1.1.193`       |     ✅     |    `2.3.2.2+`     |
| Sql Server              |      `2019`      |      `12.4.2.jre8`      |     ⌛     |    `2.3.2.3+`     |

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
#*************** Config Module Related Configurations ***************#

### If use MySQL or MariaDB as datasource:
# spring.datasource.platform=mysql
# db.url.0=jdbc:mysql://127.0.0.1:3306/nacos_server?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
# db.user.0=nacos
# db.password.0=123456

##### If use PostgreSQL or OpenGauss or GaussDB as datasource:
#spring.sql.init.platform=postgresql
#db.pool.config.driver-class-name=org.postgresql.Driver
#db.url.0=jdbc:postgresql://127.0.0.1:25432/nacos_server
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
#db.url.0=jdbc:dm://127.0.0.1:30236/nacos_server
#db.user.0=NACOS_SERVER
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
