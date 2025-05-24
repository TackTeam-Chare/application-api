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

    create table application (
        is_vip bit,
        created_on datetime2(6),
        modified_on datetime2(6),
        app_id uniqueidentifier not null,
        app_status varchar(255),
        campaign_code varchar(255),
        card_type varchar(255),
        product_program varchar(255),
        product_type varchar(255),
        primary key (app_id)
    );

    create table application (
        is_vip bit,
        created_on datetime2(6),
        modified_on datetime2(6),
        app_id uniqueidentifier not null,
        app_status varchar(255),
        campaign_code varchar(255),
        card_type varchar(255),
        product_program varchar(255),
        product_type varchar(255),
        primary key (app_id)
    );

    create table application (
        is_vip bit,
        created_on datetime2(6),
        modified_on datetime2(6),
        app_id uniqueidentifier not null,
        app_status varchar(255),
        campaign_code varchar(255),
        card_type varchar(255),
        product_program varchar(255),
        product_type varchar(255),
        primary key (app_id)
    );

    create table application (
        is_vip bit,
        created_on datetime2(6),
        modified_on datetime2(6),
        app_id uniqueidentifier not null,
        app_status varchar(255),
        campaign_code varchar(255),
        card_type varchar(255),
        product_program varchar(255),
        product_type varchar(255),
        primary key (app_id)
    );

    create table application (
        is_vip bit,
        created_on datetime2(6),
        modified_on datetime2(6),
        app_id uniqueidentifier not null,
        app_status varchar(255),
        campaign_code varchar(255),
        card_type varchar(255),
        product_program varchar(255),
        product_type varchar(255),
        primary key (app_id)
    );
