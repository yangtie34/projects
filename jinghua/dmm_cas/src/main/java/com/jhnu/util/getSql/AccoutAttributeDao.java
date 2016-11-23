package com.jhnu.util.getSql;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasig.services.persondir.IPersonAttributes;
import org.jasig.services.persondir.support.AttributeNamedPersonImpl;
import org.jasig.services.persondir.support.StubPersonAttributeDao;

import com.jhnu.util.common.BaseDao;
import com.jhnu.util.common.PropertiesUtils;
  
public class AccoutAttributeDao extends StubPersonAttributeDao {  
  
    private BaseDao baseDao;  
    
    public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override  
    public IPersonAttributes getPerson(String uid) {  
        String sql = "select ID_NO from t_sys_user where username=? ";    
        final Map<String, Object> values = baseDao.getJdbcTemplate().queryForMap(sql,uid);
        Map<String, List<Object>> attributes = new HashMap<String, List<Object>>();
        attributes.put("idno",Collections.singletonList((Object) values.get("ID_NO")));  
        attributes.put("school",Collections.singletonList((Object) PropertiesUtils.getDBPropertiesByName("school.code")));  
        return new AttributeNamedPersonImpl(attributes);  
    }  
}  
