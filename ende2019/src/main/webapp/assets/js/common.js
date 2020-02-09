/**
 * ENDE2019
 * createTime 2018.08.29
 * author Jim Wang (Hao Wang)
 * version 1.0
 *
 * updateTime 2018.12.19
 * version 1.1
 */
var baseURL = "http://localhost:8080/ENDE2019/";//不能用localhost来代替127.0.0.1
//var baseURL = "http://www.ende2019.com:80/ENDE2019/";
//var baseURL = "http://www.ende2019.com/";
//var baseURL = "http://test.ende2019.com/";
var searchURL = "http://www.ende2019.com:9200/ende2019/ende/_search";

//加载网页内容
var loadPage = function(page){
    $("#main").load(baseURL + page);
}

function setCookie(key,value,t)
{
	var oDate=new Date();
	oDate.setDate(oDate.getDate()+t);
	document.cookie=key+"="+encodeURI(value)+"; expires="+oDate.toDateString();
}

function getCookie(key){
	var arr1=document.cookie.split("; ");//由于cookie是通过一个分号+空格的形式串联起来的，所以这里需要先按分号空格截断,变成[name=Jack,pwd=123456,age=22]数组类型；
	for(var i=0;i<arr1.length;i++){
	    var arr2=arr1[i].split("=");//通过=截断，把name=Jack截断成[name,Jack]数组；
		if(arr2[0]==key){
			return decodeURI(arr2[1]);
		}
	}
}

function delCookie(name)
{
	var exp = new Date();
	exp.setTime(exp.getTime()-1);
	var cval=getCookie(name);
	if(cval!=null)
	document.cookie= name+"="+cval+";expires="+exp.toGMTString();
}

function search()
{
	var keyword =  $(".form-search").val();
	var data = {"query" : { "match" : { "keyword" : keyword}}};
	$.ajax({
        type: "POST",
        url: searchURL,
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(data){
	        if(data.hits.hits.length > 0){
	        	loadPage(data.hits.hits[0]._source.page);
	        }
	    }
    });																			
}
//中英文切换
var switchLanguage = function(lag){
    if(0 == lag){
        window.location.href = baseURL+"index.html";
    }else{
        window.location.href = baseURL+"cn_index.html";
    }
};

//登陆提示
var islogin = function(){
    var username = getCookie("username");
    var userid = getCookie("userid");
    if(username != null)
    {
        //删除注册登录按钮
        $("#username").html(username);
        $("#register").css("display","none");
        $("#login").css("display","none");
        $("#submit").css("display","none");
        $("#contribute").css("display","block");
        $("#v_papers").css("display","block");
        $("#w_register").css("display","block");
        $("#pay").css("display","block");
        $("#quitButton").css("display","block");
    }
};

$("#quitButton").click(function(){
    delCookie("username");
    delCookie("userid");
    window.location.href = baseURL+"index.html";
});