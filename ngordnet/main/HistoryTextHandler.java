package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.ngrams.NGramMap;

import java.util.List;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap map;
    public HistoryTextHandler(NGramMap map){
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        String answer = "";
        for(String word: words){
            answer += word;
            answer += ": ";
            answer += map.countHistory(word).toString();
            answer += "\n";
        }
        return answer;
    }
}
