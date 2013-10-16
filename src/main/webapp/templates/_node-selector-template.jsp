<div class="node_selector">
	<select name="type">
		{{for (var i=0, len = nodeTypes.length; i < len; i++) { }}
			<option value="{{=nodeTypes[i].name}}">{{=nodeTypes[i].label}}</option>
		{{ } }}
	</select>
	
	<div class="node_selector_node">
	</div>
</div>