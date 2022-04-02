package com.gjk.demo_lean.java.design_patterns.zerenlian;


import lombok.Getter;
import lombok.Setter;

/**
 * Handler
 *
 * @author: GJK
 * @date: 2022/4/2 17:40
 * @description:
 */
@Setter
public abstract class Handler {

    protected Handler next;

    protected int number = 1;

    public abstract void handleRequest(int request);

}