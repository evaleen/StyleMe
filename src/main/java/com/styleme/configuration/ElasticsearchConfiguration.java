package com.styleme.configuration;

/**
 *
 * @author Eibhlin McGeady
 *
 * Information requirend for setting up the elasticsearch database
 */
public class ElasticsearchConfiguration {

    private String fashionIndex;
    private String sitesIndex;
    private String mappingsIndex;
    private String styleType;
    private String topshopType;
    private String asosType;
    private String motelType;
    private String styleMapping;
    private String clothingMapping;

    public ElasticsearchConfiguration() {
        this.fashionIndex = "fashion";
        this.sitesIndex = "sites";
        this.mappingsIndex = "mappings";
        this.styleType = "styles";
        this.topshopType = "topshop";
        this.asosType = "asos";
        this.motelType = "motel";
        this.styleMapping = "{\n" +
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
                                        "\"type\": \"string\",\n" +
                                        "\"fields\": {\n" +
                                            "\"raw\": {\n" +
                                                "\"type\": \"string\",\n" +
                                                "\"index\": \"not_analyzed\"\n" +
                                            "}\n" +
                                        "}\n" +
                                    "}\n" +
                                "}\n" +
                            "}";
        this.clothingMapping = "{\n" +
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

    public String getFashionIndex() { return this.fashionIndex; }

    public String getSitesIndex() { return this.sitesIndex; }

    public String getMappingsIndex() { return this.mappingsIndex; }

    public String getStyleType() { return this.styleType; }

    public String getTopshopType() { return this.topshopType; }

    public String getAsosType() { return this.asosType; }

    public String getMotelType() { return this.motelType; }

    public String getStyleMapping() { return this.styleMapping; }

    public String getClothingMapping() { return this.clothingMapping; }
}

