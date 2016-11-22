package com.jhnu.framework.data.neo4j.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.framework.data.neo4j.dao.BaseNeo4jDao;
import com.jhnu.framework.data.neo4j.service.BaseNeo4jService;


@Service("baseNeo4jService")
public class BaseNeo4jServiceImpl implements BaseNeo4jService{

	@Autowired
	private BaseNeo4jDao baseNeo4jDao;


	/**
	 * 通用查询方法
	 * 
	 * @param cypher
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findByCypher(String cypher) {
		Result<Map<String, Object>> result = baseNeo4jDao
				.findByCypher(cypher);

		Iterator<Map<String, Object>> it = result.iterator();

		List<Map<String, Object>> retRes = new ArrayList<Map<String, Object>>();
		while (it.hasNext()) {
			Map<String, Object> itMap = it.next();
			Map<String, Object> tMap = new HashMap<String, Object>();

			for (String key : itMap.keySet()) {
				// System.out.println(key + "..." + itMap.get(key));
				if (itMap.get(key) instanceof Node) {
					Node node = (Node) itMap.get(key);
					Map<String, Object> nodeMap = new HashMap<String, Object>();
					nodeMap.put("n_id", node.getId());
					for (String prop : node.getPropertyKeys()) {
						nodeMap.put("n_"+prop, node.getProperty(prop));
						// System.out.println("\t" + prop + "..." +
						// node.getProperty(prop));
					}
					tMap.put(key, nodeMap);
				}
				else if (itMap.get(key) instanceof Relationship) {
					Relationship rel = (Relationship) itMap.get(key);
					Map<String, Object> relMap = new HashMap<String, Object>();
					relMap.put("r_id", rel.getId());
					for (String prop : rel.getPropertyKeys()) {
						relMap.put("r_"+prop, rel.getProperty(prop));
						// System.out.println("\t" + prop + "..." +
						// rel.getProperty(prop));
					}
					tMap.put(key, relMap);
				} else {
					tMap.put(key, itMap.get(key));
				}
			}
			itMap = null;
			retRes.add(tMap);
		}

		return retRes;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Result<Object> findObjectByCypher(String cypher, Class cla)
	{
		return  baseNeo4jDao.findObjectByCypher(cypher,cla);
	}
	
	public void save(Object object){
		baseNeo4jDao.save(object);
	}
	
	
	public void delete(Object object){
		baseNeo4jDao.delete(object);
	}
	
}