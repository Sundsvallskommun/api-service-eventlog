CREATE INDEX IF NOT EXISTS municipality_id_created_index on event (municipality_id, created);
CREATE INDEX IF NOT EXISTS municipality_id_owner_type_created_index on event (municipality_id, owner, type, created);
