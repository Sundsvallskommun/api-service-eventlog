package se.sundsvall.eventlog.integration.db.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EventMetadata {

	@Column(name = "`key`")
	private String key;

	@Column(name = "value")
	private String value;

	public static EventMetadata create() {
		return new EventMetadata();
	}

	public String getKey() {
		return key;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	public EventMetadata withKey(final String key) {
		this.key = key;
		return this;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public EventMetadata withValue(final String value) {
		this.value = value;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, value);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof final EventMetadata other)) {
			return false;
		}
		return Objects.equals(key, other.key) && Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("EventMetadata [key=").append(key).append(", value=").append(value).append("]");
		return builder.toString();
	}
}
