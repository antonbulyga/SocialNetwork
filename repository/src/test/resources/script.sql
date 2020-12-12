CREATE SCHEMA IF NOT EXISTS social_network;
create table dialogs
(
    id            int auto_increment
        primary key,
    name          varchar(45) not null,
    time_creation datetime    not null
);

create table roles
(
    id   int auto_increment
        primary key,
    name varchar(45) not null
);

create table token
(
    id           int auto_increment
        primary key,
    token_number varchar(200) not null
);

create table user_profile
(
    id            int auto_increment
        primary key,
    date_of_birth date                    not null,
    gender        enum ('FEMALE', 'MALE') not null,
    phone         varchar(45)             not null,
    first_name    varchar(45)             not null,
    last_name     varchar(45)             not null,
    country       varchar(45)             not null,
    city          varchar(45)             not null
);

create table users
(
    id              int auto_increment
        primary key,
    email           varchar(60)  not null,
    user_name       varchar(50)  not null,
    password        varchar(200) not null,
    creation_time   datetime     not null,
    user_profile_id int          null,
    constraint fk_users_user_profile1
        foreign key (user_profile_id) references user_profile (id)
            on update cascade on delete cascade
);

create table community
(
    id       int auto_increment
        primary key,
    name     varchar(45) not null,
    admin_id int         not null,
    constraint fk_community_users1
        foreign key (admin_id) references users (id)
);

create index fk_community_users1_idx
    on community (admin_id);

create table community_has_users
(
    community_id int not null,
    users_id     int not null,
    primary key (community_id, users_id),
    constraint fk_community_has_users_community1
        foreign key (community_id) references community (id)
            on update cascade on delete cascade,
    constraint fk_community_has_users_users1
        foreign key (users_id) references users (id)
            on update cascade on delete cascade
);

create index fk_community_has_users_community1_idx
    on community_has_users (community_id);

create index fk_community_has_users_users1_idx
    on community_has_users (users_id);

create table dialogs_has_users
(
    dialogs_id int not null,
    users_id   int not null,
    primary key (dialogs_id, users_id),
    constraint fk_dialogs_has_users_dialogs1
        foreign key (dialogs_id) references dialogs (id)
            on update cascade on delete cascade,
    constraint fk_dialogs_has_users_users1
        foreign key (users_id) references users (id)
            on update cascade on delete cascade
);

create index fk_dialogs_has_users_dialogs1_idx
    on dialogs_has_users (dialogs_id);

create index fk_dialogs_has_users_users1_idx
    on dialogs_has_users (users_id);

create table friendship
(
    id             int auto_increment
        primary key,
    status         enum ('REQUEST', 'FRIEND', 'BLOCKED', 'DECLINED') not null,
    user_one_id    int                                               not null,
    user_two_id    int                                               not null,
    action_user_id int                                               not null,
    constraint id_box_elements
        unique (user_one_id, user_two_id),
    constraint fk_friendship_users1
        foreign key (user_one_id) references users (id)
            on update cascade on delete cascade,
    constraint fk_friendship_users2
        foreign key (user_two_id) references users (id)
            on update cascade on delete cascade,
    constraint fk_friendship_users3
        foreign key (action_user_id) references users (id)
);

create index fk_friendship_users1_idx
    on friendship (user_one_id);

create index fk_friendship_users2_idx
    on friendship (user_two_id);

create index fk_friendship_users3_idx
    on friendship (action_user_id);

create table messages
(
    id            int auto_increment
        primary key,
    user_id       int          not null,
    message       varchar(500) not null,
    dialog_id     int          not null,
    creation_time datetime     not null,
    constraint fk_message_users2
        foreign key (user_id) references users (id),
    constraint fk_messages_dialogs1
        foreign key (dialog_id) references dialogs (id)
);

create index fk_message_users2_idx
    on messages (user_id);

create index fk_messages_dialogs1_idx
    on messages (dialog_id);

create table post
(
    id               int auto_increment
        primary key,
    users_id         int          not null,
    text             varchar(500) not null,
    date_of_creation datetime     not null,
    community_id     int          null,
    constraint fk_post_community1
        foreign key (community_id) references community (id)
            on update cascade on delete cascade,
    constraint fk_post_users1
        foreign key (users_id) references users (id)
            on update cascade on delete cascade
);

create table likes
(
    id       int auto_increment
        primary key,
    post_id  int not null,
    users_id int not null,
    constraint fk_like_post1
        foreign key (post_id) references post (id),
    constraint fk_like_users1
        foreign key (users_id) references users (id)
            on update cascade on delete cascade
);

create index fk_like_post1_idx
    on likes (post_id);

create index fk_like_users1_idx
    on likes (users_id);

create index fk_post_community1_idx
    on post (community_id);

create index fk_post_users1_idx
    on post (users_id);

create index fk_users_user_profile1_idx
    on users (user_profile_id);

create table users_has_roles
(
    users_id int not null,
    roles_id int not null,
    primary key (users_id, roles_id),
    constraint fk_users_has_roles_roles1
        foreign key (roles_id) references roles (id),
    constraint fk_users_has_roles_users1
        foreign key (users_id) references users (id)
);

create index fk_users_has_roles_roles1_idx
    on users_has_roles (roles_id);

create index fk_users_has_roles_users1_idx
    on users_has_roles (users_id);

