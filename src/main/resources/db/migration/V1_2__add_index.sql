CREATE INDEX IF NOT EXISTS idx_municipality_id_created on event (municipality_id, created);
CREATE INDEX IF NOT EXISTS idx_municipality_id_owner_type_created on event (municipality_id, owner, type, created);
