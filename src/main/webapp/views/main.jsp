<!DOCTYPE>
<html>
<head>
<%-- 	<script id="create-node-template" type="text/template"><%@ include file="/templates/_create-node-template.jsp" %></script>
	<script id="create-node-property-template" type="text/template"><%@ include file="/templates/_create-node-property-template.jsp" %></script>
	<script id="create-relationship-template" type="text/template"><%@ include file="/templates/_create-relationship-template.jsp" %></script>
	<script id="node-dropdown-template" type="text/template"><%@ include file="/templates/_node-dropdown-template.jsp" %></script>
	<script id="node-selector-template" type="text/template"><%@ include file="/templates/_node-selector-template.jsp" %></script>
	<script id="properties-template" type="text/template"><%@ include file="/templates/_properties-template.jsp" %></script> --%>
	

	<script src="/js/lib/jquery-1.10.2.min.js"></script>
	<script src="/js/lib/underscore-min.js"></script>
	<script src="/js/lib/backbone-min.js"></script>
	
	<script src="/js/serp.js"></script>
	<script src="/js/view/create_node_view.js"></script>
	<script src="/js/view/node_selector_view.js"></script>
	<script src="/js/view/create_relationship_view.js"></script>
	<script src="/js/view/properties_view.js"></script>
	<script src="/js/main_page.js"></script>
	
	<link rel="stylesheet" type="text/css" href="/css/main.css">
	
</head>

<body class="main_page">
	<div style="text-align: right;">
		<span class="logout_button">Logout</span>
	</div>
	
	<h1>SERP</h1>

	<div id="create_node_box" class="box"></div>
	<div id="create_relationship_box" class="box"></div>
</body>
</html>