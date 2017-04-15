create user jyunbang identified by "jyunbang"  --创建jyunbang用户

  default tablespace USERS

  temporary tablespace TEMP

  profile DEFAULT;

  

  

grant connect,create view ,resource  to jyunbang;

grant unlimited tablespace to jyunbang;

 

 

--管理员授权

　　grant create session to jyunbang;--授予jyunbang用户创建session的权限，即登陆权限

　　grant unlimited session to jyunbang;--授予jyunbang用户使用表空间的权限

　　grant create table to jyunbang;--授予创建表的权限

　　grant drop table to jyunbang;--授予删除表的权限

　　grant insert table to jyunbang;--插入表的权限

　　grant update table to jyunbang;--修改表的权限

　　grant all to public;--这条比较重要，授予所有权限(all)给所有用户(public)

--oralce对权限管理比较严谨，普通用户之间也是默认不能互相访问的

　　grant select on tablename to jyunbang;--授予jyunbang用户查看指定表的权限

　　grant drop on tablename to jyunbang;--授予删除表的权限

　　grant insert on tablename to jyunbang;--授予插入的权限

　　grant update on tablename to jyunbang;--授予修改表的权限

　　grant insert(id) on tablename to jyunbang;

　　grant update(id) on tablename to jyunbang;--授予对指定表特定字段的插入和修改权限，注意，只能是insert和update

 

--撤销权限

　　--基本语法同grant,关键字为revoke

--查看权限

　　select * from user_sys_privs;--查看当前用户所有权限

　　select * from user_tab_privs;--查看所用用户对表的权限

--操作表的用户的表

　　/*需要在表名前加上用户名，如下*/

--权限传递

　-- 即用户A将权限授予B，B可以将操作的权限再授予C，命令如下：

　　grant alert table on tablename to jyunbang with admin option;--关键字 with admin option

　　grant alert table on tablename to jyunbang with grant option;--关键字 with grant option效果和admin类似

--角色

　　--角色即权限的集合，可以把一个角色授予给用户

　　create role myrole;--创建角色

　　grant create session to myrole;--将创建session的权限授予myrole

　　grant myrole to jyunbang;--授予jyunbang用户myrole的角色

　　drop role myrole;--删除角色

　　/*但是有些权限是不能授予给角色的，比如unlimited tablespace和any关键字*/
