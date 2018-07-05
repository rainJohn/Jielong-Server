

create table distribution(
 id int(11) primary key auto_increment,
 to_user int(11) comment '奖励给的用户',
 from_user int(11) comment '来自哪个用户',
 order_num varchar(20) comment '相关订单编号',
 dist_level int(11) comment '分销等级，1一级，2二级',
 dist_money int(20) comment '奖励金额，单位分',
 create_time timestamp default '0000-00-00 00:00:00' comment '创建时间',
 update_time timestamp default current_timestamp  on update current_timestamp comment '更新时间'
);

insert into distribution(to_user,from_user,order_num,dist_level,dist_money,create_time) values(25,10,'201807041315451265',1,100,now());