-- Reset Smart City Management Database
-- Run this script in MySQL to completely reset the database

DROP DATABASE IF EXISTS smartcity_management;
CREATE DATABASE smartcity_management;
USE smartcity_management;

-- The Spring Boot application will create the tables automatically
-- when it starts with ddl-auto=create-drop
