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
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		SerpDbNode node = null;
		Transaction tx = graphDb.beginTx();
		try {
		   
			node = new SerpDbNode(type, properties);
			
			node.save();
			tx.success();
		} catch (Exception e){
			
		} finally {
			tx.finish();
		}
		return node;
	}
	
	
	public SerpDbNode updateNode(Long id, HashMap<String, String> properties){
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		SerpDbNode node = null;
		Transaction tx = graphDb.beginTx();
		try {
		    
			node = new SerpDbNode(id, properties);
			
			node.save();
			tx.success();
		} catch (Exception e){
			
		} finally {
			tx.finish();
		}
		return node;
	}
	
	public boolean deleteNode(Long nodeId){
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		SerpDbNode node = null;
		boolean result = false;
		
		Transaction tx = graphDb.beginTx();
		try {
		    
			node = SerpDbNode.getById(nodeId);
			
			node.delete();
			tx.success();
			result = true;
		} catch (Exception e){
			result = false;
		} finally {
			tx.finish();
		}
		return result;
	}
	
	
	public ArrayList<SerpDbNode> getNodeList(){
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		ArrayList<SerpDbNode> resultList = new ArrayList<SerpDbNode>();
		Transaction tx = graphDb.beginTx();
		ExecutionEngine engine = new ExecutionEngine(graphDb);
		
		try {

			String query = String.format("match (n) return n");
			ExecutionResult er = engine.execute(query);
			ResourceIterator<Map<String, Object>> iterator = er.iterator();
			while (iterator.hasNext()){
				Map<String, Object> map = iterator.next();
				try {
					SerpDbNode node = new SerpDbNode((Node)map.get("n"));
					resultList.add(node);
				} catch(Exception e){
					
				}
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
		return resultList;
	}
	
	
	public SerpDbRelationship createRelationship(long node1Id, long node2Id, String type, HashMap<String, String> properties) {
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		SerpDbRelationship relationship = null;
		Transaction tx = graphDb.beginTx();
		try {
		
			relationship = new SerpDbRelationship(node1Id, node2Id, type, properties);
			
			relationship.save();
	
			tx.success();
			
		} catch (Exception e){
			
		}
		return relationship;
	}
	
	public SerpDbRelationship updateRelationship(Long relationshipId, HashMap<String, String> properties) {
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		SerpDbRelationship relationship = null;
		Transaction tx = graphDb.beginTx();
		try {
		
			relationship = new SerpDbRelationship(relationshipId, properties);
			
			relationship.save();
	
			tx.success();
			
		} catch (Exception e){
			
		} finally {
			tx.finish();
		}
		return relationship;
	}
	

	
	public boolean deleteRelationship(Long relationshipId){
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		SerpDbRelationship relationship = null;
		boolean result = false;
		
		Transaction tx = graphDb.beginTx();
		try {
		    
			relationship = SerpDbRelationship.getById(relationshipId);
			
			relationship.delete();
			tx.success();
			result = true;
		} catch (Exception e){
			result = false;
		} finally {
			tx.finish();
		}
		return result;
	}
	
	public ArrayList<SerpDbRelationship> getRelationshipList(){
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		ArrayList<SerpDbRelationship> resultList = new ArrayList<SerpDbRelationship>();
		Transaction tx = graphDb.beginTx();
		ExecutionEngine engine = new ExecutionEngine(graphDb);
		
		try {

			String query = String.format("match n -[r]-> m return r ");
			ExecutionResult er = engine.execute(query);
			ResourceIterator<Map<String, Object>> iterator = er.iterator();
			while (iterator.hasNext()){
				Map<String, Object> map = iterator.next();
				try {
					SerpDbRelationship relationship = new SerpDbRelationship((Relationship)map.get("r"));
					resultList.add(relationship);
				} catch(Exception e){
					
				}
			}
			tx.success();
		}
		catch (Exception e) {
		
			
		}
		finally {
			tx.finish();
		}
		return resultList;
	}
	
	
	public ArrayList<SerpDbNode> getNodesByName( String nodeName){
		ArrayList<SerpDbNode> results = new ArrayList<SerpDbNode>();
		
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		Transaction tx = graphDb.beginTx();
		ExecutionEngine engine = new ExecutionEngine(graphDb);
		
		try {

			String query = String.format("match (n:%s) return n", nodeName);
			ExecutionResult er = engine.execute(query);
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
