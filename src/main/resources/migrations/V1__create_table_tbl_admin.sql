CREATE TABLE tbl_admin
(
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    type       VARCHAR(255) NOT NULL,
    name       VARCHAR(255),
    user_id    INTEGER      NOT NULL,
    CONSTRAINT pk_tbl_admin PRIMARY KEY (user_id)
);

ALTER TABLE tbl_admin
    ADD CONSTRAINT uc_tbl_admin_user UNIQUE (user_id);

ALTER TABLE tbl_admin
    ADD CONSTRAINT FK_TBL_ADMIN_ON_USER FOREIGN KEY (user_id) REFERENCES tbl_user (id);