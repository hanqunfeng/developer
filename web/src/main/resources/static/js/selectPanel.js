
	

(function($) {
		"use strict";
		function getJSLocale(key,params){
			var result = ""; 	// 对应的资源的内容
			var paramsObj = {};	// 参数对象
			if(params) paramsObj = params;
			
			if(typeof(key) != 'undefined'){
				// 根据key取得对应的资源内容，如果没有找到则返回key值
				if($.selectPanelLanguage[key] != undefined){
					result = $.selectPanelLanguage[key];
				}else{
					result = key;
				}
				
				// 替换对应参数为value的值
				var regExp = new RegExp(); //替换资源中参数的正则
				for(var k in paramsObj){
					regExp = eval("/{{:" + k + "}}/g");
					result = result.replace(regExp,paramsObj[k]);
				}
				
				// 如果没有找到对应的资源则返回 "No Value"
				if(/{{:[a-zA-Z]+}}/.test(result)){
					result = result.replace(/{{:[a-zA-Z]+}}/g, "No Value");
				}
			}
			return result;
		}

		
		$.selectPanelLanguage={
				selectedItems:"has selected {{:0}} Item(s)!",
				recurrentSelection:"the item you selected has exsited,please do not choose the same itme!",
				leafOnly:"only leaf-item can be selected!"
				
		};
		$.fn.selectPanel = function(spoptions){
			
			 // Establish our default settings  
	        var settings = $.extend(true,{
	        	title:"my title",
	        	inputTarget:"",
	        	value_label:["id","name"],
	        	datasource: {
					sync: true,
					url:null,
					autoParam:null,
					otherParam:null,
					dataFilter:null,
					name:"name",
					title:"",
					showIcon:true,
					showLine:true,
					showTitle:true
				},
				nodes:null
	        }, spoptions);  
			
			var html =$('<div class="modal fade" id="selectPanel-Modal" tabindex="-1" role="dialog" '+
    		'aria-labelledby="myModalLabel" aria-hidden="true">'+
    		'<div class="modal-dialog" style="width: 700px">'+
    			'<div class="modal-content">'+
    				'<div class="modal-header">'+
    					'<button type="button" class="close" data-dismiss="modal" '+ 
    						'aria-hidden="true">&times;</button>'+
    					'<h4 class="modal-title" >'+settings.title+'</h4>'+
    				'</div>'+
    				'<div class="modal-body" style="padding: 0px;">'+
    					'<table width="100%" border=0>'+
    						'<tr>'+
    							'<td width="45%" class="well"><div '+
    									'style="height: 300px; width: 100%; background-color: white;overflow:auto">'+
    									'<div id="selectPanel-treeDemo" class="ztree"></div>'+
    								'</div></td>'+
    							'<td width="10%" valign=middle style="text-align:center;vertical-align:center;">'+
    								'<button class="btn btn-primary btn-sm btn-block" id="selectPanel-addItem">'+
    									'<i class="icon-double-angle-right icon-2x"></i><br>add'+
    								'</button>'+
    								'<br>'+
    							'<br>'+
    								'<button class="btn btn-primary btn-sm  btn-block" id="selectPanel-removeItem">'+
    									'<i class="icon-double-angle-left icon-2x"></i><br>remove'+
    								'</button>'+
    								'<br>'+
    							'<br>'+
    								'<button class="btn btn-primary btn-sm  btn-block" id="selectPanel-clearItems">'+
    									'<i class="icon-trash icon-2x"></i><br>clear'+
    								'</button>'+

    							'</td>'+
    							'<td width="45%" class="well" stylle="text-align:center;vertical-align:center;">'+
    									'<span>'+getJSLocale("selectedItems",{0:0})+'</span><select MULTIPLE style="height: 280px; width: 100%; background-color: white;" id="selectPanel-alternativeItems"></select></td>'+
    						'</tr>'+
    					'</table>'+
    				'</div>'+
    				'<div class="modal-footer">'+
    					'<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>'+
    					'<button type="button" class="btn btn-primary" id="selectPanel-save">Save changes</button>'+
    				'</div>'+
    			'</div>'+
    		'</div>'+
    	'</div>');
			
			
	  
	        return this.each( function() {  
	            $(this).click(function(){
	            	var _this = $(this);
	            	var ztree_setting = {
	        				async: {
	        					enable: settings.datasource.sync,
	        					url:settings.datasource.url,
	        					autoParam:settings.datasource.autoParam,
	        					otherParam:settings.datasource.otherParam,
	        					dataFilter:settings.datasource.dataFilter
	        				},data:{
	        					key:{
	        					name:settings.datasource.name,
	        					title:settings.datasource.title
	        					}
	        				},view:{
	        					showIcon:true,
	        					showLine:true,
	        					showTitle:true
	        				}
	        			};
	        		html.appendTo('body');
	        		var modal = $('#selectPanel-Modal');
	        		modal.modal().on('hidden.bs.modal', function (e) {
	        			$("#selectPanel-Modal").remove();
	        		});
	        		
	        		
	        		var treeObj = $.fn.zTree.init($("#selectPanel-treeDemo"), ztree_setting,settings.nodes);
	        		
	        		var alternativeItems = $("#selectPanel-alternativeItems");
	        		
	        		
	        		$("#selectPanel-addItem").on('click',function(){
	        			var nodes = treeObj.getSelectedNodes();
	        			for(var i=0;i<nodes.length;i++){
	        				var vl = nodes[i][settings.value_label[0]]+"--"+nodes[i][settings.value_label[1]];
	        				if(nodes[i].isParent){
	        					alert(getJSLocale('leafOnly'));
	        				}else if(alternativeItems.find("option:contains('"+vl+"')").length>=1){
	        					alert(getJSLocale('recurrentSelection'));
	        				}else{
	        					alternativeItems.append("<option>"+vl+"</option>");
	        				}
	        			}		
	        			alternativeItems.siblings("span").text(getJSLocale("selectedItems",{0:alternativeItems.find("option").length}));
	        		});
	        		$("#selectPanel-removeItem").on('click',function(){
	        			alternativeItems.find("option:selected").remove();
	        			alternativeItems.siblings("span").text(getJSLocale("selectedItems",{0:alternativeItems.find("option").length}));
	        		});
	        		$("#selectPanel-clearItems").on('click',function(){
	        			alternativeItems.find("option").remove();
	        			alternativeItems.siblings("span").text(getJSLocale("selectedItems",{0:0}));
	        		});
	        		
	        		var inputTarget;
	        		if(settings.inputTarget==""||settings.inputTarget==null){
	        			inputTarget=_this;
	        		}else{
	        			inputTarget= $("#"+settings.inputTarget);
	        		}
	        		$("#selectPanel-save").on('click',function(){
	        			
	        			var options = alternativeItems.find('option');
	        			var str = [];
	        			for(var i=0;i<options.length;i++){
	        				str.push($(options[i]).text());
	        			}
	        			inputTarget.val(str);
	        			modal.modal('hide');
	        		});
	            });
	        }); 
		};
	})(jQuery);




