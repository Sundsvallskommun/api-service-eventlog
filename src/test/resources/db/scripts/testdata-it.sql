-- Event
INSERT INTO event (id, created, expires, history_reference, log_key, message, owner, source_type, type)
VALUES	('922f95e3-608b-4e3c-ae22-f11fb849799a', '2023-05-08 08:00:00', '2035-05-09 12:00:00', 'e608841a-7045-40a3-b1c8-7f3a076a0caa', '1d968db4-1ced-4144-a167-357862774116', 'message 1 logkey 1', 'owner_1', 'source_type_1', 'CREATE'),
        ('622cfd45-3f15-427a-9c46-72f321f15888', '2023-05-08 09:00:00', '2035-05-09 13:00:00', '29ed393a-d315-47b7-89a2-eca74a8166a7', '1d968db4-1ced-4144-a167-357862774116', 'message 2 logkey 1', 'owner_1', 'source_type_2', 'DELETE'),
		('e99692d1-efcb-4568-b817-5fa2bbb2971d', '2023-04-08 10:00:00', '2035-05-09 14:00:00', 'eda8a6bd-a0ca-4fb3-9d6a-fecdda4cb105', '836fcf4f-d24e-42a4-9a8f-170b75dfc158', 'message 1 logkey 2', 'owner_2', 'source_type_3', 'UPDATE');

-- EventMetadata
INSERT INTO event_metadata (event_id, `key`, value)
VALUES	('922f95e3-608b-4e3c-ae22-f11fb849799a', 'metadata_key_message-1_logkey-1', 'metadata_value_message-1_logkey-1'),
		('622cfd45-3f15-427a-9c46-72f321f15888', 'metadata_key_message-2_logkey-1', 'metadata_value_message-2_logkey-1'),
		('e99692d1-efcb-4568-b817-5fa2bbb2971d', 'metadata_key_message-1_logkey-2', 'metadata_value_message-1_logkey-2');