SELECT Pavadinimas, Leidykla 
FROM stud.Knyga 
WHERE metai = '2006';

SELECT (CURRENT_DATE)
FROM stud.Knyga;

SELECT EXTRACT(YEAR FROM (CURRENT_DATE)) 
FROM stud.Knyga;

SELECT Pavadinimas, Leidykla 
FROM stud.Knyga 
WHERE metai = EXTRACT(YEAR FROM (CURRENT_DATE)) ;

SELECT Pavadinimas, Leidykla 
FROM stud.Knyga 
WHERE metai = EXTRACT(YEAR FROM (CURRENT_DATE)) - 5;


--1 uzklausa-
SELECT vardas, pavarde 
FROM stud.Skaitytojas 
WHERE nr % 2 = 0;
-------------

SELECT *
FROM Stud.Egzempliorius, Stud.Skaitytojas
WHERE Egzempliorius.skaitytojas = Skaitytojas.nr;

SELECT *
FROM Stud.Egzempliorius as e, Stud.Skaitytojas as s
WHERE e.skaitytojas = s.nr;


--2 uzklausa-
SELECT e.nr, e.paimta, e.grazinti, s.vardas, s.pavarde
FROM Stud.Egzempliorius as e, Stud.Skaitytojas as s
WHERE e.skaitytojas = s.nr;
-------------

SELECT *
FROM Stud.Egzempliorius LEFT OUTER JOIN Stud.Skaitytojas 
ON Egzempliorius.skaitytojas = Skaitytojas.nr;

SELECT *
FROM Stud.Egzempliorius RIGHT OUTER JOIN Stud.Skaitytojas 
ON Egzempliorius.skaitytojas = Skaitytojas.nr;

SELECT *
FROM Stud.Egzempliorius FULL JOIN Stud.Skaitytojas 
ON Egzempliorius.skaitytojas = Skaitytojas.nr;

SELECT *
FROM Stud.Skaitytojas RIGHT OUTER JOIN Stud.Egzempliorius
ON Egzempliorius.skaitytojas = Skaitytojas.nr JOIN Stud.Knyga ON Egzempliorius.isbn = Knyga.isbn;

SELECT *
FROM Stud.Skaitytojas RIGHT OUTER JOIN Stud.Egzempliorius
ON Egzempliorius.skaitytojas = Skaitytojas.nr JOIN Stud.Knyga ON Egzempliorius.isbn = Knyga.isbn
WHERE Egzempliorius.nr % 2 = 0;

SELECT pavadinimas
FROM Stud.Knyga
WHERE lower(pavadinimas) like '%program%';

SELECT pavadinimas
FROM Stud.Knyga
WHERE (pavadinimas like '%program%') or (pavadinimas like '%Program%');

SELECT pavadinimas
FROM Stud.Knyga
WHERE (pavadinimas like '%program%') or (pavadinimas like '%Program%')
UNION 
SELECT pavadinimas
FROM Stud.Knyga
WHERE lower(pavadinimas) like '%duom%';

SELECT pavadinimas
FROM Stud.Knyga
WHERE (pavadinimas like '%program%') or (pavadinimas like '%Program%')
UNION 
SELECT vardas
FROM stud.Skaitytojas; 

SELECT k.pavadinimas, e.nr, CASE WHEN e.nr % 2 = 0 THEN 'Lyginis' ELSE 'Nelyginis' END AS "Lyginumas"
FROM stud.Egzempliorius AS e JOIN stud.Knyga AS k ON e.isbn = k.isbn;

SELECT e.nr, k.pavadinimas,  CASE WHEN e.nr % 2 = 0 THEN 'Lyginis' ELSE 'Nelyginis' END AS "Lyginumas"
FROM stud.Egzempliorius AS e JOIN stud.Knyga AS k ON e.isbn = k.isbn;


select a.vardas, a.pavarde, case when k.isbn = a.isbn then avg(k.verte) end
from stud.autorius as a, stud.knyga as k 
where k.isbn = a.isbn;

