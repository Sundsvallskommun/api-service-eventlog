package se.sundsvall.eventlog.integration.db.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

class EventEntityTest {

	@BeforeAll
	static void setup() {
		registerValueGenerator(() -> now().plusDays(new Random().nextInt()), OffsetDateTime.class);
	}

	@Test
	void testBean() {
		assertThat(EventEntity.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void hasValidBuilderMethods() {

		final var created = now();
		final var expires = now();
		final var historyReference = "historyReference";
		final var id = UUID.randomUUID().toString();
		final var municipalityId = "municipalityId";
		final var logKey = UUID.randomUUID().toString();
		final var message = "message";
		final var metadata = List.of(EventMetadata.create());
		final var owner = "owner";
		final var sourceType = "sourceType";
		final var type = "type";

		final var entity = EventEntity.create()
			.withCreated(created)
			.withExpires(expires)
			.withHistoryReference(historyReference)
			.withId(id)
			.withMunicipalityId(municipalityId)
			.withLogKey(logKey)
			.withMessage(message)
			.withMetadata(metadata)
			.withOwner(owner)
			.withSourceType(sourceType)
			.withType(type);

		assertThat(entity).hasNoNullFieldsOrProperties();
		assertThat(entity.getCreated()).isEqualTo(created);
		assertThat(entity.getExpires()).isEqualTo(expires);
		assertThat(entity.getHistoryReference()).isEqualTo(historyReference);
		assertThat(entity.getId()).isEqualTo(id);
		assertThat(entity.getMunicipalityId()).isEqualTo(municipalityId);
		assertThat(entity.getLogKey()).isEqualTo(logKey);
		assertThat(entity.getMessage()).isEqualTo(message);
		assertThat(entity.getMetadata()).isEqualTo(metadata);
		assertThat(entity.getOwner()).isEqualTo(owner);
		assertThat(entity.getSourceType()).isEqualTo(sourceType);
		assertThat(entity.getType()).isEqualTo(type);
	}

	@Test
	void hasNoDirtOnCreatedBean() {
		assertThat(new EventEntity()).hasAllNullFieldsOrProperties();
		assertThat(EventEntity.create()).hasAllNullFieldsOrProperties();
	}
}
