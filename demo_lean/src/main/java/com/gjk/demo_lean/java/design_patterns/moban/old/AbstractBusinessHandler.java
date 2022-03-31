package com.gjk.demo_lean.java.design_patterns.moban.old;

import org.apache.commons.lang3.RandomUtils;

/**
 * AbstractBusinessHandler
 *
 * @author: GJK
 * @date: 2022/3/23 16:42
 * @description:
 */
public abstract class AbstractBusinessHandler {

    /**
     * 模板方法
     */
    public final void execute(){
        getNumber();
        handle();
        judge();
    }

    /**
     * 取号
     */
    private void getNumber(){
        System.out.println("number-00" + RandomUtils.nextInt());
    }

    /**
     * 办理业务
     */
    public abstract void handle();

    /**
     * 评价
     */
    private void judge(){
        System.out.println("give a praised");
    }


//取钱业务的实现类

    public class DrawMoneyHandler extends AbstractBusinessHandler {

        @Override

        public void handle() {

            System.out.println("draw 1000");

        }

    }


//理财业务的实现类

    public class MoneyManageHandler extends AbstractBusinessHandler{

        @Override

        public void handle() {

            System.out.println("money manage");

        }

    }

    /**
     * 取钱
     */
    public static class SaveMoneyHandler extends AbstractBusinessHandler{
        @Override
        public void handle() {
            System.out.println("save 10000");
        }
    }
}
