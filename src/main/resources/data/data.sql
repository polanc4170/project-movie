INSERT INTO movies (imdb_id, title, year, description) VALUES
(1, 'Star Wars: Episode I – The Phantom Menace'     , 1999, 'Two Jedi escape a hostile blockade to find allies and come across a young boy who may bring balance to the Force, but the long dormant Sith resurface to claim their original glory.'),
(2, 'Star Wars: Episode II – Attack of the Clones'  , 2002, 'Ten years after initially meeting, Anakin Skywalker shares a forbidden romance with Padmé Amidala, while Obi-Wan Kenobi discovers a secret clone army crafted for the Jedi.'),
(3, 'Star Wars: Episode III - Revenge of the Sith'  , 2005, 'Three years into the Clone Wars, Obi-Wan pursues a new threat, while Anakin is lured by Chancellor Palpatine into a sinister plot to rule the galaxy.'),
(4, 'Star Wars: Episode IV - A New Hope'            , 1977, 'Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a Wookiee and two droids to save the galaxy from the Empire''s world-destroying battle station, while also attempting to rescue Princess Leia from the mysterious Darth Vader.'),
(5, 'Star Wars: Episode V - The Empire Strikes Back', 1980, 'After the Rebels are overpowered by the Empire, Luke Skywalker begins his Jedi training with Yoda, while his friends are pursued across the galaxy by Darth Vader and bounty hunter Boba Fett.'),
(6, 'Star Wars: Episode VI - Return of the Jedi'    , 1983, 'After rescuing Han Solo from Jabba the Hutt, the Rebels attempt to destroy the second Death Star, while Luke struggles to help Darth Vader back from the dark side.');

-- INSERT INTO images (uuid, imdb_id, bytes) VALUES
-- (1, 1, lo_import('C:/Developer/Workspace/Java/project-movie/res/poster_star_wars_episode_i.jpg')),
-- (2, 2, lo_import('C:/Developer/Workspace/Java/project-movie/res/poster_star_wars_episode_ii.jpg')),
-- (3, 3, lo_import('C:/Developer/Workspace/Java/project-movie/res/poster_star_wars_episode_iii.jpg'));

-- INSERT INTO movies_images (images_id, movie_id) VALUES
-- (1, 1),
-- (2, 2),
-- (3, 3);
