/*
	Table View that displays
		- nodes of the same type
		- relationships of the same type
		- query results (not editable)
*/

serp.view.ResultTableView = Backbone.View.extend({

	template: serp.template("result-table-template"),
	itemTemplate: serp.template("result-table-item-template"),

	initialize: function (options) {
		if (!options.resultType) {
			throw "no result type";
		}
		this.resultType = options.resultType;
		console.log("collection "+JSON.stringify(this.collection));
		this.listenTo(this.collection, "add", this._addItem);
		this.listenTo(this.collection, "reset", function () {console.log("reset")});
	},

	render: function () {

		// hard coded column description, to be replaced by dynamic one
		if (this.resultType === serp.model.RESULT_NODE) {
			this.columns = [
				{
					label: "ID",
					getValue: function (model) {
						return model.get("id");
					}
				},
				{
					label: "Typ",
					getValue: function (model) {
						return model.get("nodeType");
					}
				},
				{
					label: "Vorname",
					getValue: function (model) {
						return model.get("properties").first_name;
					}
				},
				{
					label: "Nachname",
					getValue: function (model) {
						return model.get("properties").last_name;
					}
				},
				{
					label: "Alter",
					getValue: function (model) {
						return model.get("properties").age;
					}
				}
			];
		}

		else if (this.resultType === serp.model.RESULT_RELATIONSHIP) {
			this.columns = [
				{
					label: "ID",
					getValue: function (model) {
						return model.get("id");
					}
				},
				{
					label: "Typ",
					getValue: function (model) {
						return model.get("relationshipType");
					}
				},
				{
					label: "Vorname",
					getValue: function (model) {
						return model.get("properties").first_name;
					}
				},
				{
					label: "Nachname",
					getValue: function (model) {
						return model.get("properties").last_name;
					}
				},
				{
					label: "Alter",
					getValue: function (model) {
						return model.get("properties").age;
					}
				}
			];
		}

		this.$el.html(this.template({
			columns: this.columns
		}));
		return this;
	},


	_addItem: function (model) {
		console.log("result table _addItem");
		this.$el.find("tbody").append(this.itemTemplate({
			columns: this.columns,
			model: model
		}));
	},

});