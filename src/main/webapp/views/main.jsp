<!DOCTYPE HTML>
<html>
<head>
	<script src="/js/lib/jquery-1.10.2.min.js"></script>
	<script src="/js/lib/underscore.js"></script>
	<script src="/js/lib/backbone-min.js"></script>
	
	<script src="/js/serp.js"></script>

	<script src="/js/model/result_model.js"></script>

	<script src="/js/view/create_node_view.js"></script>
	<script src="/js/view/node_selector_view.js"></script>
	<script src="/js/view/create_relationship_view.js"></script>
	<script src="/js/view/properties_view.js"></script>
	<script src="/js/view/result_table_view.js"></script>
	<script src="/js/main_page.js"></script>
	
	<link rel="stylesheet" type="text/css" href="/css/main.css">
	<link rel="stylesheet" type="text/css" href="/css/result_table.css">

</head>

<body class="main_page">
	<div style="text-align: right;">
		<span class="logout_button">Logout</span>
	</div>
	
	<h1>SERP</h1>

 	<div id="create_node_box" class="box"></div>
	<div id="create_relationship_box" class="box"></div> 

	<div style="height: 30px;"></div>

	<h2>Tabellen (Test)</h2>

	<div class="box">
		<h2>Knoten</h2>
		<div id="result_table_nodes_test" ></div>
	</div>
	
	<div class="box">
		<h2>Relationen</h2>
		<div id="result_table_relationship_test" ></div>
	</div>

	<div id="test"></div>
</body>
</html>