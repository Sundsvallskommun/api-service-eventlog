package se.sundsvall.eventlog.integration.db.specification;

import static java.util.Objects.nonNull;

import org.springframework.data.jpa.domain.Specification;
import se.sundsvall.eventlog.integration.db.model.EventEntity;

public interface EventEntitySpecification {

	static Specification<EventEntity> distinct() {
		return (root, query, cb) -> {
			query.distinct(true);
			return null;
		};
	}

	static Specification<EventEntity> withLogKey(final String logKey) {
		return buildEqualFilter("logKey", logKey);
	}

	static Specification<EventEntity> withMunicipalityId(final String municipalityId) {
		return buildEqualFilter("municipalityId", municipalityId);
	}

	/**
	 * Method builds an equal filter if value is not null. If value is null, method returns
	 * an always-true predicate (meaning no filtering will be applied for sent in attribute)
	 *
	 * @param  attribute name that will be used in filter
	 * @param  value     value (or null) to compare against
	 * @return           Specification<T> matching sent in comparison
	 */
	static Specification<EventEntity> buildEqualFilter(final String attribute, final Object value) {
		return (entity, cq, cb) -> nonNull(value) ? cb.equal(entity.get(attribute), value) : cb.and();
	}
}
