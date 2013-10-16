package de.seco.serp.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;

import de.seco.serp.DataSource;
import de.seco.serp.util.SerpDbNode;
import de.seco.serp.util.SerpDbNodeDefinition;
import de.seco.serp.util.SerpDbPropertyDefinition;
import de.seco.serp.util.SerpDbRelationship;
import de.seco.serp.util.SerpDbRelationshipDefinition;
import de.seco.serp.util.SerpDbSchemaDefinition;

public class GraphDBService extends BaseService {

	public SerpDbNode createNode (String type, HashMap<String, String> properties) {

		try{
			SerpDbNode serpNode = new SerpDbNode(type, properties);
			
			if( serpNode.save()){
				return serpNode;
			}
		} catch (Exception e){
			
		}
		return null;
	}
	
	
	
	public Relationship createRelationship (long node1Id, long node2Id, String type, HashMap<String, String> properties) {
		
		try {
		
			SerpDbRelationship serpRelation = new SerpDbRelationship(node1Id, node2Id, type, properties);
			
			Relationship relationship = serpRelation.create();
	
			return relationship;
			
		} catch (Exception e){
			return null;
		}
	}
	
	public ArrayList<SerpDbNode> getNodesByName( String nodeName){
		ArrayList<SerpDbNode> results = new ArrayList<SerpDbNode>();
		
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		Transaction tx = graphDb.beginTx();
		ExecutionEngine engine = new ExecutionEngine(graphDb);
		
		
		try {
			
			
			String query = String.format("match (n:%s) return n", nodeName);
			ExecutionResult er = engine.execute(query);
//			System.out.print(er.dumpToString());
			ResourceIterator<Map<String, Object>> iterator = er.iterator();
			while (iterator.hasNext()){
				Map<String, Object> map = iterator.next();
				SerpDbNode node = new SerpDbNode((Node)map.get("n"));
				results.add(node);
			}
			tx.success();
		}
		catch (Exception e) {
			System.out.println("cannot create node: " + e.getMessage());
			e.printStackTrace();
			
		}
		finally {
			tx.finish();
		}
		System.out.println("results:"+results);
		return results;
	}
	
	
	
}
