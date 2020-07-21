package com.rumenz;

import org.springframework.beans.factory.BeanNameAware;

public class Holder implements BeanNameAware {

    private Rumenz rumenz;

    public Holder(Rumenz rumenz) {
        this.rumenz = rumenz;
    }

    public Rumenz getRumenz() {
        return rumenz;
    }

    public void setRumenz(Rumenz rumenz) {
        this.rumenz = rumenz;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("BeanName: "+name);
    }
}
