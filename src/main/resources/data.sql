-- Negative sample IDs avoid collisions with Hibernate's generated positive IDs.
INSERT INTO user_details (id, name, birth_date) VALUES
    (-3, 'Adam', DATE '1996-04-15'),
    (-2, 'Eve', DATE '2001-08-22'),
    (-1, 'Jack', DATE '2006-01-10');

INSERT INTO post (id, description, user_id) VALUES
    (-3, 'Post by Adam', -3),
    (-2, 'first Post by Eve', -2),
    (-4, 'second Post by Eve', -2),
    (-5, 'third Post by Eve', -2),
    (-1, 'Post by Jack', -1)