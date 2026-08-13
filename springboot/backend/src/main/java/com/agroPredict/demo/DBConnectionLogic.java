package com.agroPredict.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


//@Controller note: this is database thing we are not controlling pages urls ,db connection is service
@Service
public class DBConnectionLogic {

    //repository obj injection by constructor
    private final SoilDataRepository repo;
    public DBConnectionLogic(SoilDataRepository repo) {
        this.repo = repo;
    }
    //db


    //New Function

    public void soildataFunction(
            int Nitrogen,
            int Phosphorus,
            int potassium,               //parameters
            float pH,
            int Rainfall,
            int Temperature,
            String soilColor

    )
    {//Function Start

        //test if values come here
        System.out.println("Nitrogen: " + Nitrogen);
        System.out.println("Phosphorus: " + Phosphorus);
        System.out.println("Potassium: " + potassium);
        System.out.println("pH: " + pH);
        System.out.println("Rainfall: " + Rainfall);
        System.out.println("Temperature: " + Temperature);
        System.out.println("Soil Color: " + soilColor);
        //test if values come here


        //settingg the  database soiltables value
        SoilTable soilTable = new SoilTable();
        soilTable.setSoilColor(soilColor);
        soilTable.setNitrogen(Nitrogen);
        soilTable.setPhosphorus(Phosphorus);
        soilTable.setPotassium(potassium);
        soilTable.setPh(pH);
        soilTable.setRainfall(Rainfall);
        soilTable.setTemperature(Temperature);

        repo.save(soilTable);
        //settingg the  database soiltables value





    }


}
