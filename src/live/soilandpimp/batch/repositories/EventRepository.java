package live.soilandpimp.batch.repositories;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import live.soilandpimp.batch.domain.Event;

/**
 * Simple repository interface for {@link Event} instances. The interface is used to declare so
 * called query methods, methods to retrieve single entities or collections of them.
 * 
 * @author NYPD
 */
public interface EventRepository extends CrudRepository<Event, String> {

    @Query("SELECT * from events where event_key in(?0)")
    public Event findEventById(String eventKey);
}
