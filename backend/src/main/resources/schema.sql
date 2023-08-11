create table if not exists part
(
    id           bigint auto_increment,
    start_second integer      not null,
    length       varchar(255) not null check (length in ('SHORT', 'STANDARD', 'LONG')),
    song_id      bigint,
    created_at   timestamp(6) not null,
    primary key (id)
);
create table if not exists song
(
    id              bigint auto_increment,
    title           varchar(100) not null,
    singer          varchar(50)  not null,
    length          integer      not null,
    video_url       text         not null,
    album_cover_url text         not null,
    created_at      timestamp(6) not null,
    primary key (id)
);
create table if not exists vote
(
    id         bigint auto_increment,
    part_id    bigint,
    created_at timestamp(6) not null,
    primary key (id)
);
create table if not exists member
(
    id       bigint auto_increment,
    email    varchar(100) not null,
    nickname varchar(100) not null,
    primary key (id)
);
create table if not exists part_comment
(
    id         bigint auto_increment,
    part_id    bigint       not null,
    content    varchar(200) not null,
    created_at timestamp(6) not null,
    primary key (id)
);
create table if not exists voting_song_part
(
    id             bigint auto_increment,
    start_second   integer      not null,
    length         varchar(255) not null check (length in ('SHORT', 'STANDARD', 'LONG')),
    voting_song_id bigint       not null,
    created_at     timestamp(6) not null,
    primary key (id)
);
create table if not exists voting_song
(
    id              bigint auto_increment,
    title           varchar(100) not null,
    singer          varchar(50)  not null,
    length          integer      not null,
    video_url       text         not null,
    album_cover_url text         not null,
    created_at      timestamp(6) not null,
    primary key (id)
);
create table if not exists register
(
    id                  bigint auto_increment,
    voting_song_part_id bigint       not null,
    created_at          timestamp(6) not null,
    primary key (id)
);
create table if not exists killing_part
(
    id           bigint auto_increment,
    start_second integer      not null,
    length       varchar(255) not null check (length in ('SHORT', 'STANDARD', 'LONG')),
    song_id      bigint       not null,
    created_at   timestamp(6) not null,
    primary key (id)
);

create table killing_part_comment
(
    id              bigint auto_increment,
    killing_part_id bigint       not null,
    content         varchar(200) not null,
    created_at      timestamp(6) not null,
    primary key (id)
);
