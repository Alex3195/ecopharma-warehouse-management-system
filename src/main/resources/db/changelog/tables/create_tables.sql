CREATE SEQUENCE IF NOT EXISTS audit_trail_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS employee_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS inbound_receipt_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS inventory_audit_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS inventory_snapshot_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS location_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS product_meta_data_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS product_output_algorithm_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS product_return_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS product_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS settings_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS storage_condition_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS task_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS transport_label_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE "audit-trail"
(
    id           BIGINT                      NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    status       VARCHAR(255)                NOT NULL,
    created_by   BIGINT,
    updated_by   BIGINT,
    table_name   VARCHAR(255),
    record_id    BIGINT,
    action_type  VARCHAR(255),
    old_value    VARCHAR(255),
    new_value    VARCHAR(255),
    performed_by BIGINT,
    action_time  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT "pk_audit-trail" PRIMARY KEY (id)
);

CREATE TABLE employee
(
    id         BIGINT                      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    status     VARCHAR(255)                NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    name       VARCHAR(255),
    role       VARCHAR(255),
    CONSTRAINT pk_employee PRIMARY KEY (id)
);

CREATE TABLE "inbound-receipt"
(
    id           BIGINT                      NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    status       VARCHAR(255)                NOT NULL,
    created_by   BIGINT,
    updated_by   BIGINT,
    product_id   BIGINT,
    receipt_type VARCHAR(255),
    quantity     INTEGER,
    supplier_id  BIGINT,
    CONSTRAINT "pk_inbound-receipt" PRIMARY KEY (id)
);

CREATE TABLE "inventory-audit"
(
    id         BIGINT                      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    status     VARCHAR(255)                NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    audit_type VARCHAR(255),
    product_id BIGINT,
    sector     VARCHAR(255),
    shelf      VARCHAR(255),
    floor      VARCHAR(255),
    quantity   INTEGER,
    audit_time TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT "pk_inventory-audit" PRIMARY KEY (id)
);

CREATE TABLE "inventory-snapshot"
(
    id            BIGINT                      NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    status        VARCHAR(255)                NOT NULL,
    created_by    BIGINT,
    updated_by    BIGINT,
    product_id    BIGINT,
    location_id   BIGINT,
    quantity      INTEGER,
    snapshot_time TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT "pk_inventory-snapshot" PRIMARY KEY (id)
);

CREATE TABLE location
(
    id         BIGINT                      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    status     VARCHAR(255)                NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    sector     VARCHAR(255),
    shelf      VARCHAR(255),
    floor      VARCHAR(255),
    product_id BIGINT,
    CONSTRAINT pk_location PRIMARY KEY (id)
);

CREATE TABLE "outbound-shipment"
(
    id            BIGINT                      NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    status        VARCHAR(255)                NOT NULL,
    created_by    BIGINT,
    updated_by    BIGINT,
    product_id    BIGINT,
    shipment_type VARCHAR(255),
    quantity      INTEGER,
    customer_id   BIGINT,
    CONSTRAINT "pk_outbound-shipment" PRIMARY KEY (id)
);

