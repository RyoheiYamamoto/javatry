package javatry.enjoy.new2017.hakiba.colorbox;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

import javatry.colorbox.ColorBox;
import javatry.colorbox.space.BoxSpace;
import javatry.colorbox.unit.ColorBoxTestCase;

/**
 * 数値関連のテスト。<br>
 * 何々は？と言われたら、それに該当するものをログに出力すること。
 * @author baki
 */
public class BakiNumberTest extends ColorBoxTestCase {

    // ===================================================================================
    //                                                                             Convert
    //                                                                             =======
    /**
     * カラーボックスに入ってる日付の月を全て足したら？
     */
    public void test_sumMonth() {
        List<ColorBox> colorBoxList = getColorBoxList();
        List<LocalDate> localDateList = new ArrayList<LocalDate>();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                Object contents = boxSpace.getContents();
                if (contents instanceof LocalDate) {
                    LocalDate localDateContents = (LocalDate) contents;
                    localDateList.add(localDateContents);
                } else if (contents instanceof LocalDateTime) {
                    LocalDate localDateContents = ((LocalDateTime) contents).toLocalDate();
                    localDateList.add(localDateContents);
                }
            }
        }
        if (!localDateList.isEmpty()) {
            int monthSum = 0;
            log("以下の日付について、月を全て足します。");
            for (LocalDate localDate : localDateList) {
                monthSum += localDate.getMonthValue();
                log(localDate);
            }
            log("合計 : " + monthSum);
        } else {
            log("カラーボックスの中に、日付を持つオブジェクトが含まれていません。");
        }
    }

    /**
     * カラーボックの中に入っている、0~100までの数値の数は？
     * @throws Exception
     */
    public void test_countZeroToHundred() throws Exception {
        List<ColorBox> colorBoxList = getColorBoxList();
        List<Object> numList = new ArrayList<Object>();
        // 現実的な数値範囲は、64ビット倍精度浮動小数点数で十分と考え、判定はdouble型で行う。
        double startNum = 0;
        double endNum = 100;

        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                Object contents = boxSpace.getContents();
                if (contents instanceof List<?>) {
                    List<?> listContents = (List<?>)contents;
                    for (Object object : listContents) {
                        addNumberInRange(numList, startNum, endNum, object);
                    }
                } else {
                    addNumberInRange(numList, startNum, endNum, contents);
                }
            }
        }
        if (!numList.isEmpty()) {
            log("カラーボックスに含まれている" + (int) startNum + "〜" + (int) endNum + "の数値は、以下の " + numList.size() + " 個です。");
            for (Object num : numList) {
                log(num);
            }
        } else {
            log("カラーボックスに" + (int) startNum + "〜" + (int) endNum + "の数値は含まれていません。");
        }
    }

    private void addNumberInRange(List<Object> numList, double startNum, double endNum, Object object) {
        Double doubleValue = castDoubleNum(object);
        if (doubleValue != null && startNum <= doubleValue && doubleValue <= endNum) {
            numList.add(object);
        }
    }

    // Number型のサブクラスであれば、double型に変換した値を返す。それ以外はnullを返す。
    private Double castDoubleNum(Object object) {
        Double doubleValue = null;
        if (object instanceof Number) {
            Number numberObj = (Number) object;
            doubleValue = numberObj.doubleValue();
        }
        return doubleValue;
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * 青色のカラーボックスに入ってる Map の中の商品で一番高いものは？
     */
    public void test_findMax() {
        String boxColor = "blue";
        List<ColorBox> colorBoxList = getColorBoxList();
        List<Map<?, ?>> mapList = new ArrayList<Map<?, ?>>();
        for (ColorBox colorBox : colorBoxList) {
            // done hakiba equals()と "==" by jflute (2016/05/25)
            // http://dbflute.seasar.org/ja/manual/topic/programming/java/beginners.html#equalsequal
            if (colorBox.getColor().getColorName().equals(boxColor)) {
                List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
                for (BoxSpace boxSpace : boxSpaceList) {
                    Object contents = boxSpace.getContents();
                    if (contents instanceof Map) {
                        Map<?, ?> mapContents = (Map<?, ?>) contents;
                        mapList.add(mapContents);
                    }
                }
            }
        }
        //取得したMapは、keyが「商品名（String）」、valueが「価格（Integer）」となっていると仮定して、最大値を求める
        if (!mapList.isEmpty()) {
            for (Map<?, ?> map : mapList) {
                int maxPrice = 0;
                String maxPriceProduct = null;
                List<String> productNameList = new ArrayList<String>();
                for (Map.Entry<?, ?> mapEntry : map.entrySet()) {
                    if (mapEntry.getKey() instanceof String && mapEntry.getValue() instanceof Integer) {
                        String compareProduct = (String) mapEntry.getKey();
                        int comparePrice = (Integer) mapEntry.getValue();
                        productNameList.add(compareProduct);
                        if (maxPriceProduct == null || maxPrice < comparePrice) {
                            maxPriceProduct = compareProduct;
                            maxPrice = comparePrice;
                        }
                    }
                }
                log("以下、計 " + productNameList.size() + " 点");
                for (String productName : productNameList) {
                    log(productName);
                }
                log("一番高い商品 -> " + maxPriceProduct + " : " + maxPrice + "");
            }
        }
    }

    /**
     * カラーボックスの中で、一番幅が大きいものでInteger型を持っているカラーボックスの色は？
     */
    public void test_findColorBigWidthHasInteger() {
        List<ColorBox> orderdColorBoxList = getColorBoxList();
        List<ColorBox> maxWidthColorBoxList = new ArrayList<ColorBox>();
        int maxWidth = 0;

        orderByBubbleSort(orderdColorBoxList);

        for (ColorBox colorBox : orderdColorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            if (colorBox.getSize().getWidth() >= maxWidth) {
                for (BoxSpace boxSpace : boxSpaceList) {
                    Object contents = boxSpace.getContents();
                    if (contents instanceof Integer) {
                        maxWidth = colorBox.getSize().getWidth();
                        maxWidthColorBoxList.add(colorBox);
                        break;
                    }
                }
            }
        }

        if (!maxWidthColorBoxList.isEmpty()) {
            log("一番幅が大きいものでInteger型を持っているカラーボックスの色は、以下のとおりです。");
            for (ColorBox colorBox : maxWidthColorBoxList) {
                log("幅 : " + colorBox.getSize().getWidth() + ", 色 : " + colorBox.getColor().getColorName());
            }
        } else {
            log("Integer型を持っているカラーボックスがありませんでした。");
        }
    }

    //バブルソートを用いて、colorBoxListを幅が大きい順にソートする。
    private void orderByBubbleSort(List<ColorBox> orderdColorBoxList) {
        for (int i = 0; i < orderdColorBoxList.size(); i++) {
            for (int j = orderdColorBoxList.size() - 1; j > i; j--) {
                if (orderdColorBoxList.get(j).getSize().getWidth() > orderdColorBoxList.get(j - 1).getSize().getWidth()) {
                    ColorBox temp = orderdColorBoxList.get(j);
                    orderdColorBoxList.set(j, orderdColorBoxList.get(j - 1));
                    orderdColorBoxList.set(j - 1, temp);
                }
            }
        }
    }

    /**
     * カラーボックスの中に入ってる BigDecimal を全て足し合わせると？
     */
    public void test_sumBigDecimal() {
        List<ColorBox> colorBoxList = getColorBoxList();
        List<BigDecimal> bigDecimalList = new ArrayList<BigDecimal>();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                Object contents = boxSpace.getContents();
                addBigDecimal(bigDecimalList, contents);
            }
        }
        BigDecimal bigDecimalSum = new BigDecimal("0");
        for (BigDecimal bigDecimal : bigDecimalList) {
            bigDecimalSum = bigDecimalSum.add(bigDecimal);
        }
        log(bigDecimalList);
        log("合計 : " + bigDecimalSum);
    }

    private void addBigDecimal(List<BigDecimal> bigDecimalList, Object contents) {
        if (contents instanceof List<?>) {
            List<?> tempList = (List<?>) contents;
            for (Object object : tempList) {
                addBigDecimal(bigDecimalList, object);
            }
        }
        if (contents instanceof BigDecimal) {
            BigDecimal bigDecimalContents = (BigDecimal) contents;
            bigDecimalList.add(bigDecimalContents);
        }
    }
}
