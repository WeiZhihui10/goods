$(function(){
	//得到所有错误信息，循环遍历。调用一个方法来确定是否显示错误信息
	$(".errorClass").each(function(){
		showError($(this));//遍历每个元素，使每个元素来调用showError方法
	})
	
	//鼠标进入元素时，切换注册按钮的图片
	$("#submitBtn").hover(
		function(){
			$("#submitBtn").attr("src","/goods/images/regist2.jpg");
			},
		function(){
			$("#submitBtn").attr("src","/goods/images/regist1.jpg");
		});
	
	//输入框得到焦点隐藏错误信息
	$(".inputClass").focus(function() {
		var labelId=$(this).attr("id")+"Error";//通过输入框找到对应的labelId
		$("#"+labelId).text("");//把label的内容清空
		showError($("#"+labelId));
	});
	//输入框失去焦点进行校验
	$(".inputClass").blur(function() {
		var id=$(this).attr("id");
		var funName="validate"+id.substring(0,1).toUpperCase()+id.substring(1)+"()";//得到对应的函数名
		eval(funName);//执行函数，funName是字符串
	});
	
	//表单校验
	$("#registForm").submit(function() {
		var bool=true;
		bool=validateLoginname();
		if(!validateLoginname()){
			bool=false;
		}
		if(!validateLoginpwd()){
			bool=false;
		}
		if(!validateLoginpwd2()){
			bool=false;
		}
		if(!validateEmail()){
			bool=false;
		}
		if(!validateVerifyCode()){
			bool=false;
		}
		
		//验证码是否正确
		var value = $("#verifyCode").val();
		$.ajax({
			cache: false,
			async: false,
			type: "POST",
			dataType: "json",
			data: {oper: "ajaxValidateVerifyCode", verifyCode: value},
			url: "/goods/UserServlet",
			success: function(flag) {
				if(!flag) {
					$("#verifyCodeError").css("display", "");
					$("#verifyCodeError").text("验证码不正确！");
					bool = false;					
				}
			}
		});
		return bool;
	});
	
});

//注册的用户名校验
function validateLoginname(){
	var id="loginname";
	var value=$("#"+id).val();
	//非空校验
	if(!value){
		$("#"+id+"Error").text("用户名不能为空！");
		showError($("#"+id+"Error"));
		return false;
	}
	//长度校验
	if(value.length<3||value.length>20){
		$("#"+id+"Error").text("用户名长度必须在3~20之间");
		showError($("#"+id+"Error"));
		return false;
	}
	//用户名是否注册校验
	$.ajax({
		url:"/goods/UserServlet",
		data:{oper:"ajaxValidateLoginname",loginname:value},
		type:"POST",
		dataType:"json",
		async:"false",
		cache:"false",
		success:function(result){
			if(result){
				$("#"+id+"Error").text("用户名已被注册");
				showError($("#"+id+"Error"));
				bool=false;
			}
		}
	});
	return true;
}

//登录密码校验
function validateLoginpwd(){
	var id="loginpwd";
	var value=$("#"+id).val();
	//非空校验
	if(!value){
		$("#"+id+"Error").text("密码不能为空！");
		showError($("#"+id+"Error"));
		return false;
	}
	//长度校验
	if(value.length<3||value.length>20){
		$("#"+id+"Error").text("密码长度必须在3~20之间");
		showError($("#"+id+"Error"));
		return false;
	}
	return true;
}

//确认密码校验
function validateLoginpwd2(){
	var id="loginpwd2";
	var value=$("#"+id).val();
	//非空校验
	if(!value){
		$("#"+id+"Error").text("密码不能为空！");
		showError($("#"+id+"Error"));
		return false;
	}
	//两次密码校验
	if(value!=$("#loginpwd").val()){
		$("#"+id+"Error").text("密码不一致");
		showError($("#"+id+"Error"));
		return false;
	}
	return true;
}

//邮箱校验
function validateEmail(){
	var id="email";
	var value=$("#"+id).val();
	var reg=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/i;
	//非空校验
	if(!value){
		$("#"+id+"Error").text("Email不能为空！");
		showError($("#"+id+"Error"));
		return false;
	}
	//Email格式校验
	if(!reg.test(value)){
		$("#"+id+"Error").text("Email格式不正确");
		showError($("#"+id+"Error"));
		return false;
	}
	//邮箱是否注册校验
	$.ajax({
		url:"/goods/UserServlet",
		data:{oper:"ajaxValidateEmail",email:value},
		type:"POST",
		dataType:"json",
		async:"false",
		cache:"false",
		success:function(result){
			if(result){
				$("#"+id+"Error").text("邮箱已被注册！");
				showError($("#"+id+"Error"));
				bool=false;
			}
		}
	});
	return true;
}

//验证码校验
function validateVerifyCode(){
	var id="verifyCode";
	var value=$("#"+id).val();
	//非空校验
	if(!value){
		$("#"+id+"Error").text("验证码不能为空！");
		showError($("#"+id+"Error"));
		return false;
	}
	//长度校验
	if(value.length<4){
		$("#"+id+"Error").text("验证码不正确");
		showError($("#"+id+"Error"));
		return false;
	}
	return true;
}

//判断当前元素是否存在内容，如果存在显示，不存在不显示！
function showError(ele) {
	var text = ele.text();//获取元素的内容
	if(!text) {//如果没有内容
		ele.css("display", "none");//隐藏元素
	} else {//如果有内容
		ele.css("display", "");//显示元素
	}
}

//换一张验证码
function _hyz(){
	$("#imgVerifyCode").attr("src","/goods/VerifyCodeServlet?a="+new Date().getTime());
}


