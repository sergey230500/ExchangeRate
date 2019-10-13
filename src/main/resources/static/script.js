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
        container.innerHTML = "";
    	let ul = document.createElement("ul");
        for(let city of cityData){
            let li = document.createElement("li");
            let label = document.createElement("label");
            label.innerText = city;
            li.appendChild(label);
            ul.appendChild(li);           
        }
        container.appendChild(ul);
      }
});