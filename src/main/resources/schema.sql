CREATE TABLE IF NOT EXISTS users (
id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
name VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL,
CONSTRAINT pk_user PRIMARY KEY (id),
CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS items (
id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
name VARCHAR(255),
description VARCHAR(255),
available boolean,
owner_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
request_id BIGINT,
CONSTRAINT pk_item PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS bookings (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  item_id BIGINT NOT NULL REFERENCES items (id) ON DELETE RESTRICT ON DELETE CASCADE,
  booker_id BIGINT NOT NULL REFERENCES users (id) ON DELETE RESTRICT ON DELETE CASCADE,
  start_booking TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  end_booking TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  status VARCHAR(50) NOT NULL DEFAULT 'WAITING',
  CONSTRAINT pk_booking PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS comments (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  item_id BIGINT NOT NULL REFERENCES items (id) ON DELETE RESTRICT ON DELETE CASCADE,
  author_id BIGINT NOT NULL REFERENCES users (id) ON DELETE RESTRICT ON DELETE CASCADE,
  text VARCHAR(1000) NOT NULL,
  created TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
  CONSTRAINT pk_comment PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS requests (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    description VARCHAR(255) NOT NULL,
    requester_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    CONSTRAINT pk_request PRIMARY KEY (id)
)