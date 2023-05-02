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
	private EventType type;

	@Schema(description = "Event description", example = "Request saved in database")
	@NotBlank
	private String message;

	@Schema(description = "Date when event can be scheduled for delete. 'null' means never", example = "2030-10-31T01:30:00.000+02:00", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime expires;

	@Schema(description = "Service that created event", example = "SupportManagement")
	@NotBlank
	private String owner;

	@Schema(description = "Timestamp when the event was created", example = "2000-10-31T01:30:00.000+02:00", accessMode = Schema.AccessMode.READ_ONLY)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime created;

	@Schema(description = "Historical external reference to an immutable snapshot of data", example = "fbe2fb67-005c-4f26-990f-1c95b5f6933e", nullable = true)
	@ValidUuid(nullable = true)
	private String historyReference;

	@Schema(description = "Source which the event refers to", example = "errand", nullable = true)
	private String sourceType;

	@ArraySchema(schema = @Schema(implementation = Metadata.class))
	private List<Metadata> metadata;

	public static Event create() {
		return new Event();
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Event withType(EventType type) {
		this.type = type;
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

	public OffsetDateTime getExpires() {
		return expires;
	}

	public void setExpires(OffsetDateTime expires) {
		this.expires = expires;
	}

	public Event withExpires(OffsetDateTime expires) {
		this.expires = expires;
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

	public OffsetDateTime getCreated() {
		return created;
	}

	public void setCreated(OffsetDateTime created) {
		this.created = created;
	}

	public Event withCreated(OffsetDateTime created) {
		this.created = created;
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
		return type == event.type && Objects.equals(message, event.message) && Objects.equals(expires, event.expires) && Objects.equals(owner, event.owner) && Objects.equals(created, event.created) && Objects.equals(historyReference, event.historyReference) && Objects.equals(sourceType, event.sourceType) && Objects.equals(metadata, event.metadata);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, message, expires, owner, created, historyReference, sourceType, metadata);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Event{");
		sb.append("type=").append(type);
		sb.append(", message='").append(message).append('\'');
		sb.append(", expires=").append(expires);
		sb.append(", owner='").append(owner).append('\'');
		sb.append(", created=").append(created);
		sb.append(", historyReference='").append(historyReference).append('\'');
		sb.append(", sourceType='").append(sourceType).append('\'');
		sb.append(", metadata=").append(metadata);
		sb.append('}');
		return sb.toString();
	}
}
