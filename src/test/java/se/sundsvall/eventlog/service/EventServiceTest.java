package se.sundsvall.eventlog.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.sundsvall.eventlog.service.EventService.logKeyFilter;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import se.sundsvall.eventlog.api.model.Event;
import se.sundsvall.eventlog.integration.db.EventRepository;
import se.sundsvall.eventlog.integration.db.model.EventEntity;
import se.sundsvall.eventlog.service.mapper.EventMapper;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

	private static final String LOG_KEY = "logKey";

	@Mock
	private EventRepository eventRepositoryMock;

	@Mock
	private Event eventMock;

	@Mock
	private EventEntity eventEntityMock;

	@Mock
	private Pageable pageableMock;
	@Captor
	private ArgumentCaptor<Specification<EventEntity>> specificationArgumentCaptor;

	@Mock
	private Page<EventEntity> eventEntityPageMock;

	@Mock
	private Specification<EventEntity> specificationMock;

	@InjectMocks
	private EventService eventService;

	@Test
	void createEvent() {
		try (MockedStatic<EventMapper> mapper = Mockito.mockStatic(EventMapper.class)) {
			mapper.when(() -> EventMapper.toEventEntity(any(), any())).thenReturn(eventEntityMock);

			eventService.createEvent(LOG_KEY, eventMock);

			mapper.verify(() -> EventMapper.toEventEntity(eq(LOG_KEY), same(eventMock)));
			verify(eventRepositoryMock).save(same(eventEntityMock));
		}
	}

	@Test
	void findEvents() {

		when(eventRepositoryMock.findAll(Mockito.<Specification<EventEntity>>any(), any(Pageable.class))).thenReturn(eventEntityPageMock);
		when(eventEntityPageMock.stream()).thenReturn(Stream.of(eventEntityMock));

		try(MockedStatic<EventMapper> mapper = Mockito.mockStatic(EventMapper.class)) {
			mapper.when(() -> EventMapper.toEvent(any())).thenReturn(eventMock);

			final var result = eventService.findEvents(LOG_KEY, specificationMock, pageableMock);

			verify(eventRepositoryMock).findAll(specificationArgumentCaptor.capture(), same(pageableMock));
			assertThat(specificationArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(logKeyFilter(LOG_KEY).and(specificationMock));
			mapper.verify(() -> EventMapper.toEvent(same(eventEntityMock)));

			assertThat(result.getSize()).isEqualTo(1);
			assertThat(result).containsExactly(eventMock);
		}
	}
}
