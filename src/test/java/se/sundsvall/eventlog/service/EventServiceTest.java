package se.sundsvall.eventlog.service;

import com.turkraft.springfilter.boot.FilterSpecification;
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

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.sundsvall.eventlog.service.EventService.logKeyFilter;
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

	@InjectMocks
	private EventService eventService;

	@Test
	void createEvent() {
		try(MockedStatic<EventMapper> mapper = Mockito.mockStatic(EventMapper.class)) {
			mapper.when(() -> EventMapper.toEventEntity(any(),any())).thenReturn(eventEntityMock);

			eventService.createEvent(LOG_KEY, eventMock);

			mapper.verify(() -> EventMapper.toEventEntity(eq(LOG_KEY), same(eventMock)));
			verify(eventRepositoryMock).save(same(eventEntityMock));
		}
	}

	@Test
	void findEvents() {
		Specification<EventEntity> filter = new FilterSpecification<>("owner: 'service-x'");
		Specification<EventEntity> expectedFilterAddition = (root, cq, cb) -> cb.equal(root.get("logKey"), LOG_KEY);

		when(eventRepositoryMock.findAll(any(Specification.class), any(Pageable.class))).thenReturn(eventEntityPageMock);
		when(eventEntityPageMock.stream()).thenReturn(Stream.of(eventEntityMock));

		try(MockedStatic<EventMapper> mapper = Mockito.mockStatic(EventMapper.class)) {
			mapper.when(() -> EventMapper.toEvent(any())).thenReturn(eventMock);

			var result = eventService.findEvents(LOG_KEY, filter, pageableMock);

			verify(eventRepositoryMock).findAll(specificationArgumentCaptor.capture(), same(pageableMock));
			assertThat(specificationArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(logKeyFilter(LOG_KEY).and(filter));
			mapper.verify(() -> EventMapper.toEvent(same(eventEntityMock)));

			assertThat(result.getSize()).isEqualTo(1);
			assertThat(result).containsExactly(eventMock);
		}
	}
}