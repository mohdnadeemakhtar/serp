
serp.view.LandingPageView = Backbone.View.extend ({
	
	events: {
		"click #login_button": "onLoginClick"
	},
	
	initialize: function () {
		console.log("init landingPage view");
	},
	
	getUserNameEl: function () {
		return this.$el.find(".login_box input[name='username']");
	},
	
	getPasswordEl: function () {
		return this.$el.find(".login_box input[name='password']");
	},
	
	onLoginClick: function () {
		serp.login(this.getUserNameEl().val(), this.getPasswordEl().val).done(function () {
			window.location.reload();
		}).fail(function () {
			alert("Login fehlgeschlagen");
		})
	}
	
});



// Init Landing Page
$(document).ready(function () {
	new serp.view.LandingPageView({el: 'body'});
});