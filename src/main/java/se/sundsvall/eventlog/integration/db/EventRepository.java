package se.sundsvall.eventlog.integration.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import se.sundsvall.eventlog.integration.db.model.EventEntity;

@CircuitBreaker(name = "eventRepository")
public interface EventRepository extends CrudRepository<EventEntity, String>, JpaRepository<EventEntity, String>, JpaSpecificationExecutor<EventEntity> {
}
