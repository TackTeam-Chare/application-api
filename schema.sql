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
