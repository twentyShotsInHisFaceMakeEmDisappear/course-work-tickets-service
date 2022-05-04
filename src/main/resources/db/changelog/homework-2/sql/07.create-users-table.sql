CREATE TABLE users (
    id bigserial unique,
    credentials_id integer,
    phone_number varchar(15) not null,
    firstname varchar null,
    surname varchar null,
    PRIMARY KEY (id),
    foreign key (credentials_id) references credentials(id)
);