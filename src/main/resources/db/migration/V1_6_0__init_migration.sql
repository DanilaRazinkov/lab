CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE occupation
(
    id         BIGINT       NOT NULL,
    hour       INTEGER      NOT NULL,
    date       date,
    teacher_id UUID         NOT NULL,
    audience   VARCHAR(255) NOT NULL,
    subject    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_occupation PRIMARY KEY (id)
);

ALTER TABLE occupation
    ADD CONSTRAINT FK_OCCUPATION_ON_TEACHER FOREIGN KEY (teacher_id) REFERENCES teacher (id);