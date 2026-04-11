package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.person.Person.MATCH_FIELD_FACEBOOK;
import static seedu.address.model.person.Person.MATCH_FIELD_INSTAGRAM;
import static seedu.address.model.person.Person.MATCH_FIELD_PHONE;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DuplicateContactMatcherTest {

    @Test
    public void findWarning_noMatches_empty() {
        Person noMatch = new PersonBuilder()
                .withName("Independent Customer")
                .withPhone("99998888")
                .withFacebook("independent.fb")
                .withInstagram("independent_ig")
                .withAddress("1 Independent Street")
                .build();

        assertTrue(DuplicateContactMatcher.findWarning(noMatch, List.of(ALICE)).isEmpty());
    }

    @Test
    public void findWarning_singleMatch_returnsMatchedField() {
        Person samePhone = new PersonBuilder()
                .withName("Twin")
                .withPhone(ALICE.getPhone().orElseThrow().toString())
                .withoutFacebook()
                .withoutInstagram()
                .withoutAddress()
                .build();

        Set<String> warningFields = DuplicateContactMatcher.findWarning(samePhone, List.of(ALICE)).orElseThrow();
        assertEquals(Set.of(MATCH_FIELD_PHONE), warningFields);
    }

    @Test
    public void findWarning_multipleMatches_returnsUnionOfFields() {
        Person sameFacebook = new PersonBuilder()
                .withName("Same Facebook")
                .withFacebook(ALICE.getFacebook().orElseThrow().toString())
                .withoutPhone()
                .withoutInstagram()
                .withoutAddress()
                .build();

        Person sameInstagram = new PersonBuilder()
                .withName("Same Instagram")
                .withInstagram(ALICE.getInstagram().orElseThrow().toString())
                .withoutPhone()
                .withoutFacebook()
                .withoutAddress()
                .build();

        Set<String> warningFields = DuplicateContactMatcher.findWarning(ALICE, List.of(sameFacebook, sameInstagram))
                .orElseThrow();
        assertEquals(Set.of(MATCH_FIELD_FACEBOOK, MATCH_FIELD_INSTAGRAM), warningFields);
    }
}
