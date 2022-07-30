CREATE TABLE Team (
    tID   INTEGER PRIMARY KEY,
    name  CHAR(32) UNIQUE NOT NULL,
    owner CHAR(32)
);

CREATE TABLE Achievement (
    season    CHAR(6),
    year      INTEGER,
    placement INTEGER,
    tID       INTEGER NOT NULL,
    PRIMARY KEY (season, year, placement),
    FOREIGN KEY (tID) REFERENCES Team (tID),
    UNIQUE (season, year, tID)
);

CREATE TABLE Roster (
    tID    INTEGER,
    season CHAR(6),
    year   INTEGER,
    wins   INTEGER NOT NULL,
    losses INTEGER NOT NULL,
    PRIMARY KEY (season, year, tID),
    FOREIGN KEY (tID) REFERENCES Team (tID)
);


CREATE TABLE TeamMember (
    tmID INTEGER PRIMARY KEY,
    name CHAR(32),
    age  INTEGER
);



CREATE TABLE PartOfRoster (
    season CHAR(6),
    year   INTEGER,
    tID    INTEGER,
    tmID   INTEGER,
    PRIMARY KEY (season, year, tID, tmID),
    FOREIGN KEY (season, year, tID) REFERENCES Roster (season, year, tID),
    FOREIGN KEY (tmID) REFERENCES TeamMember
);

CREATE TABLE Player (
    tmID     INTEGER PRIMARY KEY,
    position CHAR(7),
    alias    CHAR(20) UNIQUE NOT NULL,
    FOREIGN KEY (tmID) REFERENCES TeamMember (tmID)
);


CREATE TABLE Staff (
    tmID INTEGER PRIMARY KEY,
    role CHAR(20),
    FOREIGN KEY (tmID) REFERENCES TeamMember
);


CREATE TABLE Arena (
    aID  INTEGER PRIMARY KEY,
    name CHAR(64) NOT NULL,
    city CHAR(32) NOT NULL,
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
    FOREIGN KEY (gID) REFERENCES Game (gID),
    FOREIGN KEY (pID) REFERENCES Player (tmID)
);


CREATE TABLE SeasonDates (
    day    DATE PRIMARY KEY,
    season CHAR(6)
);


CREATE TABLE Caster (
    cID  INTEGER PRIMARY KEY,
    name CHAR(32) NOT NULL
);



CREATE TABLE Casts (
    cID      INTEGER,
    gID      INTEGER,
    language CHAR(20) NOT NULL,
    PRIMARY KEY (cID, gID),
    FOREIGN KEY (cID) REFERENCES Caster (cID),
    FOREIGN KEY (gID) REFERENCES Game (gID)
);


CREATE TABLE Viewer (
    vID  INTEGER PRIMARY KEY,
    name CHAR(32)
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
    FOREIGN KEY (gID) REFERENCES Game (gID),
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

INSERT INTO Player VALUES (1, 'TOP', 'Fudge');
INSERT INTO Player VALUES (2, 'JNG', 'Closer');
INSERT INTO Player VALUES (3, 'MID', 'Bjergsen');
INSERT INTO Player VALUES (4, 'ADC', 'Doublelift');
INSERT INTO Player VALUES (5, 'SUP', 'Vulcan');

INSERT INTO Staff VALUES	(6, 'Coach');
INSERT INTO Staff VALUES	(7, 'Assistant Coach');
INSERT INTO Staff VALUES	(8, 'Analyst');
INSERT INTO Staff VALUES	(9, 'Sports Psychologist');
INSERT INTO Staff VALUES	(10, 'Positional Coach');


INSERT INTO Arena VALUES (1, 'Rogers Arena', 'Vancouver');
INSERT INTO Arena VALUES (2, 'LCS Arena', 'Los Angeles');
INSERT INTO Arena VALUES (3, 'LEC Studio', 'Berlin');
INSERT INTO Arena VALUES (4, 'LPL Stadium', 'Shanghai');
INSERT INTO Arena VALUES (5, 'LOL Park', 'Seoul');

INSERT INTO Game VALUES (1, 1, 2, '10-OCT-22', 1);
INSERT INTO Game VALUES (2, 2, 3, '23-JAN-22', 3);
INSERT INTO Game VALUES (3, 3, 4, '30-OCT-22', 4);
INSERT INTO Game VALUES (4, 1, 2, '30-FEB-22', 3);
INSERT INTO Game VALUES (5, 5, 3, '10-OCT-22', 1);

INSERT INTO SeasonDates VALUES ('10-OCT-22', 'Winter');
INSERT INTO SeasonDates VALUES ('23-JAN-22', 'Winter');
INSERT INTO SeasonDates VALUES ('30-OCT-22', 'Winter');
INSERT INTO SeasonDates VALUES ('27-FEB-22', 'Spring');
INSERT INTO SeasonDates VALUES ('25-JUN-22', 'Summer');

INSERT INTO PartOfRoster VALUES ('Spring', 2022, 1, 1);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 2, 2);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 3, 3);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 4, 4);
INSERT INTO PartOfRoster VALUES ('Spring', 2022, 5, 5);

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

INSERT INTO Seat VALUES (1, 1, 1.00);
INSERT INTO Seat VALUES (2, 2, 2.00);
INSERT INTO Seat VALUES (3, 3, 3.00);
INSERT INTO Seat VALUES (4, 4, 4.00);
INSERT INTO Seat VALUES (5, 5, 5.00);

INSERT INTO Ticket VALUES (1, 1, 1, 1, 1);
INSERT INTO Ticket VALUES (2, 2, 2, 2, 2);
INSERT INTO Ticket VALUES (3, 3, 3, 3, 3);
INSERT INTO Ticket VALUES (4, 4, 4, 4, 4);
INSERT INTO Ticket VALUES (5, 5, 5, 5, 5);

INSERT INTO PlaysIn VALUES (1, 1);
INSERT INTO PlaysIn VALUES (2, 2);
INSERT INTO PlaysIn VALUES (3, 3);
INSERT INTO PlaysIn VALUES (4, 4);
INSERT INTO PlaysIn VALUES (5, 5);
