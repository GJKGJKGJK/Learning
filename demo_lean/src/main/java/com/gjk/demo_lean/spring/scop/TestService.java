package com.gjk.demo_lean.spring.scop;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

/**
 * TestService
 *
 * @author: GJK
 * @date: 2022/4/22 14:15
 * @description:
 */

@Service
public abstract class TestService {



      //方式一
//    @Lookup
//    public TestDao getTestDao() {
//        return null;
//    }

    //方式二
    @Lookup
    public abstract TestDao getTestDao();

    public void sysout(){
        System.out.println("TestService： " + this.hashCode());
        System.out.println("TestDao： " + getTestDao().hashCode());
    }



}
