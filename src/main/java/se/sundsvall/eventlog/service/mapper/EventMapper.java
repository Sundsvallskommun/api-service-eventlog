package se.sundsvall.eventlog.service.mapper;

import se.sundsvall.eventlog.api.model.Event;
import se.sundsvall.eventlog.api.model.EventType;
import se.sundsvall.eventlog.api.model.Metadata;
import se.sundsvall.eventlog.integration.db.model.EventEntity;
import se.sundsvall.eventlog.integration.db.model.EventMetadata;

public class EventMapper {

	private EventMapper() {}

	public static EventEntity toEventEntity(String logKey, Event event) {
		return EventEntity.create()
			.withLogKey(logKey)
			.withType(event.getType().toString())
			.withMessage(event.getMessage())
			.withExpires(event.getExpires())
			.withOwner(event.getOwner())
			.withHistoryReference(event.getHistoryReference())
			.withSourceType(event.getSourceType())
			.withMetadata(event.getMetadata().stream().map(EventMapper::toEventMetadata).toList());
	}

	public static EventMetadata toEventMetadata(Metadata metadata) {
		return EventMetadata.create().withKey(metadata.getKey()).withValue(metadata.getValue());
	}

	public static Event toEvent(EventEntity eventEntity) {
		return Event.create()
			.withType(EventType.valueOf(eventEntity.getType()))
			.withMessage(eventEntity.getMessage())
			.withExpires(eventEntity.getExpires())
			.withOwner(eventEntity.getOwner())
			.withCreated(eventEntity.getCreated())
			.withHistoryReference(eventEntity.getHistoryReference())
			.withSourceType(eventEntity.getSourceType())
			.withMetadata(eventEntity.getMetadata().stream().map(EventMapper::toMetadata).toList());
	}

	public static Metadata toMetadata(EventMetadata eventMetadata) {
		return Metadata.create().withKey(eventMetadata.getKey()).withValue(eventMetadata.getValue());
	}
}
