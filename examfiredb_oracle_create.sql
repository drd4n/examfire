CREATE TABLE "Users" (
	"userid" NUMBER(10, 0) NOT NULL DEFAULT "61130500078",
	"userfullname" VARCHAR2(60) NOT NULL,
	"password" VARCHAR2(21) NOT NULL,
	constraint USERS_PK PRIMARY KEY ("userid"));

CREATE sequence "USERS_USERID_SEQ";

CREATE trigger "BI_USERS_USERID"
  before insert on "Users"
  for each row
begin
  select "USERS_USERID_SEQ".nextval into :NEW."userid" from dual;
end;

/
CREATE TABLE "Exam" (
	"examid" NUMBER(10, 0) NOT NULL,
	"subjectid" VARCHAR2(35) NOT NULL,
	"uesrid" NUMBER(10, 0) NOT NULL,
	constraint EXAM_PK PRIMARY KEY ("examid"));

CREATE sequence "EXAM_EXAMID_SEQ";

CREATE trigger "BI_EXAM_EXAMID"
  before insert on "Exam"
  for each row
begin
  select "EXAM_EXAMID_SEQ".nextval into :NEW."examid" from dual;
end;

/
CREATE TABLE "Subjects" (
	"subjectid" NUMBER(10, 0) NOT NULL,
	"subjectname" VARCHAR2(25) NOT NULL,
	constraint SUBJECTS_PK PRIMARY KEY ("subjectid"));

CREATE sequence "SUBJECTS_SUBJECTID_SEQ";

CREATE trigger "BI_SUBJECTS_SUBJECTID"
  before insert on "Subjects"
  for each row
begin
  select "SUBJECTS_SUBJECTID_SEQ".nextval into :NEW."subjectid" from dual;
end;

/
CREATE TABLE "Score" (
	"userid" NUMBER(10, 0) NOT NULL,
	"userscore" NUMBER(2, 0) NOT NULL,
	"examid" NUMBER(10, 0) NOT NULL);


/
CREATE TABLE "choice" (
	"choiceid" VARCHAR2(255) NOT NULL,
	"question" VARCHAR2(255) NOT NULL,
	"answer" VARCHAR2(255) NOT NULL,
	"setid" NUMBER(10, 0) NOT NULL,
	constraint CHOICE_PK PRIMARY KEY ("choiceid"));


/
CREATE TABLE "Set" (
	"setid" NUMBER(10, 0) NOT NULL,
	"title" VARCHAR2(255) NOT NULL,
	"examid" NUMBER(10, 0) NOT NULL,
	constraint SET_PK PRIMARY KEY ("setid"));

CREATE sequence "SET_SETID_SEQ";

CREATE trigger "BI_SET_SETID"
  before insert on "Set"
  for each row
begin
  select "SET_SETID_SEQ".nextval into :NEW."setid" from dual;
end;

/

ALTER TABLE "Exam" ADD CONSTRAINT "Exam_fk0" FOREIGN KEY ("subjectid") REFERENCES "Subjects"("subjectid");
ALTER TABLE "Exam" ADD CONSTRAINT "Exam_fk1" FOREIGN KEY ("uesrid") REFERENCES "Users"("userid");


ALTER TABLE "Score" ADD CONSTRAINT "Score_fk0" FOREIGN KEY ("userid") REFERENCES "Users"("userid");
ALTER TABLE "Score" ADD CONSTRAINT "Score_fk1" FOREIGN KEY ("examid") REFERENCES "Exam"("examid");

ALTER TABLE "choice" ADD CONSTRAINT "choice_fk0" FOREIGN KEY ("setid") REFERENCES "Set"("setid");

ALTER TABLE "Set" ADD CONSTRAINT "Set_fk0" FOREIGN KEY ("examid") REFERENCES "Exam"("examid");

