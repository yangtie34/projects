package com.jhnu.framework.data.neo4j.dao.impl;

import java.util.Map;

import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.neo4j.dao.BaseNeo4jDao;
import com.jhnu.framework.data.neo4j.entity.Movie;

/**
 * 由于@Query中查询语句的占位符的缺陷， 有些查询只能通过拼接字符串的方式来进行模糊查询
 * 
 */
@Repository("baseNeo4jDao")
public class BaseNeo4jDaoImpl implements BaseNeo4jDao{

//	@Autowired
	private Neo4jTemplate neo4jTemplate;
	
	
	public Neo4jTemplate getNeo4jTemplate() {
		return neo4jTemplate;
	}

	/**
	 * 通过执行cypher语句查询用户
	 * @param cypher
	 * @return
	 */
	public Result<Map<String, Object>> findByCypher(String cypher)
	{
		return neo4jTemplate.query(cypher, null);
	}
	
	/**
	 * 通过执行cypher语句查询用户
	 * @param cypher
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Result<Object> findObjectByCypher(String cypher,Class clas)
	{
		return neo4jTemplate.query(cypher, null).to(clas);
	}

	/**
	 * 通过用户昵称进行匹配查询
	 * 
	 * @param onick
	 * @return 结果按昵称从小到大排序
	 */
	public Result<Movie> findByOnick(String onick) {
		String cql = "MATCH (tom:Person {name: "+onick+"})-[:ACTED_IN]->(tomHanksMovies) RETURN tom,tomHanksMovies";
		return neo4jTemplate.query(cql, null).to(Movie.class);
	}

	/**
	 * 统计昵称中包含{@code onick}的用户个数
	 * 
	 * @param addr
	 * @return
	 */
	public int countByOnick(String onick) {
		String cql = "start user=node(*) where user.onick =~'.*" + onick + ".*' return count(user)";
		return neo4jTemplate.query(cql, null).to(Integer.class).single();
	}

	/**
	 * 统计各省的微博用户数 <br>
	 * 一次性把34个省、直辖市统计出来，效率极其慢 <br>
	 * 如：START `u1`=node:`addr`("addr:*北京*"),`u2`=node:`addr`("addr:*河南*") RETURN count(`u1`),count(u2)
	 */
	public Integer countByAddrLike(String area) {
		String cql = "match (user:SinaUserBean) where user.addr=~'.*" + area + ".*' return count(user) as cnt";

		Result<Map<String, Object>> result = neo4jTemplate.query(cql, null);

		return result.to(Integer.class).single();
	}

	/**
	 * 通过所给部分地址信息进行匹配查询，类似于sql里的like语句
	 * <p>
	 * 注意：addr不包含行政单位，如：省、市、县
	 * 
	 * @param addr
	 * @return
	 */
	public Result<Movie> findByAddr(String addr, Integer skip, Integer limit) {
		String cql = "start user=node(*) where user.addr =~'.*" + addr + ".*' return user";
		if (null != skip && skip >= 0)
			cql += (" skip " + skip);
		if (null != limit && limit >= 1)
			cql += (" limit " + limit);
		return neo4jTemplate.query(cql, null).to(Movie.class);
	}
	
	public void save(Object entity){
		neo4jTemplate.save(entity);
	}
	public void delete(Object entity){
		neo4jTemplate.delete(entity);
	}
	

}
