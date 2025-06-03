-- Reset sequences
ALTER SEQUENCE seq_auth_user_id RESTART WITH 6;
ALTER SEQUENCE seq_geo_id RESTART WITH 6;
ALTER SEQUENCE seq_address_id RESTART WITH 6;
ALTER SEQUENCE seq_company_id RESTART WITH 6;
ALTER SEQUENCE seq_app_user_id RESTART WITH 6;

-- Insert admin user
INSERT INTO auth_user (name, email, password_hash)
VALUES ('Admin', 'admin@example.com', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW');

-- Insert Geo data
INSERT INTO geo (id, lat, lng) VALUES
(1, '-37.3159', '81.1496'),
(2, '-43.9509', '-34.4618'),
(3, '-68.6102', '-47.0653'),
(4, '29.4572', '-164.2990'),
(5, '-31.8129', '62.5342');

-- Insert Address data
INSERT INTO address (id, street, suite, city, zipcode, geo_id) VALUES
(1, 'Kulas Light', 'Apt. 556', 'Gwenborough', '92998-3874', 1),
(2, 'Victor Plains', 'Suite 879', 'Wisokyburgh', '90566-7771', 2),
(3, 'Douglas Extension', 'Suite 847', 'McKenziehaven', '59590-4157', 3),
(4, 'Hoeger Mall', 'Apt. 692', 'South Elvis', '53919-4257', 4),
(5, 'Skiles Walks', 'Suite 351', 'Roscoeview', '33263', 5);

-- Insert Company data
INSERT INTO company (id, name, catch_phrase, bs) VALUES
(1, 'Romaguera-Crona', 'Multi-layered client-server neural-net', 'harness real-time e-markets'),
(2, 'Deckow-Crist', 'Proactive didactic contingency', 'synergize scalable supply-chains'),
(3, 'Romaguera-Jacobson', 'Face to face bifurcated interface', 'e-enable strategic applications'),
(4, 'Robel-Corkery', 'Multi-tiered zero tolerance productivity', 'transition cutting-edge web services'),
(5, 'Keebler LLC', 'User-centric fault-tolerant solution', 'revolutionize end-to-end systems');

-- Insert User data
INSERT INTO app_user (id, name, username, email, address_id, phone, website, company_id) VALUES
(1, 'Leanne Graham', 'Bret', 'Sincere@april.biz', 1, '1-770-736-8031 x56442', 'hildegard.org', 1),
(2, 'Ervin Howell', 'Antonette', 'Shanna@melissa.tv', 2, '010-692-6593 x09125', 'anastasia.net', 2),
(3, 'Clementine Bauch', 'Samantha', 'Nathan@yesenia.net', 3, '1-463-123-4447', 'ramiro.info', 3),
(4, 'Patricia Lebsack', 'Karianne', 'Julianne.OConner@kory.org', 4, '493-170-9623 x156', 'kale.biz', 4),
(5, 'Chelsey Dietrich', 'Kamren', 'Lucio_Hettinger@annie.ca', 5, '(254)954-1289', 'demarco.info', 5); 