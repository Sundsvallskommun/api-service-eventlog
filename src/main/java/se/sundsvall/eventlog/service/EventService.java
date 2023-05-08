package se.sundsvall.eventlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import se.sundsvall.eventlog.api.model.Event;
import se.sundsvall.eventlog.integration.db.EventRepository;
import se.sundsvall.eventlog.integration.db.model.EventEntity;
import se.sundsvall.eventlog.service.mapper.EventMapper;

import static se.sundsvall.eventlog.service.mapper.EventMapper.toEventEntity;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	public void createEvent(String logKey, Event event) {
		eventRepository.save(toEventEntity(logKey, event));
	}

	public Page<Event> findEvents(String logKey, Specification<EventEntity> filter, Pageable pageable) {
		var fullFilter = logKeyFilter(logKey).and(filter);
		var matches = eventRepository.findAll(fullFilter, pageable);

		return new PageImpl<>(matches.stream().map(EventMapper::toEvent).toList(), pageable, eventRepository.count(fullFilter));
	}

	protected static Specification<EventEntity> logKeyFilter(String logKey) {
		return (root, cq, cb) -> cb.equal(root.get("logKey"), logKey);
	}
}
