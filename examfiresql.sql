CREATE TABLE exam (
    examid               VARCHAR2(4) NOT NULL,
    typeid               INTEGER,
    examtype_typeid      INTEGER NOT NULL,
    students_studentid   VARCHAR2(10) NOT NULL
);

CREATE UNIQUE INDEX exam__idx ON
    exam (
        examtype_typeid
    ASC );

ALTER TABLE exam ADD CONSTRAINT exam_pk PRIMARY KEY ( examid );

CREATE TABLE examtype (
    typeid     INTEGER NOT NULL,
    typename   VARCHAR2(10)
);

ALTER TABLE examtype ADD CONSTRAINT examtype_pk PRIMARY KEY ( typeid );

CREATE TABLE students (
    studentid   VARCHAR2(10) NOT NULL,
    name        VARCHAR2(55),
    password    VARCHAR2(21)
);

ALTER TABLE students ADD CONSTRAINT students_pk PRIMARY KEY ( studentid );

ALTER TABLE exam
    ADD CONSTRAINT exam_examtype_fk FOREIGN KEY ( examtype_typeid )
        REFERENCES examtype ( typeid );

ALTER TABLE exam
    ADD CONSTRAINT exam_students_fk FOREIGN KEY ( students_studentid )
        REFERENCES students ( studentid );