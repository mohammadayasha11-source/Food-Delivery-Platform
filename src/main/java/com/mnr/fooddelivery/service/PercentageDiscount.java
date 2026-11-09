package com.mnr.fooddelivery.service;
public class PercentageDiscount implements DiscountStrategy {
    private final double percent;
    public PercentageDiscount(double percent){this.percent=percent;}
    public double apply(double subtotal){return subtotal*(1-percent/100.0);}
    public String description(){return percent+"% discount";}
}
