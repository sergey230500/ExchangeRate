$(document).ready(function(){
  let routeData;
    $.ajax({
    url: '/timeTable.json',
    method: 'GET',
    dataType: 'json'
    }).done(function(data){
      routeData = data;
      init();
    });
 
  const MINUTES_IN_DAY = 60*24;
  /*0) Предобработка данных
    1) Найти для конкретного времени подходящие рейсы (для каждого маршрута 0-2 рейса)
    2) Отсортировать по времени ожидания ближайшего
    3a) очистить таблицу
    3б) Сформировать новую таблицу html
  */
  const fields = ["number","type","path","closest","next"];
  function init (){
    let timeInput = document.querySelector("#userTime");
    let currentTime = Date.now() - (new Date().getTimezoneOffset()*60000);
    currentTime = currentTime % (1000*60*60*24);
    currentTime = currentTime - currentTime%60000;
    timeInput.valueAsNumber = currentTime;
    let button = document.querySelector("#show");
    button.addEventListener("click", () => {
      displayRuns(timeInput.valueAsNumber);
    });
    for (let route of routeData){
      let first = route.times[0];
      for (let i = route.times.length-1;  route.times[i] < first; i--){
        route.times[i] += MINUTES_IN_DAY;
      }
      route.deadTime = (first - route.times[route.times.length-1] + MINUTES_IN_DAY) >> 1;
    }
    displayRuns(currentTime);
  }

  function  findRuns(t){//В минутах
    let runs = [];
    for (let route of routeData){
      if (t < route.deadTime) t += MINUTES_IN_DAY;
      let index = route.times.findIndex(x => x >= t);
      if (index >= 0) {
        let run = {
          number: route.number,
          type: route.type,
          path: route.path,
          closest: route.times[index]-t,
        };
        if (index + 1 < route.times.length) run.next = route.times[index+1]-t;
        runs.push(run);
      }
    }
    runs.sort(function(a, b) {
      return a.closest - b.closest;
    });
    runs.forEach(run => {
      if (run.closest === 0) run.closest = "<1";
    });
    return runs;
  }

  function displayRuns(currentTime){//в милисекундах
    
    let t = currentTime % (1000*60*60*24);
    t = Math.ceil(t/60000);
    let runs = findRuns(t);
    let container = document.querySelector(".routes tbody");
    container.innerHTML = "";
    for(let route of runs){
      let tr = document.createElement("tr");
      for (let field of fields){
        let td = document.createElement("td");
        td.innerText = route[field] || "";
        td.classList.add(field);
        tr.appendChild(td);
      }
      container.appendChild(tr);
    }
  }

});