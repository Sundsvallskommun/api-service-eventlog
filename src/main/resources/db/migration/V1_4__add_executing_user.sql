alter table if exists event
    add column executing_user_type varchar(255) after request_group_id;

alter table if exists event
    add column executing_user_value varchar(255) after executing_user_type;
