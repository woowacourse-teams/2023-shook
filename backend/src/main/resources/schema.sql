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
    created_at      timestamp(6) not null,
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
create table if not exists voting_song_part
(
    id             bigint auto_increment,
    start_second   integer      not null,
    length         varchar(255) not null check (length in ('SHORT', 'STANDARD', 'LONG')),
    voting_song_id bigint       not null,
    created_at     timestamp(6) not null,
    primary key (id)
);
create table if not exists vote
(
    id                  bigint auto_increment,
    voting_song_part_id bigint       not null,
    created_at          timestamp(6) not null,
    primary key (id)
);

create table if not exists member
(
    id       bigint auto_increment,
    email    varchar(100) not null,
    nickname varchar(100) not null,
    primary key (id)
);

alter table killing_part
    add column like_count integer not null;
alter table killing_part_comment
    add column member_id bigint not null;
alter table song
    change video_url video_id varchar(20) not null;
alter table voting_song
    change video_url video_id varchar(20) not null;
alter table killing_part
    alter column like_count set default 0;
alter table member
    add column created_at timestamp(6) not null;
alter table song
    add column genre varchar(255) check
        (genre in ('BALLAD', 'DANCE', 'HIPHOP', 'RHYTHM_AND_BLUES', 'INDIE', 'ROCK_METAL', 'TROT',
                   'FOLK_BLUES', 'POP', 'JAZZ', 'CLASSIC', 'J_POP', 'EDM', 'ETC'));
alter table voting_song_part
    add column member_id bigint not null;
