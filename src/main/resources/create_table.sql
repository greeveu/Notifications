create table notifications_messages
(
    id                  char(36)                                 not null primary key,
    receiver_type       varchar(64)                              not null,
    receiver_identifier varchar(64)                              not null,
    type                varchar(16)                              not null,
    message             varchar(512) default ''                  not null,
    ttl                 timestamp    default current_timestamp() not null
);

create table notifications_send
(
    id              int auto_increment primary key,
    user            varchar(36)                           not null,
    notification_id varchar(36)                           not null,
    timestamp       timestamp default current_timestamp() not null
);

create index notification_id on notifications_send (notification_id);

create index user on notifications_send (user);