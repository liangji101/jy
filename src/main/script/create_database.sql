CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `serialNo` varchar(24) NOT NULL DEFAULT '' ,
  `name` varchar(24) NOT NULL DEFAULT '', 
  `pic_url` varchar(256) DEFAULT 0 ,
  `category_id` int(11) DEFAULT 0  ,
  `category_sub_id` int(11) DEFAULT 0  ,
   PRIMARY KEY (`id`),
   KEY `name` (`name`),
   KEY category_id_category_sub_id (`category_id`,`category_sub_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8; 

CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `type`  tinyint(4) DEFAULT 0,
  `name` varchar(56) NOT NULL DEFAULT '',  
   PRIMARY KEY (`id`),
   KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8; 



CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `name` varchar(24) NOT NULL DEFAULT '' ,
  `phome`varchar(24) NOT NULL DEFAULT '' ,
  `pwd` varchar(24) NOT NULL DEFAULT '', 
  `enable` tinyint(4) DEFAULT 0 ,
  `type` tinyint(4) DEFAULT 0  ,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL ,
   PRIMARY KEY (`id`),
   KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8; 


CREATE TABLE `regist_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `name` varchar(24) NOT NULL DEFAULT '' ,
  `phome`varchar(24) NOT NULL DEFAULT '' ,
  `pwd` varchar(24) NOT NULL DEFAULT '', 
  `enable` tinyint(4) DEFAULT 0 ,
  `type` tinyint(4) DEFAULT 0  ,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL ,
   PRIMARY KEY (`id`),
   KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8; 


 CREATE TABLE `shop_cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `shop_id` bigint(20) NOT NULL DEFAULT 0 ,
  `item_id` bigint(20) NOT NULL DEFAULT 0 ,
  `count` int(11) NOT NULL DEFAULT 0 ,
  `comfirm` tinyint(4) NOT NULL DEFAULT 0 ,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL ,
   PRIMARY KEY (`id`),
   KEY shop_id(`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `name` varchar(24) NOT NULL DEFAULT '' ,
  `owner_user_id`  varchar(24) NOT NULL DEFAULT '' ,  
  `owner` varchar(24) NOT NULL DEFAULT '' ,
  `owner_phone` varchar(24) NOT NULL DEFAULT '' ,
  `head_url` varchar(256) NOT NULL DEFAULT '' ,
  `shop_url` varchar(256) NOT NULL DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `open_time` timestamp NULL,
  `close_time` timestamp NULL,
  `lng`  FLOAT(16,12) NOT NULL DEFAULT 0.00,
  `lat` FLOAT(16,12) NOT NULL DEFAULT 0.00 ,
  `update_time` timestamp NULL ,
   PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `shop_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `shop_id`  bigint(20) DEFAULT 0,
  `category_id` int(11) NOT NULL DEFAULT 0,
  `category_sub_id` int(11) NOT NULL DEFAULT 0,
  `name` varchar(56) NOT NULL DEFAULT '',  
  `score` int(11) NOT NULL DEFAULT 0 ,
   PRIMARY KEY (`id`),
   KEY shop_id_category_id (`shop_id`,`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8; 


 CREATE TABLE `items` (                          
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `shop_id` bigint(20) NOT NULL DEFAULT 0 ,
  `name` varchar(24) NOT NULL DEFAULT '' ,
  `category_id` int(11) NOT NULL DEFAULT 0 ,
  `category_sub_id` int(11) NOT NULL DEFAULT 0 ,
  `score` int(11) NOT NULL DEFAULT 0 ,
  `count` int(11) NOT NULL DEFAULT 0 ,
  `price` int(11) NOT NULL DEFAULT 0 ,
  `price_new` int(11) NOT NULL DEFAULT 0 ,
  `pic_url` varchar(256) NOT NULL DEFAULT '' ,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL ,
   PRIMARY KEY (`id`),  
   KEY  shop_id_category_score (`shop_id`,`category_id`,`score`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



CREATE TABLE `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `type` tinyint(4) NOT NULL DEFAULT 0,
  `user_id` bigint(20) NOT NULL DEFAULT 0,
  `city` varchar(24) NOT NULL DEFAULT '' ,
  `province` varchar(24) NOT NULL DEFAULT '' ,
  `district` varchar(24) NOT NULL DEFAULT '' ,
  `phone` varchar(24) NOT NULL DEFAULT '' ,
  `address`  varchar(245) NOT NULL DEFAULT '' ,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL ,
   PRIMARY KEY (`id`),
   KEY user_id (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `device` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `shop_id` bigint(20) NOT NULL DEFAULT 0,
  `type` tinyint(4) NOT NULL DEFAULT 0 ,
  `status` varchar(20) NOT NULL DEFAULT 0 ,
  `token` varchar(32) NOT NULL DEFAULT '',
  `secret_key` varchar(32) NOT NULL DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL ,
   PRIMARY KEY (`id`),
   KEY shop_id(`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `order_id` varchar(32) NOT NULL DEFAULT '' ,
  `shop_id` bigint(20) NOT NULL DEFAULT 0,
  `user_id` bigint(20) NOT NULL DEFAULT 0,
  `phone` varchar(20) NOT NULL DEFAULT '' ,
  `address_id`  varchar(245) NOT NULL DEFAULT '' ,
  `remarks`  varchar(245) NOT NULL DEFAULT '' ,
  `snapshot` varchar(8192)  NOT NULL DEFAULT '' ,
  `status` tinyint(4) NOT NULL DEFAULT 0 ,
  `price` int(11) NOT NULL DEFAULT 0 ,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL ,
   PRIMARY KEY (`id`),
   UNIQ KEY order_id (`order_id`),
   KEY user_id(`user_id`),
   KEY shop_id(`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `ver` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `ver` varchar(32) NOT NULL DEFAULT '' ,
  `url` varchar(256) NOT NULL DEFAULT '' ,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL ,
   PRIMARY KEY (`id`),
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

| address | CREATE TABLE `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_id` bigint(20) NOT NULL DEFAULT '0',
  `user_id` bigint(20) NOT NULL DEFAULT '0',
  `city` varchar(24) NOT NULL DEFAULT '',
  `province` varchar(24) NOT NULL DEFAULT '',
  `district` varchar(24) NOT NULL DEFAULT '',
  `name`  varchar(32) NOT NULL DEFAULT '',
  `phone`  varchar(20) NOT NULL DEFAULT '',
  `address` varchar(245) NOT NULL DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `shop_id` (`shop_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 
