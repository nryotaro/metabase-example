-- https://www.metabase.com/docs/latest/installation-and-operation/configuring-application-database
create database metabase character set utf8mb4  collate utf8mb4_unicode_ci;
-- https://www.metabase.com/docs/latest/databases/users-roles-privileges#privileges-to-enable-actions
create role analytics;
grant select on metabase.* to analytics;

create user metabase@'%' identified by 'metabase';
grant all privileges on metabase.* to 'metabase'@'%';

create role metabase_actions;

create role metabase_model_caching;
