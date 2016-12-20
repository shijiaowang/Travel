package com.yunspeak.travel.utils;

import com.yunspeak.travel.bean.BasecPriceBean;

import java.util.List;

/**
 * Created by wangyang on 2016/12/13 0013.
 */

public class MoneyUtils {
    public static double getMoney(List<BasecPriceBean> moneyDetailBeen, int day) {
        if (moneyDetailBeen == null || moneyDetailBeen.size() == 0) return 0;
        double money;
        double totalMoney = 0d;
        for (BasecPriceBean dataBean : moneyDetailBeen) {
            String value = dataBean.getValue();
            money = Double.parseDouble(value);
            if (dataBean.getType().equals("1")) {
                totalMoney += money * day;
            } else if (dataBean.getType().equals("2")) {
                totalMoney += money;
            }
        }
        return totalMoney;
    }
}