SELECT  count(*), 
		count(DISTINCT k.isbn), 
		count(CASE WHEN e.nr % 2 = 0 THEN e.nr ELSE NULL END)  AS "Lyginiai", 
		count(CASE WHEN e.nr % 2 <> 0 THEN e.nr ELSE NULL END)  AS "Nelyginiai"
FROM stud.Egzempliorius AS e JOIN stud.Knyga AS k ON e.isbn = k.isbn;

SELECT Pavadinimas, Leidykla, SUBSTRING(isbn from 7 for 1), SUBSTRING(isbn from 9 for 1)
FROM stud.Knyga k;

SELECT Pavadinimas, Leidykla, verte 
FROM stud.Knyga k
WHERE SUBSTRING(isbn from 7 for 1) = SUBSTRING(isbn from 9 for 1)
order by k.verte;

SELECT Pavadinimas, Leidykla, CASE WHEN verte IS NOT NULL THEN verte ELSE 0 END 
FROM stud.Knyga k
WHERE SUBSTRING(isbn from 7 for 1) = SUBSTRING(isbn from 9 for 1)
order by 3;

--3 uzklausa---
--Kiekvienai autoriaus pavardei bendrapavardžių autorių skaičius ir bendras visų jų knygų puslapių skaičius.
SELECT a.pavarde, COUNT( a.vardas LIKE a.vardas), SUM(k.puslapiai)
FROM Stud.Autorius a JOIN Stud.Knyga k ON a.isbn = k.isbn 
GROUP BY a.pavarde;
---------------

--4 uzklausa---
--Vardai ir pavardės autorių, kurių knygų egzempliorių bibliotekoje yra mažiau už visų autorių egzempliorių vidurkį. 
--Greta pateikti ir tų autorių visų knygų bei visų egzempliorių skaičius.
with x (vardas, pavarde, kiekisEgzemplioriu, kiekisKnygu) as 
(select x.vardas, x.pavarde, count (z.isbn), count(DISTINCT y.isbn)
from Stud.Autorius as x join Stud.Knyga as y on x.isbn = y.isbn 
join Stud.Egzempliorius as z on y.isbn = z.isbn
group by x.vardas, x.pavarde), 
	 y (vidurkis) as 
(SELECT AVG(x.kiekisEgzemplioriu) 
FROM x)
SELECT x.vardas, x.pavarde, x.kiekisEgzemplioriu as "Egzemplioriu skaicius", x.kiekisKnygu as "Knygu skaicius"
FROM x, y
WHERE x.kiekisEgzemplioriu < y.vidurkis;
---------------

------------
SELECT CASE WHEN count(DISTINCT isbn) < ((select count (*) from Stud.Egzempliorius)/(select count(distinct isbn) from Stud.Egzempliorius)) THEN (vardas || ' ' || pavarde) END
FROM Stud.Egzempliorius
GROUP BY isbn; 
------------ 

