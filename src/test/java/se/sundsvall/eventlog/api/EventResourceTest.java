package se.sundsvall.eventlog.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.sundsvall.eventlog.Application;
import se.sundsvall.eventlog.api.model.Event;
import se.sundsvall.eventlog.api.model.EventType;
import se.sundsvall.eventlog.api.model.Metadata;
import se.sundsvall.eventlog.service.EventService;

@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles("junit")
class EventResourceTest {

	private static final String PATH = "/{municipalityId}/{logKey}";

	@Autowired
	private WebTestClient webTestClient;

	@MockitoBean
	private EventService eventServiceMock;

	@Test
	void createEvent() {

		// Arrange
		final var municipalityId = "2281";
		final var logKey = UUID.randomUUID().toString();
		final var event = makeEvent();

		// Act
		webTestClient.post()
			.uri(builder -> builder.path(PATH).build(Map.of("municipalityId", municipalityId, "logKey", logKey)))
			.contentType(APPLICATION_JSON)
			.bodyValue(event)
			.exchange()
			.expectStatus().isAccepted()
			.expectHeader().contentType(ALL_VALUE)
			.expectBody().isEmpty();

		// Assert
		verify(eventServiceMock).createEvent(municipalityId, logKey, event);
	}

	@Test
	void getEvents() {

		// Arrange
		final var municipalityId = "2281";
		final var logKey = UUID.randomUUID().toString();

		// Act
		webTestClient.get()
			.uri(builder -> builder.path(PATH).build(Map.of("municipalityId", municipalityId, "logKey", logKey)))
			.exchange()
			.expectStatus().isOk();

		verify(eventServiceMock).findEvents(eq(municipalityId), eq(logKey), any(), eq(Pageable.ofSize(20)));
	}

	private Event makeEvent() {
		return Event.create()
			.withType(EventType.CREATE)
			.withMessage("Message")
			.withOwner("owner")
			.withExpires(OffsetDateTime.now().plusYears(10))
			.withHistoryReference(UUID.randomUUID().toString())
			.withSourceType("sourceType")
			.withMetadata(List.of(Metadata.create().withKey("key").withValue("value")));
	}
}
