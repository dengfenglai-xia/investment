package com.ruikun.sys.investment.entity;

import java.math.BigDecimal;

public class Order {

    private static ThreadLocal<Order> orderThreadLocal = new ThreadLocal<>();

    private Order() {}

    public static Order getInstance() {
        Order oredr = orderThreadLocal.get();
        if (oredr == null) {
            oredr = new Order();
            orderThreadLocal.set(oredr);
        }
        return oredr;
    }

    public static void setInstance(Order oredr) {
        orderThreadLocal.set(oredr);
    }

    public static void clear(){
        orderThreadLocal.remove();
    }

    private BigDecimal amount;
    private String serialNo;
    private String tradingTime;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getTradingTime() {
        return tradingTime;
    }

    public void setTradingTime(String tradingTime) {
        this.tradingTime = tradingTime;
    }



}
