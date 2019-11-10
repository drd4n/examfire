CREATE TABLE "Users" (
	"userid" INT NOT NULL UNIQUE,
	"userfullname" VARCHAR(60) NOT NULL,
	"password" VARCHAR(21) NOT NULL,
	constraint USERS_PK PRIMARY KEY ("userid"));



CREATE TABLE "Exam" (
	"examid" INT NOT NULL,
	"subjectid" VARCHAR(35) NOT NULL,
	"uesrid" INT NOT NULL,
	constraint EXAM_PK PRIMARY KEY ("examid"));



CREATE TABLE "Subjects" (
	"subjectid" INT NOT NULL,
	"subjectname" VARCHAR(25) NOT NULL,
	constraint SUBJECTS_PK PRIMARY KEY ("subjectid"));



CREATE TABLE "Score" (
	"userid" INT NOT NULL,
	"userscore" INT NOT NULL,
	"examid" INT NOT NULL);



CREATE TABLE "choice" (
	"choiceid" VARCHAR(255) NOT NULL,
	"question" VARCHAR(255) NOT NULL,
	"answer" VARCHAR(255) NOT NULL,
	"setid" INT NOT NULL,
	constraint CHOICE_PK PRIMARY KEY ("choiceid"));



CREATE TABLE "Set" (
	"setid" INT NOT NULL,
	"title" VARCHAR(255) NOT NULL,
	"examid" INT NOT NULL,
	constraint SET_PK PRIMARY KEY ("setid"));




ALTER TABLE "Exam" ADD CONSTRAINT "Exam_fk0" FOREIGN KEY ("subjectid") REFERENCES "Subjects"("subjectid");
ALTER TABLE "Exam" ADD CONSTRAINT "Exam_fk1" FOREIGN KEY ("uesrid") REFERENCES "Users"("userid");


ALTER TABLE "Score" ADD CONSTRAINT "Score_fk0" FOREIGN KEY ("userid") REFERENCES "Users"("userid");
ALTER TABLE "Score" ADD CONSTRAINT "Score_fk1" FOREIGN KEY ("examid") REFERENCES "Exam"("examid");

ALTER TABLE "choice" ADD CONSTRAINT "choice_fk0" FOREIGN KEY ("setid") REFERENCES "Set"("setid");

ALTER TABLE "Set" ADD CONSTRAINT "Set_fk0" FOREIGN KEY ("examid") REFERENCES "Exam"("examid");

