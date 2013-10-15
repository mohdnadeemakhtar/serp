<div class="create_relationship_node_selection">
	<select name="type">
		{{for (var i=0, len = nodeTypes.length; i < len; i++) { }}
			<option value="{{=nodeTypes[i].name}}">{{=nodeTypes[i].label}}</option>
		{{ } }}
	</select>
	
	<div class="create_relationship_node_selection_node">
	</div>
</div>