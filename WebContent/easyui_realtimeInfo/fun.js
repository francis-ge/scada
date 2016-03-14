(function($) {
    var privateFunction = function() {
        
// 执行代码
    }
 
    var methods = {
        init: function(options) {
 
            
        	// 在每个元素上执行方法
            return this.each(function() {
                var $this = $(this);
                
                $this.append('<div class=fun style=background-color:#00FF00,position:relative,display:inline-block,height:50px,width:50px>' +
                				'<img class=yepian src= style=height:100%,width:100%,position:absolute>' +
                				'<img class=tatong >' +
                			'</div>');
                // 尝试去获取settings，如果不存在，则返回“undefined”
                var settings = $this.data('funData');
 
                
                // 如果获取settings失败，则根据options和default创建它
                if(typeof(settings) == 'undefined') {
 
                    var defaults = {
                    	funName:'机组',
                        funMode: 0.00,
                        windSpeed: 0.00,
                        power: 0.00,
                        worning: false,
                        error: false,
                        energy: 0.00,
                        energyCounter: 0.00,
                        
                        onSomeEvent: function(){}
                    }
 
                    settings = $.extend({}, defaults, options);
 
                    
                    // 保存我们新创建的settings
                    $this.data('funData', settings);
                } else {
                    //如果我们获取了settings，则将它和options进行合并（这不是必须的，你可以选择不这样做）
                    settings = $.extend({}, settings, options);
 
                    
                    // 如果你想每次都保存options，可以添加下面代码：
                    
                    // $this.data('funData', settings);
                }
 
                
                // 执行代码
 
            });
        },
        
        
        destroy: function(options) {
            
        	// 在每个元素中执行代码
            return $(this).each(function() {
                var $this = $(this);
 
                
                // 执行代码
 
                
                // 删除元素对应的数据
                $this.removeData('funData');
            });
        },
        
        
        val: function(options) {
            
    		// 这里的代码通过.eq(0)来获取选择器中的第一个元素的，我们或获取它的HTML内容作为我们的返回值
            var someValue = this.eq(0).html();
 
            
            // 返回值
            return someValue;
        }
    };
 
    $.fn.funData = function() {
        var method = arguments[0];
 
        if(methods[method]) {
            method = methods[method];
            arguments = Array.prototype.slice.call(arguments, 1);
        } else if( typeof(method) == 'object' || !method ) {
            method = methods.init;
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.funData' );
            return this;
        }
 
        return method.apply(this, arguments);
 
    }
 
})(jQuery);
