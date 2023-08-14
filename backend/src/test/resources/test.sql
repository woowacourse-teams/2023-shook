drop table song;
drop table killing_part;
drop table killing_part_comment;
drop table voting_song;
drop table voting_song_part;
drop table vote;
drop table member;

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

TRUNCATE TABLE song;

INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('Super Shy', 'NewJeans', 200, 'https://youtu.be/ArmDp-zijuc',
        'http://i.maniadb.com/images/album/999/999126_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('Seven (feat. Latto) - Clean Ver.', '정국', 186, 'https://youtu.be/UUSbUBYqU_8',
        'http://i.maniadb.com/images/album/1000/000246_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('퀸카 (Queencard)', '(여자)아이들', 162, 'https://youtu.be/VOcb6ZHxSjc',
        'http://i.maniadb.com/images/album/992/992813_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('헤어지자 말해요', '박재정', 244, 'https://youtu.be/SrQzxD8UFdM',
        'http://i.maniadb.com/images/album/988/988164_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('I AM', 'IVE (아이브)', 208, 'https://youtu.be/cU0JrSAyy7o', '', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('이브, 프시케 그리고 푸른 수염의 아내', 'LE SSERAFIM (르세라핌)', 186,
        'https://youtu.be/Ii8L0qEvfC8', 'http://i.maniadb.com/images/album/989/989969_1_f.jpg',
        now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('Spicy', 'aespa', 198, 'https://youtu.be/1kfmWl3o8TE',
        'http://i.maniadb.com/images/album/990/990970_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('Steal The Show (From "엘리멘탈")', 'Lauv', 194, 'https://www.youtube.com/watch?v=kUMds6XKtfY',
        '', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('사랑은 늘 도망가', '임영웅', 273, 'https://youtu.be/pBEAzM2TRmE',
        'http://i.maniadb.com/images/album/918/918794_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('ISTJ', 'NCT DREAM', 186, 'https://youtu.be/es60T3k-tyM',
        'http://i.maniadb.com/images/album/998/998384_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('모래 알갱이', '임영웅', 221, 'https://youtu.be/3_wOZrzmQ1o',
        'http://i.maniadb.com/images/album/995/995218_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('UNFORGIVEN (feat. Nile Rodgers)', 'LE SSERAFIM (르세라핌)', 181,
        'https://www.youtube.com/watch?v=fzSDGXyGTjg',
        'http://i.maniadb.com/images/album/989/989969_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('Kitsch', 'IVE (아이브)', 195, 'https://www.youtube.com/watch?v=r572qh2__-U',
        'http://i.maniadb.com/images/album/984/984148_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('우리들의 블루스', '임영웅', 207, 'https://youtu.be/epz-aL5RaLQ',
        'http://i.maniadb.com/images/album/930/930879_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('Candy', 'NCT DREAM', 220, 'https://youtu.be/QuaVFoBLQeg',
        'http://i.maniadb.com/images/album/978/978389_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('Hype boy', 'NewJeans', 180, 'https://youtu.be/T--6HBX2K4g',
        'http://i.maniadb.com/images/album/946/946945_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('다시 만날 수 있을까', '임영웅', 275, 'https://youtu.be/VPDRLgfqfSs', '', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('Broken Melodies', 'NCT DREAM', 227, 'https://youtu.be/EPsh2192sTU',
        'http://i.maniadb.com/images/album/998/998384_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('Still With You', '정국', 239, 'https://youtu.be/BksBNbTIoPE', '', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('무지개', '임영웅', 198, 'https://youtu.be/o8e0Qd2H1qc', '', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('Ditto', 'NewJeans', 187, 'https://youtu.be/haCpjUXIhrI',
        'http://i.maniadb.com/images/album/978/978654_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('London Boy', '임영웅', 289, 'https://youtu.be/ZRDuScdwEbE', '', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('이제 나만 믿어요', '임영웅', 274, 'https://youtu.be/y1KXYmMuZZA',
        'http://i.maniadb.com/images/album_t/80/794/794139_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('아버지', '임영웅', 240, 'https://youtu.be/dbaiMJOnaB4', '', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('Polaroid', '임영웅', 209, 'https://youtu.be/PVDxs6GUXSI',
        'http://i.maniadb.com/images/album_t/80/972/972891_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('Dynamite', '방탄소년단', 198, 'https://youtu.be/KhZ5DCd7m6s',
        'http://i.maniadb.com/images/album_t/80/816/816954_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('손오공', '세븐틴 (SEVENTEEN)', 200, 'https://youtu.be/tFPbzfU5XL4',
        'http://i.maniadb.com/images/album_t/80/988/988481_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('인생찬가', '임영웅', 235, 'https://youtu.be/cXHduPVrcDQ', '', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('A bientot', '임영웅', 258, 'https://youtu.be/sZDDLUB8wQE', '', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('손이 참 곱던 그대', '임영웅', 197, 'https://youtu.be/OpZIaI-J0uk', '', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('사랑해 진짜', '임영웅', 241, 'https://youtu.be/qkledxNCNfY', '', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('꽃', '지수', 174, 'https://youtu.be/6zM48_rBFbY',
        'http://i.maniadb.com/images/album_t/80/984/984469_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('연애편지', '임영웅', 217, 'https://youtu.be/gSQFZvUuQ3s', '', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('New jeans', 'New jeans', 109, 'https://youtu.be/G8GEpK7YDl4',
        'http://i.maniadb.com/images/album_t/80/999/999126_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('OMG', 'New jeans', 215, 'https://www.youtube.com/watch?v=jT0Lh-N3TSg', '', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('Butter', '방탄소년단', 165, 'https://youtu.be/Uz0PppyT7Cc',
        'http://i.maniadb.com/images/album_t/80/856/856717_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('파랑 (Blue Wave)', 'NCT DREAM', 191, 'https://youtu.be/ZkLK4hUqqas',
        'http://i.maniadb.com/images/album_t/80/998/998384_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('Take Two', '방탄소년단', 230, 'https://youtu.be/3UE-vpej_VI',
        'http://i.maniadb.com/images/album_t/80/997/997196_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('Like We Just Met', 'NCT DREAM', 210, 'https://youtu.be/eA9pwL-8wJw',
        'http://i.maniadb.com/images/album_t/80/998/998384_1_f.jpg', now());
INSERT INTO song (title, singer, length, video_url, album_cover_url, created_at)
VALUES ('Yogurt Shake', 'NCT DREAM', 218, 'https://youtu.be/IUs7tOzHVJw',
        'http://i.maniadb.com/images/album_t/80/998/998384_1_f.jpg', now());
