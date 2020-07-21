package com.rumenz;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;




public class DemoApplication  {

    @Autowired
    private  Rumenz rumenz;

    @Autowired
    @Qualifier("rumenz")
    private Rumenz rumenz1;

    @Autowired
    @Qualifier
    private List<Rumenz> list;

    @Bean
    @Qualifier
    public Rumenz rumenz2(){
        Rumenz r = new Rumenz();
        r.setName("rumenz2");
        r.setId(2);
        return r;
    }
    @Bean
    @Qualifier
    public Rumenz rumenz3(){
        Rumenz r = new Rumenz();
        r.setName("rumenz3");
        r.setId(3);
        return r;
    }

    @Bean
    @Grp
    public Rumenz rumenz4(){
        Rumenz r = new Rumenz();
        r.setName("rumenz4");
        r.setId(4);
        return r;
    }
    @Bean
    @Grp
    public Rumenz rumenz5(){
        Rumenz r = new Rumenz();
        r.setName("rumenz5");
        r.setId(5);
        return r;
    }

    @Autowired
    @Grp
    private List<Rumenz> listGrp;


   
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext();
        XmlBeanDefinitionReader xr=new XmlBeanDefinitionReader(ac);
        xr.loadBeanDefinitions("Beans.xml");
        
        ac.register(DemoApplication.class);
        ac.refresh();
        DemoApplication demoApplication = ac.getBean(DemoApplication.class);

        System.out.println(demoApplication.rumenz.getName());
        System.out.println(demoApplication.rumenz1.getName());

        System.out.println(Arrays.toString(demoApplication.list.toArray()));
        System.out.println(Arrays.toString(demoApplication.listGrp.toArray()));
        ac.close();
    }

   


}