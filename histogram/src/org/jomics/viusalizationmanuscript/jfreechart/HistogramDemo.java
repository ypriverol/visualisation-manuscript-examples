package org.jomics.viusalizationmanuscript.jfreechart;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * Histogram example of JFreeChart for Visuazliation Manuscript
 */
public class HistogramDemo extends ApplicationFrame {

    public HistogramDemo(String s){
        super(s);
        JPanel jpanel = createDemoPanel();
        jpanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(jpanel);
    }

    private static IntervalXYDataset createDataset(){
        HistogramDataset histogramdataset = new HistogramDataset();
        double ad[] = new double[1000];
        Random random = new Random(0xbc614eL);
        for (int i = 0; i < 1000; i++)
            ad[i] = random.nextGaussian() + 5D;
        histogramdataset.addSeries("H1", ad, 100, 2D, 8D);
        ad = new double[1000];
        for (int j = 0; j < 1000; j++)
            ad[j] = random.nextGaussian() + 7D;
        histogramdataset.addSeries("H2", ad, 100, 4D, 10D);
        return histogramdataset;
    }

    private static JFreeChart createChart(IntervalXYDataset intervalxydataset){
        JFreeChart jfreechart = ChartFactory.createHistogram("Histogram Demo 1", null, null, intervalxydataset, PlotOrientation.VERTICAL, true, true, false);
        XYPlot xyplot = (XYPlot)jfreechart.getPlot();
        xyplot.setForegroundAlpha(0.85F);
        XYBarRenderer xybarrenderer = (XYBarRenderer)xyplot.getRenderer();
        xybarrenderer.setDrawBarOutline(false);
        return jfreechart;
    }
    public static JPanel createDemoPanel(){
        JFreeChart jfreechart = createChart(createDataset());
        return new ChartPanel(jfreechart);
    }

    public static void main(String args[]) throws IOException {
        HistogramDemo histogramdemo = new HistogramDemo("JFreeChart : HistogramDemo1");
        histogramdemo.pack();
        RefineryUtilities.centerFrameOnScreen(histogramdemo);
        histogramdemo.setVisible(true);
    }
}