CREATE TABLE product
(
    id           BIGINT                      NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    status       VARCHAR(255)                NOT NULL,
    created_by   BIGINT,
    updated_by   BIGINT,
    name         VARCHAR(255),
    description  VARCHAR(255),
    product_type VARCHAR(255),
    quantity     INTEGER,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE "product-meta-data"
(
    id            BIGINT                      NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    status        VARCHAR(255)                NOT NULL,
    created_by    BIGINT,
    updated_by    BIGINT,
    product_id    BIGINT,
    batch_number  VARCHAR(255),
    expiry_date   date,
    serial_number VARCHAR(255),
    CONSTRAINT "pk_product-meta-data" PRIMARY KEY (id)
);

CREATE TABLE "product-output-algorithm"
(
    id             BIGINT                      NOT NULL,
    created_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at     TIMESTAMP WITHOUT TIME ZONE,
    status         VARCHAR(255)                NOT NULL,
    created_by     BIGINT,
    updated_by     BIGINT,
    algorithm_type VARCHAR(255),
    description    VARCHAR(255),
    CONSTRAINT "pk_product-output-algorithm" PRIMARY KEY (id)
);

CREATE TABLE "product-return"
(
    id            BIGINT                      NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    status        VARCHAR(255)                NOT NULL,
    created_by    BIGINT,
    updated_by    BIGINT,
    product_id    BIGINT,
    return_reason VARCHAR(255),
    quantity      INTEGER,
    CONSTRAINT "pk_product-return" PRIMARY KEY (id)
);

CREATE TABLE settings_entity
(
    id            BIGINT                      NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    status        VARCHAR(255)                NOT NULL,
    created_by    BIGINT,
    updated_by    BIGINT,
    setting_name  VARCHAR(255),
    setting_value VARCHAR(255),
    CONSTRAINT pk_settingsentity PRIMARY KEY (id)
);

CREATE TABLE "storage-condition"
(
    id              BIGINT                      NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    status          VARCHAR(255)                NOT NULL,
    created_by      BIGINT,
    updated_by      BIGINT,
    location_id     BIGINT,
    condition_type  VARCHAR(255),
    condition_value VARCHAR(255),
    CONSTRAINT "pk_storage-condition" PRIMARY KEY (id)
);

CREATE TABLE task
(
    id          BIGINT                      NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    status      VARCHAR(255)                NOT NULL,
    created_by  BIGINT,
    updated_by  BIGINT,
    task_type   VARCHAR(255),
    task_status VARCHAR(255),
    assigned_to BIGINT,
    due_date    TIMESTAMP WITHOUT TIME ZONE,
    product_id  BIGINT,
    location_id BIGINT,
    CONSTRAINT pk_task PRIMARY KEY (id)
);

CREATE TABLE "transport-label"
(
    id          BIGINT                      NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    status      VARCHAR(255)                NOT NULL,
    created_by  BIGINT,
    updated_by  BIGINT,
    product_id  BIGINT,
    shipment_id BIGINT,
    label       VARCHAR(255),
    CONSTRAINT "pk_transport-label" PRIMARY KEY (id)
);

CREATE TABLE "user"
(
    id            BIGINT                      NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    status        VARCHAR(255)                NOT NULL,
    created_by    BIGINT,
    updated_by    BIGINT,
    name          VARCHAR(255),
    contact_name  VARCHAR(255),
    contact_email VARCHAR(255),
    contact_phone VARCHAR(255),
    address       VARCHAR(255),
    country       VARCHAR(255),
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE "audit-trail"
    ADD CONSTRAINT "FK_AUDIT-TRAIL_ON_PERFORMED_BY" FOREIGN KEY (performed_by) REFERENCES employee (id);

ALTER TABLE "inbound-receipt"
    ADD CONSTRAINT "FK_INBOUND-RECEIPT_ON_PRODUCT" FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE "inbound-receipt"
    ADD CONSTRAINT "FK_INBOUND-RECEIPT_ON_SUPPLIER" FOREIGN KEY (supplier_id) REFERENCES "user" (id);

ALTER TABLE "inventory-audit"
    ADD CONSTRAINT "FK_INVENTORY-AUDIT_ON_PRODUCT" FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE "inventory-snapshot"
    ADD CONSTRAINT "FK_INVENTORY-SNAPSHOT_ON_LOCATION" FOREIGN KEY (location_id) REFERENCES location (id);

ALTER TABLE "inventory-snapshot"
    ADD CONSTRAINT "FK_INVENTORY-SNAPSHOT_ON_PRODUCT" FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE location
    ADD CONSTRAINT FK_LOCATION_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE "outbound-shipment"
    ADD CONSTRAINT "FK_OUTBOUND-SHIPMENT_ON_CUSTOMER" FOREIGN KEY (customer_id) REFERENCES "user" (id);

ALTER TABLE "outbound-shipment"
    ADD CONSTRAINT "FK_OUTBOUND-SHIPMENT_ON_PRODUCT" FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE "product-meta-data"
    ADD CONSTRAINT "FK_PRODUCT-META-DATA_ON_PRODUCT" FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE "product-return"
    ADD CONSTRAINT "FK_PRODUCT-RETURN_ON_PRODUCT" FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE "storage-condition"
    ADD CONSTRAINT "FK_STORAGE-CONDITION_ON_LOCATION" FOREIGN KEY (location_id) REFERENCES location (id);

ALTER TABLE task
    ADD CONSTRAINT FK_TASK_ON_ASSIGNED_TO FOREIGN KEY (assigned_to) REFERENCES employee (id);

ALTER TABLE task
    ADD CONSTRAINT FK_TASK_ON_LOCATION FOREIGN KEY (location_id) REFERENCES location (id);

ALTER TABLE task
    ADD CONSTRAINT FK_TASK_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE "transport-label"
    ADD CONSTRAINT "FK_TRANSPORT-LABEL_ON_PRODUCT" FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE "transport-label"
    ADD CONSTRAINT "FK_TRANSPORT-LABEL_ON_SHIPMENT" FOREIGN KEY (shipment_id) REFERENCES "outbound-shipment" (id);