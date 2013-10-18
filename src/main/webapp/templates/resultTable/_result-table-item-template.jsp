<tr>
	{{for (var i=0, len = columns.length; i < len; i++) { }}
		<td>{{=columns[i].getValue(model)}}</td>
	 {{ } }}
</tr>