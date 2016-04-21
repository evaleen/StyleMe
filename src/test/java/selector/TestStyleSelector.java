package selector;

import com.styleme.pojos.Clothing;
import com.styleme.pojos.Style;
import com.styleme.selectors.StyleSelector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * @author Eibhlin McGeady
 */
public class TestStyleSelector {

    private StyleSelector styleSelector;

    private Style style;
    private List<Clothing> clothingList;
    private List<Clothing> topThree;
    private List<String> incTerms;
    private List<String> decTerms;
    private List<Clothing> returnedList;

    @Before
    public void setUp(){
        style = new Style();
        style.setStyle("preppy");
        Set<Map<String, Integer>> terms = new HashSet<>();
        Map<String, Integer> term1 = new HashMap<>();
        Map<String, Integer> term2 = new HashMap<>();
        term1.put("fresh", 1);
        term2.put("uni", 1);
        terms.add(term1);
        terms.add(term2);
        style.setTerms(terms);
        styleSelector = new StyleSelector();

        Clothing clothing1 = new Clothing("1");
        clothing1.setName("clothing 1"); clothing1.setDescription("fresh uni preppy");
        Clothing clothing2 = new Clothing("2");
        clothing2.setName("clothing 2"); clothing2.setDescription("preppy");
        Clothing clothing3 = new Clothing("3");
        clothing3.setName("clothing 3"); clothing3.setDescription("fresh uni");
        Clothing clothing4 = new Clothing("4");
        clothing4.setName("clothing 4"); clothing4.setDescription("fresh summer young");

        clothingList = new ArrayList<>();
        clothingList.add(clothing1); clothingList.add(clothing2);
        clothingList.add(clothing3); clothingList.add(clothing4);

        topThree = new ArrayList<>();
        topThree.add(clothing1); topThree.add(clothing2); topThree.add(clothing3);

        incTerms = Arrays.asList("fresh", "summer", "young");
        decTerms = Arrays.asList("uni");

        returnedList = new ArrayList<>();
        returnedList.add(clothing1); returnedList.add(clothing4);
        returnedList.add(clothing2); returnedList.add(clothing3);
    }


    @Test
    public void getClothingSuggestionsForStyleTest() {
        List<Clothing> suggestedClothing = styleSelector.getClothingSuggestionsForStyle(style, clothingList);
        assertEquals(suggestedClothing, topThree);


    }

    @Test
    public void getClothingWithUpdatedScoresTest() {
        styleSelector.getClothingSuggestionsForStyle(style, clothingList);
        List<Clothing> returnedClothing = styleSelector.getClothingWithUpdatedScores(style, clothingList, incTerms, decTerms);
        assertEquals(returnedClothing, returnedList);

    }

    @After
    public void tearDown() {}

}
