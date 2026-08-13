package com.agroPredict.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
public class modelConnections {

    //DBConnectionLogic object is injected here if @Autowire remove then no injection just dbobj variabvle declaration
    @Autowired
    private DBConnectionLogic dbobj;

//----------------------------------------------------------------------------------------------------------
// with constructor we can inject but we need 1 constructor
    //if there are 2 then also can work but need to write @Autowire above the constructor where we are gonna use the obj
//
//    public modelConnections(DBConnectionLogic dbobj){
//        this.dbobj = dbobj;
//    }
//
//----------------------------------------------------------------------------------------------------------






    @PostMapping("/predict")
    public String soildata(@RequestParam int Nitrogen,
                           @RequestParam int Phosphorus,
                           @RequestParam int potassium,
                           @RequestParam float pH,
                           @RequestParam int Rainfall,
                           @RequestParam int Temperature,
                           @RequestParam String soilColor,
                           Model model

    ) {
        //test if values come here
        System.out.println("Nitrogen: " + Nitrogen);
        System.out.println("Phosphorus: " + Phosphorus);
        System.out.println("Potassium: " + potassium);
        System.out.println("pH: " + pH);
        System.out.println("Rainfall: " + Rainfall);
        System.out.println("Temperature: " + Temperature);
        System.out.println("Soil Color: " + soilColor);
        //test if values come here


        //Nitrogen,Phosphorus,potassium,pH,Rainfall,Temperature,soilColor

        dbobj.soildataFunction(Nitrogen,Phosphorus,potassium,pH,Rainfall,Temperature,soilColor);













        Map<String,Object> soilData = new HashMap<>();
        soilData.put("Nitrogen",Nitrogen);
        soilData.put("Phosphorus",Phosphorus);
        soilData.put("Potassium",potassium);
        soilData.put("soilColor",soilColor);
        soilData.put("pH",pH);
        soilData.put("Temperature",Temperature);
        soilData.put("Rainfall",Rainfall);

        RestTemplate restTemplate = new RestTemplate();

        String pythonApiURL="http://localhost:5000/predict";


        ResponseEntity<Map> response= restTemplate.postForEntity(pythonApiURL,soilData,Map.class);

        String prediction = response.getBody().get("prediction").toString();
        System.out.println("result = "+prediction);

        model.addAttribute("result", prediction);

        return "CropPredResult"; //html page
    }




    @PostMapping("/harvestPredict")
    public String harvestData(@RequestParam float brix,
                              @RequestParam float pol,
                              @RequestParam float purity,
                              Model model) {

        System.out.println("Brix: " + brix);
        System.out.println("Pol: " + pol);
        System.out.println("Purity: " + purity);

        Map<String, Object> harvestData = new HashMap<>();
        harvestData.put("Brix", brix);     //change due to harvest model
        harvestData.put("Pol", pol);       //change due to harvest model
        harvestData.put("Purity", purity); //change due to harvest model

        RestTemplate restTemplate = new RestTemplate();

        String pythonApiURL = "http://localhost:5000/harvestPredict"; //change due to harvest model

        ResponseEntity<Map> response =
                restTemplate.postForEntity(pythonApiURL, harvestData, Map.class);

        String prediction = response.getBody().get("prediction").toString();

        System.out.println("Harvest Result = " + prediction);

        model.addAttribute("result", prediction); //change due to harvest model

        return "harvest_result"; //change due to harvest model
    }
}
