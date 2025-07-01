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

CREATE TABLE Issue(
    issue_id INT PRIMARY KEY COMMENT 'Issue ID',
    issue_title VARCHAR(200) COMMENT 'Title issue',
    issue_description TEXT COMMENT 'Description issue',
    issue_priority ENUM('LOWEST', 'LOW', 'MEDIUM', 'HIGH', 'HIGHEST') DEFAULT 'MEDIUM' COMMENT 'Priority tiên',
    issue_status ENUM('TO_DO', 'IN_PROGRESS', 'REVIEW', 'DONE') DEFAULT 'TO_DO' COMMENT 'Status issue',
    issue_project_id INT COMMENT 'Foreign key to Project',
    issue_reporter_id INT COMMENT 'Member create issue',
    issue_assignee_id INT COMMENT 'Member assignee',
    issue_deadline DATETIME COMMENT 'Deadline finish',
    issue_created_at DATETIME DEFAULT COMMENT 'On create',
    issue_updated_at DATETIME DEFAULT COMMENT 'Last update',
    issue_resolved_at DATETIME COMMENT 'On complete'
);

CREATE TABLE Attachment(
    attachment_id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Attachment ID',
    attachment_name VARCHAR(255) NOT NULL COMMENT 'File name',
    attachment_path VARCHAR(500) NOT NULL COMMENT 'File path',
    attachment_size BIGINT COMMENT 'File size(bytes)',
    attachment_type VARCHAR(50) COMMENT 'Type file (MIME type)',
    attachment_issue_id INT NOT NULL COMMENT 'Foreign key to Issue',
    attachment_uploaded_by INT NOT NULL COMMENT 'Upload by someone',
    attachment_uploaded_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Date upload'
);