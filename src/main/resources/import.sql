INSERT INTO user (id, username, password, name, email, create_time) VALUES (1, 'admin', '$2a$10$48WIKj5A3eHeU8Oy2Prk9uqJBhv.Cn2b2OpwP1EDbisKySazi2AJa', 'oyun', 'admin@oyun.com','2018-10-06 23:14:15');

INSERT INTO authority (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_VERIFIER');
INSERT INTO authority (id, name) VALUES (3, 'ROLE_USER');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);

-- INSERT INTO article (id, content,content_html,coordinate,create_time,state,title) VALUES (1,"12341234","123412341234","1,1,1,10,10,10,10,1",now(),1,"1234");