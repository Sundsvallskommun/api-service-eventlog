package se.sundsvall.eventlog.service.mapper;

import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.sundsvall.eventlog.api.model.Event;
import se.sundsvall.eventlog.api.model.EventType;
import se.sundsvall.eventlog.api.model.ExecutingUser;
import se.sundsvall.eventlog.api.model.ExecutingUserType;
import se.sundsvall.eventlog.api.model.Metadata;
import se.sundsvall.eventlog.integration.db.model.EventEntity;
import se.sundsvall.eventlog.integration.db.model.EventMetadata;

import static org.assertj.core.api.Assertions.assertThat;

class EventMapperTest {

	private static final String ID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
	private static final String LOG_KEY = "logKey";
	private static final EventType EVENT_TYPE = EventType.CREATE;
	private static final String SUB_TYPE = "ATTACHMENT";
	private static final String REQUEST_GROUP_ID = "f47ac10b-58cc-4372-a567-0e02b2c3d479";
	private static final String MESSAGE = "message";
	private static final String DETAILS = "Filnamn 'abc.pdf'";
	private static final String MUNICIPALITY_ID = "municipalityId";
	private static final OffsetDateTime EXPIRE_DATE = OffsetDateTime.now().plusYears(10);
	private static final String OWNER = "owner";
	private static final OffsetDateTime TIMESTAMP = OffsetDateTime.now();
	private static final String HISTORY_REFERENCE = "historyReference";
	private static final String SOURCE_TYPE = "sourceType";
	private static final String KEY = "key";
	private static final String VALUE = "value";
	private static final ExecutingUserType EXECUTING_USER_TYPE = ExecutingUserType.AD_USER;
	private static final String EXECUTING_USER_VALUE = "john.doe";

	@Test
	void toEventEntity() {
		final var event = Event.create()
			.withType(EVENT_TYPE)
			.withSubType(SUB_TYPE)
			.withRequestGroupId(REQUEST_GROUP_ID)
			.withExecutingUser(ExecutingUser.create().withType(EXECUTING_USER_TYPE).withValue(EXECUTING_USER_VALUE))
			.withMessage(MESSAGE)
			.withDetails(DETAILS)
			.withExpires(EXPIRE_DATE)
			.withOwner(OWNER)
			.withCreated(TIMESTAMP)
			.withHistoryReference(HISTORY_REFERENCE)
			.withSourceType(SOURCE_TYPE)
			.withMetadata(List.of(Metadata.create().withKey(KEY).withValue(VALUE)));

		final var eventEntity = EventMapper.toEventEntity(MUNICIPALITY_ID, LOG_KEY, event);

		assertThat(eventEntity.getLogKey()).isEqualTo(LOG_KEY);
		assertThat(eventEntity.getType()).isEqualTo(EVENT_TYPE.toString());
		assertThat(eventEntity.getSubType()).isEqualTo(SUB_TYPE);
		assertThat(eventEntity.getRequestGroupId()).isEqualTo(REQUEST_GROUP_ID);
		assertThat(eventEntity.getExecutingUserType()).isEqualTo(EXECUTING_USER_TYPE.toString());
		assertThat(eventEntity.getExecutingUserValue()).isEqualTo(EXECUTING_USER_VALUE);
		assertThat(eventEntity.getMessage()).isEqualTo(MESSAGE);
		assertThat(eventEntity.getDetails()).isEqualTo(DETAILS);
		assertThat(eventEntity.getMunicipalityId()).isEqualTo(MUNICIPALITY_ID);
		assertThat(eventEntity.getExpires()).isAtSameInstantAs(EXPIRE_DATE);
		assertThat(eventEntity.getOwner()).isEqualTo(OWNER);
		assertThat(eventEntity.getCreated()).isNull();
		assertThat(eventEntity.getHistoryReference()).isEqualTo(HISTORY_REFERENCE);
		assertThat(eventEntity.getSourceType()).isEqualTo(SOURCE_TYPE);
		assertThat(eventEntity.getMetadata()).containsExactly(EventMetadata.create().withKey(KEY).withValue(VALUE));
	}

	@Test
	void toEventEntityWithNullExecutingUser() {
		final var event = Event.create()
			.withType(EVENT_TYPE)
			.withMessage(MESSAGE)
			.withOwner(OWNER)
			.withMetadata(List.of(Metadata.create().withKey(KEY).withValue(VALUE)));

		final var eventEntity = EventMapper.toEventEntity(MUNICIPALITY_ID, LOG_KEY, event);

		assertThat(eventEntity.getExecutingUserType()).isNull();
		assertThat(eventEntity.getExecutingUserValue()).isNull();
	}

	@Test
	void toEvent() {
		final var eventEntity = EventEntity.create()
			.withId(ID)
			.withLogKey(LOG_KEY)
			.withType(EVENT_TYPE.toString())
			.withSubType(SUB_TYPE)
			.withRequestGroupId(REQUEST_GROUP_ID)
			.withExecutingUserType(EXECUTING_USER_TYPE.toString())
			.withExecutingUserValue(EXECUTING_USER_VALUE)
			.withMessage(MESSAGE)
			.withDetails(DETAILS)
			.withMunicipalityId(MUNICIPALITY_ID)
			.withExpires(EXPIRE_DATE)
			.withOwner(OWNER)
			.withCreated(TIMESTAMP)
			.withHistoryReference(HISTORY_REFERENCE)
			.withSourceType(SOURCE_TYPE)
			.withMetadata(List.of(EventMetadata.create().withKey(KEY).withValue(VALUE)));

		final var event = EventMapper.toEvent(eventEntity);

		assertThat(event.getId()).isEqualTo(ID);
		assertThat(event.getLogKey()).isEqualTo(LOG_KEY);
		assertThat(event.getType()).isEqualTo(EVENT_TYPE);
		assertThat(event.getSubType()).isEqualTo(SUB_TYPE);
		assertThat(event.getRequestGroupId()).isEqualTo(REQUEST_GROUP_ID);
		assertThat(event.getExecutingUser().getType()).isEqualTo(EXECUTING_USER_TYPE);
		assertThat(event.getExecutingUser().getValue()).isEqualTo(EXECUTING_USER_VALUE);
		assertThat(event.getMessage()).isEqualTo(MESSAGE);
		assertThat(event.getDetails()).isEqualTo(DETAILS);
		assertThat(event.getMunicipalityId()).isEqualTo(MUNICIPALITY_ID);
		assertThat(event.getExpires()).isAtSameInstantAs(EXPIRE_DATE);
		assertThat(event.getOwner()).isEqualTo(OWNER);
		assertThat(event.getCreated()).isAtSameInstantAs(TIMESTAMP);
		assertThat(event.getHistoryReference()).isEqualTo(HISTORY_REFERENCE);
		assertThat(event.getSourceType()).isEqualTo(SOURCE_TYPE);
		assertThat(event.getMetadata()).containsExactly(Metadata.create().withKey(KEY).withValue(VALUE));
	}

	@Test
	void toEventWithNullExecutingUser() {
		final var eventEntity = EventEntity.create()
			.withId(ID)
			.withLogKey(LOG_KEY)
			.withType(EVENT_TYPE.toString())
			.withMessage(MESSAGE)
			.withOwner(OWNER)
			.withMunicipalityId(MUNICIPALITY_ID)
			.withMetadata(List.of(EventMetadata.create().withKey(KEY).withValue(VALUE)));

		final var event = EventMapper.toEvent(eventEntity);

		assertThat(event.getExecutingUser()).isNull();
	}
}
