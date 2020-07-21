使用场景

> 当你创建多个具有相同类型的 bean 时，并且想要用一个属性只为它们其中的一个进行装配，在这种情况下，你可以使用 @Qualifier 注释和 @Autowired 注释通过指定哪一个真正的 bean 将会被装配来消除混乱。下面显示的是使用 @Qualifier 注释的一个示例。

**实体类Rumenz/SuperRumenz**

```java
package com.rumenz;

public class Rumenz{

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Rumenz{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}


package com.rumenz;

public class SuperRumenz extends Rumenz {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SuperRumenz{" +
                "type='" + type + '\'' +
                "} " + super.toString();
    }
}

```

**自定义注解@Grp进行逻辑分组用**

```java
package com.rumenz;


import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier
public @interface Grp {
}

```

**配置Beans.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd">
    <bean id="rumenz" class="com.rumenz.Rumenz">
        <property name="id" value="123"/>
        <property name="name" value="入门小站"/>
    </bean>
    <bean id="superRumenz" class="com.rumenz.SuperRumenz" parent="rumenz" primary="true">
        <property name="id" value="456"/>
        <property name="name" value="入门小站-子类"/>
        <property name="type" value="1"/>
    </bean>
</beans>
```

> 当Spring容器中有多个同一种类型的Bean时,标注为 primary="true" 的Bean将首先被注入使用

```java
@Autowired
private  Rumenz rumenz; //rumenz---->SuperRumenz
```

**调用**

```java
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
```

**输出**

```
入门小站-子类
入门小站
[Rumenz{id=2, name='rumenz2'}, Rumenz{id=3, name='rumenz3'}, Rumenz{id=4, name='rumenz4'}, Rumenz{id=5, name='rumenz5'}]
[Rumenz{id=4, name='rumenz4'}, Rumenz{id=5, name='rumenz5'}]
```

源码:https://github.com/mifunc/Spring-Qualifier
