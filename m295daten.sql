use championsLeague;

insert into championsleaguegroup values ('A', 0);
insert into championsleaguegroup values ('B', 0);
insert into championsleaguegroup values ('C', 0);
insert into championsleaguegroup values ('D', 0);

insert into teams values (NULL,'Neapel', 15, 1926, 'A');
insert into teams values (NULL,'Liverpool', 15, 1892, 'A');

insert into teams values (NULL,'FC Porto', 12, 1893, 'B');
insert into teams values (NULL,'FC Brügge', 11, 1892, 'B');
insert into teams values (NULL,'Bayern', 18, 1900, 'C');
insert into teams values (NULL,'Inter', 10, 1908, 'C');
insert into teams values (NULL,'Tottenham', 11, 1882, 'D');
insert into teams values (NULL,'Eintracht Frankfurt', 10, 1899, 'D');

use championsleague;
alter table championsleaguegroup modify column groupName enum ('A','B','C','D');
alter table teams modify column groupName enum ('A','B','C','D');

