CREATE SEQUENCE IF NOT EXISTS address_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS audit_trail_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS cell_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS characteristics_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS cross_docking_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS floor_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS inbound_receipt_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS inventory_audit_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS inventory_snapshot_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS location_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS product_meta_data_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS product_output_algorithm_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS product_return_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS product_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS rack_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sector_characteristics_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sector_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS settings_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS task_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS transport_label_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS unit_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS warehouse_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE address
(
    id              BIGINT                      NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    status          VARCHAR(255)                NOT NULL,
    created_by      BIGINT,
    updated_by      BIGINT,
    street          VARCHAR(255),
    city            VARCHAR(255),
    state           VARCHAR(255),
    postal_code     VARCHAR(255),
    country         VARCHAR(255),
    latitude        DOUBLE PRECISION,
    longitude       DOUBLE PRECISION,
    additional_info VARCHAR(255),
    CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE audit_trail
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
    CONSTRAINT pk_audit_trail PRIMARY KEY (id)
);

CREATE TABLE cell
(
    id         BIGINT                      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    status     VARCHAR(255)                NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    code       VARCHAR(255)                NOT NULL,
    width      DOUBLE PRECISION,
    depth      DOUBLE PRECISION,
    height     DOUBLE PRECISION,
    max_weight DOUBLE PRECISION,
    max_volume DOUBLE PRECISION,
    is_empty   BOOLEAN,
    floor_id   BIGINT,
    CONSTRAINT pk_cell PRIMARY KEY (id)
);

CREATE TABLE characteristic
(
    id          BIGINT                      NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    status      VARCHAR(255)                NOT NULL,
    created_by  BIGINT,
    updated_by  BIGINT,
    name        VARCHAR(255),
    description VARCHAR(255),
    type        VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_characteristic PRIMARY KEY (id)
);

CREATE TABLE cross_docking
(
    id                   BIGINT                      NOT NULL,
    created_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at           TIMESTAMP WITHOUT TIME ZONE,
    status               VARCHAR(255)                NOT NULL,
    created_by           BIGINT,
    updated_by           BIGINT,
    inbound_receipt_id   BIGINT,
    outbound_shipment_id BIGINT,
    cross_dock_type      VARCHAR(255),
    processing_time      TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_cross_docking PRIMARY KEY (id)
);

CREATE TABLE floor
(
    id         BIGINT                      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    status     VARCHAR(255)                NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    level      INTEGER                     NOT NULL,
    height     DOUBLE PRECISION            NOT NULL,
    rack_id    BIGINT,
    CONSTRAINT pk_floor PRIMARY KEY (id)
);

CREATE TABLE inbound_receipt
(
    id             BIGINT                      NOT NULL,
    created_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at     TIMESTAMP WITHOUT TIME ZONE,
    status         VARCHAR(255)                NOT NULL,
    created_by     BIGINT,
    updated_by     BIGINT,
    product_id     BIGINT,
    receipt_type   VARCHAR(255),
    quantity       INTEGER,
    supplier_id    BIGINT,
    receipt_status VARCHAR(255),
    CONSTRAINT pk_inbound_receipt PRIMARY KEY (id)
);

CREATE TABLE inventory_audit
(
    id         BIGINT                      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    status     VARCHAR(255)                NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    audit_type VARCHAR(255),
    product_id BIGINT,
    sector_id  BIGINT,
    rack_id    BIGINT,
    floor_id   BIGINT,
    quantity   INTEGER,
    audit_time TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_inventory_audit PRIMARY KEY (id)
);

CREATE TABLE inventory_snapshot
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
    CONSTRAINT pk_inventory_snapshot PRIMARY KEY (id)
);

CREATE TABLE location
(
    id           BIGINT                      NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    status       VARCHAR(255)                NOT NULL,
    created_by   BIGINT,
    updated_by   BIGINT,
    name         VARCHAR(255),
    warehouse_id BIGINT,
    sector_id    BIGINT,
    rack_id      BIGINT,
    floor_id     BIGINT,
    cell_id      BIGINT,
    product_id   BIGINT,
    CONSTRAINT pk_location PRIMARY KEY (id)
);

