<html ng-app>
  <head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no">
    <title>Loading the frist map</title>

    <link rel="stylesheet" href="http://js.arcgis.com/3.10/js/dojo/dijit/themes/claro/claro.css"/>
    <link rel="stylesheet" href="http://js.arcgis.com/3.10/js/esri/css/esri.css" />
    <link rel="stylesheet" href="/GISweb/stylesheets/style.css" />

    <script src="http://js.arcgis.com/3.10/"></script>
    <script src="/GISweb/javascripts/jquery-2.1.1.min.js"></script>
    <script src="http://cdn.bootcss.com/angular.js/1.3.0-beta.8/angular.min.js"></script>
    <script src="/GISweb/controllers/webgis.js"></script>
    <!--<script src="/GISweb/controllers/dataImport.js"></script> -->
    <!-- Web Framework CSS - Bootstrap (getbootstrap.com) and Bootstrap-map-js (github.com/esri/bootstrap-map-js) -->
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="//esri.github.io/bootstrap-map-js/src/css/bootstrapmap.css">
  </head>

  <body> <!-- class="claro" ng-controller='webGis' ng-init='loadingData()' --> 
    <div id="webtable" class="panel panel-primary panel-fixed" style="top:150;width:700;height:300;right:'85%';display: none">
      <div class="panel-heading">
        <h3 class="panel-title">TABLE</h3>
      </div>
      <div class="panel-body collapse in">
		<!-- dgrid will be created in this div -->
		<div id="grid"></div>
      </div>
    </div>
  	 <div id="layer" class="panel panel-primary panel-fixed" style="display: none">
      <div class="panel-heading">
        <h3 class="panel-title">LAYERS</h3>
      </div>
      <div class="panel-body collapse in">
        <div id="legend"></div>
      </div>
    </div>
  	  <div class="panel panel-primary panel-fixed">
      <div class="panel-heading">
        <h3 class="panel-title">FUNCTION</h3>
        <button id="chevron" class="btn btn-primary btn-xs btn-absolute">          
          <span class="glyphicon glyphicon-chevron-up"></span>
        </button>
      </div>
      <div class="panel-body collapse in">
        <div class="btn-toolbar">
          <div class="btn-group">
            <button id="btnLayers" class="btn btn-default">LAYERS</button>
            <button id="btnImport" class="btn btn-default">IMPORT</button>
            <button id="btnHybrid" class="btn btn-default">Hybrid</button>
            <button id="btnTopo" class="btn btn-default">Topo</button>
          </div>  
        </div>
        <div class="btn-toolbar">
          <div class="btn-group">
            <button id="btnGray" class="btn btn-default">Gray</button>
            <button id="btnNatGeo" class="btn btn-default">National Geographic</button>
          </div>  
        </div>
      </div>
    </div>
    <div id="map" class="roundedCorners shadow" style="width:100%; height:100%;" data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'"></div>
  </body>
</html>