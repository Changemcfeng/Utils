package com.jd.utils;

import java.math.BigDecimal;
import java.util.Random;

/**
 * The preferential amount is apportioned
 * Reserve two decimal places
 *
 * @author mcfeng
 * @date 2021/8/21
 * @version 1.1
 *
 * */
public class Allocation {

    /**按照比例分配，不足的随机分配*/
    public static BigDecimal[] randomAllocation(BigDecimal[] decimals, BigDecimal totalMoney) {
        /**实际总价保留两位小数*/
        totalMoney = totalMoney.setScale(2, BigDecimal.ROUND_DOWN);
        /**按照比例计算价格*/
        BigDecimal[] bigDecimals = estimatesMoney(decimals, totalMoney, calculateTotal(decimals));
        /**多余的金额*/
        BigDecimal remainder = remainder(decimals, totalMoney,calculateTotal(decimals));
        /**余额处理*/
        if (remainder.compareTo(BigDecimal.ZERO) > 0) {
            /**随机分配余额*/
            RandomBalanceMoney(bigDecimals, remainder);
        }
        return bigDecimals;

    }

    /**
     * 计算总价
     */
    private static BigDecimal calculateTotal(BigDecimal[] decimals) {
        BigDecimal total = new BigDecimal("0.00");
        //计算总价
        for (BigDecimal decimal : decimals) {
            total = total.add(decimal);
        }
        return total.setScale(2,BigDecimal.ROUND_DOWN);
    }

    /**
     * 按照比例计算价格
     */
    private static BigDecimal[] estimatesMoney(BigDecimal[] decimals, BigDecimal totalMoney, BigDecimal realityMoney) {
        BigDecimal[] bigs = new BigDecimal[decimals.length];
        totalMoney=totalMoney.subtract(remainder(decimals, totalMoney, realityMoney));
        for (int i = 0; i < decimals.length; i++) {
            /** 先除以 可能会存在误差，乘以 总价/累加的价格 未达到想要的效果*/
            /** 总金额减去无法分配的金额，再按比例分配*/
            bigs[i] = decimals[i].multiply(totalMoney.divide(realityMoney,2,BigDecimal.ROUND_DOWN)).setScale(2,BigDecimal.ROUND_DOWN);
        }
        return bigs;
    }

    /**
     * 未分配的金额
     */
    private static BigDecimal remainder(BigDecimal[] decimals, BigDecimal totalMoney,BigDecimal realityMoney) {

        return totalMoney.remainder(realityMoney).remainder(new BigDecimal("0.01").multiply(new BigDecimal(String.valueOf(decimals.length))));
    }

    /**
     * 随机分配余额
     */
    private static void RandomBalanceMoney(BigDecimal[] decimals, BigDecimal balanceMoney) {
        /**产生随机数左开右闭[0,decimals.length}*/
        int i = new Random().nextInt(decimals.length);
        System.out.println("分配金额"+balanceMoney.toPlainString());
        decimals[i] = decimals[i].add(balanceMoney);
    }

    /**
     * 分配余额给第一个
     */
    private static void RandomFirstMoney(BigDecimal[] decimals, BigDecimal balanceMoney) {
        System.out.println("分配金额"+balanceMoney.toPlainString());
        decimals[0] = decimals[0].add(balanceMoney);
    }

}
