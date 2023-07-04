create database championsLeague;
use championsLeague;

create table championsLeagueGroup (
	groupName enum ('A','B','C','D') primary key,
    totalPoints int
);

create table teams (
	teamId int primary key auto_increment,
	teamName varchar(64),
    points int,
    foundingYear int,
    groupName enum ('A','B','C','D'),
    foreign key (groupName) references championsLeagueGroup(groupName)
);