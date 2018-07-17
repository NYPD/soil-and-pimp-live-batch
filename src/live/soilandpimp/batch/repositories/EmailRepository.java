package live.soilandpimp.batch.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import live.soilandpimp.batch.domain.EmailSubscription;

/**
 * Simple repository interface for {@link EmailSubscription} instances. The interface is used to
 * declare so called query methods, methods to retrieve single entities or collections of them.
 * 
 * @author NYPD
 */
public interface EmailRepository extends CrudRepository<EmailSubscription, String> {

    public List<EmailSubscription> findByVerifiedTrue();
}
