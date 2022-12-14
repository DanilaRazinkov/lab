CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE semester
(
    id             BIGINT NOT NULL,
    semester_start date   NOT NULL,
    semester_end   date   NOT NULL,
    CONSTRAINT pk_semester PRIMARY KEY (id)
);

CREATE TABLE holiday
(
    id          BIGINT NOT NULL,
    date        date,
    semester_id BIGINT,
    CONSTRAINT pk_holiday PRIMARY KEY (id)
);

ALTER TABLE holiday
    ADD CONSTRAINT FK_HOLIDAY_ON_SEMESTER FOREIGN KEY (semester_id) REFERENCES semester (id);

CREATE TABLE couple
(
    id          BIGINT       NOT NULL,
    hour        INTEGER      NOT NULL,
    period      VARCHAR(255),
    subject_id  BIGINT       NOT NULL,
    teacher_id  UUID         NOT NULL,
    audience    VARCHAR(255) NOT NULL,
    semester_id BIGINT,
    CONSTRAINT pk_couple PRIMARY KEY (id)
);

ALTER TABLE couple
    ADD CONSTRAINT FK_COUPLE_ON_SEMESTER FOREIGN KEY (semester_id) REFERENCES semester (id);

CREATE TABLE subject
(
    id   BIGINT       NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_subject PRIMARY KEY (id)
);


ALTER TABLE couple
    ADD CONSTRAINT FK_COUPLE_ON_SUBJECT FOREIGN KEY (subject_id) REFERENCES subject (id);

ALTER TABLE couple
    ADD CONSTRAINT FK_COUPLE_ON_TEACHER FOREIGN KEY (teacher_id) REFERENCES teacher (id);

