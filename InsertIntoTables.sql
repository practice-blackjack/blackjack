USE [blackjack]

INSERT INTO players
VALUES(1,'Roman','fgdfdgd','2017-05-05'),
(2,'Taras','fgdfdddgd','2017-06-05'),
(3,'VArum','123dsf54f','2016-05-20'),
(4,'Nacc','s5fdfs9','2015-01-12'),
(5,'Karon','sdfsdf84ds','2016-05-10');

INSERT INTO MoneyAndRank
VALUES(1,1,50000,'Sharp'),
(2,2,300,'Beginner'),
(3,3,3600,'Beginner'),
(4,5,10500,'Advanced'),
(5,4,0,'Beginner');


INSERT INTO Rates
VALUES(1,20,100),
(2,40,400),
(3,100,2000),
(4,400,8000),
(5,1000,20000),
(6,2000,50000),
(7,5000,100000);


INSERT INTO Tables
VALUES(1,5-London-20000,5,5),
(2,3-Paris-50000,3,6),
(3,9-London-8000,9,4),
(4,7-Kyiv-400,7,2),
(5,5-Dubai-100000,7,7);
