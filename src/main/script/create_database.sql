CREATE TABLE `sk_infinite` (
  `user_id` bigint(20) unsigned NOT NULL,
  `app_id` bigint(20) unsigned NOT NULL,
  `session_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `session_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`user_id`,`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `third_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sc_uid` bigint(20) NOT NULL DEFAULT '0',
  `rr_uid` bigint(20) NOT NULL DEFAULT '0',
  `sina_uid` bigint(20) NOT NULL DEFAULT '0',
  `qq_uid` bigint(20) NOT NULL DEFAULT '0',
  `rr_token` varchar(100) NOT NULL DEFAULT '',
  `sina_token` varchar(100) NOT NULL DEFAULT '',
  `qq_token` varchar(100) NOT NULL DEFAULT '',
  `rr_refresh_token` varchar(100) NOT NULL DEFAULT '',
  `sina_refresh_token` varchar(100) NOT NULL DEFAULT '',
  `qq_refresh_token` varchar(100) NOT NULL DEFAULT '',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sc_uid` (`sc_uid`),
  KEY `rr_uid` (`rr_uid`),
  KEY `sina_uid` (`sina_uid`),
  KEY `qq_uid` (`qq_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `password` varchar(128) NOT NULL,
  `gender` tinyint(4) DEFAULT '1' COMMENT 'fmale:0,male:1',
  `level` varchar(64) DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL,
  `birthday` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `email` varchar(128) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `headurl` varchar(256) DEFAULT NULL,
  `att_privacy` int(4) DEFAULT '0',
  `phoneNumber` varchar(256) DEFAULT NULL,
  `trd` tinyint(4) DEFAULT '0',
  `push_type` int(11) DEFAULT '0',
  `sycType` int(11) DEFAULT '0',
  `share_id` bigint(20) unsigned DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `feed` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(500) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '如果是模板的形式则为json',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'feed发布时间',
  `uid` int(11) NOT NULL DEFAULT '0' COMMENT '发布人',
  `feed_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'feed类型',
  `comment_count` int(11) NOT NULL DEFAULT '0' COMMENT '回复数',
  `comment_index` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '评论地址',
  `fcomment_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '首条评论id ',
  `fcontent` varchar(200) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '首条评论内容 ',
  `fcomment_owner` int(11) NOT NULL DEFAULT '0' COMMENT '首条评论发起者',
  `fcomment_time` timestamp NULL DEFAULT NULL COMMENT '首条评论时间',
  `lcomment_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '末尾评论id ',
  `lcontent` varchar(200) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '末尾评论内容 ',
  `lcomment_owner` int(11) NOT NULL DEFAULT '0' COMMENT '末尾评论发起者',
  `lcomment_time` timestamp NULL DEFAULT NULL COMMENT '末尾评论时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `song` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `song_name` varchar(128) NOT NULL,
  `singer_id` bigint(20) unsigned DEFAULT NULL,
  `song_url` varchar(128) NOT NULL,
  `original_song_url` varchar(128) NOT NULL,
  `ksc_url` varchar(128) NOT NULL,
  `pic_url` varchar(128) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `singer` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;






CREATE TABLE `pass_song` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `pass_song_id` bigint(20) unsigned NOT NULL,
  `sender_id` bigint(20) unsigned NOT NULL,
  `receiver_id` bigint(20) unsigned NOT NULL,
  `song_id` bigint(20) unsigned NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `del_by_sender` tinyint(4) DEFAULT 0 NOT NULL  COMMENT 'not delete:0,delete:1',
  `del_by_receiver` tinyint(4) DEFAULT 0 NOT NULL COMMENT 'not delete:0,delete:1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `pass_song_item` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `pass_song_id` bigint(20) unsigned NOT NULL,
  `pass_song_url` varchar(128) DEFAULT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `contest` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `description` varchar(128) DEFAULT NULL,
  `pic_url` varchar(128) DEFAULT NULL,
  `is_over` tinyint DEFAULT 0 COMMENT 'not over:0,over:1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE TABLE `contest_song` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `contest_id` bigint(20) unsigned NOT NULL,
  `song_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE TABLE `achievement` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `description` varchar(128) NOT NULL,
  `pic_url` varchar(128) DEFAULT NULL,
  `medal_name` varchar(128) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(4) DEFAULT 0 NOT NULL  COMMENT 'not delete:0,delete:1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `user_achievement` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `achievement_id` bigint(20) unsigned NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `entity_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `entity_id` bigint(20) DEFAULT NULL,
  `entity_type` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL,
  `type` varchar(20) COLLATE utf8_bin NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq` (`entity_type`,`entity_id`,`type`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `user_entity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `entity_id` bigint(20) DEFAULT NULL,
  `entity_type` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL,
  `type` varchar(20) COLLATE utf8_bin NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq` (`uid`,`type`,`entity_type`,`entity_id`),
  KEY `index_user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE TABLE `share` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) unsigned NOT NULL COMMENT '分享者id',
  `share_id` bigint(20) NOT NULL COMMENT '分享id ',
  `feed_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'feed id ',
  `song_id` bigint(20) NOT NULL COMMENT '分享id ',
  `pics_url` varchar(400) COLLATE utf8_bin NOT NULL DEFAULT '',
  `url_md5` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '',
  `music_id` bigint(20) unsigned NOT NULL,
  `chk` varchar(4) COLLATE utf8_bin NOT NULL DEFAULT 'n',
  `share_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'song:1,mv:2',
  `comment_count` int(11) NOT NULL DEFAULT '0' COMMENT '回复数',
  `comment_index` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '评论地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `del` tinyint(4) NOT NULL DEFAULT '0',
  `score` float NOT NULL,
  `flower_count` int(11) NOT NULL DEFAULT '0',
  `duration` varchar(128) DEFAULT NULL,
  `vs_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `url_md5` (`url_md5`),
  KEY `share_id` (`share_id`)
) ENGINE=InnoDB CHARSET=utf8;

CREATE TABLE `user_position` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) unsigned NOT NULL,
  `longitude` float DEFAULT NULL,
  `latitude` float NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `device_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `device_token` char(64) COLLATE utf8_bin NOT NULL,
  `client_version` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=utf8;

CREATE TABLE `asyncJob` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL COMMENT 'sc_uid',
  `type` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '同步类型 comment, syc_comment,syc_mark,syc_like,syc_uploadVideo',
  `sid` bigint(20) NOT NULL DEFAULT '0' COMMENT '歌曲sid',
  `content` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '同步的内容',
  `flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0 pending 1 fail 2 done ',
  `modify_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;