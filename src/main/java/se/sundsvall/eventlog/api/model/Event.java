package se.sundsvall.eventlog.api.model;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import se.sundsvall.dept44.common.validators.annotation.ValidUuid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Schema(description = "Event model")
public class Event {

	@Schema(implementation = EventType.class, enumAsRef = true)
	@NotNull
	private EventType eventType;

	@Schema(description = "Event description", example = "Request saved in database")
	@NotBlank
	private String message;

	@Schema(description = "Date when event can be scheduled for delete. 'null' means never", example = "2030-10-31T01:30:00.000+02:00", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime expireDate;

	@Schema(description = "Service that created event", example = "SupportManagement")
	@NotBlank
	private String owner;

	@Schema(description = "Timestamp when the event was created", example = "2000-10-31T01:30:00.000+02:00", accessMode = Schema.AccessMode.READ_ONLY)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime timestamp;

	@Schema(description = "External reference for event", example = "fbe2fb67-005c-4f26-990f-1c95b5f6933e", nullable = true)
	@ValidUuid(nullable = true)
	private String historyReference;

	@Schema(description = "Source which the event refers to", example = "errand", nullable = true)
	private String sourceType;

	@ArraySchema(schema = @Schema(implementation = Metadata.class))
	private List<Metadata> metadata;

	public static Event create() {
		return new Event();
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public Event withEventType(EventType eventType) {
		this.eventType = eventType;
		return this;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Event withMessage(String message) {
		this.message = message;
		return this;
	}

	public OffsetDateTime getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(OffsetDateTime expireDate) {
		this.expireDate = expireDate;
	}

	public Event withExpireDate(OffsetDateTime expireDate) {
		this.expireDate = expireDate;
		return this;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Event withOwner(String owner) {
		this.owner = owner;
		return this;
	}

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Event withTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
		return this;
	}
	public String getHistoryReference() {
		return historyReference;
	}

	public void setHistoryReference(String historyReference) {
		this.historyReference = historyReference;
	}

	public Event withHistoryReference(String historyReference) {
		this.historyReference = historyReference;
		return this;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public Event withSourceType(String sourceType) {
		this.sourceType = sourceType;
		return this;
	}

	public List<Metadata> getMetadata() {
		return metadata;
	}

	public void setMetadata(List<Metadata> metadata) {
		this.metadata = metadata;
	}

	public Event withMetadata(List<Metadata> metadata) {
		this.metadata = metadata;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Event event = (Event) o;
		return eventType == event.eventType && Objects.equals(message, event.message) && Objects.equals(expireDate, event.expireDate) && Objects.equals(owner, event.owner) && Objects.equals(timestamp, event.timestamp) && Objects.equals(historyReference, event.historyReference) && Objects.equals(sourceType, event.sourceType) && Objects.equals(metadata, event.metadata);
	}

	@Override
	public int hashCode() {
		return Objects.hash(eventType, message, expireDate, owner, timestamp, historyReference, sourceType, metadata);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Event{");
		sb.append("eventType=").append(eventType);
		sb.append(", message='").append(message).append('\'');
		sb.append(", expireDate=").append(expireDate);
		sb.append(", owner='").append(owner).append('\'');
		sb.append(", timestamp=").append(timestamp);
		sb.append(", historyReference='").append(historyReference).append('\'');
		sb.append(", sourceType='").append(sourceType).append('\'');
		sb.append(", metadata=").append(metadata);
		sb.append('}');
		return sb.toString();
	}
}
