CREATE TABLE reservations
(
    reservation_id SERIAL NOT NULL,
    user_id INT NOT NULL,
    origin VARCHAR(3) NOT NULL,
    destination VARCHAR(3) NOT NULL,
    departure_date DATE NOT NULL,
    return_date DATE NOT NULL,
    airline VARCHAR(100) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    number_of_passengers INT NOT NULL CHECK(number_of_passengers > 0),
    status VARCHAR(20) CHECK (status in ('CONFIRMED', 'CANCELLED', 'PENDING')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (reservation_id),
    CONSTRAINT fk_reservations_user
        FOREIGN KEY (user_id)
            REFERENCES users (users_id)
)