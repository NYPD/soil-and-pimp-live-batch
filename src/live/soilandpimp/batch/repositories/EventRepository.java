package live.soilandpimp.batch.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import live.soilandpimp.batch.domain.Event;

/**
 * Simple repository interface for {@link Event} instances. The interface is used to declare so
 * called query methods, methods to retrieve single entities or collections of them.
 * 
 * @author NYPD
 */
public interface EventRepository extends CrudRepository<Event, String> {

    public List<Event> findByBroadcastIsFalse();
}
