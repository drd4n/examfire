DROP TABLE users;
DROP TABLE exam;
DROP TABLE score;
DROP TABLE choice;
DROP TABLE choiceset;

CREATE TABLE users (
	userid INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	username VARCHAR(21) NOT NULL,
	password VARCHAR(21) NOT NULL,
	userfullname VARCHAR(60) NOT NULL,
	email VARCHAR(50) NOT NULL,
	constraint USERS_PK PRIMARY KEY (userid));


CREATE TABLE exam (
	examid INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	examtitle VARCHAR(50) NOT NULL,
	constraint EXAM_PK PRIMARY KEY (examid));


CREATE TABLE score (
	userid INT NOT NULL,
	userscore INT NOT NULL,
	examid INT NOT NULL);



CREATE TABLE choice (
	choiceid INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	question VARCHAR(255) NOT NULL,
	answer VARCHAR(255) NOT NULL,
	choicesetid INT NOT NULL,
	constraint CHOICE_PK PRIMARY KEY (choiceid));



CREATE TABLE choiceset (
	choicesetid INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	title VARCHAR(255) NOT NULL,
	examid INT NOT NULL,
	constraint SET_PK PRIMARY KEY (choicesetid));


ALTER TABLE Score ADD CONSTRAINT Score_fk0 FOREIGN KEY (userid) REFERENCES Users(userid);
ALTER TABLE Score ADD CONSTRAINT Score_fk1 FOREIGN KEY (examid) REFERENCES Exam(examid);

ALTER TABLE choice ADD CONSTRAINT Choice_fk0 FOREIGN KEY (choicesetid) REFERENCES choiceset(choicesetid);

ALTER TABLE choiceset ADD CONSTRAINT Choiceset_fk0 FOREIGN KEY (examid) REFERENCES Exam(examid);
