CREATE TABLE IF NOT EXISTS tf_coupon(
   id int primary key not null COMMENT 'ID',
   user_account_id long not null COMMENT '用户ID',
   coupon_amount long not null COMMENT '优惠券金额',
   status int not null COMMENT '优惠券状态',
   start_time DATE not null COMMENT '开始时间', 
   end_time DATE not null COMMENT '结束时间',
   created_time DATE not null COMMENT '创建时间',
   modified_time DATE not null COMMENT '修改时间'
   )ENGINE=InnoDB DEFAULT CHARSET=utf8;
   
