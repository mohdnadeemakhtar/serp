
serp.view.CreateNodeView = Backbone.View.extend({
	
	template: serp.template("create-node-template"),
	propertyTemplate: serp.template("create-node-property-template"),
	
	events: {
		"change select[name='type']": "onSelectTypeChange",
		"click *[data-action='create']": "onCreateNodeClick",
	},
	
	initialize: function () {

		var that = this;
		$.get("/api/getNodeTypeList").done(function(response){
			that.nodeTypeList = response;
			that.render();
			that.showNode(that.nodeTypeList[0].name);
		});
		

	},
	
	getSelectNodeTypeEl: function () {
		return this.$el.find("select[name='type']");
	},
	
	getPropertyInputEl: function (propertyName) {
		return this.$el.find("input[name='" + propertyName+"']");
	},
	
	onSelectTypeChange: function () {
		console.log("onSelectTypeChange");
		this.showNode(this.getSelectNodeTypeEl().val());
	},
	
	
	showNode: function (nodeType) {
		var that = this;
		
		$.get("/api/getNodePropertyList", {nodeType: nodeType}).done(function(response){
			
			that.currentPropertyList = response;
			that.$el.find(".create_node_properties").empty();
			
			for (var i=0, len = that.currentPropertyList.length; i < len; i++) {
				
				that.$el.find(".create_node_properties").append(that.propertyTemplate(that.currentPropertyList[i]));
			}
			
		});
	},
	
	
	collectData: function () {
		var data = {
				type: this.getSelectNodeTypeEl().val(),
				properties: "",
		};
		
		var properties = {};
		for (var i=0, len = this.currentPropertyList.length; i <len; i++) {
			properties[this.currentPropertyList[i].name] = this.getPropertyInputEl(this.currentPropertyList[i].name).val();
		}
		
		data.properties = JSON.stringify(properties);
		return data;
	},
	
	onCreateNodeClick: function () {
		var data = this.collectData();
		$.post("/api/createNode", data).done(function () {
			alert("erstellt");
		})
	},
	
	render: function () {
		var modelData = {
				nodeTypes: this.nodeTypeList,	
			};
			
		this.$el.html(this.template(modelData));
	}
	
});