USE blackjack
GO

SELECT * FROM Players
GO

SELECT * FROM MoneyAndRank
GO

SELECT * FROM Rates
GO

SELECT * FROM [Tables]
GO

--List of players, their bank and rank
SELECT p.Nickname,m.Bank,m.[Rank]
FROM Players p JOIN MoneyAndRank m ON
     p.PlayerIDPK = m.PlayerIDFK
GO

--List of beginners
SELECT p.Nickname,m.Bank,m.[Rank]
FROM Players p JOIN MoneyAndRank m ON
     p.PlayerIDPK = m.PlayerIDFK
WHERE m.[Rank] = 'Beginner'
GO

--List of tables and their rates
SELECT t.TblName,t.MaxPeople,r.MinRate,r.MaxRate
FROM [Tables] t JOIN Rates r ON
     t.RateIDFK = r.RateIDPK

--Count tables from London
SELECT COUNT(*) 'Tbl from London'
FROM [Tables] t JOIN Rates r ON
     t.RateIDFK = r.RateIDPK
WHERE t.TblName LIKE '%London%';

