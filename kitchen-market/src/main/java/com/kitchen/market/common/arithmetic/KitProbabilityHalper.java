package com.kitchen.market.common.arithmetic;

import java.util.*;

/**
 * 概率算法
 * 适用于：抽奖结果的计算；
 *
 * @date 2016-12-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class KitProbabilityHalper {

    /**
     * 平均概率生成指定范围内的随机数，包含最大值和最小值
     * @param min 范围——最小值
     * @param max 范围——最大值
     * @return
     */
    public static int generateIntValueByRange(int min, int max) {
        Random random = new Random();
        int randomValue = random.nextInt(max - min + 1) + min;
        return randomValue;
    }

    /**
     * 指定概率生成随机的抵价券金额，支持整数类型的多概率随机数生成
     * @param odds 概率公式
     *               格式："40:1,60:2" 表示40%的概率生成整数1，60%的概率生成整数2
     *             （若不指定，则使用平均概率生成）
     * @return 概率随机数
     */
    public static int generateIntValueByOdds(String odds) {
        int oddsRandomValue = -1;

        String [] arrOdds = odds.split(",");
        //boolean success = false;
        if (arrOdds.length > 0) {
            List<String> listOdds = Arrays.asList(arrOdds);

            Map<Integer,Integer> mapOdds = new TreeMap<Integer, Integer>();
            boolean formatRight = true;
            for (String strOdds : listOdds) {
                String[] oddsKeyValue = strOdds.split(":");
                if (oddsKeyValue.length == 2) {
                    String strKey = oddsKeyValue[1];
                    String strValue = oddsKeyValue[0];
                    int key = 0;
                    int value = 0;
                    try {
                        key = Integer.parseInt(strKey);
                        value = Integer.parseInt(strValue);
                    } catch (NumberFormatException e) {
                        formatRight = false;
                        break;
                    }
                    mapOdds.put(key, value);
                } else {
                    formatRight = false;
                    break;
                }
            }
            //排序
            List<Map.Entry<Integer,Integer>> sortOdds = new ArrayList<Map.Entry<Integer,Integer>>(
                    mapOdds.entrySet()
            );
            Collections.sort(sortOdds,new Comparator<Map.Entry<Integer, Integer>>() {
                // 降序排序
                public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });

            if (formatRight) {//格式正确
                int point = new Random().nextInt(100);
                int startOdds = 0;
                int endOdds = 0;

                for(Map.Entry<Integer,Integer> mapping : sortOdds){
                    endOdds = startOdds + mapping.getValue();
                    if (point >= startOdds && point < endOdds) {
                        oddsRandomValue = mapping.getKey();
                        break;
                    }
                    startOdds = endOdds;
                }
            }
        }

        return oddsRandomValue;
    }
}
