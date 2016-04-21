package configuration;

import com.styleme.configuration.ElasticsearchConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Eibhlin McGeady
 */
public class ElasticsearchConfigurationTest {
    
    private ElasticsearchConfiguration elasticsearchConfiguration;

    private String fashionIndex;
    private String sitesIndex;
    private String mensSitesIndex;
    private String mappingsIndex;
    private String styleType;
    private String topshopType;
    private String newLookType;
    private String motelType;
    private String nastyGalType;
    private String riverIslandType;
    private String oiPolloiType;
    private String styleMapping;
    private String clothingMapping;

    @Before
    public void setUp() {
        elasticsearchConfiguration = new ElasticsearchConfiguration();
        fashionIndex = "fashion";
        sitesIndex = "sites";
        mensSitesIndex = "mens_sites";
        mappingsIndex = "mappings";
        styleType = "styles";
        topshopType = "topshop";
        newLookType = "newLook";
        motelType = "motel";
        nastyGalType = "nastygal";
        riverIslandType = "riverisland";
        oiPolloiType = "oipolloi";
        styleMapping = "{\n" +
                "\"properties\": {\n" +
                "\"style\": {\n" +
                "\"type\": \"string\",\n" +
                "\"fields\": {\n" +
                "\"raw\": {\n" +
                "\"type\": \"string\",\n" +
                "\"index\": \"not_analyzed\"\n" +
                "}\n" +
                "}\n" +
                "},\n" +
                "\"terms\": {\n" +
                "\"type\": \"nested\",\n" +
                "\"properties\": {\n" +
                "\"name\": {\n" +
                "\"type\": \"string\"\n" +
                "},\n" +
                "\"score\": {\n" +
                "\"type\": \"integer\"\n" +
                "}\n" +
                "}\n" +
                "}\n" +
                "}\n" +
                "}";
        clothingMapping = "{\n" +
                "\"properties\": {\n" +
                "\"name\": {\n" +
                "\"type\": \"string\",\n" +
                "\"fields\": {\n" +
                "\"raw\": {\n" +
                "\"type\": \"string\",\n" +
                "\"index\": \"not_analyzed\"\n" +
                "}\n" +
                "}\n" +
                "},\n" +
                "\"description\": {\n" +
                "\"type\": \"string\",\n" +
                "\"fields\": {\n" +
                "\"raw\": {\n" +
                "\"type\": \"string\",\n" +
                "\"index\": \"not_analyzed\"\n" +
                "}\n" +
                "}\n" +
                "},\n" +
                "\"type\": {\n" +
                "\"type\": \"string\",\n" +
                "\"fields\": {\n" +
                "\"raw\": {\n" +
                "\"type\": \"string\",\n" +
                "\"index\": \"not_analyzed\"\n" +
                "}\n" +
                "}\n" +
                "},\n" +
                "\"url\": {\n" +
                "\"type\": \"string\",\n" +
                "\"fields\": {\n" +
                "\"raw\": {\n" +
                "\"type\": \"string\",\n" +
                "\"index\": \"not_analyzed\"\n" +
                "}\n" +
                "}\n" +
                "},\n" +
                "\"image\": {\n" +
                "\"type\": \"string\",\n" +
                "\"fields\": {\n" +
                "\"raw\": {\n" +
                "\"type\": \"string\",\n" +
                "\"index\": \"not_analyzed\"\n" +
                "}\n" +
                "}\n" +
                "},\n" +
                "\"colour\": {\n" +
                "\"type\": \"string\",\n" +
                "\"fields\": {\n" +
                "\"raw\": {\n" +
                "\"type\": \"string\",\n" +
                "\"index\": \"not_analyzed\"\n" +
                "}\n" +
                "}\n" +
                "},\n" +
                "\"currency\": {\n" +
                "\"type\": \"string\",\n" +
                "\"fields\": {\n" +
                "\"raw\": {\n" +
                "\"type\": \"string\",\n" +
                "\"index\": \"not_analyzed\"\n" +
                "}\n" +
                "}\n" +
                "},\n" +
                "\"price\": {\n" +
                "\"type\": \"float\",\n" +
                "\"fields\": {\n" +
                "\"raw\": {\n" +
                "\"type\": \"string\",\n" +
                "\"index\": \"not_analyzed\"\n" +
                "}\n" +
                "}\n" +
                "}\n" +
                "}\n" +
                "}";
    }

    @Test
    public void getFashionIndexTest() {
        assertEquals(fashionIndex, elasticsearchConfiguration.getFashionIndex());
    }

    @Test
    public void getWomensSitesIndexTest() {
        assertEquals(sitesIndex, elasticsearchConfiguration.getSitesIndex("womens"));
    }

    @Test
    public void getNastyGalTypeTest() {
        assertEquals(nastyGalType, elasticsearchConfiguration.getNastyGalType());
    }

    @Test
    public void getRiverIslandTypeTest() {
        assertEquals(riverIslandType, elasticsearchConfiguration.getRiverIslandType());
    }

    @Test
    public void getMensSitesIndexTest() {
        assertEquals(mensSitesIndex, elasticsearchConfiguration.getSitesIndex("mens"));
    }

    @Test
    public void getMappingsIndexTest() {
        assertEquals(mappingsIndex, elasticsearchConfiguration.getMappingsIndex());
    }

    @Test
    public void getStyleTypeTest() {
        assertEquals(styleType, elasticsearchConfiguration.getStyleType());
    }

    @Test
    public void getTopshopTypeTest() {
        assertEquals(topshopType, elasticsearchConfiguration.getTopshopType());
    }

    @Test
    public void getMotelTypeTest() {
        assertEquals(motelType, elasticsearchConfiguration.getMotelType());
    }

    @Test
    public void getNewLookTypeTest() {
        assertEquals(newLookType, elasticsearchConfiguration.getNewLookType());
    }

    @Test
    public void getOiPolloiTypeTest() {
        assertEquals(oiPolloiType, elasticsearchConfiguration.getOiPolloiType());
    }

    @Test
    public void getStyleMappingTest() {
        assertEquals(styleMapping, elasticsearchConfiguration.getStyleMapping());
    }

    @Test
    public void getClothingMappingTest() {
        assertEquals(clothingMapping, elasticsearchConfiguration.getClothingMapping());
    }

    @After
    public void tearDown() {}
}
