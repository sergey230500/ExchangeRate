package com.example.demo.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.local.CityInfo;
import com.example.demo.model.local.ExchangeRates;
import com.example.demo.model.local.FillialInfo;

@RestController
@RequestMapping(path = "/api", method = RequestMethod.GET)
public class TimeSheetController {
	
	@RequestMapping("/cities")
	public List<CityInfo> getCities() {
		return Collections.singletonList(new CityInfo("г. Брест", 15));
	}
	
	@RequestMapping("/cities")
	public List<FillialInfo> getFillials() {
		return Collections.singletonList(new FillialInfo("Отделение 100\\/212", "пр. Партизанский 8-49"));
	}
	
	@RequestMapping("/cities")
	public List<ExchangeRates> getExchangeRates() {
		return Collections.singletonList(new ExchangeRates("USD", "BYN"));
	}
}
