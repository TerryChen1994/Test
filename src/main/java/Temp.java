import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Temp {
    public static void main(String[] args) {
        String str = "東京都文京区小石川３−１３−９ セボン小石川113号";

        Pattern p = Pattern.compile(".*[0-9１２３４５６７８９]+(丁目|-|ー|番地|番)+[0-9１２３４５６７８９]+");
        Matcher m = p.matcher(str);
        m.find();
        System.out.println(m.group());
        String sub = str.substring(m.group().length()).replaceAll("　"," ");
        System.out.println(sub.trim());
    }
}
