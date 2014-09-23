package org.jomics.viusalizationmanuscript.jfreechart;

import uk.ac.ebi.pride.utilities.mol.GraviUtilities;
import uk.ac.ebi.pride.utilities.util.NumberUtilities;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yperez on 22/09/2014.
 */
public class CreateExampleScript {
    public static void main(String[] args) throws IOException {
        PrintStream output = new PrintStream(args[1]);
        output.println("mass, isoelectric point, gravy, length, type");
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String line;
        int countbasic = 0;
        int countneutral = 0;
        int countacid = 0;
        List<String> lines = new ArrayList<String>();
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }

        while(countacid < 101 && countneutral < 101 && countbasic < 100){
            int index = (int)(Math.random() * lines.size());
            line = lines.get(index);
            if(line.charAt(0) != '#'){
                String[] lineArray = line.split("\\s+");
                if(NumberUtilities.isNumber(lineArray[1]) && NumberUtilities.isNumber(lineArray[2])){
                    Double mass = Double.parseDouble(lineArray[1]);
                    mass = mass/1000;
                    Double iso  = Double.parseDouble(lineArray[2]);
                    Integer length = lineArray[0].length();
                    String type = null;
                    Double gravy = GraviUtilities.calculate(lineArray[0]);
                    boolean print = false;
                    if(iso >= 5 && iso <= 7 && countneutral < 100 ) {
                        type = "neutral";
                        countneutral++;
                        print = true;
                    }else if(iso > 7 && countbasic < 100) {
                        type = "basic";
                        countbasic++;
                        print = true;
                    }else if(countacid < 100){
                        type = "acid";
                        countacid++;
                        print = true;
                    }
                    if(print) output.println(customFormat("#.#", mass) + "," + customFormat("#.#", iso) + "," + customFormat("#.#",gravy) + "," + customFormat("#.#", length) + "," + type);
                }
            }
        }

    }

    static public String customFormat(String pattern, double value ) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(value);
    }
}
