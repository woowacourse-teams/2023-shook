drop table song;
drop table killing_part;
drop table member;
drop table killing_part_like;
drop table killing_part_comment;

create table if not exists song
(
    id              bigint auto_increment,
    title           varchar(100) not null,
    singer          varchar(50)  not null,
    length          integer      not null,
    video_id        varchar(20)  not null,
    album_cover_url text         not null,
    genre           varchar(255) check (genre in
                                        ('BALLAD', 'DANCE', 'HIPHOP', 'RHYTHM_AND_BLUES', 'INDIE',
                                         'ROCK_METAL', 'TROT', 'FOLK_BLUES', 'POP', 'JAZZ',
                                         'CLASSIC', 'J_POP', 'EDM', 'ETC')),
    created_at      timestamp(6) not null,
    primary key (id)
);

create table if not exists killing_part
(
    id           bigint auto_increment,
    start_second integer      not null,
    length       integer      not null,
    song_id      bigint       not null,
    like_count   integer      not null default 0,
    created_at   timestamp(6) not null,
    primary key (id)
);

create table if not exists member
(
    id         bigint auto_increment,
    email      varchar(100) not null,
    nickname   varchar(100) not null,
    created_at timestamp(6) not null,
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

INSERT INTO song (title, singer, length, video_id, album_cover_url, created_at, genre)
VALUES ('Super Shy', 'NewJeans', 200, 'ArmDp-zijuc',
        'http://i.maniadb.com/images/album/999/999126_1_f.jpg', now(), 'DANCE');
INSERT INTO song (title, singer, length, video_id, album_cover_url, created_at, genre)
VALUES ('노래', '가수', 263, 'sjeifksl',
        'http://i.maniadb.com/images/album/29382/028492.jpg', now(), 'HIPHOP');
INSERT INTO song (title, singer, length, video_id, album_cover_url, created_at, genre)
VALUES ('Not Shy', 'NewJeans', 200, 'ArmDp-zijuc',
        'http://i.maniadb.com/images/album/999/999126_1_f.jpg', now(), 'DANCE');
INSERT INTO song (title, singer, length, video_id, album_cover_url, created_at, genre)
VALUES ('Seven (feat. Latto) - Clean Ver.', '정국', 186, 'UUSbUBYqU_8',
        'http://i.maniadb.com/images/album/1000/000246_1_f.jpg', now(), 'DANCE');

INSERT INTO killing_part (start_second, length, song_id, like_count, created_at)
VALUES (10, 5, 1, 0, now());
INSERT INTO killing_part (start_second, length, song_id, like_count, created_at)
VALUES (15, 15, 1, 0, now());
INSERT INTO killing_part (start_second, length, song_id, like_count, created_at)
VALUES (20, 10, 1, 0, now());

INSERT INTO killing_part (start_second, length, song_id, like_count, created_at)
VALUES (10, 5, 2, 0, now());
INSERT INTO killing_part (start_second, length, song_id, like_count, created_at)
VALUES (15, 15, 2, 0, now());
INSERT INTO killing_part (start_second, length, song_id, like_count, created_at)
VALUES (20, 10, 2, 0, now());

INSERT INTO killing_part (start_second, length, song_id, like_count, created_at)
VALUES (10, 5, 3, 0, now());
INSERT INTO killing_part (start_second, length, song_id, like_count, created_at)
VALUES (15, 15, 3, 0, now());
INSERT INTO killing_part (start_second, length, song_id, like_count, created_at)
VALUES (20, 10, 3, 0, now());

INSERT INTO member (email, nickname, created_at)
VALUES ('email@naver.com', 'nickname', now());
