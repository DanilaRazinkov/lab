
CREATE TABLE couple_group
(
    couple_id BIGINT NOT NULL,
    group_id  BIGINT NOT NULL
);

ALTER TABLE couple_group
    ADD CONSTRAINT fk_cougro_on_couple FOREIGN KEY (couple_id) REFERENCES couple (id);

ALTER TABLE couple_group
    ADD CONSTRAINT fk_cougro_on_group FOREIGN KEY (group_id) REFERENCES student_group (id);