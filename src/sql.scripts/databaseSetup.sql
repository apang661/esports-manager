CREATE TABLE Team (
    tID   INTEGER PRIMARY KEY,
    name  VARCHAR(32) UNIQUE NOT NULL,
    owner VARCHAR(32)
);

CREATE TABLE Achievement (
    season    VARCHAR(6),
    year      INTEGER,
    placement INTEGER,
    tID       INTEGER NOT NULL,
    PRIMARY KEY (season, year, placement),
    FOREIGN KEY (tID) REFERENCES Team (tID),
    UNIQUE (season, year, tID)
);

CREATE TABLE Roster (
    tID    INTEGER,
    season VARCHAR(6),
    year   INTEGER,
    wins   INTEGER NOT NULL,
    losses INTEGER NOT NULL,
    PRIMARY KEY (season, year, tID),
    FOREIGN KEY (tID) REFERENCES Team (tID)
);


CREATE TABLE TeamMember (
    tmID INTEGER PRIMARY KEY,
    name VARCHAR(32),
    age  INTEGER
);



CREATE TABLE PartOfRoster (
    season VARCHAR(6),
    year   INTEGER,
    tID    INTEGER,
    tmID   INTEGER,
    PRIMARY KEY (season, year, tID, tmID),
    FOREIGN KEY (season, year, tID) REFERENCES Roster (season, year, tID),
    FOREIGN KEY (tmID) REFERENCES TeamMember
);

CREATE TABLE Player (
    tmID     INTEGER PRIMARY KEY,
    position VARCHAR(7),
    alias    VARCHAR(20) UNIQUE NOT NULL,
    FOREIGN KEY (tmID) REFERENCES TeamMember (tmID)
);


CREATE TABLE Staff (
    tmID INTEGER PRIMARY KEY,
    role VARCHAR(20),
    FOREIGN KEY (tmID) REFERENCES TeamMember
);


CREATE TABLE Arena (
    aID  INTEGER PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    city VARCHAR(32) NOT NULL,
    capacity INTEGER NOT NULL,
    UNIQUE (name, city)
);



CREATE TABLE Game (
    gID  INTEGER PRIMARY KEY,
    rtID INTEGER NOT NULL,
    btID INTEGER NOT NULL,
    day  DATE,
    aID  INTEGER NOT NULL,
    FOREIGN KEY (rtID) REFERENCES Team (tID),
    FOREIGN KEY (btID) REFERENCES Team (tID),
    FOREIGN KEY (aID) REFERENCES Arena (aID)
);


CREATE TABLE PlaysIn (
    gID INTEGER,
    pID INTEGER,
    PRIMARY KEY (gID, pID),
    FOREIGN KEY (gID) REFERENCES Game (gID)
        ON DELETE CASCADE,
    FOREIGN KEY (pID) REFERENCES Player (tmID)
);


CREATE TABLE SeasonDates (
    day    DATE PRIMARY KEY,
    season VARCHAR(6)
);


CREATE TABLE Caster (
    cID  INTEGER PRIMARY KEY,
    name VARCHAR(32) NOT NULL
);



CREATE TABLE Casts (
    cID      INTEGER,
    gID      INTEGER,
    language VARCHAR(20) NOT NULL,
    PRIMARY KEY (cID, gID),
    FOREIGN KEY (cID) REFERENCES Caster (cID),
    FOREIGN KEY (gID) REFERENCES Game (gID)
                   ON DELETE CASCADE
);


CREATE TABLE Viewer (
    vID  INTEGER PRIMARY KEY,
    name VARCHAR(32)
);



CREATE TABLE Seat (
      aID     INTEGER,
      seatNum INTEGER,
      price   FLOAT(5),
      PRIMARY KEY (aID, seatNum),
      FOREIGN KEY (aID) REFERENCES Arena (aID)
);

CREATE TABLE Ticket (
    ticketNum INTEGER PRIMARY KEY,
    vID       INTEGER,
    gID       INTEGER NOT NULL,
    aID       INTEGER NOT NULL,
    seatNum   INTEGER NOT NULL,
    FOREIGN KEY (vID) REFERENCES Viewer (vID),
    FOREIGN KEY (gID) REFERENCES Game (gID)
        ON DELETE CASCADE,
    FOREIGN KEY (aID, seatNum) REFERENCES Seat (aID, seatNum),
    UNIQUE (gID, seatNum)
);

INSERT INTO Team VALUES (1, 'Cloud9', 'Jack Etienne');
INSERT INTO Team VALUES (2, '100 Thieves', 'Matthew Haag');
INSERT INTO Team VALUES (3, 'Team Liquid', 'Steve Arhancet');
INSERT INTO Team VALUES (4, 'Team SoloMid', 'Andy Dinh');
INSERT INTO Team VALUES (5, 'Evil Geniuses', 'Nicole LaPointe Jameson');

