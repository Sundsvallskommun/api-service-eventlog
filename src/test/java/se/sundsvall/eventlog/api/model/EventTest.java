package se.sundsvall.eventlog.api.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static com.google.code.beanmatchers.BeanMatchers.registerValueGenerator;
import static java.time.OffsetDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

class EventTest {

	@BeforeAll
	static void setup() {
		registerValueGenerator(() -> now().plusDays(new Random().nextInt()), OffsetDateTime.class);
	}

	@Test
	void testBean() {
		assertThat(Event.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var id = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
		final var logKey = "logKey";
		final var eventType = EventType.CREATE;
		final var subType = "ATTACHMENT";
		final var transactionId = "f47ac10b-58cc-4372-a567-0e02b2c3d479";
		final var message = "message";
		final var details = "Filnamn 'abc.pdf'";
		final var municipalityId = "municipalityId";
		final var expireDate = OffsetDateTime.now().plusYears(10);
		final var owner = "owner";
		final var timestamp = OffsetDateTime.now();
		final var historyReference = "historyReference";
		final var sourceType = "sourceType";
		final var metadata = Metadata.create().withKey("key").withValue("value");

		final var bean = Event.create()
			.withId(id)
			.withLogKey(logKey)
			.withType(eventType)
			.withSubType(subType)
			.withTransactionId(transactionId)
			.withMessage(message)
			.withDetails(details)
			.withMunicipalityId(municipalityId)
			.withExpires(expireDate)
			.withOwner(owner)
			.withCreated(timestamp)
			.withHistoryReference(historyReference)
			.withSourceType(sourceType)
			.withMetadata(List.of(metadata));

		assertThat(bean).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(bean.getId()).isEqualTo(id);
		assertThat(bean.getLogKey()).isEqualTo(logKey);
		assertThat(bean.getType()).isEqualTo(eventType);
		assertThat(bean.getSubType()).isEqualTo(subType);
		assertThat(bean.getTransactionId()).isEqualTo(transactionId);
		assertThat(bean.getMessage()).isEqualTo(message);
		assertThat(bean.getDetails()).isEqualTo(details);
		assertThat(bean.getMunicipalityId()).isEqualTo(municipalityId);
		assertThat(bean.getExpires()).isEqualTo(expireDate);
		assertThat(bean.getOwner()).isEqualTo(owner);
		assertThat(bean.getCreated()).isEqualTo(timestamp);
		assertThat(bean.getHistoryReference()).isEqualTo(historyReference);
		assertThat(bean.getSourceType()).isEqualTo(sourceType);
		assertThat(bean.getMetadata()).containsExactly(metadata);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(Event.create()).hasAllNullFieldsOrProperties();
	}
}
