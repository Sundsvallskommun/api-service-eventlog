alter table if exists event
    add column sub_type varchar(255) after type;

alter table if exists event
    add column transaction_id varchar(255) after sub_type;

alter table if exists event
    add column details longtext after message;
