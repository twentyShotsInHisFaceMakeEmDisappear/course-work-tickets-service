CREATE TABLE events (
    id bigserial NOT NULL,
    title varchar(255) not null,
    artists_id integer DEFAULT NULL,
    locations_id integer DEFAULT NULL,
    description text NOT NULL,
    occupied_places integer NOT NULL,
    age_limit integer NOT NULL,
    date date NOT NULL,
    continuance TIME NOT NULL,
    price integer NOT NULL,
    PRIMARY KEY (id),
    foreign key (artists_id) references artists(id),
    foreign key (locations_id) references locations(id)
);