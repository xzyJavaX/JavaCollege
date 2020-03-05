//判断是否为空
function checkEmpty(value,text){
		
    if(null==value || value.length==0){
        alert(text+ "不能为空");
        return false;
    }
    return true;
}	

//获取地址栏参数的函数
function getUrlParms(para){
    var search=location.search; //页面URL的查询部分字符串
    var arrPara=new Array(); //参数数组。数组单项为包含参数名和参数值的字符串，如“para=value”
    var arrVal=new Array(); //参数值数组。用于存储查找到的参数值
  
    if(search!=""){
        var index=0;
        search=search.substr(1); //去除开头的“?”
        arrPara=search.split("&");
  
        for(i in arrPara){
            var paraPre=para+"="; //参数前缀。即参数名+“=”，如“para=”
            if(arrPara[i].indexOf(paraPre)==0&& paraPre.length<arrPara[i].length){
                arrVal[index]=decodeURI(arrPara[i].substr(paraPre.length)); //顺带URI解码避免出现乱码
                index++;
            }
        }
    }
  
    if(arrVal.length==1){
        return arrVal[0];
    }else if(arrVal.length==0){
        return null;
    }else{
        return arrVal;
    }
} 

  //判断是否整数
  function checkInt(value, text){
		
    if(value.length==0){
        alert(text+ "不能为空");
        return false;
    }
    if(parseInt(value)!=value){
        alert(text+ "必须是整数");
        return false;
    }
    return true;
}