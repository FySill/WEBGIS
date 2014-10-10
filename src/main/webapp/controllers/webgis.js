//function webGis($scope) {
//	$scope.loadingData = function () {
		var layerNum = 1;
		var map;
	    require(["dojo/on", "dojo/query", "dojo/parser", "esri/geometry/Polyline", "esri/layers/FeatureLayer", "esri/tasks/query",
	             "dojo/ready", "dijit/layout/BorderContainer", "esri/layers/GraphicsLayer",
	             "dijit/layout/ContentPane", "dojo/dom", "esri/map", "esri/graphic",
	             "esri/geometry/Extent", "esri/urlUtils", "esri/arcgis/utils", 
	             "esri/dijit/Legend", "esri/dijit/Scalebar","dojo/_base/array",
	             "dgrid/OnDemandGrid", "dojo/store/Memory", "dojo/request", "dojo/domReady!"], 
        function( on, query, parser, Polyline, FeatureLayer, Query, ready, BorderContainer, GraphicsLayer,
        		ContentPane, dom, Map, Graphic, Extent, urlUtils, arcgisUtils, Legend, Scalebar,arrayUtils,
        		OnDemandGrid, Memory, request) {
//	    	console.log("webgis");
	    	var featureLayer, pageInfo, grid; 
	        var sortAttr = [{
	          attribute: "OBJECTID",
	          descending: true
	        }];
	        function fetchRecords(objectIds) {
	            if (objectIds.length > 0) {
	            	var query = new Query();
	                query.objectIds = objectIds;
	                query.outFields = ["*"];
	                featureLayer.queryFeatures(query, function (featureSet) {
	              		updateGrid(featureSet);
	                });
	            } else {
	              grid.showMessage("No matching records");
	              grid.setStore(null);
	            }
	        };
	        function updateGrid(featureSet) {
	        	var columns = {};
	        	for (var i = 0; i < featureSet.fields.length; i++)
	                columns[featureSet.fields[i].name] = featureSet.fields[i].name;
    	        grid = new OnDemandGrid({
  		          store: Memory({
  		            idProperty: "OBJECTID"
  		          }),
  		          columns:columns,
  		          sort: sortAttr
  		        }, "grid");
	            var data = arrayUtils.map(featureSet.features, function (entry, i) {
	              var data = {};
	              for (var attribute in entry.attributes)
	            	  data[attribute] = entry.attributes[attribute];
	              return data;
	            });
	            grid.store.setData(data);
	            grid.refresh();
	        };
	        function updatePageInformation(objectIds, page) {
	            pageInfo = {
	              objectIds: objectIds,
	              totalRecords: objectIds.length,
	              totalPages: Math.ceil(objectIds.length / 15),
	              currentPage: page || 0,
	              recordsPerPage: 15
	            };
	        };
	        function array2JSON(extendPoint){
	        	var items = [];
	        	var itemsN = [];
	        	for(var n = 0;n<extendPoint.length;n++){
	        		var x = extendPoint[n][0];
	        		var y = extendPoint[n][1];
	        		items.push("{x:"+x+",y:"+y+"}")
	        		itemsN.push(x+","+y)
	        	}
	        	var data = "data:{["+items+"]}";
	        	var num = "["+itemsN+"]";
	        	return num;
	        }
	        
	    	// Wire UI Events
	    	var layerIsHide = true;
	    	var frist = true;
	        on(dom.byId("btnLayers"),"click", function() { 
	        	if (layerIsHide) {
	        		$("#layer").animate({top:50,opacity:'show',width:150,height:400,right:'85%'},500);
	        		layerIsHide = false;
	        	} else {
	        		$("#layer").animate({top:50,opacity: 'hide',width:0,height:0,right:0},500);
	        		layerIsHide = true;
	        	}
	        	var isHide = true;
	        	var text = "";
	        	if (frist) {
	        		var length = $('#legend_operational0')[0].childNodes.length;
	        		var layerNumArr = [];
	        		for (var n = 1; n < length; n++){
	        		layerNumArr[$('#legend_operational0')[0].childNodes[n].innerText] = n;
	        		on($('#legend_operational0')[0].childNodes[n],"click", function(e) { 	
	        			if ($('#grid')) $('#grid').empty();
	        			
		            	if (isHide) {
		            		$("#webtable").animate({top:150,opacity:'show',width:700,height:300,left:'17%'},500);
		            		text = e.currentTarget.innerText;
		            		
		            		isHide = false;
		            	}else if(text != e.currentTarget.innerText){
		            		//
		            		text = e.currentTarget.innerText;
		            	}
		            	else {
		            		$("#webtable").animate({top:150,opacity: 'hide',width:0,height:0,left:0},500);
		            		text = "";
		            		isHide = true;
		            		return;
		            	}
		            	
		            	//dy add featureLayer
	        			layerNum = layerNumArr[e.currentTarget.innerText];
	        	        featureLayer = new FeatureLayer("http://192.168.2.127:6080/arcgis/rest/services/webgis/gisServiceFeng/MapServer/"+layerNum, {
	        		          outFields:["*"]
	        		        });
	        	        featureLayer.on("load", function () {
	        		          // create the query to fetch object IDs for earthquakes that have a magnitude greater than 6.0
	        		          // that occurred after January 1st 2007
	        		          var query = new Query();
	        		          query.where = "OBJECTID > 0";
//	        		          query.timeExtent = new TimeExtent(new Date("01/01/2007 UTC"));
	        		          featureLayer.queryIds(query, function (objectIds) {
//	        		        	  console.log("objectids:" + objectIds);
	        		            fetchRecords(objectIds);
	        		          });
	        	        });
		            });
	        		frist = false;
	        		}
	        	}
	        	
//	          map.setBasemap("streets");
	        });
	        on(dom.byId("btnImport"),"click", function() { 
//				var length = $('#legend_operational0')[0].childNodes.length;
//				var layerNumArr = [];
//				for (var n = 1; n < length; n++){
//				layerNumArr[$('#legend_operational0')[0].childNodes[n].innerText] = n;
//				}
//				layerNum = layerNumArr["������"];
		        featureLayer = new FeatureLayer("http://192.168.2.127:6080/arcgis/rest/services/webgis/gisServiceFeng/MapServer/12", {
			          outFields:["*"]
			    });
    	        featureLayer.on("load", function () {
  		          var query = new Query();
  		          query.where = "OBJECTID > 0";
  		          featureLayer.queryIds(query, function (objectIds) {
  		            if (objectIds.length > 0) {
  		            	var query = new Query();
  		                query.objectIds = objectIds;
  		                query.outFields = ["*"];
  		                featureLayer.queryFeatures(query, function (featureSet) {
  		                	extendPoint = [];
  		                	extendPoint = featureSet.features[0].geometry.rings[0];
//  		                	for(var point in featureSet.features[0].geometry.paths[0]){
//  		                		extendPoint.push(featureSet.features[0].geometry.paths[0][point]);
//  		                	}
//  		                	var rings = [];
////  		                	rings.push(extendPoint)
  		    		        var myPolygon = {"geometry":{"rings":[extendPoint],
						                                "spatialReference":{latestWkid: 2384, wkid: 2384}
						     							},
						                    "symbol":{"color":[0,0,0,64],
						                   	 	   "outline":{"color":[0,0,0,255],
						                   	 		   		  "width":1,
						                   	 		   		  "type":"esriSLS","style":"esriSLSSolid"},
						                   	 	   "type":"esriSFS","style":"esriSFSSolid"}
						     				};
  		    		      var gl = new GraphicsLayer({id: "myPolygon"});
	  		  	          var gra = new Graphic(myPolygon);
	  		  	          map.addLayer(gl);
	  		  	          gl.add(gra);
	  		  	          var dataJSON = dojo.toJson(array2JSON(extendPoint));
	  		  	          var dataStr = extendPoint;
		  		  	      request.post("/WEBGIS/views/BasePoints", {
		  		  	        data:{
		  		  	        	data:dataJSON,
	  		  	        		color: "blue",
		  		  	        	},
//		  		  	        { 
//		  		  	        	color: "blue",
//	  		  	        		answer: 42,
//		  		  	        },
		  		  	        headers: {
		  		  	        	"X-Something": "A value",
		  		  	            "Access-Control-Allow-Origin":"*"
		  		  	        }
		  		  	    }).then(function(text){
		  		  	        console.log("The server returned: ", text);
		  		  	    });
//  		                	var polylineJson = {
//  		                		    "paths":extendPoint,
//  		                		    "spatialReference":{latestWkid: 2384, wkid: 2384}
//  		                		  }; 
//  		                	var gl = new GraphicsLayer({id: "myPolygon"});
//  		                		  var polyline = new Polyline(polylineJson);
//  		                		map.addLayer(gl);
//  		                		gl.add(polyline);
  		                });
  		            }
  		       });  
  	        });
        });
//	        on(dom.byId("btnHybrid"),"click", function() { 
//	          map.setBasemap("hybrid");
//	        });
//	        on(dom.byId("btnTopo"),"click", function() { 
//	          map.setBasemap("topo");
//	        });
//	        on(dom.byId("btnGray"),"click", function() { 
//	          map.setBasemap("gray");
//	        });
//	        on(dom.byId("btnNatGeo"),"click", function() { 
//	          map.setBasemap("national-geographic");
//	        });
	    	// Toggle panel
	        on(dom.byId("chevron"), "click", function(e){
	          if (query(".glyphicon.glyphicon-chevron-up")[0]) {
	            query(".glyphicon").replaceClass("glyphicon-chevron-down","glyphicon-chevron-up");
	            query(".panel-body.collapse").removeClass("in");
	          } else {
	            query(".glyphicon").replaceClass("glyphicon-chevron-up","glyphicon-chevron-down");
	            query(".panel-body.collapse").addClass("in");
	          }
	        });
	        ready(function(){
	        parser.parse();
	        var webmap = {};
	        webmap.item = {
	          "title":"frist map"
	        };
	        webmap.itemData = {
	          "operationalLayers": [{
	            "url": "http://192.168.2.127:6080/arcgis/rest/services/webgis/gisServiceFeng/MapServer",
	            "visibility": true,
	            "opacity": 0.75,
	            "title": "     ",
//	            "itemId": "204d94c9b1374de9a21574c9efa31164"
	          }],
	          "baseMap": {
	            "baseMapLayers": [{
	              "opacity": 1,
	              "visibility": true,
	              "url": "http://192.168.2.127:6080/arcgis/rest/services/webgis/gisServiceFeng/MapServer"
	              },{
	              "isReference": true,
	              "opacity": 1,
	              "visibility": true,
	              "url": "http://192.168.2.127:6080/arcgis/rest/services/webgis/gisServiceFeng/MapServer"
	              }],
	            "title": "World_Terrain_Base"
	          },
	          "version": "1.1"
	        };
	        arcgisUtils.createMap(webmap,"map").then(function(response){
	          map = response.map;
	          
//	          var myPolygon = {"geometry":{"rings":[[[60303,44034],[64461,44082],[64470,44959],[65209,45075],[60303,44034]]],
//						                  "spatialReference":{latestWkid: 2384, wkid: 2384}
//											},
//						      "symbol":{"color":[2,0,0,64],
//						     	 	   "outline":{"color":[0,0,0,255],
//						     	 		   		  "width":1,
//						     	 		   		  "type":"esriSLS","style":"esriSLSSolid"},
//						     	 	   "type":"esriSFS","style":"esriSFSSolid"}
//								};
//	          var gl = new GraphicsLayer({id: "myPolygon"});
//	          var gra = new Graphic(myPolygon);
//	          map.addLayer(gl);
//	          gl.add(gra);
	          
		        var legendLayers = arcgisUtils.getLegendLayers(response); 
		          var legendDijit = new Legend({
		            map: map,
		            layerInfos: legendLayers
		          },"legend");
		          legendDijit.startup();
	        });
        });
      });
//	}
//}