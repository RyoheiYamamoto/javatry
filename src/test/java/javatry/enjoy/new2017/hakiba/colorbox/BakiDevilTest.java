package javatry.enjoy.new2017.hakiba.colorbox;

import java.io.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.time.LocalDate;
import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import javatry.colorbox.ColorBox;
import javatry.colorbox.impl.*;
import javatry.colorbox.space.BoxSpace;
import javatry.colorbox.unit.ColorBoxTestCase;

/**
 * デビルテスト。<br>
 * 何かやれって言われたらやること。
 * @author baki
 */
public class BakiDevilTest extends ColorBoxTestCase {

//    private static final String PARSE_TO_FILENAME_REGEX = "(.*)(?=\\.txt$)";
//    private static final Pattern PARSE_TO_FILENAME_COMPILE = Pattern.compile(PARSE_TO_FILENAME_REGEX);
    
    /**
     * (このテストメソッドの中だけで) 赤いカラーボックスの高さを 160 に変更せよ
     */
    public void test_devil1() {
        int targetHeight = 160;
        List<ColorBox> colorBoxList = getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            if (colorBox.getColor().getColorName() == "red") {
                int beforeHeight = colorBox.getSize().getHeight();
                log("変更前の高さ : " + beforeHeight);
                Class<?> boxSizeClass = colorBox.getSize().getClass();
                try {
                    Field heightField = boxSizeClass.getDeclaredField("height");
                    heightField.setAccessible(true);
                    log("高さを変更します。");
                    heightField.set(colorBox.getSize(), targetHeight);
                    log("変更後の高さ : " + colorBox.getSize().getHeight());
                    heightField.set(colorBox.getSize(), beforeHeight);
                    log("元の高さに戻します。");
                    heightField.setAccessible(false);
                    log(colorBox.getSize().getHeight());
                } catch (Exception e) {
                    log(e);
                }
            }
        }
    }

    /**
     * nullを含んでいるカラーボックスの色の名前の3文字目の文字で色の名前が終わっている
     * カラーボックスの深さの十の位の数字
     * が小数点第二桁目になっているスペースの中のリストの中のBigDecimalの一の位の数字
     * と同じ色の長さのカラーボックスの一番下のスペースに入っているものは？
     * 
     * edit 読みづらかったのでコメント編集しました by baki
     */
    public void test_devil2() {
        List<ColorBox> colorBoxList = getColorBoxList();
        String nullBoxColorName = null;

        // 1. nullを含んでいるカラーボックスの取得
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : boxSpaceList) {
                if (boxSpace.getContents() == null) {
                    nullBoxColorName = colorBox.getColor().getColorName();
                }
            }
        }

        // 2. 1.のカラーボックスの深さの十の位の数字の取得
        String thirdIndexChar = nullBoxColorName.substring(2, 3);
        Integer tensPlaceNum = null;
        for (ColorBox colorBox : colorBoxList) {
            if (colorBox.getColor().getColorName().endsWith(thirdIndexChar)) {
                tensPlaceNum = (colorBox.getSize().getDepth() % 100) / 10; //depthがint型であるので、可能
            }
        }

        // 3. 2.の小数点第二桁目になっているスペースの中のリストの中のBigDecimalの一の位の数字の取得
        Integer onePlaceNum = null;
        if (tensPlaceNum != null) {
            for (ColorBox colorBox : colorBoxList) {
                List<BoxSpace> boxSpaceList = colorBox.getSpaceList();
                for (BoxSpace boxSpace : boxSpaceList) {
                    if (boxSpace.getContents() instanceof List<?>) {
                        List<?> listContents = (List<?>)boxSpace.getContents();
                        for (Object object : listContents) {
                            if (object instanceof BigDecimal) {
                                BigDecimal bigDecimalObj = (BigDecimal)object;
                                BigDecimal twoDecimalPlacesNum = bigDecimalObj.multiply(new BigDecimal("100")).remainder(new BigDecimal("10"));
                                int tempNum = twoDecimalPlacesNum.intValue();
                                if (tempNum == tensPlaceNum){
                                    bigDecimalObj = bigDecimalObj.remainder(new BigDecimal("10"));
                                    onePlaceNum = bigDecimalObj.intValue();
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // 4. 3.と同じ色の長さのカラーボックスの取得
        List<ColorBox> lowerSpaceColorBoxList = new ArrayList<ColorBox>();
        if (onePlaceNum != null) {
            for (ColorBox colorBox : colorBoxList) {
                if (colorBox.getColor().getColorName().length() == onePlaceNum) {
                    lowerSpaceColorBoxList.add(colorBox);
                }
            }
        }

        // 5. 4.のカラーボックスの一番下のスペースに入っているものの取得
        List<BoxSpace> lowerSpaceContentsList = new ArrayList<BoxSpace>();
        if (!lowerSpaceColorBoxList.isEmpty()) {
            for (ColorBox colorBox : lowerSpaceColorBoxList) {
                if (colorBox instanceof CompactColorBox) {
                    CompactColorBox compactColorBox = (CompactColorBox)colorBox;
                    lowerSpaceContentsList.add(compactColorBox.getLowerSpace());
                } else if (colorBox instanceof DoorColorBox) {
                    DoorColorBox doorColorBox = (DoorColorBox)colorBox;
                    lowerSpaceContentsList.add(doorColorBox.getLowerSpace());
                } else if (colorBox instanceof StandardColorBox) {
                    StandardColorBox standardColorBox = (StandardColorBox)colorBox;
                    lowerSpaceContentsList.add(standardColorBox.getLowerSpace());
                }
            }
        }
        
        // 6. 5.で取得したものを表示
        if (!lowerSpaceContentsList.isEmpty()) {
            log("<<< 以下のものを取得しました。 >>>");
            for (BoxSpace boxSpace : lowerSpaceContentsList) {
                log(boxSpace);
            }
        }
    }
    
    /**
     * ボックスのどこかにtxtファイルが存在しています。その中身に今日の日付、名前(+あだ名)、本日学んだ内容を書いてください。<br>
     * そしてそのファイルを自分専用のディレクトリにコピーしてください。<br>
     * 書いたのち、コピー元、コピー先それぞれの中身を表示し、差がないことを確認してください。
     */
    public void test_devil3() {
        List<ColorBox> colorBoxList = getColorBoxList();
        File srcFile = null;
        String name = "山本　凌平";
        String nickname = "はきば";
        String reportContent = "汁べゑの姉妹店が清水ビルの近くにあることを知った。\n今週末に行ってみようと思う。";

        // Ex) /Users/ryohei.yamamoto/works/training
        String copydirPath = "/Users/" + System.getProperty("user.name") + "/works/training";

        // srcFileの検索と存在確認（なければ作成）
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace boxSpace : colorBox.getSpaceList()) {
                if (boxSpace.getContents() instanceof File) {
                    File fileContents = (File)boxSpace.getContents();
                    if (fileContents.getPath().endsWith(".txt")) {
                        srcFile = fileContents;
                        createNotExistFile(srcFile);
                    }
                }
            }
        }

        // srcFileに「本日学んだ内容」を書き込む。
        try {
            if (isWritableFile(srcFile)) {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(srcFile)));
            printWriter.println("日付: " + LocalDate.now());
            printWriter.println("名前: " + name + "(" + nickname + ")");
            printWriter.println("本日学んだ内容: " + reportContent);
            printWriter.close();
            } else {
                log("Fileに書き込むことができません。");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // コピー先のディレクトリの存在確認（なければ作成）
        File copydir = new File(copydirPath);
        if (!copydir.exists()) {
            copydir.mkdirs();
        }

        // copyFileの作成
        String copyFilePath = copydirPath + "/" + srcFile.getName();
        File copyFile = new File(copyFilePath);
        createNotExistFile(copyFile);
        copyFile(srcFile, copyFile);

        // srcFileとcopyFileの整合確認
        log("<<< コピー元ファイルの表示 >>>");
        showFile(srcFile);
        log("<<< コピー先ファイルの表示 >>>");
        showFile(copyFile);
    }

    private void createNotExistFile(File srcFile) {
        // srcFileが存在していなければ作成する
        try {
            if (!srcFile.exists()) {
                srcFile.createNewFile();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void showFile(File srcFile) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(srcFile));
            String str = bufferedReader.readLine();
            log("FilePath: " + srcFile);
            log("============内容===============");
            while (str != null) {
                log(str);
                str = bufferedReader.readLine();
            }
            log("==============================");
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isWritableFile(File srcFile) {
        if (srcFile.exists()) {
            if (srcFile.isFile() && srcFile.canWrite()) {
                return true;
            }
        }
        return false;
    }
    
    private void copyFile(File in, File out) {
        // AutoClosableによるリソースの自動解放 <-[bakiTODO] よくわかっていないので、後ほど調べる
        try (FileInputStream inputStream = new FileInputStream(in);
             FileOutputStream outputStream = new FileOutputStream(out)) {
            FileChannel inChannel = inputStream.getChannel();
            FileChannel outChannel = outputStream.getChannel();
            inChannel.transferTo(0, inChannel.size(),outChannel);
        }
        catch (IOException e) {
            log(e);
        }
    }
    
/*
 * 以下、同名のファイルが存在していた場合は「hogehoge_copy.txt」と命名しなおすメソッドを作ろうと思ったが、
 * 「hogehoge_copy(2).txt」,「hogehoge_copy(3).txt」...に対応できないので、一旦保留。
 * [bakiTODO] 今回のDevilTestで問われている内容とは少しずれているので、時間があるときに、再度実装 by baki
 */    
//  if (copyFile.exists()) {
//      // 「hogehoge_copy.txt」の生成
//      Matcher mathcher = PARSE_TO_FILENAME_COMPILE.matcher(srcFile.getName());
//      if (mathcher.find()) {
//          String copyFileName = mathcher.group(1);
//          copyFilePath = copydirPath + "/" + copyFileName + "_copy.txt";
//          copyFile = new File(copyFilePath);
//      }
//  }
}
