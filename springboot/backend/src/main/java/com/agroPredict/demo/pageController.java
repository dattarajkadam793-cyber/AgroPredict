package com.agroPredict.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class pageController {

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/Soilform")
    public String soiilForm(){
        return "Soilform";
    }

    @GetMapping("/normalPlanting")
    public String normalPg(){
        return "normalPlanting";
    }

    @GetMapping("/stage1pg1prePlanting")
    public String stage1p1PrePlanting(){return "stage1pg1(prePlanting)";}

    @GetMapping("/stage1pg2basalWater")
    public String stage2pg2(){return "stage1pg2basalWater";}

    @GetMapping("/nurseryPlanting")
    public String nurseryPg(){
        return "nurseryPlanting";
    }

    @GetMapping("/stage2")
    public String stage2pg(){return "stage2";}

    @GetMapping("/stage1pg1")
    public String stage1pg1(){return "stage1pg1(prePlanting)";}

    @GetMapping("/stage3")
    public String stage3(){return "stage3";}

    @GetMapping("/maturity")
    public String stage4(){return "maturity";}

    @GetMapping("/stage5")
    public String stage5(){return "stage5";}

    @GetMapping("/harvestPredict")
    public String harvestResult(){return "harvest_result";}

    @GetMapping("/harvestForm")
    public String harvestForm(){return "Harvest_date_prediction_form";}

//    @GetMapping("/RegisterLogin")
//    public String registerLogin(){return "";}


}
