package se.sundsvall.eventlog.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true, description = """
	## CRUD events:
	* `CREATE`
	* `READ`
	* `UPDATE`
	* `DELETE`
	## System events:
	* `ACCESS` - login, logout, authorization, throttling
	* `EXECUTE` - send, queue, schedule, transfer, move
	* `CANCEL` - terminate, abort, decommission, kill
	* `DROP` - skip, omit, ignore, disregard
	""")
public enum EventType {
	CREATE,
	READ,
	UPDATE,
	DELETE,
	ACCESS,
	EXECUTE,
	CANCEL,
	DROP
}
