package com.robinzhou.Lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by N550 on 2015/7/23.
 */
public class FormulaTest {
    public static void main(String[] args) {
        Formula formula = a -> Math.sqrt(a * 100);
        System.out.println(formula.calculate(100));
        System.out.println(formula.sqrt(25));
        System.out.println(Formula.get());
        System.out.println(((Formula) a -> Math.sqrt(a * 100)).calculate(100));



        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");

        Collections.sort(names, (a, b) -> a.compareTo(b));

        for (String name : names) {
            System.out.println(name);
        }
    }
}
