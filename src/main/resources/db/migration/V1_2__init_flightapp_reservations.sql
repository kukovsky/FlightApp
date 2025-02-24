CREATE TABLE reservations
(
    reservation_id SERIAL NOT NULL,
    user_id INT NOT NULL,
    origin VARCHAR(3) NOT NULL,
    destination VARCHAR(3) NOT NULL,
    departure_date TIMESTAMP WITH TIME ZONE NOT NULL,
    return_date TIMESTAMP WITH TIME ZONE,
    airline VARCHAR(100) NOT NULL,
    flight_number VARCHAR(20),
    price NUMERIC(10, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    number_of_passengers INT NOT NULL CHECK(number_of_passengers > 0),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (reservation_id),
    CONSTRAINT fk_reservations_user
        FOREIGN KEY (user_id)
            REFERENCES flightapp_users (user_id)
)