package javatry.enjoy.new2017.hakiba.colorbox;

import java.util.*;

import javatry.colorbox.ColorBox;
import javatry.colorbox.unit.ColorBoxTestCase;


// done hakiba unusedのimportで警告でてる (警告のままコミットしない習慣を) by jflute (2016/05/14)
/**
 * 文字列のテスト。<br>
 * 何々は？と言われたら、それに該当するものをログに出力すること。
 * @author baki
 */
public class BakiStreamStringTest extends ColorBoxTestCase {


    // ===================================================================================
    //                                                                            length()
    //                                                                            ========
    /**
     * カラーボックスに入ってる文字列の中で、一番長い文字列は？
     */
    public void test_length_findMax() {
        List<ColorBox> colorBoxList = getColorBoxList();
        // .get()を使っているためか、NoSuchElementExceptionが発生する可能性あり -> try-catch
        try {
            String maxString = colorBoxList.stream()
                    .flatMap(colorbox -> colorbox.getSpaceList().stream())
                    .filter(boxSpace -> boxSpace.getContents() instanceof String)
                    .map(boxSpace -> (String)boxSpace.getContents())
                    .max(Comparator.comparing(String::length))
                    .get();
            if (maxString != null) {
                log("一番長い文字列は " + maxString + "です。");
            }
        } catch (NoSuchElementException e) {
            log("カラーボックスに文字列がありません。");
        }
    }
    /**
     * カラーボックスに入ってる文字列の長さの合計は？
     */
    public void test_length_calculateLengthSum() {
        List<ColorBox> colorBoxList = getColorBoxList();
        int lengthSum = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .filter(boxSpace -> boxSpace.getContents() instanceof String)
                .map(boxSpace -> (String)boxSpace.getContents())
                .mapToInt(contents -> contents.length())
                .sum();
        log("カラーボックスに入っている文字列の長さの合計は " + lengthSum + " です。");
    }
    
    // ===================================================================================
    //                                                            startsWith(), endsWith()
    //                                                            ========================
    /**
     * 「かまくら」で始まる文字列をしまっているカラーボックスの色は？
     */
    public void test_startsWith_findFirstWord() {
        List<ColorBox> colorBoxList = getColorBoxList();
        String startStr = "かまくら";
        Map<String, String> firstKamakuraColorMap = new HashMap<String, String>();

        colorBoxList.stream()
        .forEach(colorBox -> colorBox.getSpaceList().stream()
            .filter(boxSpace -> boxSpace.getContents() instanceof String)
            .map(boxSpace -> (String)boxSpace.getContents())
            .filter(strContents -> strContents.startsWith(startStr))
            .forEach(strContents -> firstKamakuraColorMap
                .put(strContents, colorBox.getColor().getColorName()))
        );

        if (!firstKamakuraColorMap.isEmpty()) {
            log("\""+startStr +"\"から始まる文字列は " + firstKamakuraColorMap.size() + " 個です。");
            firstKamakuraColorMap.entrySet().stream()
            .forEach(map -> log("\"" + map.getKey() + "\"がしまってあるカラーボックスの色は " + map.getValue() + " です。"));
        } else {
            log("カラーボックスに\""+startStr +"\"から始まる文字列は含まれていません。");
        }
    }
}