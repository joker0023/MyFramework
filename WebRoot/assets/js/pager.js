$(function(){
	$(".pagination > li:first > a").click(function(){
		if(!$(this).parent().hasClass("disabled")){
			var currentPage = $("#pager_currentPage").val();
			currentPage = parseInt(currentPage);
			currentPage -= 1;
			$("#pager_currentPage").val(currentPage);
			jumpPage();
		}
	});
	
	$(".pagination > li:last > a").click(function(){
		if(!$(this).parent().hasClass("disabled")){
			var currentPage = $("#pager_currentPage").val();
			currentPage = parseInt(currentPage);
			currentPage += 1;
			$("#pager_currentPage").val(currentPage);
			jumpPage();
		}
	});
	
	$(".pagination .pageNum-btn").click(function(){
		$("#pager_currentPage").val($(this).text());
		jumpPage();
	});
	
	
	
	(function(){
		var params = $("#pager_params").val();
		var fields = params.split('&');
		var form_html = '';
		for(var i = 0 ; i < fields.length; i ++){
			if(fields[i].indexOf("=") != -1){
				if(fields[i].split('=')[0] != 'pager.currentPage' && fields[i].split('=')[0] != 'pager.pageSize' && fields[i].split('=')[0] != 'params' && fields[i].split('=')[0] != 'ajaxTag'){
					form_html += '<input type="hidden" name="'+fields[i].split('=')[0]+'" value="'+fields[i].split('=')[1]+'"/>';
				}
			}
		}
		$("#page-form").append(form_html);
		$("#pager_params").val('');
		
		var showTipHtml = '<div id="alert-tip">' +
						'<span class="glyphicon glyphicon-ok"></span>' +
						'<span>保存成功</span>'
						'</div>';
		
		$(document.body).prepend(showTipHtml);
	})();
	
	function jumpPage(){
		var ajaxTag = $("#pager_ajaxTag");
		
		if(ajaxTag.size() > 0 && ajaxTag.val() != ""){
			$.ajax({
			   type: "POST",
			   url: $("#page-form").attr("action"),
			   data: $("#page-form input").serialize(),
			   success: function(msg){
			   		$(ajaxTag.val()).html(msg);
			   },
			   error:function(msg){
				   alert('服务器繁忙，请稍后再试！');
				   return;
			   }
			}); 	
		}else{
			$("#page-form").submit();
		}
	}
});

function showTipAndReload(data, tip){
	if(null == data){
		return;
	}
	if(data.isSuccess){
		$("#alert-tip").find("span").last().text(tip + "成功");
		$("#alert-tip").show();
		setTimeout(function(){
			$("#alert-tip").hide();
			$("#page-form").submit();
		}, 1000);
	}else{
		alert(tip + "失败: " + data.errMsg);
	}
}