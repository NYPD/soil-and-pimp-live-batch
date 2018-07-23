package live.soilandpimp.batch.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "email_subscriptions")
public class EmailSubscription {

    @Id
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "verification_token")
    private String verificationToken;
    @Column
    private boolean verified;
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "created_date")
    private Date createdDate;

    // JPA constructor
    protected EmailSubscription() {};

    // Default Accessors *********************************************
    public String getEmailAddress() {
        return emailAddress;
    }
    public String getVerificationToken() {
        return verificationToken;
    }
    public boolean isVerified() {
        return verified;
    }
    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public String toString() {
        return "Email [emailAddress=" + emailAddress + "]";
    }
}
