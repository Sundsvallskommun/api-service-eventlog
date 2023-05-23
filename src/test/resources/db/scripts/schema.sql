    create table event (
        created datetime(6),
        expires datetime(6),
        history_reference varchar(255),
        id varchar(255) not null,
        log_key varchar(255),
        owner varchar(255),
        source_type varchar(255),
        type varchar(255),
        message longtext,
        primary key (id)
    ) engine=InnoDB;

    create table event_metadata (
        event_id varchar(255) not null,
        `key` varchar(255),
        value varchar(255)
    ) engine=InnoDB;

    create index event_log_key_index 
       on event (log_key);

    create index event_metadata_key_index 
       on event_metadata (`key`);

    alter table if exists event_metadata 
       add constraint fk_event_meta_data 
       foreign key (event_id) 
       references event (id);
