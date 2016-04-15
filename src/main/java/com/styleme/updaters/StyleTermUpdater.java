package com.styleme.updaters;

import com.styleme.pojos.Style;
import com.styleme.retrievers.ElasticsearchRetriever;
import com.styleme.submitters.ElasticsearchSubmitter;

import java.util.List;
import java.util.Map;

/**
 * @author Eibhlin McGeady
 */
public class StyleTermUpdater {

    private ElasticsearchRetriever elasticsearchRetriever;
    private ElasticsearchSubmitter elasticsearchSubmitter;

    public StyleTermUpdater() {
        this.elasticsearchRetriever = new ElasticsearchRetriever();
        this.elasticsearchSubmitter = new ElasticsearchSubmitter();
    }

    public void incrementStyleTerms(String styleName, List<String> terms) {
        Style style = elasticsearchRetriever.getStyle(styleName);
        for(Map<String, Integer> term : style.getTerms()) {
            for(String incTerm : terms) {
                if(term.containsKey(incTerm)) {
                    term.put(incTerm, term.get(incTerm)+1);
                }
            }
        }
        elasticsearchSubmitter.postStyle(style);

    }

    public void decrementTerms(String styleName, List<String> terms) {
        Style style = elasticsearchRetriever.getStyle(styleName);
        for(Map<String, Integer> term : style.getTerms()) {
            for(String incTerm : terms) {
                if(term.containsKey(incTerm)) {
                    int score = term.get(incTerm);
                    if(score > 0) term.put(incTerm, score - 1);
                }
            }
        }
        elasticsearchSubmitter.postStyle(style);
    }
}
