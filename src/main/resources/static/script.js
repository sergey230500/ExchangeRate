$(document).ready(function(){
  let cityData;
    $.ajax({
    url: '/api/cities',
    method: 'GET',
    dataType: 'json'
    }).done(function(data){
    	cityData = data;
      init();
    });
    function init(){
    	let container = document.querySelector(".cities");
       // container.innerHTML = "";
    	let select = document.createElement("select");
        for(let city of cityData){
            let option = document.createElement("option");
            option.innerText = city;
            option.value = city;
            select.appendChild(option);
        }
        container.appendChild(select);
      }
 
});

var map;
function initMap() {
	  var mark0 = {lat: 52.100623 , lng:23.681037  };
	  var mark1 = {lat:  52.1069 , lng: 23.6427}
	  map = new google.maps.Map(
	      document.getElementById('map'), {zoom: 12, center: mark0});
	  var marker0 = new google.maps.Marker({position: mark0, map: map});
	  var marker1 = new google.maps.Marker({position: mark1, map: map});
	}

function fitToCoordinates(coordinates){
	let x = coordinates.map(element => element.x);
	let y = coordinates.map(element => element.y);
	let maxX = Math.max(...x);
	let maxY = Math.max(...y);
	let minX = Math.min(...x);
	let minY = Math.min(...y);
	 var minPoint = new google.maps.LatLng(minY, minX);
	 var maxPoint = new google.maps.LatLng(maxY, maxX);
	 var bounds = new google.maps.LatLngBounds();
	 bounds.extend(minPoint);
	 bounds.extend(maxPoint);
	 map.fitBounds(bounds);
}