package se.sundsvall.eventlog.service;

import static se.sundsvall.eventlog.integration.db.specification.EventEntitySpecification.withLogKey;
import static se.sundsvall.eventlog.integration.db.specification.EventEntitySpecification.withMunicipalityId;
import static se.sundsvall.eventlog.service.mapper.EventMapper.toEventEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import se.sundsvall.eventlog.api.model.Event;
import se.sundsvall.eventlog.integration.db.EventRepository;
import se.sundsvall.eventlog.integration.db.model.EventEntity;
import se.sundsvall.eventlog.service.mapper.EventMapper;

@Service
public class EventService {

	private final EventRepository eventRepository;

	public EventService(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	public void createEvent(String municipalityId, String logKey, Event event) {
		eventRepository.save(toEventEntity(municipalityId, logKey, event));
	}

	public Page<Event> findEvents(String municipalityId, String logKey, Specification<EventEntity> filter, Pageable pageable) {
		final var fullFilter = withMunicipalityId(municipalityId).and(withLogKey(logKey)).and(filter);
		final var matches = eventRepository.findAll(fullFilter, pageable);

		return new PageImpl<>(matches.stream().map(EventMapper::toEvent).toList(), pageable, eventRepository.count(fullFilter));
	}
}
