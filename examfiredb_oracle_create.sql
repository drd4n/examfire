CREATE TABLE "Students" (
	"studentid" NUMBER(10, 0) NOT NULL DEFAULT "61130500078",
	"studentname" VARCHAR2(60) NOT NULL,
	"password" VARCHAR2(21) NOT NULL,
	constraint STUDENTS_PK PRIMARY KEY ("studentid"));

CREATE sequence "STUDENTS_STUDENTID_SEQ";

CREATE trigger "BI_STUDENTS_STUDENTID"
  before insert on "Students"
  for each row
begin
  select "STUDENTS_STUDENTID_SEQ".nextval into :NEW."studentid" from dual;
end;

/
CREATE TABLE "Exam" (
	"examid" NUMBER(10, 0) NOT NULL,
	"title" VARCHAR2(255) NOT NULL,
	"subjectid" VARCHAR2(35) NOT NULL,
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
	"studentid" NUMBER(10, 0) NOT NULL,
	"studentscore" NUMBER(2, 0) NOT NULL,
	"examid" NUMBER(10, 0) NOT NULL);


/
CREATE TABLE "choice" (
	"choiceid" VARCHAR2(255) NOT NULL,
	"question" VARCHAR2(255) NOT NULL,
	"answer" VARCHAR2(255) NOT NULL,
	"examid" NUMBER(10, 0) NOT NULL,
	constraint CHOICE_PK PRIMARY KEY ("choiceid"));


/

ALTER TABLE "Exam" ADD CONSTRAINT "Exam_fk0" FOREIGN KEY ("subjectid") REFERENCES "Subjects"("subjectid");


ALTER TABLE "Score" ADD CONSTRAINT "Score_fk0" FOREIGN KEY ("studentid") REFERENCES "Students"("studentid");
ALTER TABLE "Score" ADD CONSTRAINT "Score_fk1" FOREIGN KEY ("examid") REFERENCES "Exam"("examid");

ALTER TABLE "choice" ADD CONSTRAINT "choice_fk0" FOREIGN KEY ("examid") REFERENCES "Exam"("examid");

