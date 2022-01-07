create table role
(
    role_id   int auto_increment
        primary key,
    role_name varchar(45) null
);

create table useri
(
    user_id  int auto_increment
        primary key,
    username varchar(45) null,
    password varchar(45) null,
    name     varchar(45) null,
    surname  varchar(45) null,
    role_id  int         null,
    constraint user_role_role_id_fk
        foreign key (role_id) references role (role_id)
);

create table course
(
    course_id   int auto_increment
        primary key,
    course_name varchar(100) null,
    user_id     int          null,
    constraint course_useri_user_id_fk
        foreign key (user_id) references useri (user_id)
            on delete cascade
);

create table course_user
(
    course_id int null,
    user_id   int null,
    constraint course_user_course_course_id_fk
        foreign key (course_id) references course (course_id)
            on delete cascade,
    constraint course_user_useri_user_id_fk
        foreign key (user_id) references useri (user_id)
            on delete cascade
);

create table message
(
    from_uid int          null,
    to_uid   int          null,
    message  varchar(300) null,
    constraint message_useri_user_id_fk
        foreign key (from_uid) references useri (user_id)
            on update cascade on delete cascade,
    constraint message_useri_user_id_fk_2
        foreign key (to_uid) references useri (user_id)
            on delete cascade
);

create table test
(
    test_id     int auto_increment
        primary key,
    description varchar(50) null,
    course_id   int         null,
    constraint test_course_course_id_fk
        foreign key (course_id) references course (course_id)
            on delete cascade
);

create table question
(
    question_id   int auto_increment
        primary key,
    question_text varchar(100) null,
    test_id       int          null,
    correct       tinyint(1)   null,
    constraint question_test_test_id_fk
        foreign key (test_id) references test (test_id)
            on delete cascade
);

create table test_question_answer
(
    test_id     int        null,
    question_id int        null,
    answer      tinyint(1) null,
    user_id     int        null,
    constraint test_question_answer_question_question_id_fk
        foreign key (question_id) references question (question_id)
            on update cascade on delete cascade,
    constraint test_question_answer_test_test_id_fk
        foreign key (test_id) references test (test_id)
            on update cascade on delete cascade,
    constraint test_question_answer_user_user_id_fk
        foreign key (user_id) references useri (user_id)
            on update cascade on delete cascade
);

create index test_question_answer_user_user_id_fk_idx
    on test_question_answer (user_id);


