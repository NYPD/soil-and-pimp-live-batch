package live.soilandpimp.batch.domain;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table("email_subscriptions")
public class EmailSubscription {

    @PrimaryKey("email_address")
    private String emailAddress;

    // Cassandra constructor
    protected EmailSubscription() {};

    // Default Accessors *********************************************
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public String toString() {
        return "Email [emailAddress=" + emailAddress + "]";
    }
}
