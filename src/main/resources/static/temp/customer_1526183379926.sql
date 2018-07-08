START TRANSACTION;

INSERT INTO `customer` (`confirmed`, `email`, `firstname`, `gender`, `lastname`, `phone`, `registration_date`, `title`, `password`) VALUES
(0, 'jack@163.com', 'Jack', 'MALE', 'ZHOU', 1234, 43226, 'MR', 'j1234'),
(0, 'rookie@163.com', 'Rookie', 'MALE', 'Han', 12439357, 43226, 'MR', 'NULL'),
(0, 'duck.hu@gmail.com', 'Duke', 'MALE', 'HU', 13499655, 43226, 'MR', 'NULL');

COMMIT;
