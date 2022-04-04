CREATE TABLE artists_genres (
    id bigserial not null,
    artists_id integer NOT NULL,
    genres_id integer NOT NULL,
    primary key (id),
    foreign key (artists_id) references artists(id),
    foreign key (genres_id) references genres(id)
);
