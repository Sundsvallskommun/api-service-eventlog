-- Event
INSERT INTO event (id, created, expires, history_reference, log_key, message, owner, source_type, type)
VALUES	('c866968a-06e6-4b70-a813-54eda6a7fe41', '2023-04-24 12:00:00', '2023-05-01 12:00:00', 'history_ref_1', 'b1f4e8cf-7e61-4e87-b3c4-ffb443929553', 'message 1', 'owner_1', 'source_type_1', 'type_1'),
		('91ac3903-374c-42b3-ac6e-c7c924b0e25b', '2023-04-25 12:00:00', '2023-05-02 12:00:00', 'history_ref_2', '1f6ca00d-9418-481e-9bf4-087c954caf53', 'message 2', 'owner_2', 'source_type_2', 'type_2');
		
-- EventMetadata
INSERT INTO event_metadata (event_id, `key`, value)
VALUES	('c866968a-06e6-4b70-a813-54eda6a7fe41', 'metadata_key_1-1', 'metadata_value_1-1'),
		('c866968a-06e6-4b70-a813-54eda6a7fe41', 'metadata_key_1-2', 'metadata_value_1-2'),
		('c866968a-06e6-4b70-a813-54eda6a7fe41', 'metadata_key_1-3', 'metadata_value_1-3'),
		('91ac3903-374c-42b3-ac6e-c7c924b0e25b', 'metadata_key_2-1', 'metadata_value_2-1');