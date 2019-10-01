package com.example.demo.model.local;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.model.remote.Filial;
import com.example.demo.service.QueryBelarusBankService;

public class CityInfo {
  public String city;
  public int count;

  public CityInfo() {
	Map<String, Integer> cities = new HashMap<>();
	for(String cityName: QueryBelarusBankService.data.city) {}
	if (!cities.containsValue(cityName)) {
		cities.put(cityName, 0);
	}
	else cities.put(cityName, cities.get(cityName) + 1);
}

  public CityInfo(String name, int count) {
    this.city = name;
    this.count = 0;
  }
}
