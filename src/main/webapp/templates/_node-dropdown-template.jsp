<select name="node">
	{{for (var i=0, len = nodes.length; i < len; i++) { }}
		<option value="{{=nodes[i].id}}">{{=JSON.stringify(nodes[i]) }}</option>
	{{ } }}
</select>