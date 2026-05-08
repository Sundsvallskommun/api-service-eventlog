package se.sundsvall.eventlog.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

@Schema(description = "Executing user model")
public class ExecutingUser {

	@Schema(implementation = ExecutingUserType.class, enumAsRef = true)
	@NotNull
	private ExecutingUserType type;

	@Schema(description = "The identifier of the executing user", examples = "john.doe")
	@NotBlank
	private String value;

	public static ExecutingUser create() {
		return new ExecutingUser();
	}

	public ExecutingUserType getType() {
		return type;
	}

	public void setType(final ExecutingUserType type) {
		this.type = type;
	}

	public ExecutingUser withType(final ExecutingUserType type) {
		this.type = type;
		return this;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public ExecutingUser withValue(final String value) {
		this.value = value;
		return this;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		final ExecutingUser that = (ExecutingUser) o;
		return type == that.type && Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, value);
	}

	@Override
	public String toString() {
		return "ExecutingUser{type=" + type + ", value='" + value + "'}";
	}
}
