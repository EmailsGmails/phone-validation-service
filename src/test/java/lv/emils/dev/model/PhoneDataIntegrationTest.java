package lv.emils.dev.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Emīls on 19.02.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneDataIntegrationTest {

    private WikiData wikiData;
    private SortedSet<PhoneCode> phoneCodesTestData;
    Set<PhoneCode> phoneDataFromWiki;

    @Autowired
    private PhoneData phoneData;

    @Before
    public void setUp() throws IOException {
        wikiData = phoneData.getPhoneCodesDataFromWikipedia();
        phoneCodesTestData = new TreeSet<>();
        phoneCodesTestData = new TreeSet<>();
        phoneCodesTestData.add(new PhoneCode("+6189164", new HashSet<>(Arrays.asList("Christmas Island"))));
        phoneCodesTestData.add(new PhoneCode("+1234", new HashSet<String>(Arrays.asList("Estonia"))));
        phoneDataFromWiki = phoneData.putWikiDataInPhoneCodePojos(wikiData);
    }

    @Test
    public void testGetPhoneCodesDataFromWikipedia() {
        assertThat(wikiData.getCountries().get(0)).isEqualTo("Abkhazia");
        assertThat(wikiData.getPrefixesAll().get(0)).isEqualTo(new ArrayList<>(Arrays.asList("+7840", "+7940", "+99544")));
    }

    @Test
    public void testPutWikiDataInPhoneCodePojosValidMatch() {
        Iterator iter = phoneDataFromWiki.iterator();
        Object firstPhoneCodeFromWiki = iter.next();
        iter = phoneCodesTestData.iterator();
        Object firstPhoneCodeFromTestData = iter.next();
        assertThat(firstPhoneCodeFromWiki).isEqualTo(firstPhoneCodeFromTestData);
    }

    @Test
    public void testPutWikiDataInPhoneCodePojosInvalidMatch() {
        Iterator iter = phoneDataFromWiki.iterator();
        Object firstPhoneCodeFromWiki = iter.next();
        Object secondPhoneCodeFromWiki = iter.next();
        iter = phoneCodesTestData.iterator();
        Object firstPhoneCodeFromTestData = iter.next();
        Object secondPhoneCodeFromTestData = iter.next();
        assertThat(secondPhoneCodeFromWiki).isNotEqualTo(secondPhoneCodeFromTestData);
    }
}
