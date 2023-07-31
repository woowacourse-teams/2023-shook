drop table part;
drop table song;
drop table vote;

create table part
(
    id           bigint auto_increment,
    start_second integer      not null,
    length       varchar(255) not null check (length in ('SHORT', 'STANDARD', 'LONG')),
    song_id      bigint,
    created_at   timestamp(6) not null,
    primary key (id)
);
create table song
(
    id         bigint auto_increment,
    title      varchar(100) not null,
    singer     varchar(50)  not null,
    length     integer      not null,
    video_url  text         not null,
    image_url  text         not null,
    created_at timestamp(6) not null,
    primary key (id)
);
create table vote
(
    id         bigint auto_increment,
    part_id    bigint,
    created_at timestamp(6) not null,
    primary key (id)
);

TRUNCATE TABLE song;

INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('Super Shy', 'NewJeans', 200, 'https://youtu.be/ArmDp-zijuc', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('Seven (feat. Latto) - Clean Ver.', '정국', 186, 'https://youtu.be/UUSbUBYqU_8',
        'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('퀸카 (Queencard)', '(여자)아이들', 162, 'https://youtu.be/VOcb6ZHxSjc', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('헤어지자 말해요', '박재정', 244, 'https://youtu.be/SrQzxD8UFdM', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('I AM', 'IVE (아이브)', 208, 'https://youtu.be/cU0JrSAyy7o', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('이브, 프시케 그리고 푸른 수염의 아내', 'LE SSERAFIM (르세라핌)', 186,
        'https://youtu.be/Ii8L0qEvfC8', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('Spicy', 'aespa', 198, 'https://youtu.be/1kfmWl3o8TE', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('Steal The Show (From “엘리멘탈”)', 'Lauv', 194, 'https://www.youtube.com/watch?v=kUMds6XKtfY',
        'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('사랑은 늘 도망가', '임영웅', 273, 'https://youtu.be/pBEAzM2TRmE', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('ISTJ', 'NCT DREAM', 186, 'https://youtu.be/es60T3k-tyM', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('모래 알갱이', '임영웅', 221, 'https://youtu.be/3_wOZrzmQ1o', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('UNFORGIVEN (feat. Nile Rodgers)', 'LE SSERAFIM (르세라핌)', 181,
        'https://www.youtube.com/watch?v=fzSDGXyGTjg', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('Kitsch', 'IVE (아이브)', 195, 'https://www.youtube.com/watch?v=r572qh2__-U', 'image_url',
        now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('우리들의 블루스', '임영웅', 207, 'https://youtu.be/epz-aL5RaLQ', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('Candy', 'NCT DREAM', 220, 'https://youtu.be/QuaVFoBLQeg', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('Hype boy', 'NewJeans', 180, 'https://youtu.be/T--6HBX2K4g', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('다시 만날 수 있을까', '임영웅', 275, 'https://youtu.be/VPDRLgfqfSs', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('Broken Melodies', 'NCT DREAM', 227, 'https://youtu.be/EPsh2192sTU', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('Still With You', '정국', 239, 'https://youtu.be/BksBNbTIoPE', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('무지개', '임영웅', 198, 'https://youtu.be/o8e0Qd2H1qc', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('Ditto', 'NewJeans', 187, 'https://youtu.be/haCpjUXIhrI', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('London Boy', '임영웅', 289, 'https://youtu.be/ZRDuScdwEbE', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('이제 나만 믿어요', '임영웅', 274, 'https://youtu.be/y1KXYmMuZZA', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('아버지', '임영웅', 240, 'https://youtu.be/dbaiMJOnaB4', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('Polaroid', '임영웅', 209, 'https://youtu.be/PVDxs6GUXSI', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('Dynamite', '방탄소년단', 198, 'https://youtu.be/KhZ5DCd7m6s', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('손오공', '세븐틴 (SEVENTEEN)', 200, 'https://youtu.be/tFPbzfU5XL4', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('인생찬가', '임영웅', 235, 'https://youtu.be/cXHduPVrcDQ', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('A bientot', '임영웅', 258, 'https://youtu.be/sZDDLUB8wQE', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('손이 참 곱던 그대', '임영웅', 197, 'https://youtu.be/OpZIaI-J0uk', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('사랑해 진짜', '임영웅', 241, 'https://youtu.be/qkledxNCNfY', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('꽃', '지수', 174, 'https://youtu.be/6zM48_rBFbY', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('연애편지', '임영웅', 217, 'https://youtu.be/gSQFZvUuQ3s', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('New jeans', 'New jeans', 109, 'https://youtu.be/G8GEpK7YDl4', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('OMG', 'New jeans', 215, 'https://www.youtube.com/watch?v=jT0Lh-N3TSg', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('Butter', '방탄소년단', 165, 'https://youtu.be/Uz0PppyT7Cc', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('파랑 (Blue Wave)', 'NCT DREAM', 191, 'https://youtu.be/ZkLK4hUqqas', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('Take Two', '방탄소년단', 230, 'https://youtu.be/3UE-vpej_VI', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('Like We Just Met', 'NCT DREAM', 210, 'https://youtu.be/eA9pwL-8wJw', 'image_url', now());
INSERT INTO song (title, singer, length, video_url, image_url, created_at)
VALUES ('Yogurt Shake', 'NCT DREAM', 218, 'https://youtu.be/IUs7tOzHVJw', 'image_url', now());
