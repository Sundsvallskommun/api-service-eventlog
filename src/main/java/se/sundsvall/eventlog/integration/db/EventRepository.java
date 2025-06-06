package se.sundsvall.eventlog.integration.db;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import se.sundsvall.eventlog.integration.db.model.EventEntity;

@Transactional
@CircuitBreaker(name = "eventRepository")
public interface EventRepository extends CrudRepository<EventEntity, String>, JpaRepository<EventEntity, String>, JpaSpecificationExecutor<EventEntity> {
}
