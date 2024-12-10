package se.sundsvall.eventlog.api;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;
import static org.zalando.problem.Status.BAD_REQUEST;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.zalando.problem.violations.ConstraintViolationProblem;
import org.zalando.problem.violations.Violation;

import se.sundsvall.eventlog.Application;
import se.sundsvall.eventlog.api.model.Event;
import se.sundsvall.eventlog.api.model.EventType;
import se.sundsvall.eventlog.api.model.Metadata;
import se.sundsvall.eventlog.service.EventService;

@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles("junit")
class EventResourceFailuresTest {

	private static final String PATH = "/{municipalityId}/{logKey}";
	private static final String MUNICIPALITY_ID = "2281";

	@Autowired
	private WebTestClient webTestClient;

	@MockitoBean
	private EventService eventServiceMock;

	@Test
	void createEventWithInvalidLogKey() {

		// Arrange
		final var municipalityId = MUNICIPALITY_ID;
		final var logKey = "invalid";
		final var event = createEvent();

		// Act
		final var response = webTestClient.post()
			.uri(builder -> builder.path(PATH).build(Map.of("municipalityId", municipalityId, "logKey", logKey)))
			.contentType(APPLICATION_JSON)
			.bodyValue(event)
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response).isNotNull();
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactlyInAnyOrder(tuple("createEvent.logKey", "not a valid UUID"));

		verifyNoInteractions(eventServiceMock);
	}

	@Test
	void createEventWithInvalidMunicipalityId() {

		// Arrange
		final var municipalityId = "invalid";
		final var logKey = randomUUID().toString();
		final var event = createEvent();

		// Act
		final var response = webTestClient.post()
			.uri(builder -> builder.path(PATH).build(Map.of("municipalityId", municipalityId, "logKey", logKey)))
			.contentType(APPLICATION_JSON)
			.bodyValue(event)
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response).isNotNull();
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactlyInAnyOrder(tuple("createEvent.municipalityId", "not a valid municipality ID"));

		verifyNoInteractions(eventServiceMock);
	}

	private Event createEvent() {
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
