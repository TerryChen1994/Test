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

        Pattern p = Pattern.compile(".*[0-9１２３４５６７８９]+(丁目|-|ー|番地|番)+[0-9１２３４５６７８９]+");

        String[] currentLine;
        while ((currentLine = reader.readNext()) != null) {
            Matcher m = p.matcher(currentLine[11]);
            if(m.find()) {
                String add1 = m.group();
                String add2 = currentLine[11].substring(m.group().length()).replaceAll("　", " ").trim();
                currentLine[11] = add1;
                currentLine[12] = add2;
            }
            writer.writeNext(currentLine);
            writer.flush();
        }
    }
}
