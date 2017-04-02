package javatry.enjoy.new2017.hakiba.colorbox;

import java.util.*;
import java.util.regex.*;
import java.time.*;

import javatry.colorbox.ColorBox;
import javatry.colorbox.color.BoxColor;
import javatry.colorbox.space.BoxSpace;
import javatry.colorbox.unit.ColorBoxTestCase;


// done hakiba unusedのimportで警告でてる (警告のままコミットしない習慣を) by jflute (2016/05/14)
/**
 * 文字列のテスト。<br>
 * 何々は？と言われたら、それに該当するものをログに出力すること。
 * @author baki
 */
public class BakiStringTest extends ColorBoxTestCase {

    //「 key = value 」 or 「 key = map:{...} 」 を想定した正規表現 by baki (2016/05/14)
    private static final String PARSE_TO_MAP_REGEX = " (\\w+) = (\\w+|map\\:\\{.*\\}) ;?";
    private static final Pattern PARSE_TO_MAP_COMPILE = Pattern.compile(PARSE_TO_MAP_REGEX);

    // ===================================================================================
    //                                                                            length()
    //                                                                            ========
    /**
     * 最初のカラーボックスの色の名前の文字数は？
     */
    public void test_length_basic() {
        List<ColorBox> colorBoxList = getColorBoxList();
        ColorBox colorBox = colorBoxList.get(0);
        BoxColor boxColor = colorBox.getColor();
        String colorName = boxColor.getColorName();
        log(colorName, colorName.length());
    }


