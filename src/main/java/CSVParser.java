import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    public static void main(String[] args) throws Exception {

        URL resource = CSVParser.class.getClassLoader().getResource("list.csv");
        File input_file = new File(resource.toURI());
        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(input_file),"Shift-JIS"));

        File output_file1 = new File("output/output1.csv");
        CSVWriter writer1 = new CSVWriter(new FileWriter(output_file1));

        File output_file2 = new File("output/output2.csv");
        CSVWriter writer2 = new CSVWriter(new FileWriter(output_file2));

        File output_file3 = new File("output/output3.csv");
        CSVWriter writer3 = new CSVWriter(new FileWriter(output_file3));

        File output_file4 = new File("output/output4.csv");
        CSVWriter writer4 = new CSVWriter(new FileWriter(output_file4));

        File output_file5 = new File("output/output5.csv");
        CSVWriter writer5 = new CSVWriter(new FileWriter(output_file5));

        File output_filen = new File("output/outputn.csv");
        CSVWriter writern = new CSVWriter(new FileWriter(output_filen));

        String[] header = reader.readNext();
        writer1.writeNext(header);writer1.flush();
        writer2.writeNext(header);writer2.flush();
        writer3.writeNext(header);writer3.flush();
        writer4.writeNext(header);writer4.flush();
        writer5.writeNext(header);writer5.flush();
        writern.writeNext(header);writern.flush();

        String[] preLine = new String[0];
        String[] currentLine;
        List<String[]> list = new ArrayList<>();

        while ((currentLine = reader.readNext()) != null) {
            if(preLine.length > 0){
                if(currentLine[0].equals(preLine[0])){
                    list.add(currentLine);
                    preLine = currentLine;
                } else {
                    switch (list.size()){
                        case 1: writer1.writeAll(list); writer1.flush();break;
                        case 2: writer2.writeAll(list); writer2.flush();break;
                        case 3: writer3.writeAll(list); writer3.flush();break;
                        case 4: writer4.writeAll(list); writer4.flush();break;
                        case 5: writer5.writeAll(list); writer5.flush();break;
                        default: writern.writeAll(list); writern.flush();
                    }
                    list.clear();
                    list.add(currentLine);
                    preLine = currentLine;
                }
            } else{
                list.add(currentLine);
                preLine = currentLine;
            }
        }
    }
}