INSERT INTO Achievement VALUES ('Spring', 2022, 1, 1);
INSERT INTO Achievement VALUES ('Spring', 2022, 2, 5);
INSERT INTO Achievement VALUES ('Spring', 2022, 3, 4);
INSERT INTO Achievement VALUES ('Spring', 2022, 4, 3);
INSERT INTO Achievement VALUES ('Spring', 2022, 5, 2);

INSERT INTO Roster VALUES (1, 'Spring', 2022, 10, 3);
INSERT INTO Roster VALUES (2, 'Spring', 2022, 3, 10);
INSERT INTO Roster VALUES (3, 'Spring', 2022, 9, 6);
INSERT INTO Roster VALUES (4, 'Spring', 2022, 20, 1);
INSERT INTO Roster VALUES (5, 'Spring', 2022, 0, 10);
INSERT INTO Roster VALUES (1, 'Summer', 2022, 3, 6);
INSERT INTO Roster VALUES (2, 'Summer', 2022, 10, 1);
INSERT INTO Roster VALUES (3, 'Summer', 2022, 8, 3);
INSERT INTO Roster VALUES (4, 'Summer', 2022, 2, 9);
INSERT INTO Roster VALUES (5, 'Summer', 2022, 5, 5);
INSERT INTO Roster VALUES (1, 'Summer', 2021, 11, 9);
INSERT INTO Roster VALUES (2, 'Summer', 2021, 17, 3);
INSERT INTO Roster VALUES (3, 'Summer', 2021, 5, 15);
INSERT INTO Roster VALUES (4, 'Summer', 2021, 7, 13);
INSERT INTO Roster VALUES (5, 'Summer', 2021, 8, 12);

INSERT INTO TeamMember VALUES (1, 'Ibrahim Allami', 21);
INSERT INTO TeamMember VALUES (2, 'Can Celik', 22);
INSERT INTO TeamMember VALUES (3, 'Soren Bjerg', 23);
INSERT INTO TeamMember VALUES (4, 'Peng Yiliang', 24);
INSERT INTO TeamMember VALUES (5, 'Philippe Laflamme', 25);
INSERT INTO TeamMember VALUES (6, 'Daniel Guy', 31);
INSERT INTO TeamMember VALUES (7, 'Brian Pang', 32);
INSERT INTO TeamMember VALUES (8, 'Mickey Mouse', 33);
INSERT INTO TeamMember VALUES (9, 'Barack Obama', 34);
INSERT INTO TeamMember VALUES (10, 'Peter Parker', 35);
INSERT INTO TeamMember VALUES (11, 'Ibrahim Allami', 21);
INSERT INTO TeamMember VALUES (12, 'Can Celik', 22);
INSERT INTO TeamMember VALUES (13, 'Soren Bjerg', 23);
INSERT INTO TeamMember VALUES (14, 'Peng Yiliang', 24);
INSERT INTO TeamMember VALUES (15, 'Philippe Laflamme', 25);


INSERT INTO Player VALUES (1, 'TOP', 'Fudge');
INSERT INTO Player VALUES (2, 'JNG', 'Closer');
INSERT INTO Player VALUES (3, 'MID', 'Bjergsen');
INSERT INTO Player VALUES (4, 'ADC', 'Doublelift');
INSERT INTO Player VALUES (5, 'SUP', 'Vulcan');
INSERT INTO Player VALUES (11, 'TOP', 'Nill');
INSERT INTO Player VALUES (12, 'JNG', 'Jull');
INSERT INTO Player VALUES (13, 'MID', 'Hill');
INSERT INTO Player VALUES (14, 'ADC', 'Kill');
INSERT INTO Player VALUES (15, 'SUP', 'Dill');

INSERT INTO Staff VALUES (6, 'Coach');
INSERT INTO Staff VALUES	(7, 'Assistant Coach');
INSERT INTO Staff VALUES	(8, 'Analyst');
INSERT INTO Staff VALUES	(9, 'Sports Psychologist');
INSERT INTO Staff VALUES	(10, 'Positional Coach');


INSERT INTO Arena VALUES (1, 'Rogers Arena', 'Vancouver', 10);
INSERT INTO Arena VALUES (2, 'LCS Arena', 'Los Angeles', 20);
INSERT INTO Arena VALUES (3, 'LEC Studio', 'Berlin', 10);
INSERT INTO Arena VALUES (4, 'LPL Stadium', 'Shanghai', 5);
INSERT INTO Arena VALUES (5, 'LOL Park', 'Seoul', 20);

