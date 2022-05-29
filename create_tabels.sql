create table camine
(
  id int NOT NULL PRIMARY KEY,
  nume VARCHAR(20),
  rating int NOT NULL,
  nr_camere int
);

create table camere
(
	id int NOT NULL PRIMARY KEY,
	id_camin int NOT NULL,
	nr_locuri int NOT NULL,
	FOREIGN KEY (id_camin) REFERENCES camine(id)
);

create table studenti
(
  id int NOT NULL PRIMARY KEY,
  id_camin int,
  id_camera int,
  id_preferred_student int,
  nr_matricol VARCHAR(20) NOT NULL,
  nume VARCHAR(20),
  prenume VARCHAR(20),
  gen CHAR(1),
  an int,
  grupa CHAR(2),
  media DECIMAL(4,2),
  data_nastere VARCHAR(20),
  email VARCHAR(40),
  FOREIGN KEY (id_camin) REFERENCES camine(id),
  FOREIGN KEY (id_camera) REFERENCES camere(id),
  FOREIGN KEY (id_preferred_student) REFERENCES studenti(id)
);

create table users
(
    id int NOT NULL PRIMARY KEY,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(30),
    is_super_user BOOLEAN NOT NULL
)


