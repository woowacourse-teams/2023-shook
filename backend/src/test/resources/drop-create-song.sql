drop table if exists song;

create table if not exists song
(
    id         bigint auto_increment,
    title      varchar(100) not null,
    singer     varchar(50)  not null,
    length     integer      not null,
    video_url  text         not null,
    created_at timestamp(6) not null,
    primary key (id)
    );
