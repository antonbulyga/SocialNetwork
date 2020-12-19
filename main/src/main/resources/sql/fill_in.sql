
use social_network;

insert into `user_profile` values (1, "1994-08-22", "MALE" ,"375255541526", "Anton", "Bulyha", "Belarus", "Minsk");
insert into `user_profile` values (2, "1995-01-22", "MALE" ,"375255541514", "Vlad", "Legkiy", "Russia", "Moskow");
insert into `user_profile` values (3, "1990-04-25", "FEMALE" ,"375255641514", "Viktoria", "Azarenko", "Ukrain", "Kiev");
insert into `user_profile` values (4, "1995-02-25", "MALE" ,"375255641364", "Stas", "Mixailov", "Russia", "Tver");
insert into `user_profile` values (5, "1998-04-19", "FEMALE" ,"375255641014", "Kim", "Chen", "China", "Uhan");

insert into roles values (1, "ROLE_USER");
insert into roles values (2, "ROLE_ADMIN");
insert into roles values (3, "ROLE_GUEST");

insert into users values (1, "ant.bly@mail.ru", "antoxa555" ,"$2y$04$ZUfJO1VFjvDKWRcY35.kOeyOxbb62sfJVl0FAg6/H0T8XTVIyoC.y", "2020-08-18", 1);
insert into users values (2, "vlad_bf@mail.ru", "vlad22","$2y$04$ZUfJO1VFjvDKWRcY35.kOeyOxbb62sfJVl0FAg6/H0T8XTVIyoC.y","2020-08-18", "2");
insert into users values (3, "vika.FF@yandex.ru", "vikall", "$2y$04$ZUfJO1VFjvDKWRcY35.kOeyOxbb62sfJVl0FAg6/H0T8XTVIyoC.y","2020-08-18", "3");
insert into users values (4, "stas@yandex.ru", "stas", "$2y$04$ZUfJO1VFjvDKWRcY35.kOeyOxbb62sfJVl0FAg6/H0T8XTVIyoC.y","2018-06-18", "4");
insert into users values (5, "kim.FF@yandex.ru", "kim", "$2y$04$ZUfJO1VFjvDKWRcY35.kOeyOxbb62sfJVl0FAg6/H0T8XTVIyoC.y","2020-08-18", "5");



insert into community values (1, "Math lover", 1);
insert into community values (2, "Zaslavl city group", 1);
insert into community values (3, "Minsk city", 2);

insert into community_has_users values (1, 3);
insert into community_has_users values (1, 1);
insert into community_has_users values (2, 2);
insert into community_has_users values (2, 3);

insert into post values (1, 1, "Look! What a beautiful dog", "2020-09-22", 3);
insert into post values (2, 1, "A very interesting mathematical example", "2020-09-22",1);
insert into post values (3, 2, "Simple example", "2020-09-22",2);

insert into likes values (1, 1, 1);
insert into likes values (2, 1, 4);
insert into likes values (3, 2, 3);


insert into dialogs values (1,"Friends dialogue", "2020-10-22");
insert into dialogs values (2,"School", "2020-11-25");
insert into dialogs values (3,"Funny dogs", "2020-11-25");

insert into dialogs_has_users values (2, 1);
insert into dialogs_has_users values (1, 2);
insert into dialogs_has_users values (3, 3);
insert into dialogs_has_users values (1, 1);

insert into messages values (1, 1, "Hey, bro! How are you?", 1, "2020-10-22");
insert into messages values (2, 2, "What about today?", 1, "2020-10-23");
insert into messages values (3, 3, "Got it!", 1, "2020-10-24");


insert into friendship values (1,"FRIEND", 1, 2, 1);
insert into friendship values (2,"REQUEST", 1, 3, 1);
insert into friendship values (3,"REQUEST", 2, 3, 2);


insert into users_has_roles values (1,2);
insert into users_has_roles values (2,1);
insert into users_has_roles values (3,1);
insert into users_has_roles values (4,1);
insert into users_has_roles values (5,3);





