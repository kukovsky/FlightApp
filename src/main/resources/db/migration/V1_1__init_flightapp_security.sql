CREATE TABLE flightapp_users
(
    user_id     SERIAL       NOT NULL,
    username    VARCHAR(50)  NOT NULL UNIQUE ,
    first_name   VARCHAR(50)  NOT NULL,
    last_name    VARCHAR(50)  NOT NULL,
    email        VARCHAR(100) NOT NULL UNIQUE ,
    password     TEXT         NOT NULL,
    active       BOOLEAN      NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE flightapp_roles
(
    role_id SERIAL      NOT NULL,
    role    VARCHAR(20) NOT NULL,
    PRIMARY KEY (role_id)
);

CREATE TABLE flightapp_user_roles
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_flightapp_user_role_user
        FOREIGN KEY (user_id)
            REFERENCES flightapp_users (user_id),
    CONSTRAINT fk_flightapp_user_role_role
        FOREIGN KEY (role_id)
            REFERENCES flightapp_roles (role_id)
);
