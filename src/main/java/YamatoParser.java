import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class YamatoParser {

    public static String sendDate = "20220731";
    public static String item = "ins0248";

    public static void main(String[] args) throws Exception{

        URL resource = CSVParser.class.getClassLoader().getResource("list.csv");
        File input_file = new File(resource.toURI());
        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(input_file),"Shift-JIS"));

        URL template_url = CSVParser.class.getClassLoader().getResource("yamato-template.csv");
        File templateFile = new File(template_url.toURI());
        CSVReader template_reader = new CSVReader(new InputStreamReader(new FileInputStream(templateFile),"Shift-JIS"));

        String dirstr = "output/" + item;
        File dir = new File(dirstr);

        if (!dir.exists()) {
            dir.mkdir();
        }

        File output_file1 = new File("output/" + item + "/output1.csv");
        output_file1.createNewFile();
        Writer wri1 = new OutputStreamWriter(new FileOutputStream(output_file1), "Shift_JIS");
        CSVWriter writer1 = new CSVWriter(wri1);

        File output_file2 = new File("output/" + item + "/output2.csv");
        output_file2.createNewFile();
        Writer wri2 = new OutputStreamWriter(new FileOutputStream(output_file2), "Shift_JIS");
        CSVWriter writer2 = new CSVWriter(wri2);

        File output_file3 = new File("output/" + item + "/output3.csv");
        output_file3.createNewFile();
        Writer wri3 = new OutputStreamWriter(new FileOutputStream(output_file3), "Shift_JIS");
        CSVWriter writer3 = new CSVWriter(wri3);

        File output_file4 = new File("output/" + item + "/output4.csv");
        output_file4.createNewFile();
        Writer wri4 = new OutputStreamWriter(new FileOutputStream(output_file4), "Shift_JIS");
        CSVWriter writer4 = new CSVWriter(wri4);

        File output_filen = new File("output/" + item + "/output_other.csv");
        output_filen.createNewFile();
        Writer wrin = new OutputStreamWriter(new FileOutputStream(output_filen), "Shift_JIS");
        CSVWriter writern = new CSVWriter(wrin);

        File output_file_no = new File("output/" + item + "/output_not.csv");
        output_file_no.createNewFile();
        Writer wrinot = new OutputStreamWriter(new FileOutputStream(output_file_no), "Shift_JIS");
        CSVWriter writernot = new CSVWriter(wrinot);

        String[] header = template_reader.readNext();
        writer1.writeNext(header);writer1.flush();
        writer2.writeNext(header);writer2.flush();
        writer3.writeNext(header);writer3.flush();
        writer4.writeNext(header);writer4.flush();
        writern.writeNext(header);writern.flush();
        writernot.writeNext(header);writernot.flush();

        String[] template = template_reader.readNext();

        String[] preLine = new String[0];
        String[] currentLine;
        List<String[]> list = new ArrayList<>();

        reader.readNext();
        while ((currentLine = reader.readNext()) != null) {

            String product = currentLine[10].replaceAll("\n","")
                    .replaceAll("\"","")
                    .replaceAll("カラー:","")
                    .replaceAll("サイズ:","")
                    .replaceAll("【{1}.*。{1}","");
            if(currentLine[8].equals(item)) {
                if (product.contains("グレー")) {
                    product = product.replaceAll("グレー", "灰");
                } else if (product.contains("アイボリー")) {
                    product = product.replaceAll("アイボリー", "米");
                } else if (product.contains("ブラック")) {
                    product = product.replaceAll("ブラック", "黒");
                } else if (product.contains("グリーン")) {
                    product = product.replaceAll("グリーン", "緑");
                } else if (product.contains("ネイビー")) {
                    product = product.replaceAll("ネイビー", "紺");
                }
            }
            currentLine[10] = product;

            // 第一行以后
            if(preLine.length > 0){
                if (!currentLine[0].equals(preLine[0])) {
                    if(isItem(list)) {
                        int sum = sum(list);
                        // 1单
                        if (list.size() == 1) {
                            if (sum == 1) {
                                writer1.writeNext(exchange(list.get(0)));
                                writer1.flush();
                            } else if (sum == 2) {
                                list.get(0)[10] = list.get(0)[10] + "*2";
                                writer1.writeNext(exchange(list.get(0)));
                                writer1.flush();
                            } else if (sum == 3) {
                                writer1.writeNext(exchange(list.get(0)));
                                writer1.flush();
                                list.get(0)[10] = list.get(0)[10] + "*2";
                                writer1.writeNext(exchange(list.get(0)));
                                writer1.flush();
                            } else if (sum == 4) {
                                list.get(0)[10] = list.get(0)[10] + "*2";
                                writer1.writeNext(exchange(list.get(0)));
                                writer1.flush();
                                writer1.writeNext(exchange(list.get(0)));
                                writer1.flush();
                            } else if (sum >= 5) {
                                for(String[] s : list){
                                    writern.writeNext(exchange(s));
                                    writern.flush();
                                }
                            } else {
                                throw new RuntimeException();
                            }

                        }
                        // 2单
                        else if (list.size() == 2) {
                            if (sum == 2) {
                                list.get(0)[10] = list.get(0)[10] + list.get(1)[10];
                                writer2.writeNext(exchange(list.get(0)));
                                writer2.flush();
                            } else if (sum == 3) {
                                for (String[] order : list) {
                                    if (Integer.parseInt(order[9]) == 1) {
                                        writer2.writeNext(exchange(order));
                                        writer2.flush();
                                    } else if (Integer.parseInt(order[9]) == 2) {
                                        order[10] = order[10] + "*2";
                                        writer2.writeNext(exchange(order));
                                        writer2.flush();
                                    }
                                }
                            } else if (sum == 4) {
                                list.get(0)[10] = list.get(0)[10] + "*2";
                                writer2.writeNext(exchange(list.get(0)));
                                writer2.flush();
                                list.get(1)[10] = list.get(1)[10] + "*2";
                                writer2.writeNext(exchange(list.get(1)));
                                writer2.flush();
                            } else if (sum >= 5) {
                                for(String[] s : list){
                                    writern.writeNext(exchange(s));
                                    writern.flush();
                                }
                            } else {
                                throw new RuntimeException();
                            }
                        }
                        // 3单
                        else if (list.size() == 3) {
                            if (sum == 3) {
                                writer3.writeNext(exchange(list.get(0)));
                                writer3.flush();
                                list.get(1)[10] = list.get(1)[10] + list.get(2)[10];
                                writer3.writeNext(exchange(list.get(1)));
                                writer3.flush();
                            } else if (sum == 4) {
                                ArrayList<String[]> singleList = new ArrayList<>();
                                for (String[] order : list) {
                                    if (Integer.parseInt(order[9]) == 1) {
                                        singleList.add(order);
                                    } else if (Integer.parseInt(order[9]) == 2) {
                                        order[10] = order[10] + "*2";
                                        writer3.writeNext(exchange(order));
                                        writer3.flush();
                                    }
                                }
                                singleList.get(0)[10] = singleList.get(0)[10] + singleList.get(1)[10];
                                writer3.writeNext(exchange(singleList.get(0)));
                                writer3.flush();
                            } else if (sum >= 5) {
                                for(String[] s : list){
                                    writern.writeNext(exchange(s));
                                    writern.flush();
                                }
                            } else {
                                throw new RuntimeException();
                            }
                        }
                        // 4单
                        else if (list.size() == 4) {
                            if (sum == 4) {
                                list.get(0)[10] = list.get(0)[10] + list.get(1)[10];
                                writer4.writeNext(exchange(list.get(0)));
                                writer4.flush();
                                list.get(2)[10] = list.get(2)[10] + list.get(3)[10];
                                writer4.writeNext(exchange(list.get(2)));
                                writer4.flush();
                            } else if (sum >= 5) {
                                for(String[] s : list){
                                    writern.writeNext(exchange(s));
                                    writern.flush();
                                }
                            } else {
                                throw new RuntimeException();
                            }
                        }
                        // 5单以上
                        else if (list.size() >= 5) {
                            for(String[] s : list){
                                writern.writeNext(exchange(s));
                                writern.flush();
                            }
                        } else {
                            throw new RuntimeException();
                        }
                    }
                    else{
                        for(String[] s : list){
                            writernot.writeNext(exchange(s));
                            writernot.flush();
                        }
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

        if(list.size() > 0){
            if(isItem(list)) {
                int sum = sum(list);
                // 1单
                if (list.size() == 1) {
                    if (sum == 1) {
                        writer1.writeNext(exchange(list.get(0)));
                        writer1.flush();
                    } else if (sum == 2) {
                        list.get(0)[10] = list.get(0)[10] + "*2";
                        writer1.writeNext(exchange(list.get(0)));
                        writer1.flush();
                    } else if (sum == 3) {
                        writer1.writeNext(exchange(list.get(0)));
                        writer1.flush();
                        list.get(0)[10] = list.get(0)[10] + "*2";
                        writer1.writeNext(exchange(list.get(0)));
                        writer1.flush();
                    } else if (sum == 4) {
                        list.get(0)[10] = list.get(0)[10] + "*2";
                        writer1.writeNext(exchange(list.get(0)));
                        writer1.flush();
                        writer1.writeNext(exchange(list.get(0)));
                        writer1.flush();
                    } else if (sum >= 5) {
                        for(String[] s : list){
                            writern.writeNext(exchange(s));
                            writern.flush();
                        }
                    } else {
                        throw new RuntimeException();
                    }

                }
                // 2单
                else if (list.size() == 2) {
                    if (sum == 2) {
                        list.get(0)[10] = list.get(0)[10] + list.get(1)[10];
                        writer2.writeNext(exchange(list.get(0)));
                        writer2.flush();
                    } else if (sum == 3) {
                        for (String[] order : list) {
                            if (Integer.parseInt(order[9]) == 1) {
                                writer2.writeNext(exchange(order));
                                writer2.flush();
                            } else if (Integer.parseInt(order[9]) == 2) {
                                order[10] = order[10] + "*2";
                                writer2.writeNext(exchange(order));
                                writer2.flush();
                            }
                        }
                    } else if (sum == 4) {
                        list.get(0)[10] = list.get(0)[10] + "*2";
                        writer2.writeNext(exchange(list.get(0)));
                        writer2.flush();
                        list.get(1)[10] = list.get(1)[10] + "*2";
                        writer2.writeNext(exchange(list.get(1)));
                        writer2.flush();
                    } else if (sum >= 5) {
                        for(String[] s : list){
                            writern.writeNext(exchange(s));
                            writern.flush();
                        }
                    } else {
                        throw new RuntimeException();
                    }
                }
                // 3单
                else if (list.size() == 3) {
                    if (sum == 3) {
                        writer3.writeNext(exchange(list.get(0)));
                        writer3.flush();
                        list.get(1)[10] = list.get(1)[10] + list.get(2)[10];
                        writer3.writeNext(exchange(list.get(1)));
                        writer3.flush();
                    } else if (sum == 4) {
                        ArrayList<String[]> singleList = new ArrayList<>();
                        for (String[] order : list) {
                            if (Integer.parseInt(order[9]) == 1) {
                                singleList.add(order);
                            } else if (Integer.parseInt(order[9]) == 2) {
                                order[10] = order[10] + "*2";
                                writer3.writeNext(exchange(order));
                                writer3.flush();
                            }
                        }
                        singleList.get(0)[10] = singleList.get(0)[10] + singleList.get(1)[10];
                        writer3.writeNext(exchange(singleList.get(0)));
                        writer3.flush();
                    } else if (sum >= 5) {
                        for(String[] s : list){
                            writern.writeNext(exchange(s));
                            writern.flush();
                        }
                    } else {
                        throw new RuntimeException();
                    }
                }
                // 4单
                else if (list.size() == 4) {
                    if (sum == 4) {
                        list.get(0)[10] = list.get(0)[10] + list.get(1)[10];
                        writer4.writeNext(exchange(list.get(0)));
                        writer4.flush();
                        list.get(2)[10] = list.get(2)[10] + list.get(3)[10];
                        writer4.writeNext(exchange(list.get(2)));
                        writer4.flush();
                    } else if (sum >= 5) {
                        for(String[] s : list){
                            writern.writeNext(exchange(s));
                            writern.flush();
                        }
                    } else {
                        throw new RuntimeException();
                    }
                }
                // 5单以上
                else if (list.size() >= 5) {
                    for(String[] s : list){
                        writern.writeNext(exchange(s));
                        writern.flush();
                    }
                } else {
                    throw new RuntimeException();
                }
            }
            else{
                for(String[] s : list){
                    writernot.writeNext(exchange(s));
                    writernot.flush();
                }
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

    public static boolean isItem(List<String[]> list){
        for (String[] current : list) {
            if(!current[8].equals(item)){
                return false;
            }
        }
        return true;
    }

    public static String[] exchange(String[] row){
        String[] tmp = new String[95];
        tmp[0] = row[0];
        tmp[1] = "7";
        tmp[2] = "0";
        tmp[4] = sendDate;
        tmp[8] = threeFigure(row[11]) + fourFigure(row[12]) + fourFigure(row[13]);
        tmp[10] = threeFigure(row[1]) + "-" + fourFigure(row[2]);
        String address = row[5] + row[6] + row[7];
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
            tmp[11] = address.substring(0, index).trim();
            tmp[12] = address.substring(index).trim();
        } else{
            tmp[11] = address;
        }
        tmp[15] = row[3] + row[4];
        tmp[19] = "05036471666";
        tmp[21] = "1100016";
        tmp[22] = "東京都台東区台東1-27-6";
        tmp[23] = "グランアセット秋葉原402";
        tmp[24] = "Capsule株式会社";
        tmp[27] = row[10];
        tmp[39] = "08073700222";
        tmp[41] = "01";

        return tmp;
    }

    public static String threeFigure(String n){
        switch (n.length()){
            case 1: return "00" + n;
            case 2: return "0" + n;
            case 3: return n;
            default: System.out.println(n); return n;
        }
    }

    public static String fourFigure(String n){
        switch (n.length()){
            case 1: return "000" + n;
            case 2: return "00" + n;
            case 3: return "0" + n;
            case 4: return n;
            default: System.out.println(n); return n;
        }
    }

    public static boolean isInteger(String word){
        if(word.matches("[0-9０-９]+")){
            return true;
        }
        return false;
    }

    public static boolean isContinue(String word){
        if(word.matches("([0-9０-９]|丁|目|地|番|号|ー|-|−|—|‐)+")){
            return true;
        }
        return false;
    }
}
