
alter table if exists event
   add column municipality_id varchar(255) AFTER id;
   
create index event_municipality_id_index
   on event (municipality_id);