INSERT INTO Game VALUES (1, 1, 2, '22-OCT-10', 1);
INSERT INTO Game VALUES (2, 2, 3, '22-JAN-23', 3);
INSERT INTO Game VALUES (3, 3, 4, '22-OCT-30', 4);
INSERT INTO Game VALUES (4, 1, 2, '22-FEB-27', 3);
INSERT INTO Game VALUES (5, 5, 3, '22-OCT-10', 1);
INSERT INTO Game VALUES (6, 5, 4, '22-FEB-21', 2);
INSERT INTO Game VALUES (7, 1, 2, '22-OCT-23', 5);

INSERT INTO SeasonDates VALUES ('22-OCT-10', 'Spring');
INSERT INTO SeasonDates VALUES ('22-JAN-23', 'Spring');
INSERT INTO SeasonDates VALUES ('22-OCT-30', 'Spring');
INSERT INTO SeasonDates VALUES ('22-FEB-27', 'Spring');
INSERT INTO SeasonDates VALUES ('22-JUN-25', 'Summer');
INSERT INTO SeasonDates VALUES ('22-FEB-21', 'Spring');
INSERT INTO SeasonDates VALUES ('22-OCT-23', 'Summer');

INSERT INTO PartOfRoster VALUES ('Spring', 2022, 1, 1);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 1, 2);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 1, 3);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 1, 4);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 1, 5);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 1, 6);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 2, 7);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 3, 8);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 4, 9);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 1, 10);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 2, 11);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 2, 12);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 2, 13);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 2, 14);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 2, 15);

INSERT INTO Caster VALUES (1, 'Kobe');
INSERT INTO Caster VALUES (2, 'CaptainFlowers');
INSERT INTO Caster VALUES (3, 'Phreak');
INSERT INTO Caster VALUES (4, 'Jatt');
INSERT INTO Caster VALUES (5, 'Azael');

INSERT INTO Casts VALUES (1,1, 'English');
INSERT INTO Casts VALUES (2,1, 'French');
INSERT INTO Casts VALUES (3,2, 'English');
INSERT INTO Casts VALUES (2,3, 'French');
INSERT INTO Casts VALUES (1,4, 'English');
INSERT INTO Casts VALUES (1,5, 'English');


INSERT INTO Viewer VALUES (1, 'Bob');
INSERT INTO Viewer VALUES (2, 'Joe');
INSERT INTO Viewer VALUES (3, 'Tom');
INSERT INTO Viewer VALUES (4, 'Sam');
INSERT INTO Viewer VALUES (5, 'Rob');
INSERT INTO Viewer VALUES (6, 'Joanna');
INSERT INTO Viewer VALUES (7, 'Rick');
INSERT INTO Viewer VALUES (8, 'Liam');
INSERT INTO Viewer VALUES (9, 'Pam');
INSERT INTO Viewer VALUES (10, 'Michael');
INSERT INTO Viewer VALUES (11, 'Jacob');
INSERT INTO Viewer VALUES (12, 'Isabella');
INSERT INTO Viewer VALUES (13, 'Cindy');
INSERT INTO Viewer VALUES (14, 'Tyler');
INSERT INTO Viewer VALUES (15, 'Bobette');
INSERT INTO Viewer VALUES (16, 'Rachel');
INSERT INTO Viewer VALUES (17, 'Noel');
INSERT INTO Viewer VALUES (18, 'Pierre');
INSERT INTO Viewer VALUES (19, 'Po');
INSERT INTO Viewer VALUES (20, 'Derek');

INSERT INTO Seat VALUES (1, 1, 50.00);
INSERT INTO Seat VALUES (1, 2, 50.00);
INSERT INTO Seat VALUES (1, 3, 50.00);
INSERT INTO Seat VALUES (1, 4, 50.00);
INSERT INTO Seat VALUES (1, 5, 50.00);
INSERT INTO Seat VALUES (1, 6, 60.00);
INSERT INTO Seat VALUES (1, 7, 60.00);
INSERT INTO Seat VALUES (1, 8, 60.00);
INSERT INTO Seat VALUES (1, 9, 60.00);
INSERT INTO Seat VALUES (1, 10, 60.00);