CREATE TABLE outbound_shipment
(
    id              BIGINT                      NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    status          VARCHAR(255)                NOT NULL,
    created_by      BIGINT,
    updated_by      BIGINT,
    product_id      BIGINT,
    shipment_type   VARCHAR(255),
    quantity        INTEGER,
    customer_id     BIGINT,
    scheduled_for   TIMESTAMP WITHOUT TIME ZONE,
    shipment_status VARCHAR(255),
    CONSTRAINT pk_outbound_shipment PRIMARY KEY (id)
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

CREATE TABLE product_meta_data
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
    CONSTRAINT pk_product_meta_data PRIMARY KEY (id)
);

CREATE TABLE product_output_algorithm
(
    id             BIGINT                      NOT NULL,
    created_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at     TIMESTAMP WITHOUT TIME ZONE,
    status         VARCHAR(255)                NOT NULL,
    created_by     BIGINT,
    updated_by     BIGINT,
    algorithm_type VARCHAR(255),
    description    VARCHAR(255),
    CONSTRAINT pk_product_output_algorithm PRIMARY KEY (id)
);

CREATE TABLE product_return
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
    CONSTRAINT pk_product_return PRIMARY KEY (id)
);

CREATE TABLE racks
(
    id         BIGINT                      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    status     VARCHAR(255)                NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    name       VARCHAR(255)                NOT NULL,
    type       VARCHAR(255)                NOT NULL,
    height     DOUBLE PRECISION            NOT NULL,
    width      DOUBLE PRECISION            NOT NULL,
    depth      DOUBLE PRECISION            NOT NULL,
    sector_id  BIGINT,
    CONSTRAINT pk_racks PRIMARY KEY (id)
);

CREATE TABLE sector
(
    id           BIGINT                      NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    status       VARCHAR(255)                NOT NULL,
    created_by   BIGINT,
    updated_by   BIGINT,
    name         VARCHAR(255),
    description  VARCHAR(255),
    warehouse_id BIGINT,
    CONSTRAINT pk_sector PRIMARY KEY (id)
);

CREATE TABLE sector_characteristic
(
    id                BIGINT                      NOT NULL,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    status            VARCHAR(255)                NOT NULL,
    created_by        BIGINT,
    updated_by        BIGINT,
    sector_id         BIGINT,
    characteristic_id BIGINT,
    value             VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_sector_characteristic PRIMARY KEY (id)
);

CREATE TABLE settings
(
    id         BIGINT                      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    status     VARCHAR(255)                NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    name       VARCHAR(255),
    value      VARCHAR(255),
    CONSTRAINT pk_settings PRIMARY KEY (id)
);

CREATE TABLE task
(
    id          BIGINT                      NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    status      VARCHAR(255)                NOT NULL,
    created_by  BIGINT,
    updated_by  BIGINT,
    name        VARCHAR(255),
    task_type   VARCHAR(255),
    task_status VARCHAR(255),
    assigned_to BIGINT,
    due_date    TIMESTAMP WITHOUT TIME ZONE,
    product_id  BIGINT,
    location_id BIGINT,
    CONSTRAINT pk_task PRIMARY KEY (id)
);

CREATE TABLE transport_label
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
    CONSTRAINT pk_transport_label PRIMARY KEY (id)
);

CREATE TABLE unit
(
    id          BIGINT                      NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    status      VARCHAR(255)                NOT NULL,
    created_by  BIGINT,
    updated_by  BIGINT,
    name        VARCHAR(255),
    symbol      VARCHAR(255),
    description VARCHAR(255),
    CONSTRAINT pk_unit PRIMARY KEY (id)
);

