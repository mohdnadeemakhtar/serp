serp.view.NodeSelectorView = Backbone.View.extend ({
	
	template: serp.template("node-selector-template"),
	nodesTemplate: serp.template("node-dropdown-template"),


	events: {
		"change select[name='type']": "onSelectTypeChange",
	},
	
	initialize: function () {
		var that = this;
		$.get("/api/getNodeTypeList").done(function(response){
			that.nodeTypeList = response;
			that.render();
			that.showNodes(that.nodeTypeList[0].name);
		});
	},

	getNodeTypeEl: function () {
		return this.$el.find("select[name='type']");
	},

	onSelectTypeChange: function () {
		this.showNodes(this.getNodeTypeEl().val());
	},
	
	showNodes: function (nodeType) {
		var that = this;
		$.get("/api/getNodesByType", {type: nodeType}).done(function(response) {
			that.$el.find(".node_selector_node").html(that.nodesTemplate({
				nodes: response
			}));
		});
	},

	render: function () {
		this.$el.html(this.template({nodeTypes: this.nodeTypeList}));
	},
	
});