CREATE TABLE reservation_process (
    process_id SERIAL NOT NULL,
    reservation_id INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    PRIMARY KEY (process_id),
    CONSTRAINT fk_reservation_process_reservation
        FOREIGN KEY (reservation_id)
            REFERENCES reservations(reservation_id)
);