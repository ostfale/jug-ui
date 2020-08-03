package de.ostfale.jug.beui.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.ostfale.jug.beui.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for JSON mapper
 * Created :  01.08.2020
 *
 * @author : Uwe Sauerbrei
 */
class JsonMapperTest {

    private ObjectMapper objectMapper;
    private Person person;
    private List<Person> personList;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        person = new Person("1", "Max", "Schneider", "mschneider@email.com", "0188774433");
    }

    @Test
    @DisplayName("Deserialize JSON string into class object")
    public void TestDeserializationOfObject() throws JsonProcessingException {
        // given
        String jsonString = objectMapper.writeValueAsString(person);
        // when
        final Optional<Person> mappedPerson = JsonMapper.jsonToObject(jsonString, Person.class);
        // then
        assertTrue(mappedPerson.isPresent());
        person = mappedPerson.get();
        assertEquals("1", person.getId());
        assertEquals("Max", person.getFirstName());
        assertEquals("Schneider", person.getLastName());
        assertEquals("mschneider@email.com", person.getEmail());
        assertEquals("0188774433", person.getPhone());
    }

    @Test
    @DisplayName("Serialize class object into JSON string")
    public void TestSerializationObject() {
        // when
        final Optional<String> json = JsonMapper.objectToJson(person);
        // then
        assertTrue(json.isPresent());
        final String personString = json.get();
        assertTrue(personString.contains("Max"));
        assertTrue(personString.contains("Schneider"));
        assertTrue(personString.contains("mschneider@email.com"));
        assertTrue(personString.contains("0188774433"));
    }
}