$(document).ready(function(){
	let select = document.querySelector("#city");
    $.ajax({
    url: '/api/cities',
    method: 'GET',
    dataType: 'json'
    }).done(function(data){
      init(data);
    });
    
   $("select").change(function(){
    	$.ajax ({
    		url: '/api/filials',
    	    method: 'GET',
    	    dataType: 'json',
    	    data: {
    	    	city: select.value
    	    }
    	}).done(function(data){
    		console.log(data);
    	})
    })

    function init(cityData){
    	let container = document.querySelector(".cities");
       // container.innerHTML = "";
        for(let city of cityData){
            let option = document.createElement("option");
            option.innerText = city;
            option.value = city;
            select.appendChild(option);
        }
      }
    
});



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