insert into Users (username, password, userfullname, email) values ('deafinealy', 'quisque', 'Deafinealy Dope', 'dopeme@hotmail.co.th');
insert into Users (username, password, userfullname, email) values ('babba', 'quam', 'Babba Gump', 'babbagumpcompany@gmail.com');

insert into Exam (examtitle) values ('Basic Chemical');
insert into Exam (examtitle) values ('English M.3');

insert into choiceset (title,examid) values ('Match the Chemical symbol and Name of Element',1);
insert into choice (question,answer,choicesetid) values ('Ag','Sliver',1);
insert into choice (question,answer,choicesetid) values ('Al','Aluminium',1);
insert into choice (question,answer,choicesetid) values ('Au','Gold',1);
insert into choice (question,answer,choicesetid) values ('Ar','Argon',1);
insert into choiceset (title,examid) values ('Match Chemical Elements and their benefits',1);
insert into choice (question,answer,choicesetid) values ('N','Is a component of fertilizer',2);
insert into choice (question,answer,choicesetid) values ('Cu','Is a component of electrical wires',2);
insert into choice (question,answer,choicesetid) values ('B','Is a component of cleaning liquid',2);
insert into choice (question,answer,choicesetid) values ('Zn','Is a component of batteries',2);

insert into choiceset (title,examid) values ('Select words into blanks To complete the sentence',2);
insert into choice (question,answer,choicesetid) values ('Jenna : Can I get you a coffee? Christ : ___.','No, thanks. I’ve just had one.',3);
insert into choice (question,answer,choicesetid) values ('Bob : Would you like a Coke? Suda : ___. I’m thirsty.','Yes, please.',3);
insert into choice (question,answer,choicesetid) values ('Bill never goes to parties, ___ ?','does he',3);
insert into choice (question,answer,choicesetid) values ('Look, the keeper ___ the lions.','is feeding',3);
insert into choiceset (title,examid) values ('Select words into blanks To complete the sentence',2);
insert into choice (question,answer,choicesetid) values ('Let’s go out for a walk, ___ ? ','shall we',4);
insert into choice (question,answer,choicesetid) values ('The captain ordered the crew ___ the ship.','to abandon',4);
insert into choice (question,answer,choicesetid) values ('Yes, I burst out ___ when I saw his duck .','laugh',4);
insert into choice (question,answer,choicesetid) values ('She ___ the piano when there was a knock at the door.','was playing',4);