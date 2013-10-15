<div class="create_node">

	<h2>Knoten erstellen</h2>

	<div class="create_node_section">

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

	</div>


	<div class="create_node_section">
		<div class="create_node_row">
			<label>Attribute</label>
			<div class="create_node_content"></div>
		</div>

		<div class="create_node_properties">
		</div>
	</div>
	

	<div class="create_node_section" style="width: 100%">
		<div class="create_node_row">
			<div class="create_node_content" style="text-align: right;">
				<div class="button" data-action="create">Erstellen</div>
			</div>
		</div>
	</div>
	
</div>