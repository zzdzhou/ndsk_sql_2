START TRANSACTION;

INSERT INTO `city` (`Name`, `CountryCode`, `District`, `Population`) VALUES
('Kabul', 'AFG', 'Kabol', 1780000),
('Qandahar', 'AFG', 'Qandahar', 237500),
('Herat', 'AFG', 'Herat', 186800),
('Mazar-e-Sharif', 'AFG', 'Balkh', 127800),
('Amsterdam', 'NLD', 'Noord-Holland', 731200),
('Rotterdam', 'NLD', 'Zuid-Holland', 593321),
('Haag', 'NLD', 'Zuid-Holland', 440900),
('Utrecht', 'NLD', 'Utrecht', 234323),
('Eindhoven', 'NLD', 'Noord-Brabant', 201843),
('Tilburg', 'NLD', 'Noord-Brabant', 193238);

COMMIT;
