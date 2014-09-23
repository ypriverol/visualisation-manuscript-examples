package org.jomics.viusalizationmanuscript.jfreechart;

import org.jomics.proteomics.databases.jfasta.FastaReader;
import org.jomics.proteomics.databases.jfasta.model.DBFastaSequence;
import uk.ac.ebi.pride.utilities.mol.GraviUtilities;

import java.io.*;
import java.text.DecimalFormat;
/**
 * Created by yperez on 22/09/2014.
 */
public class createDataForHistogram {

    public static void main(String[] args) throws IOException {
        PrintStream output = new PrintStream(args[1]);
        output.println("Gravy");


        org.jomics.proteomics.databases.jfasta.FastaReader fastaReader = new FastaReader(new File(args[0]),"r");
        fastaReader.readFastaFile();
        for(String id: fastaReader.getDBFastaSequenceIds()){
            DBFastaSequence sequence = fastaReader.get(id);
            Double gravy = GraviUtilities.calculate(sequence.getSequence());
            output.println(customFormat("#.#",gravy));
        }
    }

    static public String customFormat(String pattern, double value ) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(value);
    }

}
