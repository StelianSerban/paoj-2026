DROP TABLE IF EXISTS note;
DROP TABLE IF EXISTS absente;
DROP TABLE IF EXISTS materii;
DROP TABLE IF EXISTS studenti;
DROP TABLE IF EXISTS profesori;
DROP TABLE IF EXISTS grupe;
DROP TABLE IF EXISTS specializari;

CREATE TABLE specializari (
                              id      INTEGER PRIMARY KEY AUTOINCREMENT,
                              nume    TEXT NOT NULL UNIQUE,
                              domeniu TEXT NOT NULL,
                              durata  INTEGER NOT NULL
);

CREATE TABLE grupe (
                       id               INTEGER PRIMARY KEY AUTOINCREMENT,
                       nume             TEXT NOT NULL UNIQUE,
                       specializare_id  INTEGER NOT NULL,
                       FOREIGN KEY (specializare_id) REFERENCES specializari(id)
);

CREATE TABLE profesori (
                           id          INTEGER PRIMARY KEY AUTOINCREMENT,
                           nume        TEXT NOT NULL,
                           prenume     TEXT NOT NULL,
                           email       TEXT NOT NULL UNIQUE,
                           departament TEXT NOT NULL,
                           titlu       TEXT NOT NULL
);

CREATE TABLE studenti (
                          id      INTEGER PRIMARY KEY AUTOINCREMENT,
                          nume    TEXT NOT NULL,
                          prenume TEXT NOT NULL,
                          email   TEXT NOT NULL UNIQUE,
                          an      INTEGER NOT NULL,
                          grupa_id INTEGER NOT NULL,
                          FOREIGN KEY (grupa_id) REFERENCES grupe(id)
);

CREATE TABLE materii (
                         id          INTEGER PRIMARY KEY AUTOINCREMENT,
                         nume        TEXT NOT NULL UNIQUE,
                         credite     INTEGER NOT NULL,
                         profesor_id INTEGER NOT NULL,
                         FOREIGN KEY (profesor_id) REFERENCES profesori(id)
);

CREATE TABLE note (
                      id          INTEGER PRIMARY KEY AUTOINCREMENT,
                      student_id  INTEGER NOT NULL,
                      materie_id  INTEGER NOT NULL,
                      valoare     REAL NOT NULL,
                      data        TEXT NOT NULL,
                      FOREIGN KEY (student_id) REFERENCES studenti(id),
                      FOREIGN KEY (materie_id) REFERENCES materii(id)
);

CREATE TABLE absente (
                         id          INTEGER PRIMARY KEY AUTOINCREMENT,
                         student_id  INTEGER NOT NULL,
                         materie_id  INTEGER NOT NULL,
                         data        TEXT NOT NULL,
                         motivata    INTEGER NOT NULL DEFAULT 0,
                         FOREIGN KEY (student_id) REFERENCES studenti(id),
                         FOREIGN KEY (materie_id) REFERENCES materii(id)
);