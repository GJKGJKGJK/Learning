package com.gjk.demo_lean.java.design_patterns.zerenlian;

/**
 * ConcreteHandler1
 *
 * @author: GJK
 * @date: 2022/4/2 17:52
 * @description:
 */
public class ConcreteHandler1 extends Handler{
    @Override
    public void handleRequest(int request) {
        if(request == number){
            System.out.println("具体处理者1负责处理该请求！");
            number++;
        }
        if(next != null){
            next.handleRequest(request);
        }else{
            System.out.println("请求处理完毕！");
        }

    }

}
