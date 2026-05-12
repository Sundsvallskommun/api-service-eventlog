package se.sundsvall.eventlog.api.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.eventlog.api.model.ExecutingUserType.AD_USER;
import static se.sundsvall.eventlog.api.model.ExecutingUserType.PARTY_ID;

class ExecutingUserTypeTest {

	@Test
	void enums() {
		assertThat(ExecutingUserType.values()).containsExactly(AD_USER, PARTY_ID);
	}
}
