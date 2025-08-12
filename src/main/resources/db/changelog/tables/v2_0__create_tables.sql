CREATE SEQUENCE IF NOT EXISTS inbound_receipt_meta_data_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS inbound_receipt_meta_data (id BIGINT NOT NULL,
                                        created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                        updated_at TIMESTAMP WITHOUT TIME ZONE,
                                        status VARCHAR(255) NOT NULL,
                                        created_by VARCHAR(255),
                                        updated_by VARCHAR(255),
                                        inbound_receipt_id BIGINT,
                                        batch_number VARCHAR(255),
                                        manufacture_date date,
                                        expiry_date date,
                                        serial_number VARCHAR(255),
                                        CONSTRAINT pk_inbound_receipt_meta_data
                                        PRIMARY KEY (id));

ALTER TABLE inbound_receipt_meta_data ADD CONSTRAINT FK_INBOUND_RECEIPT_META_DATA_ON_INBOUND_RECEIPT FOREIGN KEY (inbound_receipt_id) REFERENCES inbound_receipt (id);
