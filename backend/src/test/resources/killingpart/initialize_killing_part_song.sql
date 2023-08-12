drop table song;
drop table killing_part;
drop table killing_part_comment;

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

create table if not exists killing_part_comment
(
    id              bigint auto_increment,
    killing_part_id bigint       not null,
    content         varchar(200) not null,
    created_at      timestamp(6) not null,
    primary key (id)
);

INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('Super Shy', 'NewJeans', 200, 'https://youtu.be/ArmDp-zijuc',
        'http://i.maniadb.com/images/album/999/999126_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('Seven (feat. Latto) - Clean Ver.', '정국', 186, 'https://youtu.be/UUSbUBYqU_8',
        'http://i.maniadb.com/images/album/1000/000246_1_f.jpg', now());

INSERT INTO killing_part (start_second, length, song_id, created_at) VALUES (10, 'SHORT', 1, now());
INSERT INTO killing_part (start_second, length, song_id, created_at) VALUES (15, 'LONG', 1, now());
INSERT INTO killing_part (start_second, length, song_id, created_at) VALUES (20, 'STANDARD', 1, now());
