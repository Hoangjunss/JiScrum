CREATE DATABASE jiscrum
    DEFAULT CHARACTER SET = 'utf8mb4';

USE jiscrum;

CREATE TABLE Member(
    member_id INT PRIMARY KEY COMMENT 'Member id',
    member_project_id INT COMMENT 'Foreign key table project',
    member_role ENUM('PROJECT_MANAGER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'DEVELOPER', 'TESTER', 'VIEWER') COMMENT 'Role member',
    member_joined_at DATETIME COMMENT 'Date join member',
    member_left_at DATETIME COMMENT 'Date left member',
    member_status BOOLEAN COMMENT 'Status member'
)

CREATE TABLE Project(
    project_id INT PRIMARY KEY COMMENT 'Project id',
    project_name VARCHAR(50) COMMENT 'Project name',
    project_description TEXT COMMENT 'Project description',
    project_create_at DATETIME COMMENT 'Date create project',
    project_owner_id INT COMMENT 'Project owner id',
    project_status ENUM('PLANNING', 'ACTIVE', 'ON_HOLD', 'COMPLETED', 'CANCELLED') COMMENT 'Project status',
    project_start_date DATETIME COMMENT 'Project start at',
    project_end_date DATETIME COMMENT 'Project end at'
)