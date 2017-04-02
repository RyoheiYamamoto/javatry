package javatry.enjoy.new2017.judy;

import java.io.*;

/**
 * Java言語プログラミングレッスン4章<br>
 * if文の問題
 * @author ryohei.yamamoto
 */
public class Lesson4_IfStatment {
    
    public static void main(String[] args) {
        lesson_4_4();
        lesson_4_5();
    }

    /**
     * Q.)日付（月と日で良い）を入力して、以下の祝日に当てはまれば、「その日は、◯◯の日です。」と表示されるプログラムを作成。<br>
     * 1~12月に当てはまらない月、1~31に当てはまらない日を入力した場合は、間違っている旨ユーザーに伝わるよう表示すること。<br>
     * どの祝日にも当てはまらない場合場合はその旨表示すること。<br>
     * 文化の日、みどりの日、勤労感謝の日、山の日
     */
    private static void lesson_4_4() {
       BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
       try {
           String line ;
           System.out.println("月を入力してください");
           line = reader.readLine();
           int month = Integer.parseInt(line);
           System.out.println("日を入力してください");
           line = reader.readLine();
           int day = Integer.parseInt(line);
           
           if(1 <= month && month <= 12 && 1 <= day && day <= 31){
               if (month == 11 && day == 3) { //文化の日
                   System.out.println("その日は文化の日です。");
               } else if (month == 5 && day == 4) {//みどりの日
                   System.out.println("その日はみどりの日です。");
               } else if (month == 11 && day == 23) {//勤労感謝の日
                   System.out.println("その日は勤労感謝の日です。");
               } else if (month == 8 && day == 11) {//山の日
                   System.out.println("その日は山の日です。");
               } else {
                   System.out.println("多分、祝日ではないですよ。");
               }
           } else {
               System.out.println(month + "/" + day + " は存在しませんよ");
           }
       } catch (IOException e) {
            System.out.println(e);
       } catch (NumberFormatException e) {
           System.out.println(e);
           System.out.println("数値が正しくありません。");
       }
    }

    /**
     * 年齢を入力すると、喜寿まであと何年か表示する。<br>
     * 丁度喜寿であれば、「おめでとう」を、喜寿を過ぎていたら喜寿から何年たったか表示する。
     */
    private static void lesson_4_5() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line ;
            System.out.println("年齢を入力してください。");
            line = reader.readLine();
            int age = Integer.parseInt(line);
            if(0 <= age){
                int ageDiff;
                if(age == 77){
                    System.out.println("今年は喜寿の年ですね！おめでとうございます！");
                } else if (age < 77) {
                    ageDiff = 77 - age;
                    System.out.println("喜寿まであと " + ageDiff + " 年ですね！");
                } else if (77 < age) {
                    ageDiff = age - 77;
                    System.out.println("喜寿から " + ageDiff + " 年ですね！");
                }
            } else {
                System.out.println( age + " 歳なんてありませんよ！");
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (NumberFormatException e) {
            System.out.println("数字が正しく入力されていません！");
        }
    }
}
