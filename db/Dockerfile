FROM mysql:8.0.28
ENV MYSQL_ROOT_PASSWORD uzabase
ADD init.sql /docker-entrypoint-initdb.d/init.sql
#ADD custom.cnf /etc/mysql/conf.d/custom.cnf