INSERT INTO user (id, username, password, name, email) VALUES (1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'oyun', 'admin@oyun.com');

INSERT INTO authority (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_USER');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);

-- INSERT INTO article (id, content,content_html,coordinate,create_time,state,title) VALUES (1,"12341234","123412341234","1,1,1,10,10,10,10,1",now(),1,"1234");