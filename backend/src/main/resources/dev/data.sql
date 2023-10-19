TRUNCATE TABLE song;
TRUNCATE TABLE voting_song;

insert into voting_song (title, singer, length, video_id, album_cover_url, created_at)
values ('N.Y.C.T', 'NCT U', 241, '8umUXHLGl3o',
        'https://cdnimg.melon.co.kr/cm2/album/images/113/22/590/11322590_20230907111726_500.jpg?3d8bcc03a4900fdba3f199390f432b24/melon/resize/140/quality/80/optimize',
        now());

insert into voting_song (title, singer, length, video_id, album_cover_url, created_at)
values ('Slow Dancing', 'V', 190, 'eI0iTRS0Ha8',
        'https://cdnimg.melon.co.kr/cm2/album/images/113/03/638/11303638_20230811103847_500.jpg?92b308988cd1521e8bd4d9c2f56768ed/melon/resize/140/quality/80/optimize',
        now());

insert into voting_song (title, singer, length, video_id, album_cover_url, created_at)
values ('LET''S DANCE', '이채연', 222, 'kQFLWdjk_8s',
        'https://cdnimg.melon.co.kr/cm2/album/images/113/19/933/11319933_20230905152508_500.jpg?2bc0bb896e182ebb6ab11119b40657bc/melon/resize/140/quality/80/optimize',
        now());

insert into voting_song (title, singer, length, video_id, album_cover_url, created_at)
values ('Smoke (Prod. Dynamicduo, Padi)', '다이나믹 듀오, 이영지', 210, 'ZwXzaqzRVi4',
        'https://cdnimg.melon.co.kr/cm2/album/images/113/15/612/11315612_20230905120657_500.jpg?e9f1ae79ad72f3749b9678e7ebd90027/melon/resize/140/quality/80/optimize',
        now());

insert into voting_song (title, singer, length, video_id, album_cover_url, created_at)
values ('뭣 같아', 'BOYNEXTDOOR', 180, '97_-_WugRFA',
        'https://cdnimg.melon.co.kr/cm2/album/images/113/19/182/11319182_20230904102829_500.jpg?6555bb763ac1707683f5d05c0ab1b496/melon/resize/140/quality/80/optimize',
        now());

INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 1, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 1, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 1, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('해요 (2022)', '#안녕', 238, 'P6gV_t70KAk',
        'https://cdnimg.melon.co.kr/cm2/album/images/109/75/276/10975276_20220603165713_500.jpg?690c69f1d7581bed46767533175728ff/melon/resize/282/quality/80/optimize',
        'BALLAD', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 2, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 2, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 2, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('TOMBOY', '(여자)아이들', 174, '0wezH4MAncY',
        'https://cdnimg.melon.co.kr/cm2/album/images/108/90/384/10890384_20220314111504_500.jpg?4b9dba7aeba43a4e0042eedb6b9865c1/melon/resize/282/quality/80/optimize',
        'ROCK_METAL', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 3, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 3, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 3, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('다정히 내 이름을 부르면', '경서예지, 전건호', 263, 'b_6EfFZyBxY',
        'https://cdnimg.melon.co.kr/cm2/album/images/106/10/525/10610525_20210518143433_500.jpg?e8c5aa44ff6608c13fa48eb6a20e81af/melon/resize/282/quality/80/optimize',
        'BALLAD', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 4, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 4, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 4, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('That''s Hilarious', 'Charlie Puth', 146, 'F3KMndbOhIcㅍ',
        'https://cdnimg.melon.co.kr/cm2/album/images/108/44/485/10844485_20221006154824_500.jpg?b752b5ed8fad66b79e2705840630dd94/melon/resize/282/quality/80/optimize',
        'POP', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 5, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 5, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 5, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Heaven(2023)', '임재현', 279, 'fPLXgfcyoMc',
        'https://cdnimg.melon.co.kr/cm2/album/images/111/54/876/11154876_20230121133335_500.jpg?0ae26bb599c92ddd436282395563596e/melon/resize/282/quality/80/optimize',
        'BALLAD', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 6, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 6, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 6, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('당신을 만나', '김호중, 송가인', 238, 'kn_j1Ipw4DM',
        'https://cdnimg.melon.co.kr/cm2/album/images/111/54/876/11154876_20230121133335_500.jpg?0ae26bb599c92ddd436282395563596e/melon/resize/282/quality/80/optimize',
        'TROT', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 7, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 7, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 7, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('잘 지내자, 우리 (여름날 우리 X 로이킴)', '로이킴', 258, 'MbSAeRQl0Xw',
        'https://cdnimg.melon.co.kr/cm2/album/images/111/54/876/11154876_20230121133335_500.jpg?0ae26bb599c92ddd436282395563596e/melon/resize/282/quality/80/optimize',
        'INDIE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 8, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 8, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 8, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('빛이 나는 너에게', '던 (DAWN)', 175, 'wkr3S0hIXLk',
        'https://cdnimg.melon.co.kr/cm2/album/images/111/54/876/11154876_20230121133335_500.jpg?0ae26bb599c92ddd436282395563596e/melon/resize/282/quality/80/optimize',
        'FOLK_BLUES', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 9, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 9, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 9, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('파랑 (Blue Wave)', 'NCT DREAM', 189, 'NhgoqtRhb4g',
        'https://cdnimg.melon.co.kr/cm2/album/images/111/54/876/11154876_20230121133335_500.jpg?0ae26bb599c92ddd436282395563596e/melon/resize/282/quality/80/optimize',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 10, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 10, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 10, 3, now());


INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Ling Ling', '검정치마', 230, 'gjQwwWjxPaQ',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/082/976/703/82976703_1663118461097_1_600x600.JPG/dims/resize/Q_80,0',
        'INDIE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 11, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 11, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 11, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('고백', '델리 스파이스 (Deli Spice)', 323, 'BYyVDi8BpZw',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/015/027/552/15027552_1368610256849_1_600x600.JPG/dims/resize/Q_80,0',
        'INDIE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 12, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 12, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 12, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Polaroid', 'ENHYPEN', 184, 'vRdZVDWs3BI',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/082/472/258/82472258_1641790812739_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 13, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 13, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 13, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('사랑앓이', 'FTISLAND', 218, 'gnLwCb8Cz7I',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/049/974/430/49974430_1317964170310_1_600x600.JPG/dims/resize/Q_80,0',
        'ROCK_METAL', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 14, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 14, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 14, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('맞네', 'LUCY', 276, 'BRs0GGCT4bU',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/082/427/599/82427599_1638854125897_1_600x600.JPG/dims/resize/Q_80,0',
        'ROCK_METAL', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 15, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 15, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 15, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Madeleine Love', 'CHEEZE (치즈)', 218, 'EHTagN5HJKQ',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/080/580/341/80580341_1431423374354_1_600x600.JPG/dims/resize/Q_80,0',
        'INDIE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 16, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 16, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 16, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('26', '윤하 (YOUNHA)', 199, 'eUqwF1-jjwQ',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/341/257/81341257_1578293989428_1_600x600.JPG/dims/resize/Q_80,0',
        'ROCK_METAL', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 17, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 17, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 17, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('하늘 위로', 'IZ*ONE (아이즈원)', 192, 'P1jdwGsV4lk',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 18, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 18, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 18, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Super Shy', 'NewJeans', 200, 'ArmDp-zijuc',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 19, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 19, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 19, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Seven (feat. Latto) - Clean Ver.', '정국', 186, 'UUSbUBYqU_8',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'RHYTHM_AND_BLUES', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 20, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 20, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 20, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('퀸카 (Queencard)', '(여자)아이들', 162, 'VOcb6ZHxSjc',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 21, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 21, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 21, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('헤어지자 말해요', '박재정', 244, 'SrQzxD8UFdM',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'BALLAD', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 22, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 22, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 22, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('I AM', 'IVE (아이브)', 208, 'cU0JrSAyy7o',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 23, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 23, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 23, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('이브, 프시케 그리고 푸른 수염의 아내', 'LE SSERAFIM (르세라핌)', 186,
        'Ii8L0qEvfC8',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 24, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 24, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 24, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Spicy', 'aespa', 198, '1kfmWl3o8TE',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 25, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 25, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 25, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Steal The Show (From "엘리멘탈")', 'Lauv', 194, 'kUMds6XKtfY',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'POP', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 26, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 26, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 26, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('사랑은 늘 도망가', '임영웅', 273, 'pBEAzM2TRmE',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'FOLK_BLUES', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 27, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 27, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 27, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('ISTJ', 'NCT DREAM', 186, 'es60T3k-tyM',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 28, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 28, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 28, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('모래 알갱이', '임영웅', 221, '3_wOZrzmQ1o',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'BALLAD', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 29, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 29, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 29, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('UNFORGIVEN (feat. Nile Rodgers)', 'LE SSERAFIM (르세라핌)', 181,
        'fzSDGXyGTjg',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 30, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 30, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 30, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Kitsch', 'IVE (아이브)', 195, 'r572qh2__-U',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 31, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 31, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 31, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('우리들의 블루스', '임영웅', 207, 'epz-aL5RaLQ',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'FOLK_BLUES', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 32, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 32, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 32, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Candy', 'NCT DREAM', 220, 'QuaVFoBLQeg',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 33, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 33, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 33, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Hype boy', 'NewJeans', 180, 'T--6HBX2K4g',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 34, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 34, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 34, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('다시 만날 수 있을까', '임영웅', 275, 'VPDRLgfqfSs',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'FOLK_BLUES', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 35, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 35, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 35, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Broken Melodies', 'NCT DREAM', 227, 'EPsh2192sTU',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'ROCK_METAL', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 36, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 36, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 36, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Still With You', '정국', 239, 'BksBNbTIoPE',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'RHYTHM_AND_BLUES', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 37, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 37, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 37, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('무지개', '임영웅', 198, 'o8e0Qd2H1qc',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'BALLAD', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 38, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 38, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 38, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Ditto', 'NewJeans', 187, 'haCpjUXIhrI',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 39, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 39, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 39, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('London Boy', '임영웅', 289, 'ZRDuScdwEbE',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'BALLAD', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 40, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 40, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 40, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('이제 나만 믿어요', '임영웅', 274, 'y1KXYmMuZZA',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'TROT', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 41, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 41, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 41, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('아버지', '임영웅', 240, 'dbaiMJOnaB4',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'TROT', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 42, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 42, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 42, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Polaroid', '임영웅', 209, 'PVDxs6GUXSI',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'FOLK_BLUES', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 43, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 43, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 43, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Dynamite', '방탄소년단', 198, 'KhZ5DCd7m6s',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'POP', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 44, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 44, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 44, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('손오공', '세븐틴 (SEVENTEEN)', 200, 'tFPbzfU5XL4',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 45, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 45, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 45, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('인생찬가', '임영웅', 235, 'cXHduPVrcDQ',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'FOLK_BLUES', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 46, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 46, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 46, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('A bientot', '임영웅', 258, 'sZDDLUB8wQE',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'FOLK_BLUES', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 47, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 47, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 47, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('손이 참 곱던 그대', '임영웅', 197, 'OpZIaI-J0uk',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'BALLAD', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 48, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 48, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 48, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('사랑해 진짜', '임영웅', 241, 'qkledxNCNfY',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'FOLK_BLUES', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 49, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 49, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 49, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('꽃', '지수', 174, '6zM48_rBFbY',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 50, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 50, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 50, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('연애편지', '임영웅', 217, 'gSQFZvUuQ3s',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 51, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 51, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 51, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('New jeans', 'New jeans', 109, 'G8GEpK7YDl4',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 52, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 52, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 52, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('OMG', 'New jeans', 215, 'jT0Lh-N3TSg',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 53, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 53, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 53, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Butter', '방탄소년단', 165, 'Uz0PppyT7Cc',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 54, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 54, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 54, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('파랑 (Blue Wave)', 'NCT DREAM', 191, 'ZkLK4hUqqas',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 55, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 55, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 55, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Take Two', '방탄소년단', 230, '3UE-vpej_VI',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 56, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 56, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 56, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Like We Just Met', 'NCT DREAM', 210, 'eA9pwL-8wJw',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 57, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 57, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 57, 3, now());

INSERT INTO song (title, singer, length, video_id, album_cover_url, genre, created_at)
VALUES ('Yogurt Shake', 'NCT DREAM', 218, 'IUs7tOzHVJw',
        'https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/179/198/81179198_1554106431207_1_600x600.JPG/dims/resize/Q_80,0',
        'DANCE', now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (5, 5, 58, 10, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (10, 10, 58, 5, now());
INSERT INTO killing_part(start_second, length, song_id, like_count, created_at)
VALUES (100, 15, 58, 3, now());
