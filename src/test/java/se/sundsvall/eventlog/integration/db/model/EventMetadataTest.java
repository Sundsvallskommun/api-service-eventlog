package se.sundsvall.eventlog.integration.db.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

import org.junit.jupiter.api.Test;

class EventMetadataTest {

	@Test
	void testBean() {
		assertThat(EventMetadata.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void hasValidBuilderMethods() {

		final var key = "key";
		final var value = "value";

		final var object = EventMetadata.create()
			.withKey(key)
			.withValue(value);

		assertThat(object).hasNoNullFieldsOrProperties();
		assertThat(object.getKey()).isEqualTo(key);
		assertThat(object.getValue()).isEqualTo(value);
	}

	@Test
	void hasNoDirtOnCreatedBean() {
		assertThat(new EventMetadata()).hasAllNullFieldsOrProperties();
		assertThat(EventMetadata.create()).hasAllNullFieldsOrProperties();
	}
}
