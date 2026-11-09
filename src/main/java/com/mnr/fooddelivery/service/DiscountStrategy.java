package com.mnr.fooddelivery.service;
public interface DiscountStrategy { double apply(double subtotal); String description(); }
