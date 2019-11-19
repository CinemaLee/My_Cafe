

insert into user(`id`, `user_Id`, `password`, `name`, `email` ,`img_code`, `created_at`) values (1,'lee','123','이강우','lee@na.com','https://graph.facebook.com/v5.0/1768228496643310/picture',current_timestamp());
insert into user(`id`, `user_Id`, `password`, `name`, `email`,`img_code`, `created_at` ) values (2,'kim','123','김민수','kim@na.com','http://drive.google.com/uc?export=view&id=16WHxGxAQ6yhmr_TEZRc2lFDDW994G20a',current_timestamp());


insert into question(`id`,`title`,`contents`,`created_at`,`count_Of_Answer`, `user_idx`) values (1,'첫번째 질문','인간은 태어날때부터 악한가요?',current_timestamp() ,0,1);
insert into question(`id`,`title`,`contents`,`created_at`,`count_Of_Answer`, `user_idx`) values (2,'두번째 질문','이 세상은 고통의 연속인가요?',current_timestamp(),0,2);


