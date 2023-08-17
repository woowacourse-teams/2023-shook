TRUNCATE TABLE song;
TRUNCATE TABLE voting_song;

insert into voting_song (title, singer, length, video_id, album_cover_url, created_at)
values ('달빛소년', '체리필터 (cherryfilter)', 241, 'MENOHM2a8Oo', 'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/015/027/236/15027236_1319188420285_1_600x600.JPG/dims/resize/Q_80,0', now());

insert into voting_song (title, singer, length, video_id, album_cover_url, created_at)
values ('Happy Day', '체리필터 (cherryfilter)', 216, '6CFs-4if788', 'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/041/719/050/41719050_1319703431175_1_600x600.JPG/dims/resize/Q_80,0', now());

insert into voting_song (title, singer, length, video_id, album_cover_url, created_at)
values ('낭만 고양이', '체리필터 (cherryfilter)', 228, 'Nh5Ld4EpXJs', 'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/015/026/138/15026138_1406191371950_1_600x600.JPG/dims/resize/Q_80,0', now());

insert into voting_song (title, singer, length, video_id, album_cover_url, created_at)
values ('오리날다 (영화 ''권순분여사 납치사건'' CF 테마송)', '체리필터 (cherryfilter)', 246, 'ivbdRCDCOig', 'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/015/027/236/15027236_1319188420285_1_600x600.JPG/dims/resize/Q_80,0', now());

insert into voting_song (title, singer, length, video_id, album_cover_url, created_at)
values ('피아니시모 (Pianissimo)', '체리필터 (cherryfilter)', 229, 'VxnWkErj2TE', 'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/079/949/993/79949993_1398756703913_1_600x600.JPG/dims/resize/Q_80,0', now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, created_at)
VALUES ('취중고백', '김민석 (멜로망스)', 258, 'FCrMKhrFH7A',
        'https://cdnimg.melon.co.kr/cm2/album/images/108/16/959/10816959_20211217144957_500.jpg?c1818ddc493cb2bbb4d268431e6de7b5/melon/resize/282/quality/80/optimize',
        now());

INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 'SHORT', 1, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 'STANDARD', 1, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 'LONG', 1, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, created_at)
VALUES ('해요 (2022)', '#안녕', 238, 'P6gV_t70KAk', 'https://cdnimg.melon.co.kr/cm2/album/images/109/75/276/10975276_20220603165713_500.jpg?690c69f1d7581bed46767533175728ff/melon/resize/282/quality/80/optimize', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 'SHORT', 2, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 'STANDARD', 2, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 'LONG', 2, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, created_at)
VALUES ('TOMBOY', '(여자)아이들', 174, '0wezH4MAncY', 'https://cdnimg.melon.co.kr/cm2/album/images/108/90/384/10890384_20220314111504_500.jpg?4b9dba7aeba43a4e0042eedb6b9865c1/melon/resize/282/quality/80/optimize', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 'SHORT', 3, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 'STANDARD', 3, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 'LONG', 3, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, created_at)
VALUES ('다정히 내 이름을 부르면', '경서예지, 전건호', 263, 'b_6EfFZyBxY', 'https://cdnimg.melon.co.kr/cm2/album/images/106/10/525/10610525_20210518143433_500.jpg?e8c5aa44ff6608c13fa48eb6a20e81af/melon/resize/282/quality/80/optimize', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 'SHORT', 4, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 'STANDARD', 4, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 'LONG', 4, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, created_at)
VALUES ('That''s Hilarious', 'Charlie Puth', 146, 'F3KMndbOhIcㅍ', 'https://cdnimg.melon.co.kr/cm2/album/images/108/44/485/10844485_20221006154824_500.jpg?b752b5ed8fad66b79e2705840630dd94/melon/resize/282/quality/80/optimize', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 'SHORT', 5, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 'STANDARD', 5, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 'LONG', 5, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, created_at)
VALUES ('Heaven(2023)', '임재현', 279, 'fPLXgfcyoMc', 'https://cdnimg.melon.co.kr/cm2/album/images/111/54/876/11154876_20230121133335_500.jpg?0ae26bb599c92ddd436282395563596e/melon/resize/282/quality/80/optimize', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 'SHORT', 6, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 'STANDARD', 6, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 'LONG', 6, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, created_at)
VALUES ('당신을 만나', '김호중, 송가인', 238, 'kn_j1Ipw4DM', 'https://cdnimg.melon.co.kr/cm2/album/images/111/54/876/11154876_20230121133335_500.jpg?0ae26bb599c92ddd436282395563596e/melon/resize/282/quality/80/optimize', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 'SHORT', 7, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 'STANDARD', 7, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 'LONG', 7, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, created_at)
VALUES ('잘 지내자, 우리 (여름날 우리 X 로이킴)', '로이킴', 258, 'MbSAeRQl0Xw', 'https://cdnimg.melon.co.kr/cm2/album/images/111/54/876/11154876_20230121133335_500.jpg?0ae26bb599c92ddd436282395563596e/melon/resize/282/quality/80/optimize', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 'SHORT', 8, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 'STANDARD', 8, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 'LONG', 8, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, created_at)
VALUES ('빛이 나는 너에게', '던 (DAWN)', 175, 'wkr3S0hIXLk', 'https://cdnimg.melon.co.kr/cm2/album/images/111/54/876/11154876_20230121133335_500.jpg?0ae26bb599c92ddd436282395563596e/melon/resize/282/quality/80/optimize', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 'SHORT', 9, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 'STANDARD', 9, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 'LONG', 9, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, created_at)
VALUES ('파랑 (Blue Wave)', 'NCT DREAM', 189, 'NhgoqtRhb4g', 'https://cdnimg.melon.co.kr/cm2/album/images/111/54/876/11154876_20230121133335_500.jpg?0ae26bb599c92ddd436282395563596e/melon/resize/282/quality/80/optimize', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 'SHORT', 10, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 'STANDARD', 10, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 'LONG', 10, 3, now());
