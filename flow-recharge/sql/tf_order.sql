CREATE TABLE IF NOT EXISTS tf_order(
   id int primary key not null COMMENT 'ID',
   order_no varchar(20) not null COMMENT '订单编号',
   user_account_id long not null COMMENT '用户ID',
   business_account_id long not null COMMENT '商户ID',
   business_name varchar(20) not null COMMENT '商户名称',
   amount DECIMAL(10,2) not null COMMENT '订单金额',
   title varchar(20) not null COMMENT '订单标题',
   type varchar(20) not null COMMENT '订单类型',
   status int not null COMMENT '订单状态',
   pay_type int not null COMMENT '支付类型',
   refill_comment varchar(20) not null COMMENT '充值说明',
   refill_phone_number varchar(20) not null COMMENT '充值手机号',
   refill_data DATE not null COMMENT '充值时间', 
   credit DATE not null COMMENT '赠送积分',
   created_time DATE not null COMMENT '创建时间',
   modified_time DATE not null COMMENT '修改还是件'
   )ENGINE=InnoDB DEFAULT CHARSET=utf8;