INSERT INTO Seat VALUES (2, 1, 30.00);
INSERT INTO Seat VALUES (2, 2, 30.00);
INSERT INTO Seat VALUES (2, 3, 30.00);
INSERT INTO Seat VALUES (2, 4, 30.00);
INSERT INTO Seat VALUES (2, 5, 30.00);
INSERT INTO Seat VALUES (2, 6, 30.00);
INSERT INTO Seat VALUES (2, 7, 30.00);
INSERT INTO Seat VALUES (2, 8, 30.00);
INSERT INTO Seat VALUES (2, 9, 30.00);
INSERT INTO Seat VALUES (2, 10, 30.00);
INSERT INTO Seat VALUES (2, 11, 50.00);
INSERT INTO Seat VALUES (2, 12, 50.00);
INSERT INTO Seat VALUES (2, 13, 50.00);
INSERT INTO Seat VALUES (2, 14, 50.00);
INSERT INTO Seat VALUES (2, 15, 50.00);
INSERT INTO Seat VALUES (2, 16, 70.00);
INSERT INTO Seat VALUES (2, 17, 70.00);
INSERT INTO Seat VALUES (2, 18, 70.00);
INSERT INTO Seat VALUES (2, 19, 70.00);
INSERT INTO Seat VALUES (2, 20, 70.00);

INSERT INTO Seat VALUES (3, 1, 30.00);
INSERT INTO Seat VALUES (3, 2, 30.00);
INSERT INTO Seat VALUES (3, 3, 30.00);
INSERT INTO Seat VALUES (3, 4, 30.00);
INSERT INTO Seat VALUES (3, 5, 30.00);
INSERT INTO Seat VALUES (3, 6, 20.00);
INSERT INTO Seat VALUES (3, 7, 20.00);
INSERT INTO Seat VALUES (3, 8, 20.00);
INSERT INTO Seat VALUES (3, 9, 20.00);
INSERT INTO Seat VALUES (3, 10, 20.00);

INSERT INTO Seat VALUES (4, 1, 50.00);
INSERT INTO Seat VALUES (4, 2, 50.00);
INSERT INTO Seat VALUES (4, 3, 50.00);
INSERT INTO Seat VALUES (4, 4, 50.00);
INSERT INTO Seat VALUES (4, 5, 50.00);

INSERT INTO Seat VALUES (5, 1, 20.00);
INSERT INTO Seat VALUES (5, 2, 20.00);
INSERT INTO Seat VALUES (5, 3, 20.00);
INSERT INTO Seat VALUES (5, 4, 20.00);
INSERT INTO Seat VALUES (5, 5, 20.00);
INSERT INTO Seat VALUES (5, 6, 20.00);
INSERT INTO Seat VALUES (5, 7, 20.00);
INSERT INTO Seat VALUES (5, 8, 20.00);
INSERT INTO Seat VALUES (5, 9, 20.00);
INSERT INTO Seat VALUES (5, 10, 50.00);
INSERT INTO Seat VALUES (5, 11, 50.00);
INSERT INTO Seat VALUES (5, 12, 50.00);
INSERT INTO Seat VALUES (5, 13, 50.00);
INSERT INTO Seat VALUES (5, 14, 50.00);
INSERT INTO Seat VALUES (5, 15, 50.00);
INSERT INTO Seat VALUES (5, 16, 50.00);
INSERT INTO Seat VALUES (5, 17, 50.00);
INSERT INTO Seat VALUES (5, 18, 50.00);
INSERT INTO Seat VALUES (5, 19, 50.00);
INSERT INTO Seat VALUES (5, 20, 50.00);

INSERT INTO Ticket VALUES (1, 1, 1, 1, 1);
INSERT INTO Ticket VALUES (2, 1, 2, 3, 1);
INSERT INTO Ticket VALUES (3, 1, 3, 4, 1);
INSERT INTO Ticket VALUES (4, 1, 4, 3, 1);
INSERT INTO Ticket VALUES (5, 2, 1, 1, 2);
INSERT INTO Ticket VALUES (6, 2, 2, 3, 2);
INSERT INTO Ticket VALUES (7, 2, 3, 4, 1);
INSERT INTO Ticket VALUES (8, 3, 4, 3, 2);
INSERT INTO Ticket VALUES (9, 3, 5, 1, 2);
INSERT INTO Ticket VALUES (10, 4, 3, 4, 2);
INSERT INTO Ticket VALUES (11, 5, 5, 1, 1);
INSERT INTO Ticket VALUES (12, 6, 3, 4, 3);
INSERT INTO Ticket VALUES (13, 8, 4, 3, 3);
INSERT INTO Ticket VALUES (14, 10, 3, 4, 4);
INSERT INTO Ticket VALUES (15, 15, 2, 3, 3);
INSERT INTO Ticket VALUES (16, 9, 6, 2, 1);
INSERT INTO Ticket VALUES (17, 4, 6, 2, 2);
INSERT INTO Ticket VALUES (18, 11, 7, 5, 1);



INSERT INTO PlaysIn VALUES (1, 1);
INSERT INTO PlaysIn VALUES (2, 2);
INSERT INTO PlaysIn VALUES (3, 3);
INSERT INTO PlaysIn VALUES (4, 4);
INSERT INTO PlaysIn VALUES (5, 5);


commit;
