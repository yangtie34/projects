package com.jhkj.mosdc.output.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.jdbc.core.RowMapper;

import com.jhkj.mosdc.framework.dao.impl.BaseDaoImpl;
import com.jhkj.mosdc.output.dao.IPersonalInfoDao;
import com.jhkj.mosdc.output.po.PersonalInfo;
import com.jhkj.mosdc.output.util.WidgetCommonUtils;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;
/**
 * 个人信息查询实现类。
 * 
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-12-20
 * @TIME: 下午12:01:08
 */
public class PersonalInfoDaoImpl extends BaseDaoImpl implements IPersonalInfoDao{

	public class PersonMapper implements RowMapper{

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			PersonalInfo pi = new PersonalInfo();
			pi.setBm(rs.getString("bm")==null?"":rs.getString("bm"));
			String greetings = WidgetCommonUtils.getAmOrPm()+"好,";
			pi.setGreeting(greetings);
			pi.setImageFileName(rs.getString("imageFileName"));
			pi.setLastLoginIP(rs.getString("LASTLOGINIP"));
			pi.setLastLoginTime(rs.getString("lastLoginTime"));
			pi.setRole(rs.getString("roleName")==null?"":rs.getString("roleName"));
			pi.setSchoolName(rs.getString("schoolName"));
			pi.setUserName(rs.getString("userName"));
			pi.setXgh(rs.getString("xgh"));
			return pi;
		}
		
	}
	@Override
	public Object queryPersonInfo(String xgh) {
		String sql="SELECT * FROM TB_XTJC_PERSONINFO WHERE XGH=?";
		Object obj = this.getJdbcTemplate().queryForObject(sql, new Object[]{xgh}, new PersonMapper());
		return obj;
	}
	@Override
	public boolean savePersonInfo(String loginTime, String loginIP) {

        boolean result = true;
        UserInfo ui;
        try {
            ui = UserPermissionUtil.getUserInfo();
            String xgh = ui.getXjh() == null ? ui.getJzsh() : ui.getXjh();
            if(xgh==null){
                xgh="0";
            }
            // 先查询用户是否存在
            String querySql = "SELECT COUNT(*) FROM TB_XTJC_PERSONINFO WHERE XGH=?";
            int count = this.getJdbcTemplate().queryForInt(querySql,
                    new Object[] { xgh });
            // 存在则修改 不存在则插入
            if (count == 1) {
                String updateSql = "UPDATE TB_XTJC_PERSONINFO SET LASTLOGINTIME='"
                        + loginTime
                        + "',LASTLOGINIP='"
                        + loginIP
                        + "' WHERE XGH='"
                        + xgh+"'";
                int updatec = this.getJdbcTemplate().update(updateSql);
                if (updatec != 1) {
                    result = false;
                }
            } else if (count == 0) {
                long userid = ui.getId();
                String username = ui.getUsername();
                String bmmc = ui.getBmmc()==null?"":ui.getBmmc();

                /*String xxmSql="select xxmc from tb_xxjbxx";


                Query query = this.getSession().createSQLQuery(xxmSql);
                query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                Map xx = (Map)query.list().get(0);
                String xxmc = (String)xx.get("XXMC");*/


                String roleSql ="SELECT JSMS AS ROLENAME FROM tp_role WHERE JSLX_ID in("+ui.getRoleIds()+")";
                Query query1 = this.getSession().createSQLQuery(roleSql);
                query1.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List<Map> roleList1Map = query1.list();

                StringBuffer roleSb = new StringBuffer();
                for(Map m1l : roleList1Map){
                    String roleName = (String)m1l.get("ROLENAME");
                    roleSb.append(roleName).append(";");
                }

                String roleStr = roleSb.toString();
                String insertSql = "insert into tb_xtjc_personinfo (USER_ID, USERNAME, ROLENAME, XGH, IMAGEFILENAME, SCHOOLNAME, LASTLOGINTIME, LASTLOGINIP, BM) "
                        + "values ('"
                        + userid
                        + "','"
                        + username
                        + "','"+roleStr.substring(0,roleStr.length()-1)+"','"
                        + xgh
                        + "','images/"+userid+".jpg', '', '"
                        + loginTime + "','" + loginIP + "','"+bmmc+"')";
                int insertc = this.getJdbcTemplate().update(insertSql);
                if (insertc != 1) {
                    result = false;
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return result;
    }
	
}
