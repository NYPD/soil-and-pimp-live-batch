CREATE KEYSPACE soilandpimp WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;

CREATE TYPE soilandpimp.schedule (
    date date,
    enter_time text,
    start_time text,
    prefecture text,
    place text,
    memo text,
    link text,
	call text
);

CREATE TABLE soilandpimp.email_subscriptions (
    email_address text PRIMARY KEY
);

CREATE TABLE soilandpimp.users (
    id uuid,
    nickname text,
    api_identities map<text, text>,
    user_role text,
    PRIMARY KEY (id, nickname)
) WITH CLUSTERING ORDER BY (nickname ASC);

CREATE TABLE soilandpimp.events (
    event_key text,
    name text,
    broadcast boolean,
    event_url text,
    jvc_url text,
    memo text,
    open_date timestamp,
    schedule_change boolean,
    schedules set<frozen<schedule>>,
    social_networking_title text,
    PRIMARY KEY (event_key)
);

CREATE INDEX events_schedule_change_idx ON soilandpimp.events (schedule_change);
CREATE INDEX events_broadcast_idx ON soilandpimp.events (broadcast);
CREATE INDEX users_api_identities_idx ON soilandpimp.users (ENTRIES(api_identities));


CREATE ROLE soilandpimpbatch 
WITH PASSWORD = 'superSecretPassword' 
AND LOGIN = true;

GRANT SELECT ON KEYSPACE soilandpimp TO soilandpimpbatch;
GRANT MODIFY ON KEYSPACE soilandpimp TO soilandpimpbatch;



INSERT INTO users (id, nickname , api_identities , user_role ) 
VALUES (uuid(), 'NYPD', {'GOOGLE': 'user-id-goes-here'}, 'ADMIN');