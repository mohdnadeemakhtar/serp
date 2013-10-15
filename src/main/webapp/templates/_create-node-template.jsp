<div class="create_node">
	<div class="create_node_row">
		<label>Typ:</label>
		<div class="create_node_content">
			<select name="type">
				{{for (var i=0, len = nodeTypes.length; i < len; i++) { }}
					<option value="{{=nodeTypes[i].name}}">{{=nodeTypes[i].label}}</option>
				{{ } }}
			</select>
		</div>
	</div>
	
	<div class="create_node_row">
		<label>Attribute</label>
		<div class="create_node_content"></div>
	</div>

	<div class="create_node_properties">
	</div>
	
</div>