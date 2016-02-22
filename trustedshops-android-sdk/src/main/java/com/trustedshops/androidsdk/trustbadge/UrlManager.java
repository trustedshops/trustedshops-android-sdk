package com.trustedshops.androidsdk.trustbadge;


import java.util.HashMap;

public class UrlManager {

    private static HashMap<String, HashMap> urlList
            = new HashMap<String, HashMap>() {{
        put("DEU_de", new HashMap(){{ put("reviewProfile", "https://www.trustedshops.de/bewertung/info_%s.html"); }});
        put("AUT_de", new HashMap(){{ put("reviewProfile", "https://www.trustedshops.at/bewertung/info_%s.html"); }});
        put("CHE_de", new HashMap(){{ put("reviewProfile", "https://www.trustedshops.ch/bewertung/info_%s.html"); }});
        put("CHE_fr", new HashMap(){{ put("reviewProfile", "https://www.trustedshops.fr/evaluation/info_%s.html"); }});
        put("FRA_fr", new HashMap(){{ put("reviewProfile", "https://www.trustedshops.fr/evaluation/info_%s.html"); }});
        put("GBR_en", new HashMap(){{ put("reviewProfile", "https://www.trustedshops.co.uk/buyerrating/info_%s.html"); }});
        put("POL_pl", new HashMap(){{ put("reviewProfile", "https://www.trustedshops.pl/opinia/info_%s.html"); }});
        put("ESP_es", new HashMap(){{ put("reviewProfile", "https://www.trustedshops.es/evaluacion/info_%s.html"); }});
        put("NLD_nl", new HashMap(){{ put("reviewProfile", "https://www.trustedshops.nl/verkopersbeoordeling/info_%s.html"); }});
        put("BEL_fr", new HashMap(){{ put("reviewProfile", "https://www.trustedshops.be/fr/evaluation/info_%s.html"); }});
        put("BEL_nl", new HashMap(){{ put("reviewProfile", "https://www.trustedshops.be/nl/verkopersbeoordeling/info_%s.html"); }});
        put("ITA_it", new HashMap(){{ put("reviewProfile", "https://www.trustedshops.it/valutazione-del-negozio/info_%s.html"); }});
        put("EUO_en", new HashMap(){{ put("reviewProfile", "https://www.trustedshops.com/buyerrating/info_%s.html"); }});

    }};

    public static String getShopProfileUrl(Shop shop) {
        String urlListKey = getUrlListKeyFromCountryCodeAndLanguage(shop.get_targetMarketISO3(), shop.get_languageISO2());

        if (urlList.containsKey(urlListKey) && urlList.get(urlListKey).containsKey("reviewProfile")) {
            return String.format(urlList.get(urlListKey).get("reviewProfile").toString(), shop.get_tsId());
        }
        return String.format(urlList.get("EUO_en").get("reviewProfile").toString(), shop.get_tsId());
    }

    protected static String getUrlListKeyFromCountryCodeAndLanguage(String targetMarketISO3, String languageISO2) {
        return targetMarketISO3.toUpperCase()+"_"+languageISO2.toLowerCase();
    }

    public static String getTrustMarkAPIUrl(String tsId) {
        return String.format("https://api.trustedshops.com/rest/internal/v2/shops/%s/trustmarks.json", tsId);
    }
}
