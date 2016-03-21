(function($) {
    var init = function(target) {
        var $this = $(target);
        
        $this.attr({style:'display:inline-block;'});
        
        $this.append( "<div class='fun' data-options=region:'west',border:false>"+
        				  "<img class='fun1' src='../pic/fun_gray.png'   style='height:100%;width:100%;position:absolute;left:0;top:0' />"+
        				  "<img class='yepian' src='../pic/fun_null.png' style='height:100%;width:100%;position:absolute;left:0;top:0' />"+ 
        				  "<img class='error'  src='../pic/fun_null.png' style='height:100%;width:100%;position:absolute;left:0;top:0' />"+
					  "</div>"+
					  "<div class='data1' data-options=region:'center',border:false style='overflow:hidden;'>"+
					  	"<p class='funName' style='font-family: Verdana;'>111</p>"+
					  	"<p class='funMode' style='font-family: Verdana;background: rgb(230, 238, 214)'>222</p>"+
					  	"<p class='windSpeed' style='font-family: Verdana;'>333</p>"+
					  	"<p class='power' style='font-family: Verdana;background: rgb(230, 238, 214)'>444</p>"+
					  	"<p class='energy'style='font-family: Verdana;'>1113</p>"+
					  	"<p class='energyCounter' style='font-family: Verdana;background: rgb(230, 238, 214)'>222</p>"+
					  "</div>"
	);
        $('.fun',$this).width($this.height());
        
        $this.layout();
        
        $this.fundata('resize');
        
        // 尝试去获取settings，如果不存在，则返回“undefined”
        var settings = $this.data('fundata');
        
        // 如果获取settings失败，则根据options和default创建它
        if(typeof(settings) == 'undefined') {

            var defaults = {
            	funName:'#机组',
            	___main_loop_mode_number: 0,
                windSpeed: 0.00,
                power: 0.00,
                energy: 0.00,
                energyCounter: 0.00,
                worning: false,
                error: false,
                url:'',
                onSomeEvent: function(){}
            }
            
            $('.funName',$this).text(defaults.funName);
            $('.funMode',$this).text('风机模式：' + defaults.___main_loop_mode_number);
            $('.windSpeed',$this).text('风速：    ' + defaults.windSpeed);
            $('.power',$this).text('有功功率：' + defaults.power);
            $('.energy',$this).text('日发电量：' + defaults.energy);
            $('.energyCounter',$this).text('总发电量：' + defaults.energyCounter);
            
            //settings = $.extend({}, defaults, thisOptions);

            // 保存新创建的settings
            $this.data('fundata', defaults);
            
            
        } else {
            //如果获取了settings，则将它和options进行合并（这不是必须的，你可以选择不这样做）
            settings = $.extend({}, settings, thisOptions);

            
            // 如果你想每次都保存options，可以添加下面代码：
            
             $this.data('fundata', settings);
        }

        // 执行代码
    }

    $.fn.fundata = function(options, param) {
        //如果options为string，则是方法调用，如$('#divMyPlugin').hello('sayHello');
        if (typeof options == 'string'){
            var method = $.fn.fundata.methods[options];
            if (method){
                return method(this, param);
            }
        }
         
        //否则是插件初始化函数，如$('#divMyPlugin').hello();
        options = options || {};
        return this.each(function(){
            var state = $.data(this, 'fundata');
            if (state){
                $.extend(state.options, options);
            } else {
                //easyui的parser会依次计算options、initedObj
                state = $.data(this, 'fundata', {
                    options: $.extend({}, $.fn.fundata.defaults, $.fn.fundata.parseOptions(this), options),
                    init: init(this) //这里的initedObj名称随便取的
                });
            }
        })      
    }
    
    $.fn.fundata.methods =  {        
        resize: function( jq, params){
			var funCount = jq.length;
			var height = $(window).height();
			var width = $(window).width();
			var funArea = (height*width/funCount)*0.6;
			var funHeight = (Math.sqrt(10000-(-4*funArea))-100)/2;//求解四边形边长的一元二次方程
			
			return jq.each(function(){
				   var $this = $(this);
				   $this.height(funHeight).width(funHeight+100);
				   $this.layout('panel','west').panel('resize',{height:funHeight,width:funHeight});
				   $this.layout('panel','center').panel('resize',{left:funHeight,height:funHeight,width:100});
				   
			})

        },

        refresh: function(jq, params) {
    	
	    	// 在每个元素中执行代码
	        return jq.each(function(i) {
	        	var thisOptions = params[i];
	        	
	            var $this = $(this);
	            // 执行代码
	            var settings = $this.data('fundata');
	            
	            var funMode = settings.___main_loop_mode_number;	            
	            
	            if(thisOptions.___main_loop_mode_number!=funMode){
	            	switch(thisOptions.___main_loop_mode_number){
	            	case 0:
	            		$('.fun1',$this).attr('src','../pic/fun_gray.png');
	            		$('.yepian',$this).attr('src','../pic/fun_null.png');
	            		$('.error',$this).attr('src','../pic/fun_null.png');
	            		
	            		$('.yepian',$this).velocity('stop');
	            		break;
	            	case 1:
	            		$('.fun1',$this).attr('src','../pic/fun_red.png');
	            		$('.yepian',$this).attr('src','../pic/fun_null.png');
	            		$('.error',$this).attr('src','../pic/fun_null.png');
	            		$('.yepian',$this).velocity('stop');
	            		break;
	            	case 2:
	            		$('.fun1',$this).attr('src','../pic/fun_yellow.png');
	            		$('.yepian',$this).attr('src','../pic/fun_null.png');
	            		$('.error',$this).attr('src','../pic/fun_null.png');
	            		$('.yepian',$this).velocity('stop');
	            		break;
	            	case 3:
	            	case 4:
	            	case 5:
	            		$('.fun1',$this).attr('src','../pic/tatong.png');
	            		$('.yepian',$this).attr('src','../pic/yepian.png');
	            		$('.error',$this).attr('src','../pic/fun_null.png');
	        			$('.yepian',$this).velocity({rotateZ:'360deg'},{ duration:5000,loop:true,easing:'linear'});
	            		break;
	            	case 9:
	            		$('.fun1',$this).attr('src','../pic/fun_purple.png');
	            		$('.yepian',$this).attr('src','../pic/fun_null.png');
	            		$('.error',$this).attr('src','../pic/fun_null.png');
	            		$('.yepian',$this).velocity('stop');
	            		break;
	            	case 10:
	            		$('.fun1',$this).attr('src','../pic/fun_blue.png');
	            		$('.yepian',$this).attr('src','../pic/fun_null.png');
	            		$('.error',$this).attr('src','../pic/fun_null.png');
	            		$('.yepian',$this).velocity('stop');
	            		break;
	            	}
	            }
	            
	            $('.funName',$this).text(thisOptions.fun.name);
	            $('.funMode',$this).text('风机模式：' + thisOptions.___main_loop_mode_number);
	            
	            if(thisOptions.___wind_speed!=undefined){
	            	
	            	$('.windSpeed',$this).text('风速：    ' + thisOptions.___wind_speed);
	            	$('.power',$this).text('有功功率：' + thisOptions.___visu_grid_power);
	            	$('.energy',$this).text('日发电量：' + thisOptions.___visu_grid_energy);
	            	$('.energyCounter',$this).text('总发电量：' + thisOptions.___visu_grid_energy_counter);
	            }
	            
	            settings = $.extend({}, settings, thisOptions);
	
	            // 保存新创建的settings
	            $this.data('fundata', settings);
	
	        });
	        
	    }
    };
    
    $.fn.fundata.defaults = {//默认属性定义
          	funName:'#机组',
        	___main_loop_mode_number: 0,
            windSpeed: 0.00,
            power: 0.00,
            energy: 0.00,
            energyCounter: 0.00,
            worning: false,
            error: false,
            url:''
    	};
    
    $.fn.fundata.parseOptions = function (target) {
        var opts = $.extend({}, $.parser.parseOptions(target, []));//这里可以指定参数的类型
        return opts;
    };

    $.parser.plugins.push('fundata');//将自定义的插件加入 easyui 的插件组
 
})(jQuery);
