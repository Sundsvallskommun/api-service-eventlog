package se.sundsvall.eventlog.integration.rabbitmq;

import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import se.sundsvall.eventlog.api.model.Event;
import se.sundsvall.eventlog.service.EventService;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static se.sundsvall.eventlog.api.model.EventType.CREATE;

@ExtendWith(MockitoExtension.class)
class EventListenerTest {

	private static final String MUNICIPALITY_ID = "2281";
	private static final String LOG_KEY = "f0882f1d-06bc-47fd-b017-1d8307f5ce95";
	private static final ValidatorFactory VALIDATOR_FACTORY = buildDefaultValidatorFactory();

	@Mock
	private EventService mockEventService;

	private EventListener listener;

	@BeforeEach
	void setUp() {
		listener = new EventListener(mockEventService, VALIDATOR_FACTORY.getValidator());
	}

	@AfterAll
	static void closeValidatorFactory() {
		VALIDATOR_FACTORY.close();
	}

	@Test
	void storesTheEvent() {
		final var event = event();

		listener.receive(new EventMessage(MUNICIPALITY_ID, LOG_KEY, event));

		verify(mockEventService).createEvent(MUNICIPALITY_ID, LOG_KEY, event);
	}

	@Test
	void rejectsAnInvalidMessageWithoutRequeue() {
		final var message = new EventMessage("not-a-municipality", "not-a-uuid", Event.create().withType(CREATE));

		assertThatExceptionOfType(AmqpRejectAndDontRequeueException.class)
			.isThrownBy(() -> listener.receive(message))
			.withMessageStartingWith("Invalid event message: ")
			.withMessageContaining("event.message")
			.withMessageContaining("event.owner")
			.withMessageContaining("logKey")
			.withMessageContaining("municipalityId");
		verifyNoInteractions(mockEventService);
	}

	@Test
	void rejectsAMessageWithoutEvent() {
		final var message = new EventMessage(MUNICIPALITY_ID, LOG_KEY, null);

		assertThatExceptionOfType(AmqpRejectAndDontRequeueException.class)
			.isThrownBy(() -> listener.receive(message))
			.withMessage("Invalid event message: event must not be null");
		verifyNoInteractions(mockEventService);
	}

	private static Event event() {
		return Event.create()
			.withType(CREATE)
			.withMessage("Create facility delegation")
			.withOwner("InstalledBase")
			.withSourceType("Delegation");
	}
}
