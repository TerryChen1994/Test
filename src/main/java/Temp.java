import com.opencsv.CSVWriter;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Temp {
    public static void main(String[] args) throws URISyntaxException, IOException {

        String[] s = new String[10];

        File output_file1 = new File("output/test.csv");
        output_file1.createNewFile();
        Writer wri1 = new OutputStreamWriter(new FileOutputStream(output_file1), "Shift_JIS");
        CSVWriter writer1 = new CSVWriter(wri1, ',', CSVWriter.NO_QUOTE_CHARACTER, '"', "\n");

        s[1] = "7";

        writer1.writeNext(s);
        writer1.flush();

        System.out.println(s[3]);
    }
}
