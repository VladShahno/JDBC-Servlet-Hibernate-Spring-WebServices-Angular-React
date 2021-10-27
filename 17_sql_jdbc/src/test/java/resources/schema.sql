create TABLE IF NOT EXISTS ROLES(
    id IDENTITY NOT NULL,
    login varchar(20) NOT NULL,
    password varchar(20) NOT NULL,
    email varchar(30) NOT NULL,
    firstName varchar(45) NOT NULL,
    lastName varchar(45) NOT NULL,
    birthday Date,
    role_id bigint NOT NULL,
    FOREIGN KEY(role_id) REFERENCES ROLES(id)
);

create TABLE IF NOT EXISTS USERS(
    id IDENTITY NOT NULL,
    name varchar(45) NOT NULL
);
