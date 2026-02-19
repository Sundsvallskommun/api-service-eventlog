package se.sundsvall.eventlog.service.mapper;

import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.sundsvall.eventlog.api.model.Event;
import se.sundsvall.eventlog.api.model.EventType;
import se.sundsvall.eventlog.api.model.Metadata;
import se.sundsvall.eventlog.integration.db.model.EventEntity;
import se.sundsvall.eventlog.integration.db.model.EventMetadata;

import static org.assertj.core.api.Assertions.assertThat;

class EventMapperTest {

	private static final String LOG_KEY = "logKey";
	private static final EventType EVENT_TYPE = EventType.CREATE;
	private static final String MESSAGE = "message";
	private static final String MUNICIPALITY_ID = "municipalityId";
	private static final OffsetDateTime EXPIRE_DATE = OffsetDateTime.now().plusYears(10);
	private static final String OWNER = "owner";
	private static final OffsetDateTime TIMESTAMP = OffsetDateTime.now();
	private static final String HISTORY_REFERENCE = "historyReference";
	private static final String SOURCE_TYPE = "sourceType";
	private static final String KEY = "key";
	private static final String VALUE = "value";

	@Test
	void toEventEntity() {
		final var event = Event.create()
			.withType(EVENT_TYPE)
			.withMessage(MESSAGE)
			.withExpires(EXPIRE_DATE)
			.withOwner(OWNER)
			.withCreated(TIMESTAMP)
			.withHistoryReference(HISTORY_REFERENCE)
			.withSourceType(SOURCE_TYPE)
			.withMetadata(List.of(Metadata.create().withKey(KEY).withValue(VALUE)));

		final var eventEntity = EventMapper.toEventEntity(MUNICIPALITY_ID, LOG_KEY, event);

		assertThat(eventEntity.getLogKey()).isEqualTo(LOG_KEY);
		assertThat(eventEntity.getType()).isEqualTo(EVENT_TYPE.toString());
		assertThat(eventEntity.getMessage()).isEqualTo(MESSAGE);
		assertThat(eventEntity.getMunicipalityId()).isEqualTo(MUNICIPALITY_ID);
		assertThat(eventEntity.getExpires()).isAtSameInstantAs(EXPIRE_DATE);
		assertThat(eventEntity.getOwner()).isEqualTo(OWNER);
		assertThat(eventEntity.getCreated()).isNull();
		assertThat(eventEntity.getHistoryReference()).isEqualTo(HISTORY_REFERENCE);
		assertThat(eventEntity.getSourceType()).isEqualTo(SOURCE_TYPE);
		assertThat(eventEntity.getMetadata()).containsExactly(EventMetadata.create().withKey(KEY).withValue(VALUE));
	}

	@Test
	void toEvent() {
		final var eventEntity = EventEntity.create()
			.withLogKey(LOG_KEY)
			.withType(EVENT_TYPE.toString())
			.withMessage(MESSAGE)
			.withMunicipalityId(MUNICIPALITY_ID)
			.withExpires(EXPIRE_DATE)
			.withOwner(OWNER)
			.withCreated(TIMESTAMP)
			.withHistoryReference(HISTORY_REFERENCE)
			.withSourceType(SOURCE_TYPE)
			.withMetadata(List.of(EventMetadata.create().withKey(KEY).withValue(VALUE)));

		final var event = EventMapper.toEvent(eventEntity);

		assertThat(event.getLogKey()).isEqualTo(LOG_KEY);
		assertThat(event.getType()).isEqualTo(EVENT_TYPE);
		assertThat(event.getMessage()).isEqualTo(MESSAGE);
		assertThat(event.getMunicipalityId()).isEqualTo(MUNICIPALITY_ID);
		assertThat(event.getExpires()).isAtSameInstantAs(EXPIRE_DATE);
		assertThat(event.getOwner()).isEqualTo(OWNER);
		assertThat(event.getCreated()).isAtSameInstantAs(TIMESTAMP);
		assertThat(event.getHistoryReference()).isEqualTo(HISTORY_REFERENCE);
		assertThat(event.getSourceType()).isEqualTo(SOURCE_TYPE);
		assertThat(event.getMetadata()).containsExactly(Metadata.create().withKey(KEY).withValue(VALUE));
	}
}
