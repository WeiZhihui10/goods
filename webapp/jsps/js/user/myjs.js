window.onload=function(){
	//鼠标进入该元素时，切换注册按钮的图片
	var submitBtn=document.getElementById("submitBtn");
	submitBtn.onmouseover=function(){
		submitBtn.src="/goods/images/regist2.jpg";
	}
	submitBtn.onmouseout=function(){
		submitBtn.src="/goods/images/regist1.jpg";
	}
	
	//换一张验证码
	function _hyz(){
		var imgVerufyCode=document.getElementById("imgVerifyCode");
		imgVerufyCode.src="/goods/VerifyCodeServlet?a="+new Date().getTime();
	}
	
}


//获取元素当前样式
function getStyle(obj,name){
	if(window.getComputedStyle){
		return	getComputedStyle(obj,null)[name];
	}
	else{
		return obj.currentStyle[name];
	}
}



/*
* 定义一个函数，用来向一个元素中添加指定的class属性值
* 参数
* 	obj:要添加class属性的元素
* 	name:要添加的class的值
*/
function addClass(obj,name){
	obj.className+=" "+name;
}

/*
* 判断一个元素中是否含有指定的class属性值
*  obj:要检查class属性的元素
* 	name:要检查的class的值
*/
function hasClass(obj,name){
	var reg=new RegExp("\\b"+name+"\\b");
	return reg.test(obj.className);
}

/*
* 定义一个函数，删除一个元素中指定的元素
*/
function removeClass(obj,name){
	var reg=new RegExp("\\b"+name+"\\b");
	var regs=/\s/;
	obj.className=obj.className.replace(reg,"")
	obj.className=obj.className.replace(regs,"");
}

/*
* toggleClass可以用来切换一个类
* 如果元素中具有该类，则删除
* 如果元素中没有该类，则添加
*/
function toggleClass(obj,name){
	if(hasClass(obj,name)){
		removeClass(obj,name);
	}else{
		addClass(obj,name);
	}
}


