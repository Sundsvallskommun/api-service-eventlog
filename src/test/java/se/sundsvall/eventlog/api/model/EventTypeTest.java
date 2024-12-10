package se.sundsvall.eventlog.api.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static se.sundsvall.eventlog.api.model.EventType.ACCESS;
import static se.sundsvall.eventlog.api.model.EventType.CANCEL;
import static se.sundsvall.eventlog.api.model.EventType.CREATE;
import static se.sundsvall.eventlog.api.model.EventType.DELETE;
import static se.sundsvall.eventlog.api.model.EventType.DROP;
import static se.sundsvall.eventlog.api.model.EventType.EXECUTE;
import static se.sundsvall.eventlog.api.model.EventType.READ;
import static se.sundsvall.eventlog.api.model.EventType.UPDATE;

import org.junit.jupiter.api.Test;

class EventTypeTest {

	@Test
	void enums() {
		assertThat(EventType.values()).containsExactly(CREATE, READ, UPDATE, DELETE, ACCESS, EXECUTE, CANCEL, DROP);
	}
}
