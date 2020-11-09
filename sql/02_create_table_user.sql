drop table if exists USER;
create table USER (
    id                      int        not null AUTO_INCREMENT,
    name                    varchar(255)                      ,
    age                 	int	                              ,
    phone             		int                               ,
	password             	varchar(255)                      ,
    primary key(id)
);
