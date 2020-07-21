package com.rumenz;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;

public class Config {


    @Bean
    public Rumenz rumenz1(){
        Rumenz r=new Rumenz();
        r.setId(456);
        r.setName("入门小站");
        return r;
    }

    @Bean
    public Rumenz rumenz(){
        Rumenz r=new Rumenz();
        r.setId(123);
        r.setName("入门小站");
        return r;
    }


}
