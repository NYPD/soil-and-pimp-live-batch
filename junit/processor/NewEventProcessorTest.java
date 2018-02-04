package processor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class NewEventProcessorTest {

    @BeforeAll
    public void setup() {

    }

    @Test
    public void shouldResultInNewEvent() {
        assertEquals(2, 1 + 1);
    }
}
