package de.seco.serp.controller;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.tooling.GlobalGraphOperations;

import de.seco.serp.DataSource;
import de.seco.serp.services.GraphDBService;

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
			
			Node node = (new GraphDBService()).createNode(type, properties);
			if (node == null) {
				render("could not create node");
			}
			else {
				render("node created!");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public void showAllNodes() {
		GraphDatabaseService graphDB = DataSource.getGraphDB();
		StringBuilder out = new StringBuilder();
		Transaction tx = graphDB.beginTx();
		
		try {

			
			Iterable<Node> iter = GlobalGraphOperations.at(graphDB).getAllNodes();
			
	
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

}
