CREATE TABLE attractions(
  attraction_id SERIAL NOT NULL,
    country_id INT NOT NULL,
    date_time DATE not null,
    hour_time TIME not null,
    attraction_place VARCHAR(50) NOT NULL,
    attraction_comment TEXT NOT NULL,
    attractionUUID VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (attraction_id),
    CONSTRAINT fk_attractions_country
        FOREIGN KEY (country_id)
            REFERENCES travel_countries (country_id)
);