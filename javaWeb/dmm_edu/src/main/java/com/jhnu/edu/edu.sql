--drop table "t_edu_schools" cascade constraints;

create table t_edu_schools  (
  	 id                 VARCHAR2(100),
   ch_name              VARCHAR2(100),
   en_name              VARCHAR2(100),
 	 icoid              VARCHAR2(100),
 	 desc_				VARCHAR2(1000)
);

--drop table T_EDU_SCHOOL_DETAILS cascade constraints;

create table T_EDU_SCHOOL_DETAILS  (
   ID                   VARCHAR2(100),
   "SCHOOLID"           VARCHAR2(100),
   "TITLEID"            VARCHAR2(100),
   "VALUE_"              VARCHAR2(100)
   "YEAR_"              VARCHAR2(100)
);
alter table t_edu_schools add (desc_ VARCHAR2(1000))
select * from  T_EDU_SCHOOL_DETAILS;
select * from  t_edu_schools for update;
truncate table T_EDU_SCHOOL_DETAILS;
truncate table t_edu_schools

update t_edu_schools set icoid='hbslsddx' where id=1001565031;
update t_edu_schools set icoid='hnlgdx' where id=1001565249;
update t_edu_schools set icoid='zzqgyxy' where id=1001565467;
update t_edu_schools set icoid='hngydx' where id=1001565685;
update t_edu_schools set icoid='hnkjdx' where id=1001565903;
update t_edu_schools set icoid='zygxy' where id=1001566121;
update t_edu_schools set icoid='hnnydx' where id=1001566339;
update t_edu_schools set icoid='hnkjxy' where id=1001566557;
update t_edu_schools set icoid='hnmyjjxy' where id=1001566775;
update t_edu_schools set icoid='hnzyxy' where id=1001566993;
update t_edu_schools set icoid='xxyxy' where id=1001567211;
update t_edu_schools set icoid='hndx' where id=1001567429;
update t_edu_schools set icoid='hnsfdx' where id=1001567647;
update t_edu_schools set icoid='xysfxy' where id=1001567865;
update t_edu_schools set icoid='zksfxy' where id=1001568083;
update t_edu_schools set icoid='aysfxy' where id=1001568301;
update t_edu_schools set icoid='xcxy' where id=1001568519;
update t_edu_schools set icoid='nysfxy' where id=1001568737;
update t_edu_schools set icoid='lysfxy' where id=1001568955;
update t_edu_schools set icoid='sqsfxy' where id=1001569173;
update t_edu_schools set icoid='hncjzfdx' where id=1001569391;
update t_edu_schools set icoid='zzhkgyglxy' where id=1001569609;
update t_edu_schools set icoid='hnzyjsxy' where id=1001569827;
update t_edu_schools set icoid='lhzyjsxy' where id=1001570045;
update t_edu_schools set icoid='smxzyjsxy' where id=1001570263;
update t_edu_schools set icoid='zztlzyjsxy' where id=1001570481;
update t_edu_schools set icoid='hhxy' where id=1001570699;
update t_edu_schools set icoid='pdsxy' where id=1001570917;
update t_edu_schools set icoid='zzdx' where id=1001571135;
update t_edu_schools set icoid='kfdx' where id=1001571353;
update t_edu_schools set icoid='lylgxy' where id=1001571571;
update t_edu_schools set icoid='xxxy' where id=1001571789;
update t_edu_schools set icoid='xynlxy' where id=1001572007;

