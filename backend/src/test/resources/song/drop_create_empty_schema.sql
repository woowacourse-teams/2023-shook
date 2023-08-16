drop table if exists song;
drop table if exists killing_part;
drop table if exists killing_part_like;
drop table if exists killing_part_comment;
drop table if exists member;

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

create table if not exists killing_part
(
    id           bigint auto_increment,
    start_second integer      not null,
    length       varchar(255) not null check (length in ('SHORT', 'STANDARD', 'LONG')),
    song_id      bigint       not null,
    like_count   integer      not null,
    created_at   timestamp(6) not null,
    primary key (id)
    );

create table if not exists killing_part_like
(
    id              bigint auto_increment,
    killing_part_id bigint       not null,
    member_id       bigint       not null,
    is_deleted      boolean      not null,
    created_at      timestamp(6) not null,
    updated_at      timestamp(6) not null,
    primary key (id)
    );

create table if not exists killing_part_comment
(
    id              bigint auto_increment,
    killing_part_id bigint       not null,
    content         varchar(200) not null,
    member_id       bigint       not null,
    created_at      timestamp(6) not null,
    primary key (id)
    );

create table if not exists member
(
    id       bigint auto_increment,
    email    varchar(100) not null,
    nickname varchar(100) not null,
    primary key (id)
    );