    /**
     * カラーボックス内に含まれるオブジェクトのクラスの出力
     * bakiが中身の確認のために勝手に作ったメソッドです。
     */
    public void test_class_output() {
        List<ColorBox> colorBoxList = getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                Object contents = boxSpace.getContents();
                if (contents == null) {
                    log("\"" + contents + "\"");
                } else {
                    log("\"" + contents + "\" のクラスは " + contents.getClass());
                }
            }
        }
        
    }

    /**
     * カラーボックスに入ってる文字列の中で、一番長い文字列は？
     */
    public void test_length_findMax() {
        List<ColorBox> colorBoxList = getColorBoxList();
        String maxString = null;
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                Object contents = boxSpace.getContents();
                if (contents instanceof String) {
                    String strContents = (String)contents;
                    if (maxString == null || maxString.length() < strContents.length()) {
                        maxString = strContents;
                    }
                }
            }
        }
        if (maxString != null) {
            log("一番長い文字列は " + maxString + " です。");
        } else {
            log("カラーボックスの中に文字列は含まれていませんでした。");
        }
    }

    /**
     * カラーボックスに入ってる文字列の中で、一番長いものと短いものの差は何文字？
     */
    public void test_length_findMaxMinDiff() {
        List<ColorBox> colorBoxList = getColorBoxList();
        int maxMinDiff = 0;
        String maxString = null;
        String minString = null;
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                Object contents = boxSpace.getContents();
                if (contents instanceof String) {
                    String strContents = (String)contents;
                    if (minString == null || strContents.length() < minString.length()) {
                        minString = strContents;
                    }
                    if (maxString == null || maxString.length() < strContents.length()) {
                        maxString = strContents;
                    }
                }
            }
        }
        if (maxString != null && minString != null) {
            maxMinDiff = maxString.length() - minString.length();
            log("一番長い文字列は " + maxString + " です。");
            log("一番短い文字列は " + minString + " です。");
            log("一番長いものと短いものの差は " + maxMinDiff + " です。");
        } else {
            log("カラーボックスに2つ以上の文字列が含まれていなかったので、差を求められませんでした。");
        }
    }

    /**
     * カラーボックスに入ってる値 (文字列以外のものはtoString()) の中で、二番目に長い文字列は？ <br>
     * ソートして二番目を取得する、ってやり方で。
     */
    public void test_length_findSecondMax_bySort() {
        List<ColorBox> colorBoxList = getColorBoxList();
        List<String> contentsStrList = new ArrayList<String>();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                String contentsStr = boxSpace.toString();
                contentsStrList.add(contentsStr);
            }
        }
        for (int i = 0; i < contentsStrList.size(); i++) {
            for (int j = contentsStrList.size() - 1; j > i; j--) {
                if (contentsStrList.get(j).length() > contentsStrList.get(j - 1).length()) {
                    String tempStr = contentsStrList.get(j);
                    contentsStrList.set(j, contentsStrList.get(j - 1));
                    contentsStrList.set(j - 1, tempStr);
                }
            }
        }
        if (contentsStrList.size() >= 2) {
            log("<ソート後>");
            // done habaki contentsであることの方が大切なので、String string よりかは String contents の方がいいかな by jflute (2016/05/14)
            // string を選択して command + 1 => enter して rename してみよう
            for (String contents : contentsStrList) {
                log(contents.length() + " 文字: " + contents);
            }
            log("二番目に長い文字列は \"" + contentsStrList.get(1) + "\" で " + contentsStrList.get(1).length() + " 文字です。");
        } else {
            log("カラーボックスに2つ以上の文字列が含まれていません。");
        }
    }

    /**
     * カラーボックスに入ってる値 (文字列以外のものはtoString()) の中で、二番目に長い文字列は？ <br>
     * ただし、ソートして二番目を取得する、ってやり方は利用しないこと。
     */
    public void test_length_findSecondMax_nonSorted() {
        String firstLongStr = null;
        String secondLongStr = null;
        List<ColorBox> colorBoxList = getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                // done hakiba Stringはstrって略すことが多いので、ちょこっとだけ短くしてみよう、もうちょいすっきりするかもね by jflute (2016/05/14)
                // って書いてから気付いたけど、つーか secondLongStr は Str じゃん！
                String contentsStr = boxSpace.toString();
                if (firstLongStr == null) {
                    firstLongStr = contentsStr;
                } else if (firstLongStr.length() < contentsStr.length()) {
                    secondLongStr = firstLongStr;
                    firstLongStr = contentsStr;
                } else if (secondLongStr == null
                        || secondLongStr.length() < contentsStr.length() && contentsStr.length() < firstLongStr.length()) {
                    secondLongStr = contentsStr;
                }
            }
        }
        if (secondLongStr != null) {
            log("二番目に長い文字列は \"" + secondLongStr + "\" で " + secondLongStr.length() + " 文字です。");
        } else {
            log("カラーボックスに2つ以上の文字列が含まれていません。");
        }
    }

    /**
     * カラーボックスに入ってる文字列の長さの合計は？
     */
    public void test_length_calculateLengthSum2() {
        List<ColorBox> colorBoxList = getColorBoxList();
        int strLengthSum = 0;
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                Object contents = boxSpace.getContents();
                if (contents instanceof String) {
                    String stringContents = (String)contents;
                    log(stringContents.length() + " 文字 : " + stringContents);
                    strLengthSum += boxSpace.toString().length();
                }
            }
        }
        log("カラーボックスに入っている文字列の長さの合計は " + strLengthSum + " です。");
    }
    
    // ===================================================================================
    //                                                            startsWith(), endsWith()
    //                                                            ========================
    /**
     * 「かまくら」で始まる文字列をしまっているカラーボックスの色は？
     */
    public void test_startsWith_findFirstWord() {
        List<ColorBox> colorBoxList = getColorBoxList();
        Map<String, String> firstKamakuraColorMap = new HashMap<String, String>();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                Object contents = boxSpace.getContents();
                if (contents instanceof String) {
                    String stringContents = (String)contents;
                    if (stringContents.startsWith("かまくら")) {
                        firstKamakuraColorMap.put(stringContents, colorBox.getColor().getColorName());
                    }
                }
            }
        }
        log("「かまくら」から始まる文字列は " + firstKamakuraColorMap.size() + " 個です。");
        for (Map.Entry<String, String> map : firstKamakuraColorMap.entrySet()) {
            log("\"" + map.getKey() + "\" がしまってあるカラーボックスの色は " + map.getValue() + " です。");
        }
    }

    /**
     * 「いぬ」で終わる文字列をしまっているカラーボックスの色は？
     */
    public void test_endsWith_findLastWord() {
        List<ColorBox> colorBoxList = getColorBoxList();
        Map<String, String> lastInuColorMap = new HashMap<String, String>();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                Object contents = boxSpace.getContents();
                if (contents instanceof String) {
                    String stringContents = (String)contents;
                    if (stringContents.endsWith("いぬ")) {
                        lastInuColorMap.put(stringContents, colorBox.getColor().getColorName());
                    }
                }
            }
        }
        log("「いぬ」で終わる文字列は " + lastInuColorMap.size() + " 個です。");
        for (Map.Entry<String, String> map : lastInuColorMap.entrySet()) {
            log("\"" + map.getKey() + "\" がしまってあるカラーボックスの色は " + map.getValue() + " です。");
        }
    }

    // ===================================================================================
    //                                                            indexOf(), lastIndexOf()
    //                                                            ========================
    /**
     * あなたのカラーボックスに入ってる「いぬ」で終わる文字列で、「いぬ」は何文字目から始まる？
     */
    public void test_indexOf_findIndex() {
        List<ColorBox> colorBoxList = getColorBoxList();
        List<String> lastInuStrList = new ArrayList<String>();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                Object contents = boxSpace.getContents();
                if (contents instanceof String) {
                    // done hakiba downcastしてね by jflute (2016/05/14)
                    String stringContents = (String)contents;
                    // done hakiba ifの後ろに空白がない by jflute (2016/05/14)
                    if(stringContents.endsWith("いぬ")) {
                        lastInuStrList.add(stringContents);
                    }
                }
            }
        }
        log("「いぬ」で終わる文字列は " + lastInuStrList.size() + " 個です。" );
        for (String inuStr : lastInuStrList) {
            log("\"" + inuStr + "\" の「いぬ」は " + (inuStr.indexOf("いぬ") + 1) + " 文字目から始まります。");
        }
    }

    /**
     * あなたのカラーボックスに入ってる「ず」を二つ以上含む文字列で、最後の「ず」は何文字目から始まる？
     */
    public void test_lastIndexOf_findIndex() {
        // done hakiba さすがに "ず" を変数にして再利用しよう by jflute (2016/05/14)
        String searchWord = "ず";
        List<ColorBox> colorBoxList = getColorBoxList();
        Map<String, Integer> lastZuIndexMap = new HashMap<String, Integer>();
        // done hakiba インターフェース型で受ける習慣を (つまり、Map だね) by jflute (2016/05/14)
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                Object contents = boxSpace.getContents();
                if (contents instanceof String) {
                    String stringContents = (String)contents;
                    // done hakiba 一つもないときは、indexOf()とlastIndexOf()も両方とも -1 になるから、後ろの && の条件はなくても大丈夫かなと by jflute (2016/05/14)
                    if (stringContents.indexOf(searchWord) != stringContents.lastIndexOf(searchWord)) {
                        lastZuIndexMap.put(stringContents, (stringContents.lastIndexOf(searchWord) + 1));
                    }
                }
            }
        }
        // done hakiba Mapも if (!lastZuIndexMap.isEmpty()) って、isEmpty() が使えるよ by jflute (2016/05/14)
        if (!lastZuIndexMap.isEmpty()) {
            for (Map.Entry<String, Integer> map : lastZuIndexMap.entrySet()) {
                log("\"" + map.getKey() + "\" の最後の「ず」は " + map.getValue() + " 文字目です。");
            }
        } else {
            log("カラーボックスに「ず」を二つ以上含む文字列が含まれていませんでした。");
        }
    }

    // ===================================================================================
    //                                                                         substring()
    //                                                                         ===========
    /**
     * カラーボックスに入ってる「いぬ」で終わる文字列の最初の一文字は？
     */
    public void test_substring_findFirstChar() {
        List<ColorBox> colorBoxList = getColorBoxList();
        ArrayList<String> lastInuStrList = new ArrayList<String>();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                Object contents = boxSpace.getContents();
                if (contents instanceof String) {
                    String stringContents = (String)contents;
                    if (stringContents.endsWith("いぬ")) {
                        lastInuStrList.add(stringContents);
                    }
                }
            }
        }
        if (!lastInuStrList.isEmpty()) {
            for (String string : lastInuStrList) {
                log("\"" + string + "\" の最初の一文字は \"" + string.substring(0, 1) + "\"" + " です。");
            }
        } else {
            log("カラーボックスに「いぬ」で終わる文字列が含まれていません。");
        }
    }
    
    /**
     * カラーボックスに入ってる「かまくら」で始まる文字列の最後の一文字は？
     */
    public void test_substring_findLastChar() {
        List<ColorBox> colorBoxList = getColorBoxList();
        List<String> firstKamkuraStrList = new ArrayList<String>();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                Object contents = boxSpace.getContents();
                if (contents instanceof String) {
                    String stringContents = (String)contents;
                    if (stringContents.startsWith("かまくら")) {
                        firstKamkuraStrList.add(stringContents);
                    }
                }
            }
        }
        if (!firstKamkuraStrList.isEmpty()) {
            for (String string : firstKamkuraStrList) {
                log("\"" + string + "\" の最後の一文字は \"" + string.substring((string.length() - 1), string.length()) + "\"" + " です。");
            }
        } else {
            log("カラーボックスに「かまくら」で始まる文字列が含まれていませんでした。");
        }

    }

    // ===================================================================================
    //                                                                           replace()
    //                                                                           =========
    /**
     * カラーボックスに入ってる「ー」を含んだ文字列から「ー」を全て除去したら何文字？
     */
    public void test_replace_removeBo() {
        String searchWord = "ー";
        List<ColorBox> colorBoxList = getColorBoxList();
        List<String> boStrList = new ArrayList<String>();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                Object contents = boxSpace.getContents();
                if (contents instanceof String) {
                    String stringContents = (String)contents;
                    // done hakiba contains()を使ってみよう by jflute (2016/05/14)
                    if (stringContents.contains(searchWord)) {
                        boStrList.add(stringContents);
                    }
                }
            }
        }
        if (!boStrList.isEmpty()) {
            log("「" + searchWord + "」を含んだ文字列から「" + searchWord + "」を全て除去したあとの文字数は、以下のとおりです。");
            for (String string : boStrList) {
                log("\"" + string + "\" から「" + searchWord + "」を全て除去すると \"" + string.replace(searchWord, "") + "\" となり、 " + string.replace(searchWord, "").length() + " 文字です。");
            }
        } else {
            log("カラーボックスに「" + searchWord + "」を含んだ文字列が含まれていませんでした。");
        }
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * カラーボックスの中で、色の名前が一番長いものは？
     */
    public void test_findMaxColorSize() throws Exception {
        List<ColorBox> colorBoxList = getColorBoxList();
        List<String> maxColorNameList = new ArrayList<String>(); //orange, yellowなど同じ長さの一番長い色が存在した場合への対応として、StringのListで宣言 by baki (2016/05/14)
        for (ColorBox colorBox : colorBoxList) {
            // done hakiba getColorName()が何度も登場してごちゃごちゃするので、変数として助けだしてあげよう by jflute (2016/05/15)
            // colorBox.getColor().getColorName()を選択して command + 1 => enter で変数の抽出やってみよう
            String colorName = colorBox.getColor().getColorName();
            if (maxColorNameList.size() == 0 || maxColorNameList.get(0).length() == colorName.length()) {
                maxColorNameList.add(colorName);
            } else if (maxColorNameList.get(0).length() < colorName.length()) {
                maxColorNameList.clear();
                maxColorNameList.add(colorName);
            }
        }
        if (!maxColorNameList.isEmpty()) {
            log("カラーボックスの中で、色の名前が一番長いものは、以下のとおりです。");
            for (String string : maxColorNameList) {
                log(string);
            }
        } else {
            log("カラーボックスの色の名前を取得していません.");
        }
    }

    /**
     * カラーボックスの中で、2012/06/04 を示す日付が持っている秒は？
     */
    public void test_findDBFluteBirthdateSecond() throws Exception {
        String searchDateStr = "2012/06/04";
        String[] dateArray = searchDateStr.split("/");
        LocalDate searchDate = LocalDate.of(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]), Integer.parseInt(dateArray[2]));

        List<ColorBox> colorBoxList = getColorBoxList();
        List<LocalDateTime> localDateTimeList = new ArrayList<LocalDateTime>();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxpaceList) {
                Object contents = boxSpace.getContents();
                if (contents instanceof LocalDateTime) {
                    LocalDateTime localDateTimeContents = (LocalDateTime)contents;
                    localDateTimeList.add(localDateTimeContents);
                    if (localDateTimeContents.toLocalDate().equals(searchDate)) {
                    }
                }
            }
        }
        if (!localDateTimeList.isEmpty()) {
            for (LocalDateTime localDateTime : localDateTimeList) {
                log(localDateTime + " が持っている秒は " + localDateTime.getSecond() + " 秒です。");
            }
        } else {
            log("カラーボックスに " + searchDateStr + " の日付を示すオブジェクトは含まれていません。");
        }
    }

    /**
     * カラーボックスの中に入っている Map を "map:{ key = value ; key = value ; ... }" という形式で表示。
     */
    public void test_showMap() throws Exception {
        String logMessage = "map:{";
        List<ColorBox> colorBoxList = getColorBoxList();
        List<Map<?, ?>> mapList = new ArrayList<Map<?, ?>>();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object contents = boxSpace.getContents();
                if (contents instanceof Map<?, ?>) {
                    Map<?, ?> mapContents = (Map<?, ?>)contents;
                    mapList.add(mapContents);
                }
            }
        }
        if (!mapList.isEmpty()) {
            for (Map<?, ?> map : mapList) {
                for (Map.Entry<?, ?> mapContents : map.entrySet()) {
                    logMessage += " " + mapContents.getKey() + " = " + mapContents.getValue() + " ;";
                }
                logMessage = logMessage.substring(0, (logMessage.length() - 1)) + "}";
                log(logMessage);
            }
        } else {
            log("カラーボックスにMapが含まれていません。");
        }
    }

    /**
     * "map:{ key1 = value1 ; key2 = value2 ; key3 = value3 }" という文字列をMapに変換してtoString()すると？
     * <pre>
     * 変換後のMapの中身は、以下のようになっていること
     *  o key1というキーに対してvalue1という値
     *  o key2というキーに対してvalue2という値
     *  o key3というキーに対してvalue3という値
     * </pre>
     */
    public void test_parseMap() throws Exception {
        String originStr = "map:{ key1 = value1 ; key2 = value2 ; key3 = value3 }";
        Map<String, String> newMap = new LinkedHashMap<String, String>();
        String regex = "\\{? *(\\w+) *= *(\\w+) *[;\\}]?";
        Matcher matcher = Pattern.compile(regex).matcher(originStr);
        while (matcher.find()) {
            newMap.put(matcher.group(1), matcher.group(2));
        }
        log(newMap.toString());
    }
    
    /**
     * "map:{ key1 = value1 ; key2 = map:{ nkey21 = nvalue21 ; nkey22 = nvalue22 } ; key3 = value3 }" <br />
     * という文字列をMapに変換してtoString()すると？ <br />
     * <br />
     * "map:{ key1 = value1 ; key2 = value2 ; key3 = map:{ nkey31 = nvalue31 ; nkey32 = nvalue32 } }" <br />
     * でも、同じプログラムでMapに変換できるようにするべし。
     */
    public void test_parseMap_deep() throws Exception {
        String[] originStrArray= {
                "map:{ key1 = value1 ; key2 = map:{ nkey21 = nvalue21 ; nkey22 = nvalue22 } ; key3 = value3 }",
                "map:{ key1 = value1 ; key2 = value2 ; key3 = map:{ nkey31 = nvalue31 ; nkey32 = nvalue32 } }",
//                "map:{ key1 = value1 ; key2 = value2 ; key3 = map:{ nkey31 = nvalue31 ; nkey32 = map:{ nkey321 = nvalue321 ; nkey322 = nvalue322 } } }"
        };
        for (String originStr : originStrArray) {
            Map<String, Object> parsedMap = parseToMap(originStr);
            log("\"" + originStr + "\" をMapに変換してtoString()すると");
            log("\"" + parsedMap.toString() + "\" になります。");
        }
    }
    
    // done hakiba このメソッドは private でいい by jflute (2016/05/15)
    /**
     *  文字列をLinkedHashMapにparseするメソッド
     *  "map:{ key = value ; key = value ; ... }" という形式以外の文字列が入ること
     *  を想定したつくりにはなっていません。
     */ 
    private Map<String, Object> parseToMap(String originStr) throws Exception {
        // done hakiba せっかくなので、Patternを再利用してみよう。compileは重いので何度もやりたくないもの by jflute (2016/05/15)
        // Pattern.compile(regex)を選択してcommand+1 => Extract to constant で private static final で定義しちゃおう
        Map<String, Object> newMap = new LinkedHashMap<String, Object>();
        Matcher matcher = PARSE_TO_MAP_COMPILE.matcher(originStr);
        while (matcher.find()) {
            if (matcher.group(2).startsWith("map:{") && matcher.group(2).endsWith("}")) {
                newMap.put(matcher.group(1), parseToMap(matcher.group(2)));
            } else {
                newMap.put(matcher.group(1), matcher.group(2));
            }
        }
        return newMap;
    }
}