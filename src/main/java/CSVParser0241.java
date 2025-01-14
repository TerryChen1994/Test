import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CSVParser0241 {
    public static String item = "cins0241";

    public static void main(String[] args) throws URISyntaxException, IOException, CsvValidationException {

        URL resource = CSVParser0241.class.getClassLoader().getResource("output_other.csv");
        File input_file = new File(resource.toURI());
        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(input_file),"Shift-JIS"));

        File output_file1 = new File("output/"+item+"/output1.csv");
        output_file1.createNewFile();
        Writer wri1 = new OutputStreamWriter(new FileOutputStream(output_file1), "Shift_JIS");
        CSVWriter writer1 = new CSVWriter(wri1);

        File output_file2 = new File("output/"+item+"/output2.csv");
        output_file2.createNewFile();
        Writer wri2 = new OutputStreamWriter(new FileOutputStream(output_file2), "Shift_JIS");
        CSVWriter writer2 = new CSVWriter(wri2);

        File output_file3 = new File("output/"+item+"/output3.csv");
        output_file3.createNewFile();
        Writer wri3 = new OutputStreamWriter(new FileOutputStream(output_file3), "Shift_JIS");
        CSVWriter writer3 = new CSVWriter(wri3);

        File output_file4 = new File("output/"+item+"/output4.csv");
        output_file4.createNewFile();
        Writer wri4 = new OutputStreamWriter(new FileOutputStream(output_file4), "Shift_JIS");
        CSVWriter writer4 = new CSVWriter(wri4);

        File output_filen = new File("output/"+item+"/output_other.csv");
        output_filen.createNewFile();
        Writer wrin = new OutputStreamWriter(new FileOutputStream(output_filen), "Shift_JIS");
        CSVWriter writern = new CSVWriter(wrin);

        String[] header = reader.readNext();
        writer1.writeNext(header);writer1.flush();
        writer2.writeNext(header);writer2.flush();
        writer3.writeNext(header);writer3.flush();
        writer4.writeNext(header);writer4.flush();
        writern.writeNext(header);writern.flush();

        String[] preLine = new String[0];
        String[] currentLine;
        List<String[]> list = new ArrayList<>();

        while ((currentLine = reader.readNext()) != null) {

            // 第一行以后
            if(preLine.length > 0){
                if (!currentLine[0].equals(preLine[0])) {
                    if(is0241(list)) {
                        int sum = sum(list);
                        // 1单
                        if (list.size() == 1) {
                            if (sum == 1) {
                                writer1.writeNext(list.get(0));
                                writer1.flush();
                            } else if (sum == 2) {
                                list.get(0)[10] = list.get(0)[10] + "*2";
                                writer1.writeNext(list.get(0));
                                writer1.flush();
                            } else if (sum == 3) {
                                writer1.writeNext(list.get(0));
                                writer1.flush();
                                list.get(0)[10] = list.get(0)[10] + "*2";
                                writer1.writeNext(list.get(0));
                                writer1.flush();
                            } else if (sum == 4) {
                                list.get(0)[10] = list.get(0)[10] + "*2";
                                writer1.writeNext(list.get(0));
                                writer1.flush();
                                writer1.writeNext(list.get(0));
                                writer1.flush();
                            } else if (sum >= 5) {
                                writern.writeAll(list);
                                writern.flush();
                            } else {
                                throw new RuntimeException();
                            }

                        }
                        // 2单
                        else if (list.size() == 2) {
                            if (sum == 2) {
                                list.get(0)[10] = list.get(0)[10] + list.get(1)[10];
                                writer2.writeNext(list.get(0));
                                writer2.flush();
                            } else if (sum == 3) {
                                for (String[] order : list) {
                                    if (Integer.parseInt(order[9]) == 1) {
                                        writer2.writeNext(order);
                                        writer2.flush();
                                    } else if (Integer.parseInt(order[9]) == 2) {
                                        order[10] = order[10] + "*2";
                                        writer2.writeNext(order);
                                        writer2.flush();
                                    }
                                }
                            } else if (sum == 4) {
                                list.get(0)[10] = list.get(0)[10] + "*2";
                                writer2.writeNext(list.get(0));
                                writer2.flush();
                                list.get(1)[10] = list.get(1)[10] + "*2";
                                writer2.writeNext(list.get(1));
                                writer2.flush();
                            } else if (sum >= 5) {
                                writern.writeAll(list);
                                writern.flush();
                            } else {
                                throw new RuntimeException();
                            }
                        }
                        // 3单
                        else if (list.size() == 3) {
                            if (sum == 3) {
                                writer3.writeNext(list.get(0));
                                writer3.flush();
                                list.get(1)[10] = list.get(1)[10] + list.get(2)[10];
                                writer3.writeNext(list.get(1));
                                writer3.flush();
                            } else if (sum == 4) {
                                ArrayList<String[]> singleList = new ArrayList<>();
                                for (String[] order : list) {
                                    if (Integer.parseInt(order[9]) == 1) {
                                        singleList.add(order);
                                    } else if (Integer.parseInt(order[9]) == 2) {
                                        order[10] = order[10] + "*2";
                                        writer3.writeNext(order);
                                        writer3.flush();
                                    }
                                }
                                singleList.get(0)[10] = singleList.get(0)[10] + singleList.get(1)[10];
                                writer3.writeNext(singleList.get(0));
                                writer3.flush();
                            } else if (sum >= 5) {
                                writern.writeAll(list);
                                writern.flush();
                            } else {
                                throw new RuntimeException();
                            }
                        }
                        // 4单
                        else if (list.size() == 4) {
                            if (sum == 4) {
                                list.get(0)[10] = list.get(0)[10] + list.get(1)[10];
                                writer4.writeNext(list.get(0));
                                writer4.flush();
                                list.get(2)[10] = list.get(2)[10] + list.get(3)[10];
                                writer4.writeNext(list.get(2));
                                writer4.flush();
                            } else if (sum >= 5) {
                                writern.writeAll(list);
                                writern.flush();
                            } else {
                                throw new RuntimeException();
                            }
                        }
                        // 5单以上
                        else if (list.size() >= 5) {
                            writern.writeAll(list);
                            writern.flush();
                        } else {
                            throw new RuntimeException();
                        }
                    }
                    else{
                        writern.writeAll(list);
                        writern.flush();
                    }
                    list.clear();
                }
                list.add(currentLine);
                preLine = currentLine;
            }
            //　第一行
            else{
                list.add(currentLine);
                preLine = currentLine;
            }
        }
    }

    public static int sum(List<String[]> list){
        int sum = 0;
        for (String[] current : list) {
            int i = Integer.parseInt(current[9]);
            sum = sum + i;
        }
        return sum;
    }

    public static boolean is0241(List<String[]> list){
        for (String[] current : list) {
            if(!current[8].equals(item)){
                return false;
            }
        }
        return true;
    }
}
