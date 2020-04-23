CREATE TABLE `MessageFlow` (
`id`  varchar(48) NOT NULL ,
`money`  decimal NOT NULL ,
`payUserid`  varchar(48) NOT NULL ,
`receiveUserid`  varchar(48) NOT NULL ,
`status`  varchar(16) NOT NULL ,
`createdate`  date NULL ,
`updatedate`  date NULL ,
PRIMARY KEY (`id`)
)
;
