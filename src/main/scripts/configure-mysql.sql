-- 1. Create a MySQL Docker container: docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=true -d mysql
-- 2. Check if container was created: docker ps
-- 3. Create a new connection in MySQL Workbench: root user, port:3306 and connect to Database
-- 4. Run the following queries

-- Create dev and pro databases
CREATE DATABASE mysql_dev;
CREATE DATABASE mysql_pro;

-- Create database users
CREATE USER 'dev_user'@'localhost' IDENTIFIED BY 'pass'; -- password: pass
CREATE USER 'dev_user'@'%' IDENTIFIED BY 'pass';
CREATE USER 'pro_user'@'localhost' IDENTIFIED BY 'pass';
CREATE USER 'pro_user'@'%' IDENTIFIED BY 'pass';

-- Provide DML permissions to database users
GRANT SELECT ON mysql_dev.* to 'dev_user'@'localhost';
GRANT INSERT ON mysql_dev.* to 'dev_user'@'localhost';
GRANT UPDATE ON mysql_dev.* to 'dev_user'@'localhost';
GRANT DELETE ON mysql_dev.* to 'dev_user'@'localhost';
GRANT SELECT ON mysql_dev.* to 'dev_user'@'%';
GRANT INSERT ON mysql_dev.* to 'dev_user'@'%';
GRANT UPDATE ON mysql_dev.* to 'dev_user'@'%';
GRANT DELETE ON mysql_dev.* to 'dev_user'@'%';
GRANT SELECT ON mysql_pro.* to 'pro_user'@'localhost';
GRANT INSERT ON mysql_pro.* to 'pro_user'@'localhost';
GRANT UPDATE ON mysql_pro.* to 'pro_user'@'localhost';
GRANT DELETE ON mysql_pro.* to 'pro_user'@'localhost';
GRANT SELECT ON mysql_pro.* to 'pro_user'@'%';
GRANT INSERT ON mysql_pro.* to 'pro_user'@'%';
GRANT UPDATE ON mysql_pro.* to 'pro_user'@'%';
GRANT DELETE ON mysql_pro.* to 'pro_user'@'%';