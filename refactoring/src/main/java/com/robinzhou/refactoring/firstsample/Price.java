package com.robinzhou.refactoring.firstsample;

/**
 * Created by N550 on 2015/7/6.
 */
public abstract class Price {
    abstract int getPriceCode();

    abstract double getCharge(int daysRented);

    int getFrequentRenterPoints(int daysRented) {
        return 1;
    }
}
