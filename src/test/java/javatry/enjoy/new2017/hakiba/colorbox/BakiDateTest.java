package javatry.enjoy.new2017.hakiba.colorbox;

import javatry.colorbox.ColorBox;
import javatry.colorbox.space.BoxSpace;
import javatry.colorbox.unit.ColorBoxTestCase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 日付関連のテスト。<br>
 * 何々は？と言われたら、それに該当するものをログに出力すること。
 * @author baki
 */
public class BakiDateTest extends ColorBoxTestCase {

    // ===================================================================================
    //                                                                             Convert
    //                                                                             =======
    /**
     * カラーボックスに入っている日付をスラッシュ区切りのフォーマットで表示したら？
     */
    public void test_convert() {
        DateTimeFormatter slashSeparateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        List<LocalDate> localDateList = new ArrayList<LocalDate>();
        List<ColorBox> colorBoxList = getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                Object contents = boxSpace.getContents();
                if (contents instanceof LocalDateTime) {
                    LocalDateTime localDateTimeContents = (LocalDateTime)contents;
                    localDateList.add(localDateTimeContents.toLocalDate());
                } else if (contents instanceof LocalDate){
                    LocalDate localDateContents = (LocalDate)contents;
                    localDateList.add(localDateContents);
                }
            }
        }
        if (!localDateList.isEmpty()) {
            log("<<< カラーボックスに入っている日付をスラッシュ区切りのフォーマットで表示します。 >>>");
            for (LocalDate localDateObj : localDateList) {
                log(localDateObj.format(slashSeparateFormatter));
            }
        }
    }

    // ===================================================================================
    //                                                                              Basic
    //                                                                             =======
    /**
     * カラーボックスに入っている最初の日付は何曜日？
     */
    public void test_weekOfDayOf2017Newcomer() {
        List<ColorBox> colorBoxList = getColorBoxList();
        LocalDate firstLocalDateContents = null;
        outside: for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                Object contents = boxSpace.getContents();
                if (contents instanceof LocalDateTime) {
                    LocalDateTime localDateTimeContents = (LocalDateTime)contents;
                    firstLocalDateContents = localDateTimeContents.toLocalDate();
                    break outside;
                } else if (contents instanceof LocalDate){
                    LocalDate localDateContents = (LocalDate)contents;
                    firstLocalDateContents = localDateContents;
                    break outside;
                }
            }
        }
        if (firstLocalDateContents != null) {
            log("カラーボックスに入っている最初の日付は " + firstLocalDateContents + " です。");
            log("その日は、 " + firstLocalDateContents.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.JAPANESE) + " です。");
        }
    }

    /**
     * 色がyellowのカラーボックスに入っている二つの日付にそれぞれ3日足すと、それぞれ何曜日になる？
     */
    public void test_weekOfDay() {
        List<ColorBox> colorBoxList = getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            if (colorBox.getColor().getColorName() == "yellow") {
                List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
                for (BoxSpace boxSpace : boxSpaceList) {
                    Object contents = boxSpace.getContents();
                    if (contents instanceof LocalDateTime) {
                        LocalDateTime localDateTimeContents = (LocalDateTime)contents;
                        LocalDateTime after3daysDateTime = localDateTimeContents.plusDays(3);
                        log(localDateTimeContents.toLocalDate() + " の3日後は、 " + after3daysDateTime.toLocalDate() + " で、");
                        log(after3daysDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.JAPANESE) + " です。");
                    } else if (contents instanceof LocalDate) {
                        LocalDate localDateContents = (LocalDate)contents;
                        LocalDate after3daysDate = localDateContents.plusDays(3);
                        log(localDateContents + " の3日後は、 " + after3daysDate + " で、");
                        log(after3daysDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.JAPANESE));
                    }
                }
            }
        }
    }

    /**
     * 来年(2017年)の新卒が入社する日は何曜日？
     */
    public void test_weekOfDayOf2017NewcomerDiff() {
        String dayOf2017NewcomerStr = "2017-04-01";
        LocalDate dayOf2017Newcomer = LocalDate.parse(dayOf2017NewcomerStr);
        int dayOfWeekOf2017NewcomerNum = dayOf2017Newcomer.getDayOfWeek().getValue();
        
        // 土日だったら、翌週月曜に入社になると考え、日付を更新する。
        if (dayOfWeekOf2017NewcomerNum == 6) { // 土曜日
            dayOf2017Newcomer = dayOf2017Newcomer.plusDays(2);
        } else if (dayOfWeekOf2017NewcomerNum == 7) { // 日曜日
            dayOf2017Newcomer = dayOf2017Newcomer.plusDays(1);
        }

        log("来年の新卒が入社する日は、 " + dayOf2017Newcomer + " で、");
        log(dayOf2017Newcomer.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.JAPANESE) + " です。");
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * 色がyellowのカラーボックスに入っている二つの日付の日数の差は？
     */
    public void test_diffDay() {
        List<ColorBox> colorBoxList = getColorBoxList();
        String boxColor = "yellow";
        LocalDate firstDateTime = null;
        LocalDate secondDateTime = null;
        for (ColorBox colorBox : colorBoxList) {
            // done hakiba equals()と "==" by jflute (2016/05/25)
            // http://dbflute.seasar.org/ja/manual/topic/programming/java/beginners.html#equalsequal
            if (colorBox.getColor().getColorName().equals(boxColor)) {
                List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
                for (BoxSpace boxSpace : boxSpaceList) {
                    Object contents = boxSpace.getContents();
                    LocalDate localDateContents = null;
                    if (contents instanceof LocalDateTime) {
                        LocalDateTime localDateTimeContents = (LocalDateTime)contents;
                        localDateContents = localDateTimeContents.toLocalDate();
                    } else if (contents instanceof LocalDate) {
                        localDateContents = (LocalDate)contents;
                    }
                    if (localDateContents != null && firstDateTime == null) {
                        firstDateTime =localDateContents;
                        continue;
                    }
                    if (localDateContents != null && secondDateTime == null) {
                        secondDateTime =localDateContents;
                        break;
                    }                        
                }
            }
        }
        if (firstDateTime != null && secondDateTime != null) {
            log(boxColor + " のカラーボックスには、"); 
            log(firstDateTime + " と " + secondDateTime + " が含まれており、");
            log("その差は、" + Math.abs(firstDateTime.until(secondDateTime, ChronoUnit.DAYS)) + " 日です。");
        } else {
            log(boxColor + " のカラーボックスに入った二つの日付はありませんでした。");
        }
    }    
}