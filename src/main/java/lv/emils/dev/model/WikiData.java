package lv.emils.dev.model;

import java.util.List;

/**
 * Created by Emīls on 18.02.2017.
 */
public class WikiData {
    private List<String> countries;
    private List<List<String>> prefixesAll;

    public WikiData(List<String> countries, List<List<String>> prefixesAll) {
        this.countries = countries;
        this.prefixesAll = prefixesAll;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<List<String>> getPrefixesAll() {
        return prefixesAll;
    }

    public void setPrefixesAll(List<List<String>> prefixesAll) {
        this.prefixesAll = prefixesAll;
    }
}
