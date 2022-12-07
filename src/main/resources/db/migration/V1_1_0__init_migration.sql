CREATE TABLE user_role
(
    id   BIGINT NOT NULL,
    role VARCHAR(255),
    CONSTRAINT pk_user_role PRIMARY KEY (id)
);

CREATE TABLE user_to_role
(
    user_id UUID   NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT pk_user_to_role PRIMARY KEY (user_id, role_id)
);


ALTER TABLE user_to_role
    ADD CONSTRAINT FK_ROLE FOREIGN KEY (role_id) REFERENCES user_role (id);


CREATE TABLE student_group
(
    id     BIGINT  NOT NULL,
    number INTEGER NOT NULL,
    CONSTRAINT pk_student_group PRIMARY KEY (id)
);

CREATE TABLE student
(
    id       UUID NOT NULL,
    group_id BIGINT,
    user_id  UUID NOT NULL,
    CONSTRAINT pk_student PRIMARY KEY (id)
);

ALTER TABLE student
    ADD CONSTRAINT FK_STUDENT_ON_GROUP FOREIGN KEY (group_id) REFERENCES "student_group" (id);


CREATE TABLE base_user
(
    id         UUID         NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    surname    VARCHAR(255) NOT NULL,
    name       VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255) NOT NULL,
    student_id UUID,
    teacher_id UUID,
    CONSTRAINT pk_base_user PRIMARY KEY (id)
);

ALTER TABLE user_to_role
    ADD CONSTRAINT FK_USER FOREIGN KEY (user_id) REFERENCES "base_user" (id);

ALTER TABLE base_user
    ADD CONSTRAINT FK_BASE_USER_ON_STUDENT FOREIGN KEY (student_id) REFERENCES student (id);

ALTER TABLE student
    ADD CONSTRAINT FK_STUDENT_ON_USER FOREIGN KEY (user_id) REFERENCES base_user (id);

CREATE TABLE teacher
(
    id         UUID         NOT NULL,
    position   VARCHAR(255) NOT NULL,
    experience INTEGER      NOT NULL,
    degree     VARCHAR(255) NOT NULL,
    user_id    UUID         NOT NULL,
    CONSTRAINT pk_teacher PRIMARY KEY (id)
);

ALTER TABLE teacher
    ADD CONSTRAINT FK_TEACHER_ON_USER FOREIGN KEY (user_id) REFERENCES base_user (id);

ALTER TABLE base_user
    ADD CONSTRAINT FK_BASE_USER_ON_TEACHER FOREIGN KEY (teacher_id) REFERENCES teacher (id);

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;
