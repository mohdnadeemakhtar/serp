

// Init

$(document).ready(function() {
	var createNodeView = new serp.view.CreateNodeView();
	var createRelationshipView = new serp.view.CreateRelationshipView();
	$("#create_node_box").append(createNodeView.$el);
	$("#create_relationship_box").append(createRelationshipView.$el);

	$(".logout_button").on("click", function () {
		serp.logout().done(function () {
			window.location.reload();
		})
	})
});