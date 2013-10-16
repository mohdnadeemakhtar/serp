<div class="create_relationship">
	<h2>Relation erstellen</h2>
	
	<h3>Knoten 1</h3>
	<div class="create_relationship_node" data-nr="1">
		
	</div>

	<h3>Relation</h3>
	<div class="create_relationship_relation">
		<select name="type">
			{{for (var i=0, len = relationTypes.length; i < len; i++) { }}
				<option value="{{=relationTypes[i].name}}">{{=relationTypes[i].label}}</option>
			{{ } }}
		</select>
	</div>
	
	<h3>Knoten 2</h3>
	<div class="create_relationship_node" data-nr="2">
		
	</div>
	
</div>