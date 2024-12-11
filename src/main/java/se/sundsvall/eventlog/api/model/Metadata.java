package se.sundsvall.eventlog.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

@Schema(description = "Metadata model")
public class Metadata {

	@Schema(description = "The key", example = "userId")
	@NotBlank
	private String key;

	@Schema(description = "The value", example = "john123")
	@NotBlank
	private String value;

	public static Metadata create() {
		return new Metadata();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Metadata withKey(String key) {
		this.key = key;
		return this;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Metadata withValue(String value) {
		this.value = value;
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
		Metadata metadata = (Metadata) o;
		return Objects.equals(key, metadata.key) && Objects.equals(value, metadata.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, value);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Metadata{");
		sb.append("key='").append(key).append('\'');
		sb.append(", value='").append(value).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
