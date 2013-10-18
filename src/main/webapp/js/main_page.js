
serp.data = {};

// load global data
serp.loadData = function (success) {
	var step = _.after(2, success);
	serp.data.nodeTypeCollection = new Backbone.Collection;
	serp.data.nodeTypeCollection.url = "/api/getNodeTypeList";
	serp.data.nodeTypeCollection.fetch({
		success: step
	});
	serp.data.relationshipTypeCollection = new Backbone.Collection;
	serp.data.relationshipTypeCollection.url = "/api/getRelationshipTypeList";
	serp.data.relationshipTypeCollection.fetch({
		success: step
	});
}


// Init Main Page

$(document).ready(function() {
	
	var loadPage = function () {
		var createNodeView = new serp.view.CreateNodeView();
		var createRelationshipView = new serp.view.CreateRelationshipView();
		$("#create_node_box").append(createNodeView.$el);
		$("#create_relationship_box").append(createRelationshipView.$el);

		$(".logout_button").on("click", function () {
			serp.logout().done(function () {
				window.location.reload();
			})
		});

		var resultNodeCollection = new serp.model.ResultCollection;
		var resultNodesTableView = new serp.view.ResultTableView({
			resultType: serp.model.RESULT_NODE,
			collection: resultNodeCollection,
		});
		$("#result_table_nodes_test").append(resultNodesTableView.render().$el);
		resultNodeCollection.url = "/api/getNodeList";
		resultNodeCollection.fetch({
			add:true
		});


		var resultRelationshipsCollection = new serp.model.ResultCollection;
		var resultRelationshipsTableView = new serp.view.ResultTableView({
			resultType: serp.model.RESULT_RELATIONSHIP,
			collection: resultRelationshipsCollection,
		});
		$("#result_table_relationships_test").append(resultRelationshipsTableView.render().$el);
		resultRelationshipsCollection.url = "/api/getRelationshipList";
		resultRelationshipsCollection.fetch({
			add:true
		});
	};

	// load templates & initial data, then render views
	var step = _.after(2, loadPage);
	serp.loadTemplates(step);
	serp.loadData(step);
});