<p align = "center">
<img src="doc/logo.png" width="28%" height="28%" />
<br><br>
<b>NacosPlus，Generate power for China's(🇨🇳Xinchuang) database love.</b>
<br><br>Star ⭐ (top right), the motivation for continuous version maintenance😊
<br><br>Watch it, if you encounter problems and ask Issues, you can receive the progress of the problem in real time
<br><br>Fork it, after improving and adapting to the special scene, remember to Pull Request to NacosPlus
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

English | [中文文档](https://github.com/dylan-tao/nacos-plus/blob/main/README.md)

### 🔖 Support the database type:

---

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

### 📦 Manufactured Product

---

nacos-2.x.x\distribution\target\nacos-server-2.x.x.tar</br>
nacos-2.x.x\distribution\target\nacos-server-2.x.x.zip

### 📄 SQL Schema

---

#### It is in the configuration directory of the manufactured product, format: {db}-schema.sql
nacos-2.x.x\distribution\target\nacos-server-2.x.x.tar\conf\{db}-schema.sql</br>
nacos-2.x.x\distribution\target\nacos-server-2.x.x.zip\conf\{db}-schema.sql

### 📌 DB Connection Configuration

---

#### The following configuration is in the conf\application.properties file of the manufactured product

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

### 🚀 Start by the provided startup package

---

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

### 🚣 Package ReBuild

---

```
mvn -Prelease-nacos -Dmaven.test.skip=true clean install -U
```

### 🙋 FAQ

---

[Get 【FAQ-x】 list from issues, Click me !!!](https://github.com/dylan-tao/nacos-plus/issues)

### 💑 Who is using it?

---

<p align = "center">
<img src="doc/customer/yidong.png" width="102" />
<img src="doc/customer/caixun.png" width="102" />
<img src="doc/customer/xinwang.png" width="102" />
<img src="doc/customer/dongguan.png" width="102" />
<img src="doc/customer/yinzhou.png" width="102" />
<img src="doc/customer/jilin.png" width="102" />
</p>
<p align = "center">
<img src="doc/customer/huaxia.png" width="102" />
<img src="doc/customer/luzhou.png" width="102" />
<img src="doc/customer/tailong.png" width="102" />
<img src="doc/customer/xingye.png" width="102" />
<img src="doc/customer/minsheng.png" width="102" />
<img src="doc/customer/shengjing.png" width="102" />
</p>
<p align = "center">
<img src="doc/customer/biyadi.png" width="102" />
<img src="doc/customer/beiqi.png" width="102" />
<img src="doc/customer/kelaisile.png" width="102" />
<img src="doc/customer/weilai.png" width="102" />
<img src="doc/customer/weipinhui.png" width="102" />
<img src="doc/customer/qingnianyoupin.png" width="102" />
</p>
<p align = "center">
<img src="doc/customer/taibao.png" width="102" />
<img src="doc/customer/samoye.png" width="102" />
<img src="doc/customer/fengbang.png" width="102" />
<img src="doc/customer/lazada.png" width="102" />
<img src="doc/customer/zhongtie.png" width="102" />
<img src="doc/customer/haerbin.png" width="102" />
</p>

### 💝 Thanks

---

Thanks to the free open source license provided by JetBrains
<p align = "left">
<img src="doc/thanks/jetbrains.png" width="80" />
</p>

### 🔗 Emergency Support

---

The author's WeChat QR code only provides emergency support. You must submit ISSUE before seeking help, otherwise the answer will not be supported.

<p align = "center">
<img src="doc/wechat.png" width="350" height="350" />
</p>
