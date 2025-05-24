CREATE TABLE application (
    app_id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
    app_status VARCHAR(255) NULL,
    campaign_code VARCHAR(255) NULL,
    card_type VARCHAR(255) NULL,
    created_on DATETIME2(6) NULL,
    is_vip BIT NULL,
    modified_on DATETIME2(6) NULL,
    product_program VARCHAR(255) NULL,
    product_type VARCHAR(255) NOT NULL
);
