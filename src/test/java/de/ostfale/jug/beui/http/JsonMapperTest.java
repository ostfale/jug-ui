package de.ostfale.jug.beui.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.ostfale.jug.beui.common.JsonMapper;
import de.ostfale.jug.beui.person.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for JSON mapper
 * Created :  01.08.2020
 *
 * @author : Uwe Sauerbrei
 */
class JsonMapperTest {

    private ObjectMapper objectMapper;
    private Person person;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        person = new Person("Max", "Schneider", "mschneider@email.com", "0188774433","My Bio");
    }

    @Test
    @DisplayName("Deserialize JSON string into class object")
    public void TestDeserializationOfObject() throws JsonProcessingException {
        // given
        String jsonString = objectMapper.writeValueAsString(person);
        // when
        final Person mappedPerson = JsonMapper.jsonToObject(jsonString, Person.class);
        // then
        assertNotNull(mappedPerson);
        assertEquals("Max", person.getFirstName());
        assertEquals("Schneider", person.getLastName());
        assertEquals("mschneider@email.com", person.getEmail());
        assertEquals("0188774433", person.getPhone());
    }

    @Test
    @DisplayName("Serialize class object into JSON string")
    public void TestSerializationObject() throws JsonProcessingException {
        // when
        final String json = JsonMapper.objectToJson(person);
        // then
        assertFalse(json.isEmpty());
        assertTrue(json.contains("Max"));
        assertTrue(json.contains("Schneider"));
        assertTrue(json.contains("mschneider@email.com"));
        assertTrue(json.contains("0188774433"));
    }
}