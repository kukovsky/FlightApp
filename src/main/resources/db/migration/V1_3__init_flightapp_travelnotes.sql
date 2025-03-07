CREATE TABLE travel_countries(
    country_id SERIAL not null,
    user_id INT NOT NULL,
    country_UUID VARCHAR(50) NOT NULL UNIQUE,
    country_name VARCHAR(50) NOT NULL,
    visited BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (country_id),
    CONSTRAINT fk_travel_countries_user
        FOREIGN KEY (user_id)
            REFERENCES flightapp_users (user_id)
);
CREATE TABLE travel_experience(
    experience_id SERIAL not null,
    user_id INT not null,
    experience_UUID VARCHAR(50) NOT NULL UNIQUE,
    experience_comment TEXT NOT NULL,
    done BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (experience_id),
    CONSTRAINT fk_travel_experience_user
        FOREIGN KEY (user_id)
            REFERENCES flightapp_users (user_id)
);