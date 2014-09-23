package org.jomics.viusalizationmanuscript.jfreechart;

import uk.ac.ebi.pride.utilities.mol.GraviUtilities;
import uk.ac.ebi.pride.utilities.util.NumberUtilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

/**
 * Created by yperez on 22/09/2014.
 */

public class ScriptForData {
    public static String PEPTIDEATLAS = "Peptide Atlas";
    public static String GPMDB        = "GPMDB";
    public static String MAXQB        = "MaxQB";
    public static String PROTEOMICSDB = "proteomicsDB";
    public static String HPM          = "HPM";

    public static void main(String[] args) throws IOException {
        Map<String, Map<Integer, Integer>> values = new TreeMap<String, Map<Integer, Integer>>();
        Map<String, List<String>> repositoryPRoteins = new HashMap<String, List<String>>();

        repositoryPRoteins.put(PEPTIDEATLAS, new ArrayList<String>());
        repositoryPRoteins.put(GPMDB, new ArrayList<String>());
        repositoryPRoteins.put(MAXQB, new ArrayList<String>());
        repositoryPRoteins.put(PROTEOMICSDB, new ArrayList<String>());
        repositoryPRoteins.put(HPM, new ArrayList<String>());

        PrintStream output = new PrintStream(args[2]);
        output.println("PeptideAtlas, GPMDB, MaxQB, ProteomicsDB, HPM");
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String line;

        BufferedReader brUniprot = new BufferedReader(new FileReader(args[1]));
        String lineuniprot;

        List<String> completeUniprot = new ArrayList<String>();
        while ((lineuniprot = brUniprot.readLine()) != null) {
            if(lineuniprot.charAt(0) != '#'){
                completeUniprot.add(lineuniprot);
            }
        }

        List<String> lines = new ArrayList<String>();
        while ((line = br.readLine()) != null) {
            lines.add(line);
            if(line.charAt(0) != '#'){
                String[] lineArray = line.split(",");
                System.out.println(line);
                if(lineArray[0]!=null && !lineArray[0].isEmpty())repositoryPRoteins.get(PEPTIDEATLAS).add(lineArray[0]);
                if(lineArray[1]!=null && !lineArray[1].isEmpty())repositoryPRoteins.get(GPMDB).add(lineArray[1]);
                if(lineArray[2]!=null && !lineArray[2].isEmpty())repositoryPRoteins.get(MAXQB).add(lineArray[2]);
                if(lineArray[3]!=null && !lineArray[3].isEmpty())repositoryPRoteins.get(HPM).add(lineArray[3]);
                if(lineArray[4]!=null && !lineArray[4].isEmpty())repositoryPRoteins.get(PROTEOMICSDB).add(lineArray[4]);
            }
        }
        values.put(PEPTIDEATLAS, retrieveMap(repositoryPRoteins.get(PEPTIDEATLAS),repositoryPRoteins.get(GPMDB), repositoryPRoteins.get(MAXQB), repositoryPRoteins.get(PROTEOMICSDB), repositoryPRoteins.get(HPM)));
        values.put(GPMDB, retrieveMap(repositoryPRoteins.get(GPMDB),repositoryPRoteins.get(PEPTIDEATLAS), repositoryPRoteins.get(MAXQB), repositoryPRoteins.get(PROTEOMICSDB), repositoryPRoteins.get(HPM)));
        values.put(MAXQB, retrieveMap(repositoryPRoteins.get(MAXQB),repositoryPRoteins.get(GPMDB), repositoryPRoteins.get(PEPTIDEATLAS), repositoryPRoteins.get(PROTEOMICSDB), repositoryPRoteins.get(HPM)));
        values.put(PROTEOMICSDB, retrieveMap(repositoryPRoteins.get(PROTEOMICSDB), repositoryPRoteins.get(GPMDB), repositoryPRoteins.get(PEPTIDEATLAS), repositoryPRoteins.get(MAXQB), repositoryPRoteins.get(HPM)));
        values.put(HPM, retrieveMap(repositoryPRoteins.get(HPM),repositoryPRoteins.get(GPMDB), repositoryPRoteins.get(PEPTIDEATLAS), repositoryPRoteins.get(MAXQB), repositoryPRoteins.get(PROTEOMICSDB)));
        for(String key: values.keySet()){
            Map<Integer, Integer> value = values.get(key);
            for(Integer keyInt: value.keySet()){
               output.println(key + ":" + keyInt + ":" + value.get(keyInt));
            }
        }
        output.println("No Identified");
        for(String uniprot: completeUniprot)
            if(!(repositoryPRoteins.get(PEPTIDEATLAS).contains(uniprot) ||
                    repositoryPRoteins.get(GPMDB).contains(uniprot)    ||
                    repositoryPRoteins.get(MAXQB).contains(uniprot)    ||
                    repositoryPRoteins.get(PROTEOMICSDB).contains(uniprot) ||
                    repositoryPRoteins.get(HPM).contains(uniprot))){
                output.println(uniprot);
            }
    }

    public static Map<Integer, Integer> retrieveMap(List<String> toSearch,
                                                 List<String> target1,
                                                 List<String> target2,
                                                 List<String> target3,
                                                 List<String> target4){
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(String protein: toSearch){
            int count = 1;
            if(target1.contains(protein))
                count ++;
            if(target2.contains(protein))
                count++;
            if(target3.contains(protein))
                count++;
            if(target4.contains(protein))
                count++;
            int value = 1;
            if(map.containsKey(count))
                value = map.get(count) + 1;
             map.put(count,value);

        }
        return map;
    }
}
