CREATE TABLE artists (
    users_id integer NOT NULL,
    nickname varchar(40) NOT NULL,
    avatar varchar NOT NULL,
    id bigserial NOT NULL,
    short_quote varchar(60) not null,
    long_quote text not null,
    PRIMARY KEY (id),
    foreign key (users_id) references users(id)
);