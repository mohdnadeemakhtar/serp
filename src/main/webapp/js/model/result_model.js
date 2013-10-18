
serp.model.RESULT_NODE = 1;
serp.model.RESULT_RELATIONSHIP = 2;
serp.model.RESULT_DATA = 3;

/*
	Model that holds data of query result, which may be a node, a relation or some other result data
*/
serp.model.ResultModel = Backbone.Model.extend({

	getType: function () {
		return this.get("type");
	},

	getProperties: function () {
		return this.get("properties");
	}

});


serp.model.ResultCollection = Backbone.Collection.extend({
	model: serp.model.ResultModel,
});