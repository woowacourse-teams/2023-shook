drop table if exists song;
drop table if exists killing_part;
drop table if exists killing_part_like;
drop table if exists killing_part_comment;
drop table if exists voting_song_part;
drop table if exists voting_song;
drop table if exists vote;
drop table if exists member;
drop table if exists member_part;
drop table if exists artist;
drop table if exists artist_synonym;

create table if not exists song
(
    id              bigint auto_increment,
    title           varchar(100) not null,
    video_id        varchar(20)  not null,
    album_cover_url text         not null,
    length          integer      not null,
    genre           varchar(255) check (genre in
                                        ('BALLAD', 'DANCE', 'HIPHOP', 'RHYTHM_AND_BLUES', 'INDIE',
                                         'ROCK_METAL', 'TROT', 'FOLK_BLUES', 'POP', 'JAZZ',
                                         'CLASSIC', 'J_POP', 'EDM', 'ETC')),
    score           integer      not null default 0,
    created_at      timestamp(6) not null,
    primary key (id)
);

create table if not exists artist
(
    id                bigint auto_increment,
    name              varchar(50)  not null,
    profile_image_url text         not null,
    created_at        timestamp(6) not null,
    primary key (id)
);

create table if not exists song_artist
(
    id        bigint auto_increment,
    song_id   bigint not null,
    artist_id bigint not null,
    primary key (id)
);

create table if not exists part
(
    id           bigint auto_increment,
    length       integer      not null,
    song_id      bigint       not null,
    start_second integer      not null,
    count        integer      not null default 0,
    created_at   timestamp(6) not null,
    primary key (id)
);

-- TODO: updated_at 추가하기
create table if not exists member
(
    id         bigint auto_increment,
    identifier varchar(100) not null,
    nickname   varchar(100) not null,
    created_at timestamp(6) not null,
    primary key (id)
);

create table if not exists member_part
(
    id         bigint auto_increment,
    song_id    bigint       not null,
    part_id    bigint       not null,
    member_id  bigint       not null,
    created_at timestamp(6) not null,
    primary key (id)
);


create table if not exists part_like
(
    id         bigint auto_increment,
    is_deleted boolean      not null,
    part_id    bigint       not null,
    member_id  bigint       not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    primary key (id)
);

create table if not exists part_comment
(
    id         bigint auto_increment,
    content    varchar(200) not null,
    part_id    bigint       not null,
    member_id  bigint       not null,
    created_at timestamp(6) not null,
    primary key (id)
);


create table if not exists synonym
(
    id        bigint auto_increment,
    target_id bigint       not null,
    type      varchar(255) check (type in
                                  ('ARTIST', 'SONG')),
    value   varchar(255) not null,
    primary key (id)
);
