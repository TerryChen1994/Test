import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Temp {
    public static void main(String[] args) throws URISyntaxException, IOException {
//        URL resource = CSVParser.class.getClassLoader().getResource("test.txt");
//        File input_file = new File(resource.toURI());
//        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(input_file));
//        String line = new String(bis.readAllBytes());
//        String[] list = line.split(",");
//
//
//        String product = list[10].replaceAll("\n","")
//                .replaceAll("\"","")
//                .replaceAll("カラー:","")
//                .replaceAll("サイズ:","")
//                .replaceAll("【{1}.*。{1}","");
//        if(product.contains("グレー")){
//            product = product.replaceAll("グレー","灰");
//        } else if(product.contains("アイボリー")){
//            product = product.replaceAll("アイボリー","米");
//        } else if(product.contains("ブラック")){
//            product = product.replaceAll("ブラック","黒");
//        } else if(product.contains("グリーン")){
//            product = product.replaceAll("グリーン","緑");
//        } else if(product.contains("ネイビー")){
//            product = product.replaceAll("ネイビー","紺");
//        } else {
//            throw new RuntimeException();
//        }

        String s = "miXL";
        s = s + "*2";
        System.out.println(s);
    }
}
