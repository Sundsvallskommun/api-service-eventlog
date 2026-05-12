package se.sundsvall.eventlog.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true, description = """
	## Executing user types:
	* `AD_USER` - Active Directory user
	* `PARTY_ID` - Party identifier (UUID)
	""")
public enum ExecutingUserType {
	AD_USER,
	PARTY_ID
}
