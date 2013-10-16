package de.seco.serp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.tooling.GlobalGraphOperations;

import de.seco.serp.DataSource;
import de.seco.serp.services.GraphDBService;
import de.seco.serp.util.SerpDbNode;
import de.seco.serp.util.SerpDbNodeDefinition;
import de.seco.serp.util.SerpDbPropertyDefinition;
import de.seco.serp.util.SerpDbRelationshipDefinition;
import de.seco.serp.util.SerpDbSchemaDefinition;

public class ApiController extends BaseController {
	
	public void testMethod() {
		
//		try {
//			TimeUnit.MILLISECONDS.sleep(3000);
//		}
//		catch (Exception e) {};
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", "Max Mustermann");
		map.put("age", "123");
		render(map, "json");
	}
	
	public void testMethod2() {
		render("<html><body><h1>Test</h1></body></html>");
	}
	
	
	public void createNode() {
		
		String type = request.getParameter("type");
		System.out.println("properties " + request.getParameter("properties"));
		
		try {
			HashMap<String, String> properties = new ObjectMapper().readValue(request.getParameter("properties"), HashMap.class);
			
			SerpDbNode node = (new GraphDBService()).createNode(type, properties);
			if (node == null) {
				response.setStatus(400);
				render("could not create node");
			}
			else {
				render("node created!");
			}
		}
		catch (Exception e) {
			response.setStatus(400);
			e.printStackTrace();
		}
	}
	
	public void createRelationship () {
		try {
			Long node1Id = Long.parseLong(request.getParameter("node1Id"));
			Long node2Id = Long.parseLong(request.getParameter("node2Id"));
			String type = request.getParameter("type");
			HashMap<String, String> properties = new ObjectMapper().readValue(request.getParameter("properties"), HashMap.class);
			Relationship relationship = (new GraphDBService()).createRelationship(node1Id, node2Id, type, properties);
			
			if (relationship == null) {
				render("could not create relation");
			}
			else {
				render("relation created!");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showAllNodes() {
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		StringBuilder out = new StringBuilder();
		Transaction tx = graphDb.beginTx();
		
		try {
			Iterable<Node> iter = GlobalGraphOperations.at(graphDb).getAllNodes();
			
			out.append("<ul>");
			
			for (Node node : iter ) {
				out.append("<li> Node " + node.getId() + ": keys: " + node.getPropertyKeys() + ", values: " + node.getPropertyValues()  + "</li>");
			}
			
			tx.success();
		}
		finally{
			tx.finish();
		}
		
		out.append("</ul>");
		render (out);
	}
	
	public void showAllRelationships() {
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		StringBuilder out = new StringBuilder();
		Transaction tx = graphDb.beginTx();
		
		try {
			Iterable<Relationship> iter = GlobalGraphOperations.at(graphDb).getAllRelationships();
			
			out.append("<ul>");
			
			for (Relationship rel : iter ) {
				out.append("<li> Relation " + rel.getId()+ " [" + rel.getType().name() + "]: node " + rel.getStartNode().getId() + " -> node " + rel.getEndNode().getId() + ": keys: " + rel.getPropertyKeys() + ", values: " + rel.getPropertyValues()  + "</li>");
			}
			
			tx.success();
		}
		finally{
			tx.finish();
		}
		
		out.append("</ul>");
		render (out);
	}
	
	
	public void getNodeTypeList () {
		SerpDbSchemaDefinition serpDbDef = SerpDbSchemaDefinition.getInstance();
		HashMap<String, SerpDbNodeDefinition> nodeDefs = serpDbDef.getNodes();
		
		ArrayList< HashMap<String, String> > resultList = new ArrayList<HashMap<String, String> >();
		
		for (Map.Entry<String, SerpDbNodeDefinition> nodeDef :  nodeDefs.entrySet()) {
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name",nodeDef.getKey());
			map.put("label", nodeDef.getValue().getDisplayLabel());
			
			resultList.add(map);
		}
		
		render(resultList, "json");
		
	}
	
	public void getNodePropertyList () {
		String nodeType = request.getParameter("nodeType");
		
		SerpDbSchemaDefinition serpDbDef = SerpDbSchemaDefinition.getInstance();
		SerpDbNodeDefinition nodeDef = serpDbDef.getNodeType(nodeType);
		if (nodeDef == null) {
			response.setStatus(400);
			render ("");
			return;
		}
		HashMap<String, SerpDbPropertyDefinition> nodeProperties = nodeDef.getProperties();
		
		ArrayList< HashMap<String, String> > resultList = new ArrayList<HashMap<String, String> >();
		
		for (Map.Entry<String, SerpDbPropertyDefinition> it : nodeProperties.entrySet()) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", it.getKey());
			map.put("label", it.getValue().getDisplayLabel());
			resultList.add(map);
		}
		
		render(resultList, "json");
		
	}
	
	public void getNodesByName(){
		String nodeName = request.getParameter("nodeName");
		
		ArrayList<SerpDbNode> nodes = (new GraphDBService().getNodesByName(nodeName));
		
		render(nodes, "json");
		
		
	}
	
	
	public void getRelationshipTypeList () {
		 SerpDbSchemaDefinition serpDbDef = SerpDbSchemaDefinition.getInstance();
		 HashMap<String, SerpDbRelationshipDefinition> relDefs = serpDbDef.getRelationships();
		
		ArrayList< HashMap<String, String> > resultList = new ArrayList<HashMap<String, String> >();
		
		for (Map.Entry<String, SerpDbRelationshipDefinition> relDef :  relDefs.entrySet()) {
			
		 	HashMap<String, String> map = new HashMap<String, String>();
		 	map.put("name",relDef.getKey());
		 	map.put("label", relDef.getValue().getDisplayLabel());
			
		 	resultList.add(map);
		 }
		
		render(resultList, "json");
		
	}

}
