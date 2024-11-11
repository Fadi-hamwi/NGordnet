package ngordnet.ngrams;

import java.sql.Time;
import java.util.Collection;
import java.util.HashMap;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.mockito.internal.verification.Times;

import javax.sound.midi.Soundbank;

/** An object that provides utility methods for making queries on the
 *  Google NGrams dataset (or a subset thereof).
 *
 *  An NGramMap stores pertinent data from a "words file" and a "counts
 *  file". It is not a map in the strict sense, but it does provide additional
 *  functionality.
 *
 *  @author Fadi Hamwi
 */
public class NGramMap {
    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    private TimeSeries countTimeSeries = new TimeSeries();
    private HashMap<String, TimeSeries> wordTimeSeries = new HashMap<>();
    public NGramMap(String wordsFilename, String countsFilename) {
        In countsFile = new In(countsFilename);
        In wordsFile = new In(wordsFilename);

        // Reading the countsFilename has four columns last two ignore.
        while(countsFile.hasNextLine()) {
            String line = countsFile.readLine();
            String[] values = line.split(",");
            int year = Integer.parseInt(values[0]);
            double countWord = Double.parseDouble(values[1]);
            countTimeSeries.put(year, countWord);
        }

        while(wordsFile.hasNextLine()){
            String line = wordsFile.readLine();
            String[] values = line.split("\t");
            String word = values[0];
            int year = Integer.parseInt(values[1]);
            double wordOccurrence = Double.parseDouble(values[2]);

            TimeSeries val = wordTimeSeries.get(word);
            if(val == null){
                val = new TimeSeries();
            }
            val.put(year, wordOccurrence);
            wordTimeSeries.put(word, val);
        }
    }

    /** Provides the history of WORD. The returned TimeSeries should be a copy,
     *  not a link to this NGramMap's TimeSeries. In other words, changes made
     *  to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word) {
        return new TimeSeries(wordTimeSeries.get(word), 1400, 2100);
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     *  returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     *  changes made to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        return new TimeSeries(countHistory(word), startYear, endYear);
    }

    /** Returns a defensive copy of the total number of words recorded per year in all volumes. */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(countTimeSeries, 1400, 2100);
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD compared to
     *  all words recorded in that year. */
    public TimeSeries weightHistory(String word) {
        return countHistory(word).dividedBy(countTimeSeries);
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     *  and ENDYEAR, inclusive of both ends. */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        return new TimeSeries(weightHistory(word), startYear, endYear);
    }

    /** Returns the summed relative frequency per year of all words in WORDS. */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries sum = new TimeSeries();
        for (String word: words) {
            sum = sum.plus(countHistory(word));
        }
        return sum.dividedBy(countTimeSeries);
    }

    /** Provides the summed relative frequency per year of all words in WORDS
     *  between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     *  this time frame, ignore it rather than throwing an exception. */
    public TimeSeries summedWeightHistory(Collection<String> words,
                              int startYear, int endYear) {
        return new TimeSeries(summedWeightHistory(words), startYear, endYear);
    }
}
