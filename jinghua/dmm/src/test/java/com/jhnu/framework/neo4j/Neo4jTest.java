package com.jhnu.framework.neo4j;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.data.neo4j.conversion.Result;

import com.jhnu.framework.data.neo4j.entity.FansRelationship;
import com.jhnu.framework.data.neo4j.entity.Movie;
import com.jhnu.framework.data.neo4j.entity.Person;
import com.jhnu.framework.data.neo4j.service.BaseNeo4jService;
import com.jhnu.spring.SpringTest;

/**
 * Neo4j 测试
 */
public class Neo4jTest extends SpringTest{
	@Resource
	private BaseNeo4jService baseNeo4jService;
	
	@Test
	public void testNeo4j(){
		Result<Object> m=baseNeo4jService.findObjectByCypher("MATCH (tom {title: 'mm'}) RETURN tom",Movie.class);
		Iterator<Object> ms= m.iterator();
		while (ms.hasNext()) {
			Movie mo=(Movie) ms.next();
			baseNeo4jService.delete(mo);
			
		}
		long oldtime= System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			Person person=new Person();
			person.setBorn(1980);
			person.setName("person"+i);
			baseNeo4jService.save(person);
			for (int j = 0; j < 10; j++) {
				Movie movie=new Movie();
				movie.setTitle("movie"+i+j);
				movie.setTagline("testTagline");
				movie.setReleased("Released");
				baseNeo4jService.save(movie);
				FansRelationship fs=new FansRelationship(person,movie);
				baseNeo4jService.save(fs);
			}
		}
		long newtime= System.currentTimeMillis();
		System.out.println(newtime-oldtime+"毫秒");
		List<Map<String,Object>> res= baseNeo4jService.findByCypher("MATCH (tom:Person {name: 'Tom Hanks'})-[:ACTED_IN]->(tomHanksMovies) RETURN tom,tomHanksMovies");
		Iterator<Map<String,Object>> mos= res.iterator();
		while (mos.hasNext()) {
			Map<String,Object> mo=mos.next();
			System.out.println("123"+mo.size());
			
		}
	}
	
}
