CREATE TABLE occupation_group
(
    group_id      BIGINT NOT NULL,
    occupation_id BIGINT NOT NULL
);

ALTER TABLE occupation_group
    ADD CONSTRAINT fk_occgro_on_group FOREIGN KEY (group_id) REFERENCES student_group (id);

ALTER TABLE occupation_group
    ADD CONSTRAINT fk_occgro_on_occupation FOREIGN KEY (occupation_id) REFERENCES occupation (id);