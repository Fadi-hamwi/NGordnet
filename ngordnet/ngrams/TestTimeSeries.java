package ngordnet.ngrams;

import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TestTimeSeries {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertEquals(expectedYears, totalPopulation.years());

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertEquals(expectedTotal.get(i), totalPopulation.data().get(i), 1E-10);
        }
    }
    @Test
    public void timeSeriesCreation(){
        TimeSeries ts = new TimeSeries();
        ts.put(1999, 0.2);
        ts.put(1992, 0.6);
        TimeSeries newClonedTS = new TimeSeries(ts, 1993, 1999);
        TimeSeries actual = new TimeSeries();
        actual.put(1999, 0.2);
        assertEquals(newClonedTS, actual);
    }

    @Test
    public void testYearsFunction(){
        TimeSeries expected = new TimeSeries();
        ArrayList<Integer> actual = new ArrayList<>();

        for(int i=0;i<1000; i++){
            expected.put(i, (double) i);
            actual.add(i);
        }

        assertEquals(expected.years(), actual);

    }

    @Test
    public void testDataFunction(){
        TimeSeries expected = new TimeSeries();
        ArrayList<Double> actual = new ArrayList<>();

        for(int i=0;i<1000; i++){
            expected.put(i, (double) i);
            actual.add((double) i);
        }

        assertEquals(expected.data(), actual);

    }

    @Test
    public void plusFunctionTest(){
        TimeSeries ts1 = new TimeSeries();
        TimeSeries ts2 = new TimeSeries();

        ts1.put(2001, 1.0);
        ts1.put(2000, 2.0);
        ts1.put(1999, 3.0);

        ts2.put(2001, 4.0);
        ts2.put(2010, 11.0);
        ts2.put(1999, -1.0);

        TimeSeries expected = new TimeSeries();
        expected.put(1999, 2.0);
        expected.put(2000, 2.0);
        expected.put(2001, 5.0);
        expected.put(2010, 11.0);

        TimeSeries actual = ts1.plus(ts2);

        assertEquals(expected, actual);
    }


    @Test
    public void dividByFunctionTest(){
        TimeSeries ts1 = new TimeSeries();
        TimeSeries ts2 = new TimeSeries();

        ts1.put(2001, 1.0);
        ts1.put(2000, 2.0);
        ts1.put(1999, 3.0);

        ts2.put(2001, 4.0);
        ts2.put(2010, 11.0);
        ts2.put(1999, -1.0);
        ts2.put(2000, 2.0);

        TimeSeries expected = new TimeSeries();
        expected.put(1999, -3.0);
        expected.put(2000, 1.0);
        expected.put(2001, 0.25);

        TimeSeries actual = ts1.dividedBy(ts2);

        assertEquals(expected, actual);
    }
} 