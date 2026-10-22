package se.sundsvall.eventlog.integration.rabbitmq;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import se.sundsvall.eventlog.service.EventService;

import static java.util.stream.Collectors.joining;

/**
 * Stores the events other services publish, as a POST to the REST API would.
 * <p>
 * An invalid message is rejected without requeue, so it goes straight to the queue's dead letter queue: delivering it
 * again would not make it valid. Any other failure, such as the database not answering, is delivered again until the
 * queue's delivery limit dead-letters it.
 */
@Component
class EventListener {

	private final EventService eventService;
	private final Validator validator;

	EventListener(final EventService eventService, final Validator validator) {
		this.eventService = eventService;
		this.validator = validator;
	}

	@RabbitListener(queues = "${integration.rabbitmq.queue}")
	void receive(final EventMessage message) {
		final var violations = validator.validate(message);
		if (!violations.isEmpty()) {
			throw new AmqpRejectAndDontRequeueException("Invalid event message: " + describe(violations));
		}
		eventService.createEvent(message.municipalityId(), message.logKey(), message.event());
	}

	private static String describe(final Set<ConstraintViolation<EventMessage>> violations) {
		return violations.stream()
			.map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
			.sorted()
			.collect(joining(", "));
	}
}
