package se.sundsvall.eventlog.integration.db.model;

import static java.time.OffsetDateTime.now;
import static java.time.ZoneId.systemDefault;
import static java.time.temporal.ChronoUnit.MILLIS;
import static org.hibernate.annotations.TimeZoneStorageType.NORMALIZE;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import org.hibernate.Length;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "event",
	indexes = {
		@Index(name = "event_log_key_index", columnList = "log_key"),
		@Index(name = "event_municipality_id_index", columnList = "municipality_id")
	})
public class EventEntity {

	@Id
	@UuidGenerator
	@Column(name = "id")
	private String id;

	@Column(name = "municipality_id")
	private String municipalityId;

	@Column(name = "log_key")
	private String logKey;

	@Column(name = "type")
	private String type;

	@Column(name = "owner")
	private String owner;

	@Column(name = "source_type")
	private String sourceType;

	@Column(name = "message", length = Length.LONG32)
	private String message;

	@Column(name = "history_reference")
	private String historyReference;

	@Column(name = "created")
	@TimeZoneStorage(NORMALIZE)
	private OffsetDateTime created;

	@Column(name = "expires")
	@TimeZoneStorage(NORMALIZE)
	private OffsetDateTime expires;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "event_metadata",
		joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_event_meta_data")),
		indexes = {
			@Index(name = "event_metadata_key_index", columnList = "`key`")
		})
	private List<EventMetadata> metadata;

	public static EventEntity create() {
		return new EventEntity();
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public EventEntity withId(final String id) {
		this.id = id;
		return this;
	}

	public String getMunicipalityId() {
		return municipalityId;
	}

	public void setMunicipalityId(String municipalityId) {
		this.municipalityId = municipalityId;
	}

	public EventEntity withMunicipalityId(String municipalityId) {
		this.municipalityId = municipalityId;
		return this;
	}

	public String getLogKey() {
		return logKey;
	}

	public void setLogKey(final String logKey) {
		this.logKey = logKey;
	}

	public EventEntity withLogKey(final String logKey) {
		this.logKey = logKey;
		return this;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public EventEntity withType(final String type) {
		this.type = type;
		return this;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(final String owner) {
		this.owner = owner;
	}

	public EventEntity withOwner(final String owner) {
		this.owner = owner;
		return this;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(final String sourceType) {
		this.sourceType = sourceType;
	}

	public EventEntity withSourceType(final String sourceType) {
		this.sourceType = sourceType;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public EventEntity withMessage(final String message) {
		this.message = message;
		return this;
	}

	public String getHistoryReference() {
		return historyReference;
	}

	public void setHistoryReference(final String historyReference) {
		this.historyReference = historyReference;
	}

	public EventEntity withHistoryReference(final String historyReference) {
		this.historyReference = historyReference;
		return this;
	}

	public OffsetDateTime getCreated() {
		return created;
	}

	public void setCreated(final OffsetDateTime created) {
		this.created = created;
	}

	public EventEntity withCreated(final OffsetDateTime created) {
		this.created = created;
		return this;
	}

	public OffsetDateTime getExpires() {
		return expires;
	}

	public void setExpires(final OffsetDateTime expires) {
		this.expires = expires;
	}

	public EventEntity withExpires(final OffsetDateTime expires) {
		this.expires = expires;
		return this;
	}

	public List<EventMetadata> getMetadata() {
		return metadata;
	}

	public void setMetadata(final List<EventMetadata> metadata) {
		this.metadata = metadata;
	}

	public EventEntity withMetadata(final List<EventMetadata> metadata) {
		this.metadata = metadata;
		return this;
	}

	@PrePersist
	void onCreate() {
		created = now(systemDefault()).truncatedTo(MILLIS);
	}

	@Override
	public int hashCode() {
		return Objects.hash(created, expires, historyReference, id, logKey, message, metadata, municipalityId, owner, sourceType, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (!(obj instanceof final EventEntity other)) { return false; }
		return Objects.equals(created, other.created) && Objects.equals(expires, other.expires) && Objects.equals(historyReference, other.historyReference) && Objects.equals(id, other.id) && Objects.equals(logKey, other.logKey) && Objects.equals(message,
			other.message) && Objects.equals(metadata, other.metadata) && Objects.equals(municipalityId, other.municipalityId) && Objects.equals(owner, other.owner) && Objects.equals(sourceType, other.sourceType) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("EventEntity [id=").append(id).append(", municipalityId=").append(municipalityId).append(", logKey=").append(logKey).append(", type=").append(type).append(", owner=").append(owner).append(", sourceType=").append(sourceType).append(
			", message=").append(message).append(", historyReference=").append(historyReference).append(", created=").append(created).append(", expires=").append(expires).append(", metadata=").append(metadata).append("]");
		return builder.toString();
	}
}
