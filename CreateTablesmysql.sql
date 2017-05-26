USE [blackjack]


CREATE TABLE Players(
PlayerIDPK int auto_increment NOT NULL PRIMARY KEY,
Nickname varchar (15) NOT NULL,
Pass varchar(20),
DateStartPlay date,
UNIQUE (Nickname));


CREATE TABLE Tables(
TableIDPK int auto_increment PRIMARY KEY,
TblName varchar(30),
MaxPeople smallint NOT NULL  CHECK(MaxPeople LIKE '[2-9]'),
RateIDFK int NOT NULL ,
 FOREIGN KEY (RateIDFK) REFERENCES Rates(RateIDPK));



CREATE TABLE Rates(
RateIDPK int AUTO_INCREMENT PRIMARY KEY,
MinRate int DEFAULT 20,
MaxRate int DEFAULT 200);


CREATE TABLE MoneyAndRank(
MoneyPlayerID int auto_increment PRIMARY KEY,
PlayerIDFK int ,
Bank bigint NOT NULL,
Rank Char(20),
FOREIGN KEY (PlayerIDFK)
        REFERENCES Players(PlayerIDPK)
        ON DELETE CASCADE);


CREATE TABLE RaitingForLastDay(
RaitID int auto_increment PRIMARY KEY,
PlayerRaitIDFK int,
FOREIGN KEY (PlayerIDFK)
        REFERENCES Players(PlayerIDPK)
        ON DELETE CASCADE)

