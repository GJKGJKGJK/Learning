package com.gjk.demo_lean.jvm.classloader;

import java.util.HashMap;
import java.util.Map;

/**
 * action
 *
 * @author: GJK
 * @date: 2022/3/23 18:04
 * @description:
 */
public class Action {


    public static void main1(String[] args) {
        ClassLoader systemLoader = Action.class.getClassLoader();
        System.out.println("系统类加载器：：：：" + systemLoader);

        ClassLoader extLoader = systemLoader.getParent();
        System.out.println("扩展类加载器：：：：" + extLoader);

        ClassLoader bootLoader = extLoader.getParent();
        System.out.println("扩展类加载器：：：：" + bootLoader);

    }


    public static void main(String[] args) {
//        System.out.println("我是Action,我是被" + Action.class.getClassLoader() + "加载的");
//        A a = new A();
//        a.sayHi();
        Map<String, String> map = new HashMap<>();
        map.put("123","777");
        map.put("234","888");
        System.out.println(map);
    }


}
