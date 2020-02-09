<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="./statics/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="./assets/css/index.min.css">
    <link rel="stylesheet" href="./assets/css/common.min.css_vv20180704.css">
    <style>
        .content{
			width: 936px;
			background: #fff;
			position: relative;
			margin: 30px auto;
			padding: 20px;
		}
        .content .glyphicon-ok{
            font-size: 50px;
            color: green;
            padding: 20px 0
        }
        .content .zf{
            color: green
        }
        .content .cz{
            width: 300px;
            margin: 100px auto 0;
            display: flex;
            justify-content: space-between
        }
		
    </style>
    <title>pay</title>
</head>
<body>

<div class="content">
    
    <div class="success text-center">
        <p class="glyphicon glyphicon-ok"></p>

        <p class="zf">您已支付成功</p>
    </div>

    <div class="cz">
        <a href="./payList.html" class="btn btn-primary">查看支付记录</a>
        <button class="btn btn-default">返回主页</button>
    </div>
</div>


<script src="./statics/jquery/3.2.1/jquery.min.js"></script>
<script src="./statics/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script>


</script>
</body>
</html>