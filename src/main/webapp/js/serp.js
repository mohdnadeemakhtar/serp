
var serp = {};
serp.view = {};

serp.login = function (username, password) {
	return $.post ("/login", {
		username: username,
		password: password
	});
}