CREATE TABLE "user"
(
    id         BIGINT                      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    status     VARCHAR(255)                NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    name       VARCHAR(255),
    email      VARCHAR(255),
    phone      VARCHAR(255),
    role       VARCHAR(255),
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE warehouse
(
    id          BIGINT                      NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    status      VARCHAR(255)                NOT NULL,
    created_by  BIGINT,
    updated_by  BIGINT,
    name        VARCHAR(255),
    description VARCHAR(255),
    address_id  BIGINT,
    CONSTRAINT pk_warehouse PRIMARY KEY (id)
);

ALTER TABLE audit_trail
    ADD CONSTRAINT FK_AUDIT_TRAIL_ON_PERFORMED_BY FOREIGN KEY (performed_by) REFERENCES "user" (id);

ALTER TABLE cell
    ADD CONSTRAINT FK_CELL_ON_FLOOR FOREIGN KEY (floor_id) REFERENCES floor (id);

ALTER TABLE cross_docking
    ADD CONSTRAINT FK_CROSS_DOCKING_ON_INBOUND_RECEIPT FOREIGN KEY (inbound_receipt_id) REFERENCES inbound_receipt (id);

ALTER TABLE cross_docking
    ADD CONSTRAINT FK_CROSS_DOCKING_ON_OUTBOUND_SHIPMENT FOREIGN KEY (outbound_shipment_id) REFERENCES outbound_shipment (id);

ALTER TABLE floor
    ADD CONSTRAINT FK_FLOOR_ON_RACK FOREIGN KEY (rack_id) REFERENCES racks (id);

ALTER TABLE inbound_receipt
    ADD CONSTRAINT FK_INBOUND_RECEIPT_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE inbound_receipt
    ADD CONSTRAINT FK_INBOUND_RECEIPT_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES "user" (id);

ALTER TABLE inventory_audit
    ADD CONSTRAINT FK_INVENTORY_AUDIT_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE inventory_snapshot
    ADD CONSTRAINT FK_INVENTORY_SNAPSHOT_ON_LOCATION FOREIGN KEY (location_id) REFERENCES location (id);

ALTER TABLE inventory_snapshot
    ADD CONSTRAINT FK_INVENTORY_SNAPSHOT_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE location
    ADD CONSTRAINT FK_LOCATION_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE outbound_shipment
    ADD CONSTRAINT FK_OUTBOUND_SHIPMENT_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES "user" (id);

ALTER TABLE outbound_shipment
    ADD CONSTRAINT FK_OUTBOUND_SHIPMENT_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE product_meta_data
    ADD CONSTRAINT FK_PRODUCT_META_DATA_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE product_return
    ADD CONSTRAINT FK_PRODUCT_RETURN_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE racks
    ADD CONSTRAINT FK_RACKS_ON_SECTOR FOREIGN KEY (sector_id) REFERENCES sector (id);

ALTER TABLE sector_characteristic
    ADD CONSTRAINT FK_SECTOR_CHARACTERISTIC_ON_CHARACTERISTIC FOREIGN KEY (characteristic_id) REFERENCES characteristic (id);

ALTER TABLE sector_characteristic
    ADD CONSTRAINT FK_SECTOR_CHARACTERISTIC_ON_SECTOR FOREIGN KEY (sector_id) REFERENCES sector (id);

ALTER TABLE sector
    ADD CONSTRAINT FK_SECTOR_ON_WAREHOUSE FOREIGN KEY (warehouse_id) REFERENCES warehouse (id);

ALTER TABLE task
    ADD CONSTRAINT FK_TASK_ON_ASSIGNED_TO FOREIGN KEY (assigned_to) REFERENCES "user" (id);

ALTER TABLE task
    ADD CONSTRAINT FK_TASK_ON_LOCATION FOREIGN KEY (location_id) REFERENCES location (id);

ALTER TABLE task
    ADD CONSTRAINT FK_TASK_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE transport_label
    ADD CONSTRAINT FK_TRANSPORT_LABEL_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE transport_label
    ADD CONSTRAINT FK_TRANSPORT_LABEL_ON_SHIPMENT FOREIGN KEY (shipment_id) REFERENCES outbound_shipment (id);

ALTER TABLE warehouse
    ADD CONSTRAINT FK_WAREHOUSE_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);