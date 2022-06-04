import com.opencsv.CSVReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.*;

public class TestA {
    public static void main(String[] args) throws Exception{

        URL resource = TestA.class.getClassLoader().getResource("item.csv");
        File input_file = new File(resource.toURI());
        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(input_file),"Shift-JIS"));

        List<Map<String, String>> list = new ArrayList<>();

        String [] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            String itemId = nextLine[1];

            if(itemId.startsWith("ins") && !itemId.equals("ins0")) {

                String itemOriginPrice = nextLine[9];
                String itemPrice = nextLine[8];
                String itemImgDiv = nextLine[26];
                String[] imgList = itemImgDiv.split("\n");
                String itemImg = imgList[0].split(" ")[0];

                Map<String, String> map = new HashMap<>();
                map.put("item_id", itemId);
                map.put("item_origin_price", itemOriginPrice);
                map.put("item_price", itemPrice);
                map.put("item_img", itemImg);
                list.add(map);
            }
        }

        Collections.reverse(list);
        JSONArray jsonArray = new JSONArray(list);

        System.out.println(jsonArray.toString());

    }
}
