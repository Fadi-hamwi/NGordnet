package ngordnet.ngrams;

import java.lang.reflect.Array;
import java.util.*;

/** An object for mapping a year number (e.g. 1996) to numerical data. Provides
 *  utility methods useful for data analysis.
 *  @author Fadi Hamwi
 */
public class TimeSeries extends TreeMap<Integer, Double> {
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     *  inclusive of both end points. */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        if(ts == null) return;
        for(Map.Entry<Integer, Double> entry : ts.entrySet()){
            if(entry.getKey() != null && entry.getKey() >= startYear && entry.getKey() <= endYear){
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    /** Returns all years for this TimeSeries (in any order). */
    public List<Integer> years() {
        Set<Integer> tempSet = this.keySet();

        return new ArrayList<>(tempSet);
    }

    /** Returns all data for this TimeSeries (in any order).
     *  Must be in the same order as years(). */
    public List<Double> data() {
        Collection<Double> tempCollection = this.values();

        return new ArrayList<>(tempCollection);
    }

    /** Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     *  each year, sum the data from this TimeSeries with the data from TS. Should return a
     *  new TimeSeries (does not modify this TimeSeries). */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries answer = new TimeSeries(ts, 1400, 2100);
        for(Map.Entry<Integer, Double> entry : this.entrySet()){
            if(answer.containsKey(entry.getKey())){
                answer.merge(entry.getKey(), entry.getValue(), Double::sum);
            }else{
                answer.put(entry.getKey(), entry.getValue());
            }
        }
        return answer;
    }

     /** Returns the quotient of the value for each year this TimeSeries divided by the
      *  value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
      *  throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
      *  Should return a new TimeSeries (does not modify this TimeSeries). */
     public TimeSeries dividedBy(TimeSeries ts) {
         TimeSeries answer = new TimeSeries();
         for(Map.Entry<Integer, Double> entry : this.entrySet()){
             if(ts.containsKey(entry.getKey())) {
                 answer.put(entry.getKey(), entry.getValue() / ts.get(entry.getKey()));
             } else {
                 throw new IllegalArgumentException();
             }
         }
         return answer;
     }


}
