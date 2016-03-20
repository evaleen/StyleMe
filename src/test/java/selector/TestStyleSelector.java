package selector;

import com.styleme.pojos.Clothing;
import com.styleme.selectors.StyleSelector;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * @author Eibhlin McGeady
 */
public class TestStyleSelector {

    private StyleSelector styleSelector;

    private List<Clothing> clothingList;
    private TreeMap<Integer, Set<Clothing>> clothingScores;

    @Before
    public void setUp(){
        styleSelector = new StyleSelector();
        Clothing clothing1 = new Clothing("1");
        Clothing clothing2 = new Clothing("2");
        Clothing clothing3 = new Clothing("3");
        Clothing clothing4 = new Clothing("4");
        clothingScores = new TreeMap<>();
        Set<Clothing> set1 = new HashSet<>();
        Set<Clothing> set2 = new HashSet<>();
        Set<Clothing> set3 = new HashSet<>();
        Set<Clothing> set4 = new HashSet<>();
        set1.add(clothing1);
        set2.add(clothing2);
        set3.add(clothing3);
        set4.add(clothing4);
        clothingScores.put(1232, set4);
        clothingScores.put(555, set3);
        clothingScores.put(343, set2);
        clothingScores.put(132, set1);
        clothingList = new ArrayList<>();
        clothingList.add(clothing1); clothingList.add(clothing2);
        clothingList.add(clothing3); clothingList.add(clothing4);
    }

    @Test
    public void testOrderClothingItems() {
       List<Clothing> result = styleSelector.getSortedClothesList(clothingScores);
        Assert.assertEquals(clothingList.get(0).getId(), result.get(0).getId());
        Assert.assertEquals(clothingList.get(1).getId(), result.get(1).getId());
        Assert.assertEquals(clothingList.get(2).getId(), result.get(2).getId());
        Assert.assertEquals(clothingList.get(3).getId(), result.get(3).getId());
    }

    @After
    public void tearDown() {}

}
