package live.soilandpimp.batch.dao;

import java.util.List;

import live.soilandpimp.batch.domain.Event;

public interface WebEventDao {

    public List<Event> getCurrentPostedEvents();
}
