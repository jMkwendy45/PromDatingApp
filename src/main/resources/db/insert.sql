SET FOREIGN_KEY_CHECKS =0;
    truncate table address;
    truncate table users;
    truncate table notification;
    truncate table media;
    truncate  table media_reactions;

insert  into address(id,house_number,streets,state,country)
values(101,'HerbertMaculyStreet','LAGOS','NIGERIA','RR'),
      (107,'HerbertMaculyStree','LAGOS','NIGERIA','LL'),
      (102,'HerbertMaculyStree','LAGOS','NIG','pou'),
      (104,'HerbertMaculyStree','LAGOS','NG','Goo'),
      (109,'HerbertMaculyStree','LAGOS','NIE','kop'),
      (100,'HerbertMaculyStree','LAGOS','NRF','ROO');




insert  into users(id,email,password,is_active,address_id)
values(501,'test@email.com','password',0,101),
      (507,'test1@email.com','password',0,107),
      (503,'test2@email.com','password',0,102),
      (604,'test3@email.com','password',0,104),
      (599,'test4@email.com','password',0,109),
       (677,'test5@email.com','password',0,100);