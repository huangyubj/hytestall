CREATE TABLE `MessageFlow` (
`id`  varchar(48) NOT NULL ,
`money`  decimal NOT NULL ,
`payUserid`  integer NOT NULL ,
`receiveUserid` integer NOT NULL ,
`status`  varchar(16) NOT NULL ,
`createdate`  date NULL ,
`updatedate`  date NULL ,
PRIMARY KEY (`id`)
)
;