CREATE TABLE users (
	userid INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	username VARCHAR(21) NOT NULL,
	password VARCHAR(21) NOT NULL,
	userfullname VARCHAR(60) NOT NULL,
	constraint USERS_PK PRIMARY KEY (userid));


CREATE TABLE exam (
	examid INT NOT NULL,
	subjectid INT NOT NULL,
	uesrid INT NOT NULL,
	constraint EXAM_PK PRIMARY KEY (examid));



CREATE TABLE subjects (
	subjectid INT NOT NULL,
	subjectname VARCHAR(25) NOT NULL,
	constraint SUBJECTS_PK PRIMARY KEY (subjectid));



CREATE TABLE score (
	userid INT NOT NULL,
	userscore INT NOT NULL,
	examid INT NOT NULL);



CREATE TABLE choice (
	choiceid VARCHAR(255) NOT NULL,
	question VARCHAR(255) NOT NULL,
	answer VARCHAR(255) NOT NULL,
	choicesetid INT NOT NULL,
	constraint CHOICE_PK PRIMARY KEY (choiceid));



CREATE TABLE choiceset (
	choicesetid INT NOT NULL,
	title VARCHAR(255) NOT NULL,
	examid INT NOT NULL,
	constraint SET_PK PRIMARY KEY (choicesetid));




ALTER TABLE Exam ADD CONSTRAINT Exam_fk0 FOREIGN KEY (subjectid) REFERENCES Subjects(subjectid);
ALTER TABLE Exam ADD CONSTRAINT Exam_fk1 FOREIGN KEY (uesrid) REFERENCES Users(userid);


ALTER TABLE Score ADD CONSTRAINT Score_fk0 FOREIGN KEY (userid) REFERENCES Users(userid);
ALTER TABLE Score ADD CONSTRAINT Score_fk1 FOREIGN KEY (examid) REFERENCES Exam(examid);

ALTER TABLE choice ADD CONSTRAINT Choice_fk0 FOREIGN KEY (choicesetid) REFERENCES choiceset(choicesetid);

ALTER TABLE choiceset ADD CONSTRAINT Choiceset_fk0 FOREIGN KEY (examid) REFERENCES Exam(examid);


insert into Users (username, password, userfullname) values ('deafinealy', 'quisque', 'Deafinealy Dope');
insert into Users (username, password, userfullname) values ('babba', 'quam', 'Babba Gump');