
<img src="doc/Nacos_Logo.png" width="36%" syt height="36%" />

## Star一下，是持续版本维护的动力

## Nacos: Support the database type: GaussDB \ OpenGauss \ PostgreSQL 

-------
### Database Server Version
```
(openGauss 2.1.0 build 590b0f8e) compiled at 2021-09-30 14:29:04 commit 0 last mr on x86_64-unknown-linux-gnu, compiled by g++ (GCC) 7.3.0, 64-bit
```

### Manufactured Product
```
nacos-2.2.0\distribution\target\nacos-server-2.2.0.tar
nacos-2.2.0\distribution\target\nacos-server-2.2.0.zip
```
### SQL Schema
#### postgresql-schema.sql is in the config directory of the manufactured product
```
nacos-2.2.0\distribution\target\nacos-server-2.2.0.tar\conf\postgresql-schema.sql
nacos-2.2.0\distribution\target\nacos-server-2.2.0.zip\conf\postgresql-schema.sql
```
### DB Connection Configuration
```
#*************** Config Module Related Configurations ***************#
### Config of DB:
db.num=1
nacos.plugin.datasource.log.enabled=true
 
### Connect URL of MYSQL:
# spring.sql.init.platform=mysql
# db.url.0=jdbc:mysql://127.0.0.1:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
# db.user.0=nacos
# db.password.0=nacos
 
### Connect URL of POSTGRESQL / GaussDB / OpenGauss:
spring.sql.init.platform=postgresql
db.pool.config.driver-class-name=org.postgresql.Driver
db.url.0=jdbc:postgresql://127.0.0.1:5432/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
db.user.0=nacos
db.password.0=nacos
```

### Start by the provided startup package

#### Step 1: Download the binary package 

You can download the package from the [latest stable release](https://github.com/alibaba/nacos/releases).  

Take release `nacos-server-2.2.0.zip` for example:
```sh
unzip nacos-server-2.2.0.zip
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
