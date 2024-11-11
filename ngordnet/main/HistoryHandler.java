package ngordnet.main;
import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap map;
    public HistoryHandler(NGramMap map){
        this.map = map;
    }
    @Override
    public String handle(NgordnetQuery q){
        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        int startYear = q.startYear();
        int endYear = q.endYear();

        for(String word : q.words()){
            lts.add(new TimeSeries(map.countHistory(word), startYear, endYear));
            labels.add(word);
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);

        return Plotter.encodeChartAsString(chart);
    }
}
