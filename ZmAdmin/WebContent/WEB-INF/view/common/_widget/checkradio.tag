<div id="${id!}_cr" data-type="checkradio" class="control-group"></div>
<input id="${id!}" type="hidden" name="${name!}" value="${value!}"/>
<script type="text/javascript">
	$(function(){
		@ if (autoInit!'true' == 'true') {
			initCheckRadio${id!}_cr("${value!}")
		@ }
		
		$("#${id!}_cr").closest("td").css("padding", "5px");
	});
	
	function initCheckRadio${id!}_cr(val){
		var ajax = new Ajax("${ctxPath}/cache/getCheckRadio", function(data){
			if (data.code === 0) {
				var json = data.data;
				var html = [];
				for (var i = 0; i < json.length; i++) {
					var id = json[i].id;
					var text = json[i].text;
					html.push("<label>");
					html.push("		<input id=\"${id!}_cr_" + id + "\"  value='" + id + "' type=\"${check!'checkbox'}\" class=\"ace\" />");
					html.push("		<span class=\"lbl\">" + text + "</span>");
					html.push("</label>&nbsp;&nbsp;");
				}
				$("#${id!}_cr").html(html.join(""));
				$("#${id!}_cr").find("input").bind("click", function(){
					$("#form_token").val(1);
					var ids = [];
					$("#${id!}_cr").find("input").each(function(){
						if($(this).is(":checked")) {
							ids.push($(this).val());
						}
					});
					$("#${id!}").val(ids.join(","));
				});
				if (val != "") {
					var ids = new Array();
					ids = val.split(",");
					$("#${id!}_cr").find("input").each(function(){
						if(ids.contains($(this).attr("value"))) {
							$(this).attr("checked","checked");
						}
					});
				}
			} else {
				layer.alert(data.message, {icon: 2,title:"发生错误"});
			}
		});
		ajax.set("type", "${type!}");
		ajax.set("source", "${source!}");
		ajax.set("where", "${where!}");
		ajax.start();
	}
	
	
</script>