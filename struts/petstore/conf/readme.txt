+Web
    |---------------- index.jsp 前端跳转首页
    |---------------- images 图片目录
    |---------------- +META-INF 应用装配配置文件
    |                                   |---------- context.xml 特于此应用的上下文配置，此例包括在使用 Tomcat 数据源的配置(早先时候的
    |---------------- WEB-INF

应用配置说明
============

# ================ Mysql 数据库初始化 =================

# 在命令行下以 root 身份运行
C:\Documents and Settings\pprun > mysql -h localhost -u root -p < $hjpetstore\conf\jpetstore_mysql.sql
Enter password: ********

以同样的方式执行以下两个文件（jpetstore-mysql-schema.sql生成表结构，jpetstore-mysql-dataload.sql是数据初始化）
$hjpetstore\db\mysql\jpetstore-mysql-schema.sql
$hjpetstore\db\mysql\jpetstore-mysql-dataload.sql

如果熟悉，以上步骤可以在 NetBeans 或其他工具中执行。
