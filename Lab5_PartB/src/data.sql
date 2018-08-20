insert into password values('11','11');
insert into password values('12','12');
insert into password values('13','13');
insert into password values('14','14');

insert into users values('11','ram','91');
insert into users values('12','shyam','92');
insert into users values('13','geeta','93');
insert into users values('14','sam','94');

insert into conversations values('11','12');
insert into conversations values('11','13');
insert into conversations values('13','12');

insert into posts(thread_id,uid, timestamp,text) values (1,'11',now(),'hi');
insert into posts(thread_id,uid, timestamp,text) values (1,'12',now(),'hello');
insert into posts(thread_id,uid, timestamp,text) values (1,'12',now(),'bye, busy');

insert into posts(thread_id,uid, timestamp,text) values (2,'11',now(),'hi');
insert into posts(thread_id,uid, timestamp,text) values (2,'13',now(),'hiii');
insert into posts(thread_id,uid, timestamp,text) values (2,'13',now(),'how u doing');
insert into posts(thread_id,uid, timestamp,text) values (2,'11',now(),'fine');

insert into posts(thread_id,uid, timestamp,text) values (3,'12',now(),'hi');
insert into posts(thread_id,uid, timestamp,text) values (3,'12',now(),'name');
insert into posts(thread_id,uid, timestamp,text) values (3,'13',now(),'hello');
insert into posts(thread_id,uid, timestamp,text) values (3,'13',now(),'geeta');

