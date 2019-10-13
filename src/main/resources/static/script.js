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
