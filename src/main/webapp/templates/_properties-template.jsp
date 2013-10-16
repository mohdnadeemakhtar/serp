<div class="properties">

{{for (var i=0, len = properties.length; i < len; i++) { }}
	<div class="properties_row">
		<label>{{=properties[i].label}}</label>
		<input type="text" name="{{=properties[i].name}}">
	</div>
{{ } }}

</div>