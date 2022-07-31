import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressParser {
    public static void main(String[] args) throws Exception {

        URL resource = AddressParser.class.getClassLoader().getResource("yamato.csv");
        File input_file = new File(resource.toURI());
        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(input_file),"Shift-JIS"));

        File output_file = new File("output/yamato_result.csv");
        FileOutputStream fileOutputStream = new FileOutputStream(output_file);
        Writer wri = new OutputStreamWriter(fileOutputStream, "Shift_JIS");
        CSVWriter writer = new CSVWriter(wri);

        String[] header = reader.readNext();
        writer.writeNext(header);
        writer.flush();

        String[] currentLine;
        while ((currentLine = reader.readNext()) != null) {
            String address = currentLine[11];
            int index = 0;
            label1: for(int i = 0; i < address.length(); i ++){
                if(isInteger(String.valueOf(address.charAt(i)))){
                    for(int j = i + 1; j < address.length(); j++){
                        if(!isContinue(String.valueOf(address.charAt(j)))){
                            index = j;
                            break label1;
                        }
                    }
                }
            }

            if(index != 0){
                currentLine[11] = address.substring(0, index).trim();
                currentLine[12] = address.substring(index).trim();
            }

            writer.writeNext(currentLine);
            writer.flush();
        }
    }

    public static boolean isInteger(String word){
        if(word.matches("[0-9０-９]+")){
            return true;
        }
        return false;
    }

    public static boolean isContinue(String word){
        if(word.matches("([0-9０-９]|丁|目|地|番|号|ー|-|−|—)+")){
            return true;
        }
        return false;
    }
}
