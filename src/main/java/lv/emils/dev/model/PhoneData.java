package lv.emils.dev.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

/**
 * Created by Emīls on 15.02.2017.
 */
@Component
public class PhoneData {
    private SortedSet<PhoneCode> phoneCodes;

    @PostConstruct
    public void init() throws IOException {
        WikiData wikiData = getPhoneCodesDataFromWikipedia();
        // parse data in PhoneCode POJOs
        putWikiDataInPhoneCodePojos(wikiData);
    }

    WikiData getPhoneCodesDataFromWikipedia() throws IOException {
        String site = "https://en.wikipedia.org/wiki/List_of_country_calling_codes";
        Document doc = Jsoup.connect(site).userAgent("Mozilla").get();
        Element table = doc.select("h2:contains(Alphabetical listing by country or region) + table").get(0);
        Elements rows = table.select("tr");
        List<String> countries = new ArrayList<>();
        List<List<String>> prefixesAll = new ArrayList<>();
        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            countries.add(cols.get(0).text());
            List<String> prefixesForCountry = new ArrayList<>();
            Elements prefixElements = cols.get(1).getElementsByAttribute("title");
            for (Element prefixElement : prefixElements) {
                String[] prefixes = prefixElement.text().replaceAll("\\s", "").split(",");
                prefixesForCountry.addAll(Arrays.asList(prefixes));
            }
            prefixesAll.add(prefixesForCountry);
        }
        return new WikiData(countries, prefixesAll);
    }

    Set<PhoneCode> putWikiDataInPhoneCodePojos(WikiData wikiData) {
        List<List<String>> prefixesAll = wikiData.getPrefixesAll();
        List<String> countries = wikiData.getCountries();
        Set<PhoneCode> phoneCodesUnsorted = new HashSet<>();
        for (int i = 0; i < prefixesAll.size(); i++) {
            for (int j = 0; j < prefixesAll.get(i).size(); j++) {
                String code = prefixesAll.get(i).get(j);
                PhoneCode currentPhoneCode = new PhoneCode(code);
                if (phoneCodesUnsorted.contains(new PhoneCode(code))) {
                    for (Iterator<PhoneCode> it = phoneCodesUnsorted.iterator(); it.hasNext(); ) {
                        PhoneCode phoneCode = it.next();
                        if (phoneCode.equals(new PhoneCode(code)))
                            currentPhoneCode = phoneCode;
                    }
                    for (PhoneCode phoneCode : phoneCodesUnsorted) {
                        if (currentPhoneCode.getCode().equals(phoneCode.getCode())) {
                            phoneCode.getCountries().add(countries.get(i));
                            break;
                        }
                    }
                } else {
                    currentPhoneCode.getCountries().add(countries.get(i));
                    phoneCodesUnsorted.add(currentPhoneCode);
                }
            }
        }
        setPhoneCodes(new TreeSet<>(phoneCodesUnsorted));
        return getPhoneCodes();
    }

    @Override
    public String toString() {
        return "PhoneData{" +
                "phoneCodes=" + putWikiDataInPhoneCodePojos() +
                '}';
    }

    public SortedSet<PhoneCode> putWikiDataInPhoneCodePojos() {
        return getPhoneCodes();
    }

    public void setPhoneCodes(SortedSet<PhoneCode> phoneCodes) {
        this.phoneCodes = phoneCodes;
    }

    public SortedSet<PhoneCode> getPhoneCodes() {
        return phoneCodes;
    }
}

