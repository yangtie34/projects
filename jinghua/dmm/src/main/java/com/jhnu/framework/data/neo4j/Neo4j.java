package com.jhnu.framework.data.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/**
 * 创建2个节点，并创建这2个节点间的关系，并输出。
 * @author Administrator
 *
 */
public class Neo4j {
	public static void main(String[] args){
		GraphDatabaseService db = null;
		Transaction tx = null;
		Node oneNode = null;
		Node otherNode = null;
		try{
			db = new GraphDatabaseFactory().newEmbeddedDatabase("a.db");
			tx =  db.beginTx();
			oneNode = db.createNode();
			oneNode.setProperty("name", "zhangzg");
			otherNode = db.createNode();
			otherNode.setProperty("name", "hyt");
			
			Relationship ship = oneNode.createRelationshipTo(otherNode, RelationshipTypes.KNOWS);
			ship.setProperty("message", "认识");
			
			
			System.out.println(oneNode.getProperty("name"));
			System.out.println(ship.getProperty("message"));
			System.out.println(otherNode.getProperty("name"));
			
			tx.success();
		}catch(Exception e){
			tx.failure();
			e.printStackTrace();
		}finally{
			tx.close();
		}
		
	}
	
	
	
}
