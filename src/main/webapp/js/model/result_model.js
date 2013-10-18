
serp.model.RESULT_NODE = 1;
serp.model.RESULT_RELATIONSHIP = 2;
serp.model.RESULT_DATA = 3;

serp.model.ResultModel = Backbone.Model.extend({

	getType: function () {
		return this.get("type");
	},

});


serp.model.ResultCollection = Backbone.Collection.extend({
	model: serp.model.ResultModel,
});