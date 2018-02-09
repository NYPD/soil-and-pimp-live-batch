CREATE TYPE  soilandpimp.schedule (
    date date,
    enter_time text,
    start_time text,
    prefecture text,
    place text,
    memo text,
    link text
);
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
    PRIMARY KEY (event_key, name)
);
CREATE TABLE soilandpimp.email_subscriptions (
    email_address text PRIMARY KEY
);

CREATE INDEX events_schedule_change_idx ON soilandpimp.events (schedule_change);
CREATE INDEX events_broadcast_idx ON soilandpimp.events (broadcast);
