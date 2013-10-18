

// Init

$(document).ready(function() {
	
	serp.loadTemplates(function () {
		var createNodeView = new serp.view.CreateNodeView();
		var createRelationshipView = new serp.view.CreateRelationshipView();
		$("#create_node_box").append(createNodeView.$el);
		$("#create_relationship_box").append(createRelationshipView.$el);

		$(".logout_button").on("click", function () {
			serp.logout().done(function () {
				window.location.reload();
			})
		})
		
		var func1 = serp.template("create-node-template");
		var func2 = serp.template("create-relationship-template");
		//alert(func1 + "\n\n" + func2);
	});
	
	
});