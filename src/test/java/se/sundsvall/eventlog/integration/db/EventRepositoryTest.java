package se.sundsvall.eventlog.integration.db;

import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import se.sundsvall.eventlog.integration.db.model.EventEntity;
import se.sundsvall.eventlog.integration.db.model.EventMetadata;

import static java.time.OffsetDateTime.now;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.Assertions.within;
import static se.sundsvall.eventlog.integration.db.specification.EventEntitySpecification.distinct;
import static se.sundsvall.eventlog.integration.db.specification.EventEntitySpecification.withLogKey;
import static se.sundsvall.eventlog.integration.db.specification.EventEntitySpecification.withMunicipalityId;

/**
 * Event repository tests.
 *
 * @see <a href=
 *      "/src/test/resources/db/scripts/testdata-junit.sql">/src/test/resources/db/scripts/testdata-junit.sql</a> for
 *      data setup.
 */
@SpringBootTest
@ActiveProfiles("junit")
@Sql(scripts = {
	"/db/scripts/truncate.sql",
	"/db/scripts/testdata-junit.sql"
})
@Transactional
class EventRepositoryTest {

	@Autowired
	private EventRepository repository;

	@Autowired
	private FilterSpecificationConverter filterSpecificationConverter;

	private static Stream<Arguments> findByFilterWhereLogKeyIsSetProgramaticallyArguments() {
		return Stream.of(
			Arguments.of(Map.of(
				"municipalityId", "2281",
				"logKey", "b1f4e8cf-7e61-4e87-b3c4-ffb443929553",
				"filter", "(metadata.key : 'metadata_key_1-1')",
				"expectedResults", "1")),
			Arguments.of(Map.of(
				"municipalityId", "2281",
				"logKey", "b1f4e8cf-7e61-4e87-b3c4-ffb443929553",
				"filter", "(metadata.value : 'metadata_value_1-2')",
				"expectedResults", "1")),
			Arguments.of(Map.of(
				// Same as first argument, but with an invalid logKey.
				"municipalityId", "2281",
				"logKey", "doesnt-exist",
				"filter", "(metadata.key : 'metadata_key_1-1')",
				"expectedResults", "0")),
			Arguments.of(Map.of(
				// Same as second argument, but with an invalid logKey.
				"municipalityId", "2281",
				"logKey", "doesnt-exist",
				"filter", "(metadata.value : 'metadata_value_1-2')",
				"expectedResults", "0")),
			Arguments.of(Map.of(
				// Same as first argument, but with an invalid municipalityId.
				"municipalityId", "invalid",
				"logKey", "b1f4e8cf-7e61-4e87-b3c4-ffb443929553",
				"filter", "(metadata.key : 'metadata_key_1-1')",
				"expectedResults", "0")),
			Arguments.of(Map.of(
				// Same as second argument, but with an invalid municipalityId.
				"municipalityId", "invalid",
				"logKey", "b1f4e8cf-7e61-4e87-b3c4-ffb443929553",
				"filter", "(metadata.value : 'metadata_value_1-2')",
				"expectedResults", "0")));
	}

	@Test
	void create() {

		// Arrange
		final var expires = now().plusYears(10);
		final var historyReference = UUID.randomUUID().toString();
		final var logKey = UUID.randomUUID().toString();
		final var message = "message";
		final var metadata = List.of(
			EventMetadata.create().withKey("theKey1").withValue("theValue1"),
			EventMetadata.create().withKey("theKey2").withValue("theValue2"));
		final var owner = "owner";
		final var sourceType = "sourceType";
		final var type = "type";

		final var entity = EventEntity.create()
			.withExpires(expires)
			.withHistoryReference(historyReference)
			.withLogKey(logKey)
			.withMessage(message)
			.withMetadata(metadata)
			.withOwner(owner)
			.withSourceType(sourceType)
			.withType(type);

		// Act
		final var persistedEntity = repository.save(entity);

		// Assert
		assertThat(persistedEntity).isNotNull();
		assertThat(isValidUUID(persistedEntity.getId())).isTrue();
		assertThat(persistedEntity.getCreated()).isCloseTo(now(), within(2, SECONDS));
		assertThat(persistedEntity.getExpires()).isEqualTo(expires);
		assertThat(persistedEntity.getHistoryReference()).isEqualTo(historyReference);
		assertThat(persistedEntity.getLogKey()).isEqualTo(logKey);
		assertThat(persistedEntity.getMessage()).isEqualTo(message);
		assertThat(persistedEntity.getOwner()).isEqualTo(owner);
		assertThat(persistedEntity.getSourceType()).isEqualTo(sourceType);
		assertThat(persistedEntity.getType()).isEqualTo(type);
		assertThat(persistedEntity.getMetadata())
			.extracting(EventMetadata::getKey, EventMetadata::getValue)
			.containsExactly(
				tuple("theKey1", "theValue1"),
				tuple("theKey2", "theValue2"));
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"(metadata.key : 'metadata_key_1-1')",
		"(metadata.value : 'metadata_value_1-2')",
		"(logKey : 'b1f4e8cf-7e61-4e87-b3c4-ffb443929553')"
	})
	void findByFilter(final String filter) {

		final Specification<EventEntity> specification = filterSpecificationConverter.convert(filter);
		final Pageable pageable = PageRequest.of(0, 20);

		final var entityList = repository.findAll(specification, pageable);

		assertThat(entityList).isNotNull();
		assertThat(entityList.getTotalElements()).isEqualTo(1);

		assertThat(entityList)
			.extracting(EventEntity::getId, EventEntity::getLogKey, EventEntity::getMessage)
			.containsExactly(tuple("c866968a-06e6-4b70-a813-54eda6a7fe41", "b1f4e8cf-7e61-4e87-b3c4-ffb443929553", "message 1"));
	}

	@ParameterizedTest
	@MethodSource("findByFilterWhereLogKeyIsSetProgramaticallyArguments")
	void findByFilterWhereLogKeyIsSetProgramatically(final Map<String, String> arguments) {

		final var municipalityId = arguments.get("municipalityId");
		final var logKey = arguments.get("logKey");
		final var filter = arguments.get("filter");
		final var expectedResults = Integer.parseInt(arguments.get("expectedResults"));

		final Specification<EventEntity> specification = filterSpecificationConverter.convert(filter);
		final Pageable pageable = PageRequest.of(0, 20);

		final var entityList = repository.findAll(distinct().and(withLogKey(logKey)).and(withMunicipalityId(municipalityId)).and(specification), pageable);

		assertThat(entityList).isNotNull();
		assertThat(entityList.getTotalElements()).isEqualTo(expectedResults);
	}

	private boolean isValidUUID(final String value) {
		try {
			UUID.fromString(String.valueOf(value));
		} catch (final Exception e) {
			return false;
		}

		return true;
	}
}
