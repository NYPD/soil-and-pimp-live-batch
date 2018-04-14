package live.soilandpimp.batch.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "email_subscriptions")
public class EmailSubscription {

    @Id
    @Column(name = "email_address")
    private String emailAddress;

    // JPA constructor
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
