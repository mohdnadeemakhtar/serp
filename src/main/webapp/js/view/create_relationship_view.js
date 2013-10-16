
serp.view.CreateRelationshipView = Backbone.View.extend({

	template: serp.template("create-relationship-template"),

	events: {
		"click *[data-action='create']": "onCreateButtonClick",
	},

	initialize: function () {
		var that = this;
		$.get("/api/getRelationshipTypeList").done(function(response) {
			that.relationshipTypeList = response;
			that.render();
			that.showRelationProperties(that.relationshipTypeList[0].name);
		});
	},

	getSelectNode1El: function () {
		return this.$el.find(".create_relationship_node[data-nr='1'] select[name='node']");
	},

	getSelectNode2El: function () {
		return this.$el.find(".create_relationship_node[data-nr='2'] select[name='node']");
	},

	getRelationshipTypeEl: function () {
		return this.$el.find(".create_relationship_relation select");
	},

	collectData: function () {
		var data = {
			node1Id: this.getSelectNode1El().val(),
			node2Id: this.getSelectNode2El().val(),
			type: this.getRelationshipTypeEl().val(),
			properties: JSON.stringify(this.relationPropertiesView.collectData())
		}

		return data;
	},

	onCreateButtonClick: function () {
		$.post("/api/createRelationship", this.collectData()).done(function (response) {
			alert("Erstellt!");
		}).fail(function(response) {
			alert("konnte nicht erstellt werden!");
		})
	},

	showRelationProperties: function (relType) {
		if (this.relationPropertiesView) {
			this.relationPropertiesView.close();
		}
		var that = this;

		$.get("/api/getRelationshipPropertyList", {relationshipType: relType}).done(function(response) {
			that.relationPropertiesView = new serp.view.PropertiesView({
				properties: response
			});
			that.$el.find(".create_relationship_relation_properties").append(that.relationPropertiesView.$el);
		});

		
	},

	render: function () {
		this.$el.html(this.template({
			relationTypes: this.relationshipTypeList,
		}));

		this.nodeSelector1 = new serp.view.NodeSelectorView({
			el: ".create_relationship_node[data-nr='1']",
		});
		this.nodeSelector2 = new serp.view.NodeSelectorView({
			el: ".create_relationship_node[data-nr='2']",
		});
	},

});