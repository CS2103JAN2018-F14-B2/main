package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.PetPatientCardHandle;
import guitests.guihandles.PetPatientListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.person.Person;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.ui.PersonCard;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    private static final String LABEL_DEFAULT_STYLE = "label";

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
        expectedCard.getTags().forEach(tag ->
                assertEquals(expectedCard.getTagStyleClasses(tag),
                        actualCard.getTagStyleClasses(tag)));
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());

        assertTagsEqual(expectedPerson, actualCard);
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPetPatient}.
     */
    public static void assertCardDisplaysPetPatient(PetPatient expectedPetPatient, PetPatientCardHandle actualCard) {
        assertEquals(expectedPetPatient.getName().toString(), actualCard.getName());
        assertEquals(expectedPetPatient.getSpecies(), actualCard.getSpecies());
        assertEquals(expectedPetPatient.getBreed(), actualCard.getBreed());
        assertEquals(expectedPetPatient.getColour(), actualCard.getColour());
        assertEquals(expectedPetPatient.getBloodType(), actualCard.getBloodType());
        assertEquals(expectedPetPatient.getOwner().toString(), actualCard.getOwnerNric());

    }


    /**
     * Returns the color style for {@code tagName}'s label. The tag's color is determined by looking up the color
     * in {@code PersonCard#TAG_COLOR_STYLES}, using an index generated by the hash code of the tag's content.
     * Solution below adopted from :
     * https://github.com/se-edu/addressbook-level4/pull/798/commits/167b3d0b4f7ad34296d2fbf505f9ae71f983f53c
     *
     * @see PersonCard#getTagColorStyleFor(String)
     */
    private static String getTagColorStyleFor(String tagName) {

        switch (tagName) {
        case "classmates":
        case "owesMoney":
            return "teal";

        case "colleagues":
        case "neighbours":
            return "yellow";

        case "family":
        case "friend":
            return "orange";
        case "owner":
        case "friends":
            return "brown";

        case "husband":
            return "grey";

        default:
            fail(tagName + " does not have a color assigned.");
            return "";
        }
    }

    /**
     * Asserts that the tags in {@code actualCard} matches all the tags in {@code expectedPerson} with the correct
     * color.
     */
    private static void assertTagsEqual(Person expectedPerson, PersonCardHandle actualCard) {
        List<String> expectedTags = expectedPerson.getTags().stream()
                .map(tag -> tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getTags());
        expectedTags.forEach(tag ->
                assertEquals(Arrays.asList(LABEL_DEFAULT_STYLE, getTagColorStyleFor(tag)),
                        actualCard.getTagStyleClasses(tag)));
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code petPatientListPanelHandle} displays the details of {@code petPatient} correctly
     * and in the correct order.
     */
    public static void assertListMatching(PetPatientListPanelHandle petPatientListPanelHandle,
                                          PetPatient... petPatient) {
        for (int i = 0; i < petPatient.length; i++) {
            assertCardDisplaysPetPatient(petPatient[i], petPatientListPanelHandle.getPetPatientCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new Person[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
