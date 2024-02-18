--Delete constraint from movies_images:
ALTER TABLE IF EXISTS movies_images
DROP CONSTRAINT IF EXISTS FKt648xh7iv5r8khv4u4kxbqqvv;

--Delete constraint from movies_images:
ALTER TABLE IF EXISTS movies_images
DROP CONSTRAINT IF EXISTS FKbxrlom4skf0pso8w8i2ilx7n6;

--Delete tables if they exist:
DROP TABLE IF EXISTS images        CASCADE;
DROP TABLE IF EXISTS movies        CASCADE;
DROP TABLE IF EXISTS movies_images CASCADE;

--Create images table:
CREATE TABLE images (
	id			BIGSERIAL   NOT NULL,
	uuid		VARCHAR(64) NOT NULL UNIQUE,
    imdb_id     BIGINT		NOT NULL,
	bytes		OID,
	PRIMARY KEY (id)
);

--Create movies table:
CREATE TABLE movies (
	id          BIGSERIAL    NOT NULL,
	imdb_id     BIGINT       NOT NULL UNIQUE,
	title       VARCHAR(128) NOT NULL,
	year        INTEGER      NOT NULL,
	description VARCHAR(255) NOT NULL,
	PRIMARY KEY (id)
);

--Create one-to-many table:
CREATE TABLE movies_images (
	images_id BIGINT NOT NULL UNIQUE,
	movie_id  BIGINT NOT NULL,
	PRIMARY KEY (images_id, movie_id)
);

--Add constraint:
ALTER TABLE IF EXISTS movies_images
ADD CONSTRAINT FKt648xh7iv5r8khv4u4kxbqqvv
FOREIGN KEY (images_id)
REFERENCES images;

--Add constraint:
ALTER TABLE IF EXISTS movies_images
ADD CONSTRAINT FKbxrlom4skf0pso8w8i2ilx7n6
FOREIGN KEY (movie_id)
REFERENCES movies;
