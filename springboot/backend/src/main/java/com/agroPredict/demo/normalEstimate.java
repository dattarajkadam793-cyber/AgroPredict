package com.agroPredict.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class normalEstimate {



        @PostMapping("/normalPlanting")
        public String calculateEstimate(@RequestParam(required = false) Double acre
                                        ) {//@RequestParam String email
//            if(acre!=null) {
                System.out.println(">>> CONTROLLER HIT, acre = " + acre);

                if (acre == null) {
                    return "Acre value missing";
                }
                int totalBuds = (int) (acre * 7250);

                return "You need approximately " + totalBuds + " sugarcane buds(eyes)." +
                        "\n\nif 3 bud on each sugarcane segment then " + totalBuds / 3 + " segments" +
                        "\n\nprice: " + totalBuds * 2.20;
           // }
//            if(email!=null)
//                System.out.println("email received" + email);
//                return "received email successfully";


        }


//        @PostMapping("/normalPlanting/reminder")
//        public String emailNotification(@RequestParam String email){
//            if (email == null) {
//                return "email value missing";
//            }
//            System.out.println("email received " + email);
//            return "received email successfully";
//        }

    @PostMapping("/stage2/NoOfCanes")
    public String ApproxNoOfCanes(@RequestParam Double acre,
                                  @RequestParam Double rowDistance,
                                  @RequestParam Double settDistance,
                                  @RequestParam Double NoOfBuds){
        if(acre == null || rowDistance==null || settDistance==null){
            return "Missing value";
        }
        System.out.println("acre"+acre);
        System.out.println("rowDistance"+rowDistance);
        System.out.println("settDistance"+settDistance);


        double perBudResult = rowDistance * settDistance;

        double result = acre * perBudResult * NoOfBuds;

        System.out.println("result="+perBudResult);

        return "You will have Approximately "+perBudResult+" number of tillers per bud(eye)" +
                "\n You will have Approximately "+result+" number of sugarcane's";
    }

    @Autowired
    private ReminderScheduler reminderScheduler;

    @PostMapping("/normalPlanting/reminder")
    public String emailNotification(@RequestParam String email) {
        if (email == null || email.isEmpty()) {
            return "email value missing";
        }

        reminderScheduler.scheduleEveryOneMinute(email);
        return "Reminder emails scheduled successfully";
    }



    }



