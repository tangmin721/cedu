package com.yanxiu.ce.test;

import java.util.Date;

/**
 * Created by srt on 2016.09.19.
 */
public class TestIdea {
    private String name;
    /**
     * hello
     */
    private String realName;
    private Integer age;
    private Date birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;

    }

    public static void main(String args[]){
        TestIdea idea = new TestIdea();
        idea.setAge(11);
        if (idea.getAge()==11){
            System.out.println(idea.age);
        }
    }

}








