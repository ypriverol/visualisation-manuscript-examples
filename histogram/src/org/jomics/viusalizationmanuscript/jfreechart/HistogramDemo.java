package org.jomics.viusalizationmanuscript.jfreechart;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import uk.ac.ebi.pride.utilities.util.NumberUtilities;

/**
 * Histogram example of JFreeChart for Visualisation Manuscript.
 */
public class HistogramDemo extends ApplicationFrame {

    public List<Double> noidentified = new ArrayList<Double>();
    public List<Double> uniprot = new ArrayList<Double>();

    public HistogramDemo(String s){
        super(s);
        JPanel jpanel = null;
        try {
            jpanel = createDemoPanel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        jpanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(jpanel);
    }

    private IntervalXYDataset createDataset() throws IOException {
        readGravyNonIdentified();
        HistogramDataset histogramdataset = new HistogramDataset();
        double ad[] = new double[noidentified.size()];
        for (int i = 0; i < noidentified.size(); i++)
            ad[i] = noidentified.get(i);
        histogramdataset.addSeries("No Identified Proteins", ad, 100, -3.4, 3.4);
        ad = new double[uniprot.size()];
        for (int j = 0; j < uniprot.size(); j++)
            ad[j] = uniprot.get(j);
        histogramdataset.addSeries("Identified Proteins", ad, 100, -3.4, 3.4);
        histogramdataset.setType(HistogramType.RELATIVE_FREQUENCY);
        return histogramdataset;
    }

    private JFreeChart createChart(IntervalXYDataset intervalxydataset){
        JFreeChart jfreechart = ChartFactory.createHistogram("Gravy Index Histogram", null, null, intervalxydataset, PlotOrientation.VERTICAL, true, true, false);
        XYPlot xyplot = (XYPlot)jfreechart.getPlot();
        xyplot.setForegroundAlpha(0.85F);
        XYBarRenderer xybarrenderer = (XYBarRenderer)xyplot.getRenderer();
        xybarrenderer.setDrawBarOutline(false);
        return jfreechart;
    }
    public JPanel createDemoPanel() throws IOException {
        JFreeChart jfreechart = createChart(this.createDataset());
        return new ChartPanel(jfreechart);
    }

    public static void main(String args[]) throws IOException {
        HistogramDemo histogramdemo = new HistogramDemo("JFreeChart : HistogramDemo1");
        histogramdemo.pack();
        RefineryUtilities.centerFrameOnScreen(histogramdemo);
        histogramdemo.setVisible(true);
    }

    public void readGravyNonIdentified() throws IOException {

        URL noidentifed = HistogramDemo.class.getClassLoader().getResource("noidentified.csv");
        BufferedReader br = new BufferedReader(new FileReader(noidentifed.getFile()));
        String line;

        while ((line = br.readLine()) != null) {
            if(NumberUtilities.isNumber(line))
                noidentified.add(Double.parseDouble(line));
        }

        noidentifed = getClass().getClassLoader().getResource("uniprot-all.csv");
        br = new BufferedReader(new FileReader(noidentifed.getFile()));

        while ((line = br.readLine()) != null) {
            if(NumberUtilities.isNumber(line))
                uniprot.add(Double.parseDouble(line));
        }

    }
}
