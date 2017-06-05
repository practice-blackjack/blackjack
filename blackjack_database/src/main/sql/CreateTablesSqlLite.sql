USE [blackjack]
GO

CREATE TABLE Players(
PlayerIDPK int IDENTITY(1,1) NOT NULL PRIMARY KEY,
Nickname varchar (15) NOT NULL  CONSTRAINT PlaUQNick UNIQUE,
DateStartPlay date );
GO

CREATE TABLE [Tables](
TableIDPK int IDENTITY PRIMARY KEY,
TblName varchar(30),
MaxPeople smallint NOT NULL CONSTRAINT TblMaxPl CHECK(MaxPeople LIKE '[2-9]'),
RateIDFK int NOT NULL CONSTRAINT TblRateFK   REFERENCES Rates(RateIDPK));
GO

CREATE TABLE Rates(
RateIDPK int IDENTITY PRIMARY KEY,
MinRate int DEFAULT 20,
MaxRate int DEFAULT 200);
GO 

CREATE TABLE [MoneyAndRank](
MoneyPlayerID int IDENTITY PRIMARY KEY,
PlayerIDFK int CONSTRAINT MonARanFK  REFERENCES Players(PlayerIDPK),
Bank bigint NOT NULL,
[Rank] Char(20));
GO


