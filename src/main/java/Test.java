import com.opencsv.CSVReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Test {
    public static void main(String[] args) throws Exception{

        URL resource = Test.class.getClassLoader().getResource("item_data.csv");
        File input_file = new File(resource.toURI());
        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(input_file),"Shift-JIS"));

        List<Map<String, String>> topsList = new ArrayList<>();
        List<Map<String, String>> bottomsList = new ArrayList<>();
        List<Map<String, String>> onepieceList = new ArrayList<>();
        List<Map<String, String>> outerList = new ArrayList<>();
        List<Map<String, String>> goodsList = new ArrayList<>();
        List<Map<String, String>> bagList = new ArrayList<>();

        String [] nextLine;
        while ((nextLine = reader.readNext()) != null) {   // 2
            String itemId = nextLine[1];
            if(itemId.startsWith("ins") && !itemId.equals("ins0")) {
                String itemImg = nextLine[3].split(" ")[0];
                String category = itemImg.substring(itemImg.indexOf("/item/")+6, itemImg.lastIndexOf("/"));
                if(!category.startsWith(":")){
                    Map<String, String> map = new HashMap<>();
                    map.put("item_id", itemId);
                    map.put("item_img", itemImg);
                    switch (category){
                        case "tops" : topsList.add(map);break;
                        case "bottoms" : bottomsList.add(map);break;
                        case "onepiece" : onepieceList.add(map);break;
                        case "outer" : outerList.add(map);break;
                        case "goods" : goodsList.add(map);break;
                        case "bag" : bagList.add(map);break;
                    }
                }
            }
        }

        Collections.reverse(topsList);
        Collections.reverse(bottomsList);
        Collections.reverse(onepieceList);
        Collections.reverse(outerList);
        Collections.reverse(goodsList);
        Collections.reverse(bagList);

        JSONArray topsArray = new JSONArray(topsList);
        JSONArray bottomsArray = new JSONArray(bottomsList);
        JSONArray onepieceArray = new JSONArray(onepieceList);
        JSONArray outerArray = new JSONArray(outerList);
        JSONArray goodsArray = new JSONArray(goodsList);
        JSONArray bagArray = new JSONArray(bagList);

        System.out.println("tops");
        System.out.println(topsArray.toString());

        System.out.println("bottoms");
        System.out.println(bottomsArray.toString());

        System.out.println("outer");
        System.out.println(outerArray.toString());

        System.out.println("onepiece");
        System.out.println(onepieceArray.toString());

        System.out.println("goods");
        System.out.println(goodsArray.toString());

        System.out.println("bag");
        System.out.println(bagArray.toString());

    }
}
