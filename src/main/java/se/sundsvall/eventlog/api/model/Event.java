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

	@Schema(implementation = EventType.class, enumAsRef = true)
	@NotNull
	private EventType type;

	@Schema(description = "Municipality ID", example = "2281", accessMode = READ_ONLY)
	private String municipalityId;

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

	public String getMunicipalityId() {
		return municipalityId;
	}

	public void setMunicipalityId(String municipalityId) {
		this.municipalityId = municipalityId;
	}

	public Event withMunicipalityId(String municipalityId) {
		this.municipalityId = municipalityId;
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
	public int hashCode() {
		return Objects.hash(created, expires, historyReference, message, metadata, municipalityId, owner, sourceType, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (!(obj instanceof final Event other)) { return false; }
		return Objects.equals(created, other.created) && Objects.equals(expires, other.expires) && Objects.equals(historyReference, other.historyReference) && Objects.equals(message, other.message) && Objects.equals(metadata, other.metadata) && Objects
			.equals(municipalityId, other.municipalityId) && Objects.equals(owner, other.owner) && Objects.equals(sourceType, other.sourceType) && (type == other.type);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Event [type=").append(type).append(", municipalityId=").append(municipalityId).append(", message=").append(message).append(", expires=").append(expires).append(", owner=").append(owner).append(", created=").append(created).append(
			", historyReference=").append(historyReference).append(", sourceType=").append(sourceType).append(", metadata=").append(metadata).append("]");
		return builder.toString();
	}
}