---klaida----
with x (vardas, pavarde, kiekisEgzemplioriu, kiekisKnygu) as (SELECT a.vardas, a.pavarde, count (case when a.isbn = e.isbn then e.isbn end, count (case when a.isbn = e.isbn then e.isbn end)
FROM Stud.Autorius a join Stud.Egzempliorius e on a.isbn = e.isbn
GROUP BY a.vardas, a.pavarde), 
y (vidurkis) as (SELECT AVG(x.kiekisEgzemplioriu) FROM x)
SELECT x.vardas, x.pavarde, x.kiekisEgzemplioriu as "Egzemplioriu skaicius", 
FROM x, y
WHERE x.kiekisEgzemplioriu < y.vidurkis;
--------------

select x.vardas, x.pavarde, count (z.isbn) as "Egzemplioriu skaicius", count(DISTINCT y.isbn) as "Knygu skaicius"
from Stud.Autorius as x join Stud.Knyga as y on x.isbn = y.isbn 
join Stud.Egzempliorius as z on y.isbn = z.isbn
group by x.vardas, x.pavarde;

---3 uzklausa-----
---Kiekvieno autoriaus (vardas ir pavardė) visų knygų skaičius pagal kiekvienus išleidimo metus atskirai.
SELECT a.vardas, a.pavarde, COUNT( k.isbn) as "Visu knygu skaicius"
FROM Stud.Autorius a JOIN Stud.Knyga k ON a.isbn = k.isbn 
GROUP BY k.metai, a.vardas, a.pavarde;
------------------

---4 uzklausa-----
---Pavadinimai ir leidyklos knygų, kurių egzepliorių yra paimta mažiau nei vidutiniškai. 
---Greta pateikti ir tų knygų paimtų egzempliorių skaičių.
---klaida----------------------------
with paimtuEgzemplioriuSkaicius (skaicius) as 
(SELECT count (e.paimta)
FROM Stud.Egzempliorius as e),
visuEgzemplioriuSkaicius (skaicius) AS
(select COUNT(e.isbn)
from Stud.Egzempliorius as e),
vidurkis (vidurkis) AS
(SELECT (cast(v.skaicius as float) / cast(e.skaicius as float))
FROM paimtuEgzemplioriuSkaicius as e, visuEgzemplioriuSkaicius as v),
EgzemplioriuSkaicius (pavadinimas, leidykla, paimtiEgzemplioriai) as 
(select k.pavadinimas, k.leidykla, count(e.isbn)
from vidurkis v, Stud.Egzempliorius e JOIN Stud.Knyga k ON e.isbn = k.isbn
group by k.pavadinimas, k.leidykla)
select e.pavadinimas, e.leidykla, e.paimtiEgzemplioriai
from EgzemplioriuSkaicius as e, vidurkis as v
where e.paimtiEgzemplioriai < v.vidurkis;
-------------------
with paimta (knyga, paimta) as 
(select k.pavadinimas, k.leidykla, count (e.paimta)
from Stud.Egzempliorius e JOIN Stud.Knyga k ON e.isbn = k.isbn
group by k.isbn),
vidurkis (vidurkis) as 
(select avg(paimta)
from paimta);


---4 uzklausa-----
---Pavadinimai ir leidyklos knygų, kurių egzepliorių yra paimta mažiau nei vidutiniškai. 
---Greta pateikti ir tų knygų paimtų egzempliorių skaičių.
with paimta (knyga, leidykla, paimta) as 
(select k.pavadinimas, k.leidykla, count (e.paimta)
from Stud.Egzempliorius e JOIN Stud.Knyga k ON e.isbn = k.isbn
group by k.isbn),
vidurkis (vidurkis) as 
(select avg(paimta)
from paimta)
select p.knyga, p.leidykla, p.paimta
from paimta p, vidurkis v
where p.paimta < v.vidurkis;
----------------------------

with vidurkis (vidurkis) AS
(SELECT AVG(e.paimta)
FROM Stud.Egzempliorius as e)
select *
from vidurkis;



---4 uzklausa-----
---Metai, kuriais išleistos knygos yra labiausiai skaitomos, 
---t.y. metai, kuriais išleistų knygų visų egzempliorių yra daugiausiai paimta.
with skaitomumas (metai, paimta) AS
(select k.metai, count (e.paimta) 
from Stud.Egzempliorius e JOIN Stud.Knyga k ON e.isbn = k.isbn
group by k.metai),
skaitomiausiMetai (paimta) AS
(select max(paimta)
from skaitomumas)
select s.metai, s.paimta
from skaitomumas s, skaitomiausiMetai m
where s.paimta = m.paimta;
------------------

---Kiekvienos leidyklos knyga, kurios egzempliuriu yra daugiausia bibliotekoje.
WITH knyguEgzemplioriuSkaicius (pavadinimas, skaicius) AS
(SELECT k.pavadinimas, COUNT (e.isbn)
FROM Stud.Knyga k JOIN Stud.Egzempliorius e ON k.isbn = e.isbn
GROUP BY k.pavadinimas),
kyguEgzemplioriuSkaiciusSuLeidyklomKuriuYraDaugiausia (leidykla, skaicius) AS
(SELECT k.leidykla, MAX(s.skaicius)
FROM knyguEgzemplioriuSkaicius s JOIN Stud.Knyga k ON k.pavadinimas = s.pavadinimas
GROUP BY k.leidykla)
SELECT k.leidykla, k.pavadinimas
FROM kyguEgzemplioriuSkaiciusSuLeidyklomKuriuYraDaugiausia g JOIN Stud.Knyga k ON g.leidykla = k.leidykla 
JOIN knyguEgzemplioriuSkaicius s ON k.pavadinimas = s.pavadinimas
WHERE g.skaicius = s.skaicius;

---leidykla ir daugiausiai turincios egzemplioriu knygos egzemplioriu skaicius 
WITH knyguEgzemplioriuSkaicius (isbn, skaicius) AS
(SELECT e.isbn, COUNT (e.isbn)
FROM Stud.Egzempliorius e 
GROUP BY e.isbn)
SELECT k.leidykla, MAX(s.skaicius)
FROM knyguEgzemplioriuSkaicius s JOIN Stud.Knyga k ON k.isbn = s.isbn
GROUP BY k.leidykla;
-------------------

---3 uzklausa-------
---Kiekvienam skaitytojui (vardas, pavardė) 
---ir datai, kai jis turi grąžinti bent vieną egzempliorių, 
--visų jo tuomet grąžintinų egzempliorių skaičius.
SELECT s.vardas, s.pavarde, e.grazinti, count(e.grazinti)
FROM Stud.Skaitytojas s JOIN Stud.Egzempliorius e ON s.nr = e.skaitytojas
GROUP BY s.vardas, s.pavarde, e.grazinti;
---------------------


---4 uzklausa---------
---Kiekvienai knygai (pavadinimas, ISBN) 
---vyriausio jos skaitytojo vardas, pavardė ir gimimo data.
with egzSkaitytojai (pavadinimas, knygosNr, vardas, pavarde, gimimas) AS
(SELECT k.pavadinimas, k.isbn, s.vardas, s.pavarde, s.gimimas
FROM Stud.Skaitytojas s join Stud.Egzempliorius e ON s.nr = e.skaitytojas 
join Stud.Knyga k ON e.isbn = k.isbn),
minimalus (knygosNr, minimalus) AS
(select knygosNr, min(gimimas)
from egzSkaitytojai 
group by knygosNr)
select e.pavadinimas, e.knygosNr, e.vardas, e.pavarde, e.gimimas as "Vyriausio skaitytojo gimimo data"
from egzSkaitytojai e join minimalus m on e.knygosNr = m.knygosNr
WHERE e.gimimas = m.minimalus;
----------------------

with egzSkaitytojai (pavadinimas, egzempliorius, vardas, pavarde, gimimas) AS
(SELECT k.pavadinimas, e.nr, s.vardas, s.pavarde, s.gimimas
FROM Stud.Skaitytojas s join Stud.Egzempliorius e ON s.nr = e.skaitytojas 
join Stud.Knyga k ON e.isbn = k.isbn)
select min(gimimas)
from egzSkaitytojai 
group by egzempliorius;

---4 uzklausa------
---Dienos, kai turi būti grąžinta mažiau egzempliorių negu per visas grąžinimo dienas vidutiniškai. 
---Greta pateikti ir tuomet grąžintinų egzempliorių skaičių.
with grazinti (grazinimoData, egzemplioriuSkaicius) AS
(select e.grazinti, count(e.nr)
from Stud.Egzempliorius e
where e.grazinti is not null
group by e.grazinti),
vidurkis (vidurkis) AS
(select avg(egzemplioriuSkaicius)
from grazinti)
select g.grazinimoData, g.egzemplioriuSkaicius
from grazinti g, vidurkis v
where g.egzemplioriuSkaicius < v.vidurkis;
-------------------

---4 uzklausa-------
---Dienos, kai paėmusiųjų knygas skaitytojų buvo daugiau negu per visas paėmimo dienas vidutiniškai. 
---Greta pateikti ir tuomet ėmusiųjų knygas skaitytojų skaičių.
with paimta (paemimoData, egzemplioriuSkaicius) AS
(select e.paimta, count(e.nr)
from Stud.Egzempliorius e
where e.paimta is not null
group by e.paimta),
vidurkis (vidurkis) AS
(select avg(egzemplioriuSkaicius)
from paimta)
select g.paemimoData, g.egzemplioriuSkaicius
from paimta g, vidurkis v
where g.egzemplioriuSkaicius > v.vidurkis;
--------------------

---4 uzklausa---------
---Pavadinimai knygų, kurių egzempliorių yra paimta mažiau už visų knygų paimtų egzempliorių vidurkį.
with paimta (knyga, paimta) as 
(select k.pavadinimas, count (e.paimta)
from Stud.Egzempliorius e JOIN Stud.Knyga k ON e.isbn = k.isbn
group by k.isbn),
vidurkis (vidurkis) as 
(select avg(paimta)
from paimta)
select p.knyga, p.paimta
from paimta p, vidurkis v
where p.paimta < v.vidurkis;
-----------------------

---4 uzklausa----------
---Sarašas metų, kuriais išleistų knygų yra daugiau už visais leidimo metais išleistų knygų vidurkį. 
---Greta pateikti ir tais metais išleistų knygų visų egzempliorių skaičių
with metai (metai, isleistosKnygos) as 
(select k.metai, count(k.isbn)
from Stud.Knyga k 
group by k.metai),
vidurkis (vidurkis) as 
(select avg(m.isleistosKnygos)
from metai m)
select m.metai, m.isleistosKnygos as ""
-----------------------


-----------------------
----kiekvienos knygos skaitytoju amziaus vidurkis
----skaitytojai kurie yra jaunesni negu skaitomos knygos skaitytoju amziaus vidurkis
with amzius (knyga, isbn, vardas, pavarde, amzius) AS
(select k.pavadinimas, k.isbn, s.vardas, s.pavarde, AGE (CURRENT_DATE, s.gimimas)
from Stud.Knyga k join Stud.Egzempliorius e ON e.isbn = k.isbn join Stud.Skaitytojas s on e.skaitytojas = s.nr),
vidurkis (knyga, vidurkis) as 
(select a.isbn, avg( a.amzius)
from amzius a
group by a.isbn)
select a.knyga, a.vardas, a.pavarde, extract(year from amzius) as "metai"
from  vidurkis v join amzius a on v.knyga = a.isbn
where a.amzius < v.vidurkis;
-----------------------


---3 uzklausa----------
---Kiekvienam skaitytojui (numeris, vardas, pavardė) jo paimtų egzempliorių skaičius, 
---bendra jų vertė. Jei knygos vertė nenurodyta, laikyti, kad ji yra lygi 10.
SELECT s.nr, s.vardas, s.pavarde, count(e.nr), sum( COALESCE (k.verte, 10))
FROM Stud.Skaitytojas s JOIN Stud.Egzempliorius e ON s.nr = e.skaitytojas JOIN Stud.Knyga k ON e.isbn = k.isbn
GROUP BY s.nr;
-----------------------



---4 uzklausa----------
---Leidykla (leidyklos), kurios knygos yra labiausiai neskaitomos, 
---t.y. leidykla, kurioje išleistų knygų nepaimtų egzempliorių yra daugiausia. 
---Greta pateikti ir nepaimtų tos leidyklos egzempliorių skaičių.
with skaitomumas (leidykla, nepaimta) as 
(select k.leidykla, count (e.isbn) - count (e.paimta)
from Stud.Egzempliorius e JOIN Stud.Knyga k ON e.isbn = k.isbn
group by k.leidykla),
neskaitomiausia (daugiausiaiNepaimta) AS
(SELECT max(s.nepaimta)
from skaitomumas s)
SELECT s.leidykla, s.nepaimta as "nepaimta egzemplioriu"
FROM skaitomumas s, neskaitomiausia n
where s.nepaimta = n.daugiausiaiNepaimta;
-----------------------

---5 uzklausa-----------
----Konkrečiam naudotojui - sąrašas lentelių, kurioms jis turi teisę rašyti užklausas.
SELECT Table_Name
FROM information_schema.Tables;

SELECT *
FROM information_schema.table_privileges;

SELECT Table_Name
FROM information_schema.Tables
WHERE table_schema = 'stud';

SELECT Table_Name  
FROM information_schema.table_privileges
WHERE table_schema = 'stud';

SELECT Table_Name  
FROM information_schema.table_privileges
WHERE table_schema = 'stud' AND privilege_type = 'SELECT';

---!!!!
SELECT Table_Name  
FROM information_schema.table_privileges p join pg_roles r on r.rolname = p.grantee
WHERE table_schema = 'stud' AND privilege_type = 'SELECT' AND (r.rolname = 'anza8176' or p.grantee = 'PUBLIC');
---!!!!

SELECT Table_Name  
FROM information_schema.table_privileges p join pg_roles r on r.rolname = p.grantee
WHERE privilege_type = 'SELECT' AND (r.rolname = (current_user) or p.grantee = 'PUBLIC');

SELECT Table_Name  
FROM information_schema.Tables
WHERE table_schema = 'stud' AND privilege_type = 'SELECT'; --  column "privilege_type" does not exist!!!
------------------------

---5 uzklausa-----------
---Visi domenai, apibrėžti naudojant konkretų duomenų tipą.
SELECT domain_name
FROM information_schema.domains
WHERE data_type = 'integer';
------------------------

---5 uzklausa-----------
---Konkrečiam naudotojui - sąrašas stulpelių, kuriuos jis turi teisę naudoti užklausose.
SELECT DISTINCT column_name 
FROM information_schema.column_privileges
WHERE table_schema = 'stud' AND grantee = (current_user) AND privilege_type = 'SELECT';
------------------------

---5 uzklausa-----------
---Kiekvienai duomenų bazės lentelei - konkretaus simbolinio tipo stulpelių skaičius.
SELECT Table_Name, COUNT(data_type)  
FROM information_schema.columns
WHERE table_schema = 'stud' AND data_type = 'character'
GROUP BY Table_Name;
------------------------




---Visos lentelės, neturinčios pirminio rakto.
with pirmrakt (name) as
(SELECT DISTINCT table_name
FROM Information_schema.Key_Column_Usage
WHERE position_in_unique_constraint is not null)
select table_name
FROM Information_schema.tables
WHERE table_name not in (SELECT * FROM pirmrakt);

---Visos lentelės, neturinčios isorinio rakto.
with lenteles (name,isoriniai,pirminiai) AS
(SELECT table_name, count (position_in_unique_constraint) as "isoriniai raktai", 
MIN (case when position_in_unique_constraint is null then ordinal_position end) as "pirminiai raktai"
FROM Information_schema.Key_Column_Usage
GROUP BY table_name, table_schema)
select name
from lenteles
where isoriniai = 0;

SELECT table_name, count (position_in_unique_constraint) as "isoriniai raktai", 
MIN (case when position_in_unique_constraint is null then ordinal_position end) as "pirminiai raktai"
FROM Information_schema.Key_Column_Usage
GROUP BY table_name, table_schema 
having count (position_in_unique_constraint) = 0;
