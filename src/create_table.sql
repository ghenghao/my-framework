create TABLE customer (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	name varchar(100) DEFAULT NULL,
	contact varchar(100) DEFAULT NULL,
	telephone varchar(100) DEFAULT NULL,
	email varchar(100) DEFAULT NULL,
	remark text,
	regdate timestamp DEFAULT current_timestamp,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE customer;
insert into customer (name, contact, telephone, email) VALUES ('customer1', 'JACK', '13512345678', 'jj@gmail.com');
insert into customer (name, contact, telephone, email) VALUES ('customer2', 'Rose', '13687654321', 'rose@gmail.com');


	

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `enabled` int(11) DEFAULT NULL,
  `regdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 