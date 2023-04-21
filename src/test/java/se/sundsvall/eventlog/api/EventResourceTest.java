package se.sundsvall.eventlog.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.sundsvall.eventlog.Application;
import se.sundsvall.eventlog.api.model.Event;
import se.sundsvall.eventlog.api.model.EventType;
import se.sundsvall.eventlog.api.model.Metadata;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles("junit")
class EventResourceTest {

	private static final String PATH = "/{logKey}";

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void postEvent() {
		webTestClient.post()
			.uri(builder -> builder.path(PATH).build(Map.of("logKey", UUID.randomUUID().toString())))
			.contentType(APPLICATION_JSON)
			.bodyValue(createEvent())
			.exchange()
			.expectStatus().isAccepted()
			.expectBody().isEmpty();

		//TODO add assertions on mocks
	}

	@Test
	void getEvent() {
		final var response = webTestClient.get()
			.uri(builder -> builder.path(PATH).build(Map.of("logKey", UUID.randomUUID().toString())))
			.exchange()
			.expectStatus().isOk();

		// TODO extend when implemented
	}

	private Event createEvent() {
		return Event.create()
			.withEventType(EventType.CREATE)
			.withMessage("Message")
			.withOwner("owner")
			.withExpireDate(OffsetDateTime.now().plusYears(10))
			.withHistoryReference(UUID.randomUUID().toString())
			.withSourceType("sourceType")
			.withMetadata(List.of(Metadata.create().withKey("key").withValue("value")));
	}
}