<p align = "center">
<img src="doc/Nacos_Logo.png" width="28%" syt height="28%" />
<br><br>
NacosPlus，为信创数据库爱心发电，Star⭐一下，是持续版本维护的动力
<br>
<br>
<a title="GitHub Watchers" target="_blank" href="https://github.com/dylan-tao/nacos-plus/watchers"><img src="https://img.shields.io/github/watchers/dylan-tao/nacos-plus.svg?label=Watchers&style=social"></a>  
<a title="GitHub Stars" target="_blank" href="https://github.com/dylan-tao/nacos-plus/stargazers"><img src="https://img.shields.io/github/stars/dylan-tao/nacos-plus.svg?label=Stars&style=social"></a>  
<a title="GitHub Forks" target="_blank" href="https://github.com/dylan-tao/nacos-plus/network/members"><img src="https://img.shields.io/github/forks/dylan-tao/nacos-plus.svg?label=Forks&style=social"></a>  
<a title="Author GitHub Followers" target="_blank" href="https://github.com/dylan-tao"><img src="https://img.shields.io/github/followers/dylan-tao.svg?label=Followers&style=social"></a>
<br>
</p>

### Nacos: Support the database type: 
✅ supported   ⌛ planning   ❌ not planned

| Database Type     | Database Version | Database Client Version | Supported | NacosPlus Version |
|:------------------|:----------------:|:-----------------------:|:---------:|:-----------------:|
| Mysql             |     `5.7.36`     |        `8.0.33`         |     ✅     |     `2.2.0.1`     |
| PostgreSQL        |     `90204`      |        `42.5.1`         |     ✅     |     `2.2.0.1`     |
| OpenGauss         |     `3.0.0`      |         `2.0.0`         |     ✅     |     `2.2.0.2`     |
| GaussDB           |     `2.1.0`      |         `2.0.0`         |     ✅     |     `2.2.0.2`     |
| Oracle            |    `11.2.0.1`    |       `11.2.0.4`        |     ✅     |     `2.3.2.1`     |
| Sql Server        |      `2019`      |          ``             |     ⌛     |     `2.3.2.3`     |
| DM DBMS           |   `8.1.3.100`    |       `8.1.1.193`       |     ⌛     |     `2.3.2.2`     |

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

##### Public Config:
nacos.plugin.datasource.log.enabled=true
### Count of DB:
db.num=1

### If use MySQL as datasource:
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
```

### Start by the provided startup package

#### Step 1: Download the binary package 

You can download the package from the [latest stable release](https://github.com/alibaba/nacos/releases).  

Take release `nacos-server-2.x.x.zip` for example:
```sh
unzip nacos-server-2.x.x.zip
cd nacos/bin 
``` 

#### Step 2: Start Server

On the **Linux/Unix/Mac** platform, run the following command to start server with standalone mode: 
```sh
sh startup.sh -m standalone
```

On the **Windows** platform, run the following command to start server with standalone mode.  Alternatively, you can also double-click the `startup.cmd` to run NacosServer.
```
startup.cmd -m standalone
```

### Package ReBuild
```
mvn -Prelease-nacos -Dmaven.test.skip=true clean install -U
```

### FAQ
#### 1. org.postgresql.util.PsQLException: Invalid or unsupported by client SCRAM mechanisms
> password_encryption_type未兼容postgreSQL的访问, 当password_encryption_type=1时，需要使用opengauss-jdbc的jar，否则会报错。
2.2.0.2版本已经支持GaussDB的此问题，如果服务端是原生postgreSQL的话，用2.2.0.1版本即可
#### 2. opengauss-jdbc-2.0.0在Maven公共仓库不能找到
> 此版本为银行内部提供的商业稳定版本，如果本地需要重新编译打包请将根目录lib下的jar包deploy到自己公司的maven仓库即可（2.2.0.2版本开始使用此jar包）
  ```
  <dependency>
      <groupId>org.opengauss</groupId>
      <artifactId>opengauss-jdbc</artifactId>
      <version>2.0.0</version>
  </dependency>
  ```
