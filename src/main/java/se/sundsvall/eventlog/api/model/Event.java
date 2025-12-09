package se.sundsvall.eventlog.api.model;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;
import se.sundsvall.dept44.common.validators.annotation.ValidUuid;

@Schema(description = "Event model")
public class Event {

	@Schema(description = "Unique identifier", examples = "fbe2fb67-005c-4f26-990f-1c95b5f6933e", accessMode = READ_ONLY)
	private String logKey;

	@Schema(implementation = EventType.class, enumAsRef = true)
	@NotNull
	private EventType type;

	@Schema(description = "Municipality ID", examples = "2281", accessMode = READ_ONLY)
	private String municipalityId;

	@Schema(description = "Event description", examples = "Request saved in database")
	@NotBlank
	private String message;

	@Schema(description = "Date when event can be scheduled for delete. 'null' means never", examples = "2030-10-31T01:30:00.000+02:00", nullable = true)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime expires;

	@Schema(description = "Service that created event", examples = "SupportManagement")
	@NotBlank
	private String owner;

	@Schema(description = "Timestamp when the event was created", examples = "2000-10-31T01:30:00.000+02:00", accessMode = Schema.AccessMode.READ_ONLY)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime created;

	@Schema(description = "Historical external reference to an immutable snapshot of data", examples = "fbe2fb67-005c-4f26-990f-1c95b5f6933e", nullable = true)
	@ValidUuid(nullable = true)
	private String historyReference;

	@Schema(description = "Source which the event refers to", examples = "errand", nullable = true)
	private String sourceType;

	@ArraySchema(schema = @Schema(implementation = Metadata.class))
	private List<Metadata> metadata;

	public static Event create() {
		return new Event();
	}

	public String getLogKey() {
		return logKey;
	}

	public void setLogKey(final String logKey) {
		this.logKey = logKey;
	}

	public Event withLogKey(final String logKey) {
		this.logKey = logKey;
		return this;
	}

	public EventType getType() {
		return type;
	}

	public void setType(final EventType type) {
		this.type = type;
	}

	public Event withType(final EventType type) {
		this.type = type;
		return this;
	}

	public String getMunicipalityId() {
		return municipalityId;
	}

	public void setMunicipalityId(final String municipalityId) {
		this.municipalityId = municipalityId;
	}

	public Event withMunicipalityId(final String municipalityId) {
		this.municipalityId = municipalityId;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public Event withMessage(final String message) {
		this.message = message;
		return this;
	}

	public OffsetDateTime getExpires() {
		return expires;
	}

	public void setExpires(final OffsetDateTime expires) {
		this.expires = expires;
	}

	public Event withExpires(final OffsetDateTime expires) {
		this.expires = expires;
		return this;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(final String owner) {
		this.owner = owner;
	}

	public Event withOwner(final String owner) {
		this.owner = owner;
		return this;
	}

	public OffsetDateTime getCreated() {
		return created;
	}

	public void setCreated(final OffsetDateTime created) {
		this.created = created;
	}

	public Event withCreated(final OffsetDateTime created) {
		this.created = created;
		return this;
	}

	public String getHistoryReference() {
		return historyReference;
	}

	public void setHistoryReference(final String historyReference) {
		this.historyReference = historyReference;
	}

	public Event withHistoryReference(final String historyReference) {
		this.historyReference = historyReference;
		return this;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(final String sourceType) {
		this.sourceType = sourceType;
	}

	public Event withSourceType(final String sourceType) {
		this.sourceType = sourceType;
		return this;
	}

	public List<Metadata> getMetadata() {
		return metadata;
	}

	public void setMetadata(final List<Metadata> metadata) {
		this.metadata = metadata;
	}

	public Event withMetadata(final List<Metadata> metadata) {
		this.metadata = metadata;
		return this;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		final Event event = (Event) o;
		return Objects.equals(logKey, event.logKey) && type == event.type && Objects.equals(municipalityId, event.municipalityId) && Objects.equals(message, event.message) && Objects.equals(expires, event.expires)
			&& Objects.equals(owner, event.owner) && Objects.equals(created, event.created) && Objects.equals(historyReference, event.historyReference) && Objects.equals(sourceType, event.sourceType)
			&& Objects.equals(metadata, event.metadata);
	}

	@Override
	public int hashCode() {
		return Objects.hash(logKey, type, municipalityId, message, expires, owner, created, historyReference, sourceType, metadata);
	}

	@Override
	public String toString() {
		return "Event{" +
			"logKey='" + logKey + '\'' +
			", type=" + type +
			", municipalityId='" + municipalityId + '\'' +
			", message='" + message + '\'' +
			", expires=" + expires +
			", owner='" + owner + '\'' +
			", created=" + created +
			", historyReference='" + historyReference + '\'' +
			", sourceType='" + sourceType + '\'' +
			", metadata=" + metadata +
			'}';
	}
}
