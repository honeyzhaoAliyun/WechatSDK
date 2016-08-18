
******************************************************************************************************
*                                                                                                    *
*                            WechatSDK weixin 微信全接口SDK封装 Java                                 *
*                                                                                                    *
*                            QQ 591270247  honey.zhao@aliyun.com                                     *
******************************************************************************************************   

##一、接口说明

###1）         br.com.rest.servlet.CervejaServlet.java       微网站可调用该接口获取微信openid用户信息

###2）         br.com.rest.servlet.menuServlet.java          菜单刷新接口调用

###3）         br.com.rest.servlet.OauthCodeServlet.java     【用户授权后得到的code】获取Access_Token（oAuth认证,此access_token与基础支持的access_token不同）

###4）         /** br.com.rest.servlet.OauthServlet.java  
			 * @author honey.zhao@aliyun.com
			 * 获得Oauth认证的URL
			 * @param redirectUrl	跳转的url
			 * @param charset	字符集格式
			 * @param scope	OAUTH scope
			 * @return oauth url
			 * @API 参数说明
			 * 		参数	必须	说明
			 * 		appid	是	公众号的唯一标识
			 * 		redirect_uri	是	授权后重定向的回调链接地址
			 * 		response_type	是	返回类型，请填写code
			 * 		scope	是	应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
			 * 		state	否	重定向后会带上state参数，开发者可以填写任意参数值
			 * 		#wechat_redirect	否	直接在微信打开链接，可以不填此参数。做页面302重定向时候，必须带此参数
			 */
			 
###5）br.com.rest.servlet.Gcj02Bd09TransferServlet  国测局GCJ-02坐标体系（谷歌、高德、腾讯），与百度坐标BD-09体系的转换			

###6) JsonFileServlet 针对用户 获取当前位置信息，没有进行store.js或jquery cookie方式处理，我们采用了json文件方式处理，在微网站端可以如下调用：eg.
####6.1)写入当前微信用户的地理位置
		getLocationPosition : function(){
        	wx.config({
                debug: false,
                appId: $appId,
                timestamp: $timestamp,
                nonceStr: $random_str,
                signature: getSignature($ticket),
                jsApiList: [
					//所有要调用的 API 都要加到这个列表中
					'checkJsApi',
					'openLocation',
					'getLocation'
                ]
            });
        	wx.checkJsApi({
        	    jsApiList: [
        	        'getLocation'
        	    ],
        	    success: function (res) {
        	        // alert(JSON.stringify(res));
        	        // alert(JSON.stringify(res.checkResult.getLocation));
        	        if (res.checkResult.getLocation == false) {
        	            alert('你的微信版本太低，不支持微信JS接口，请升级到最新的微信版本！');
        	            return;
        	        }
        	    }
        	});
        	wx.getLocation({
        	    success: function (res) {
        	    	var openid = $("#openid").val();
        	        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
        	        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
        	        var speed = res.speed; // 速度，以米/每秒计
        	        var accuracy = res.accuracy; // 位置精度
        	        
        	        if(openid !=""){
        	        	$.ajax({
        				    url: 'http://www.XXX.com/WechatSDK/jsonFile?type=write&openid='+openid+'&latitude='+latitude+'&longitude='+longitude+'&speed='+speed+'&accuracy='+accuracy+'',
        				    dataType: 'json',
        				    success: function(data){}
        				});
        	        }
        	        
        	    },
        	    cancel: function (res) {
        	        alert('用户拒绝授权获取地理位置');
        	    }
        	});
        	
        }
 ####6.2)读取当前微信用户的地理位置
 		$.ajax({
		    url: 'http://www.XXX.com/WechatSDK/jsonFile?type=read&openid='+openid+'',
		    dataType: 'json',
		    success: function(data){
		    	$latitude_temp  = parseFloat(data.locationPosition.latitude);
				$longitude_temp = parseFloat(data.locationPosition.longitude);
		    }
		});

##二、初始化调用servlet:
com.ifp.wechat.controller.WeChatServlet.java

##三、jsapiticket处理 
此外 doc文件夹下的api 是token监听 api部分 可以帮助 大家读懂token监听部分代码的实现 这部分代码其实wechat4j 的部分 只不过把数据存储部分改成 了properties文件 的操作 考虑到 每7000s才去操作一次 不是太频繁所以才这么做的 不足之处请 提建议 上边有联系方式

			 