CREATE DATABASE IF NOT EXISTS edu_admin DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE edu_admin;

CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_role (role)
);

CREATE TABLE IF NOT EXISTS course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    semester VARCHAR(30) NOT NULL,
    teacher_id BIGINT NOT NULL,
    capacity INT NOT NULL,
    selected_count INT NOT NULL DEFAULT 0,
    version BIGINT NOT NULL DEFAULT 0,
    INDEX idx_course_semester (semester),
    INDEX idx_course_teacher (teacher_id)
);

CREATE TABLE IF NOT EXISTS course_selection (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    selected_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_selection_student_course UNIQUE (student_id, course_id),
    INDEX idx_selection_student (student_id),
    INDEX idx_selection_course (course_id)
);

CREATE TABLE IF NOT EXISTS score (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    score DECIMAL(5,2) NOT NULL,
    grade_point DECIMAL(3,1) NOT NULL,
    remark VARCHAR(255),
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_score_student_course UNIQUE (student_id, course_id),
    INDEX idx_score_student (student_id),
    INDEX idx_score_course (course_id)
);

CREATE TABLE IF NOT EXISTS course_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL,
    classroom VARCHAR(50) NOT NULL,
    week_day INT NOT NULL,
    start_section INT NOT NULL,
    end_section INT NOT NULL,
    weeks VARCHAR(30) NOT NULL,
    INDEX idx_schedule_course (course_id)
);

INSERT IGNORE INTO sys_user (id, username, password, real_name, role)
VALUES
    (1, 'admin', '123456', 'System Admin', 'ADMIN'),
    (2, 'teacher01', '123456', 'Teacher Zhang', 'TEACHER'),
    (3, 'student01', '123456', 'Student Li', 'STUDENT'),
    (4, 'student02', '123456', 'Student Wang', 'STUDENT');

INSERT IGNORE INTO course (id, code, name, semester, teacher_id, capacity, selected_count, version)
VALUES
    (1, 'CS101', 'Introduction to Computer Science', '2026-Spring', 2, 60, 0, 0),
    (2, 'MATH201', 'Linear Algebra', '2026-Spring', 2, 80, 0, 0);

INSERT IGNORE INTO course_schedule (id, course_id, classroom, week_day, start_section, end_section, weeks)
VALUES
    (1, 1, 'A-201', 1, 1, 2, '1-16'),
    (2, 2, 'B-305', 3, 3, 4, '1-16');

