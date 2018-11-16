/*!
 * blogedit.html 页面脚本.
 * 
 * @since: 1.0.0 2017-03-26
 * @author Way Lau <https://waylau.com>
 */
"use strict";
//# sourceURL=blogedit.js

// DOM 加载完再执行
$(function() {

    var map = null;
    var drawnItems;
    var mapDOMid = '';
    var existPoint = [];
    var existPoint_bak = [];
    var Background = '';
    var Background_bak = '';
    var areaArr = {};
    var areaArr_bak = {};
    var pointAttr = [];
    var savePoint = [];
    var weight = 2;
    var textFontSize_default = 14;
    var colorList = ["#ffbf26","#ff6438","#2cfff5","#c556ff","#ff4c5f","#0bed07","#4d596c","#8AC007","#ccb324","#FFAD5C","#72ff71","#ff78af","#93adff","#ffb3cf"];
    function init(domId,imgUrl,callback) {
        if(typeof(map) != 'undefined' && map != null){map.remove();}
        var _tmpIMG = new Image();
        _tmpIMG.src = imgUrl;
        _tmpIMG.onload = function() {
            var _tmpThis = this;
            var _tmpIMG_width = _tmpThis.width;
            var _tmpIMG_height = _tmpThis.height;
            map = new L.Map(domId, {
                crs: L.CRS.Simple,
                zoom:1,
                maxZoom : 5,
                minZoom : -3.5,
                center:[210,300]
            });

            var southWest = map.unproject([ 0, 0 ], map.getMinZoom() - 5);
            var northEast = map.unproject([ 210,300 ], map.getMaxZoom() - 5);
            var bounds = new L.LatLngBounds(southWest, northEast);
            // add the image overlay, so that it covers the entire map
            var image = L.imageOverlay(imgUrl, bounds).addTo(map);

            // tell leaflet that the map is exactly as big as the image
            map.setMaxBounds(bounds);
            map.fitBounds(bounds);
            drawnItems = new L.FeatureGroup();
            showPoint(existPoint)
            map.addLayer(drawnItems);

        };
    }

    function showPoint(n){
            switch(n.type){
                case 'polygon':
                    var polygon = new L.Polygon(n.latlngs,{
                        weight : weight,
                        color : getColorByRandom()
                    })
                    polygon.addTo(drawnItems);

                    pointAttr.push({
                        'leaflet_id': polygon._leaflet_id,
                        'type': n.type,
                        'area': n.area,
                        'area_id': n.area_id
                    });
                    break;
                case 'rectangle':
                    var rectangle = new L.Rectangle(n.latlngs,{
                        weight : weight,
                        color : getColorByRandom()
                    })
                    rectangle.addTo(drawnItems);
                    pointAttr.push({
                        'leaflet_id': rectangle._leaflet_id,
                        'type': n.type,
                        'area': n.area,
                        'area_id': n.area_id
                    });
                    break;
            }
    }

    function getRandomColor(){//随机颜色
        return '#'+Math.floor(Math.random()*16777215).toString(16);
    }
    function getColorByRandom(){//随机取出列表内的颜色
        var colorIndex = Math.floor(Math.random()*colorList.length);
        var color = colorList[colorIndex];
//        colorList.splice(colorIndex,1);//除去抽中项
        return color;
    }
    $(document).ready(function(){
        mapDOMid = 'image-map';
        existPoint = JSON.parse(coordinate);
        Background = pageImagePath;
        //数据备份
        existPoint_bak = existPoint;
        Background_bak = Background;
        areaArr_bak = jQuery.extend(true, {}, areaArr);
        init(mapDOMid,Background,function(){


        });

    });

});