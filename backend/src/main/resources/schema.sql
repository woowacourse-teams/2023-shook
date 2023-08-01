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
    id         bigint auto_increment,
    title      varchar(100) not null,
    singer     varchar(50)  not null,
    length     integer      not null,
    video_url  text         not null,
    image_url  text         not null,
    created_at timestamp(6) not null,
    primary key (id)
);
create table if not exists vote
(
    id         bigint auto_increment,
    part_id    bigint,
    created_at timestamp(6) not null,
    primary key (id)
)
