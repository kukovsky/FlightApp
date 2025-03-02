CREATE TABLE reservations
(
    reservation_id SERIAL NOT NULL,
    user_id INT NOT NULL,
    -- Lot wylotowy
    departure_origin VARCHAR(3) NOT NULL,
    departure_destination VARCHAR(3) NOT NULL,
    departure_date TIMESTAMP WITH TIME ZONE NOT NULL,
    departure_return_date TIMESTAMP WITH TIME ZONE,
    departure_airline VARCHAR(10) NOT NULL,
    departure_flight_number VARCHAR(10),
    -- Lot powrotny
    return_origin VARCHAR(3),
    return_destination VARCHAR(3),
    return_departure_date TIMESTAMP WITH TIME ZONE,
    return_return_date TIMESTAMP WITH TIME ZONE,
    return_airline VARCHAR(10),
    return_flight_number VARCHAR(10),
    -- Dodatkowe informacje
    price NUMERIC(10, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    number_of_passengers INT NOT NULL CHECK(number_of_passengers > 0),
    number_of_stops INT DEFAULT 0,
    status VARCHAR(20) DEFAULT 'WAITING_FOR_PAYMENT',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (reservation_id),
    CONSTRAINT fk_reservations_user
        FOREIGN KEY (user_id)
            REFERENCES flightapp_users (user_id)
)