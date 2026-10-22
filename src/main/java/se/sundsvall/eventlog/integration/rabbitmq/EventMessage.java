package se.sundsvall.eventlog.integration.rabbitmq;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import se.sundsvall.dept44.common.validators.annotation.ValidMunicipalityId;
import se.sundsvall.dept44.common.validators.annotation.ValidUuid;
import se.sundsvall.eventlog.api.model.Event;

/**
 * An event received over RabbitMQ: the event, together with the municipality and log key that the REST API takes from
 * the path. Validated as the REST API validates them.
 */
public record EventMessage(
	@ValidMunicipalityId String municipalityId,
	@ValidUuid String logKey,
	@Valid @NotNull Event event) {
}
