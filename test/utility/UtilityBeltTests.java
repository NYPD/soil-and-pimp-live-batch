package utility;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.Test;

import live.soilandpimp.batch.domain.Schedule;
import live.soilandpimp.batch.util.UtilityBelt;

public class UtilityBeltTests {

    @Test
    public void shouldCompareTwoListWithSameContents() throws Exception {

        Schedule mock = mock(Schedule.class);

        ArrayList<Schedule> list1 = new ArrayList<>();
        ArrayList<Schedule> list2 = new ArrayList<>();

        list1.add(mock);
        list2.add(mock);

        boolean sameContents = UtilityBelt.compareListContents(list1, list2);

        assertThat(sameContents, is(true));

    }

    @Test
    public void shouldCompareTwoListWithDiffrentContents() throws Exception {

        Schedule mock = mock(Schedule.class);
        Schedule mock2 = mock(Schedule.class);

        ArrayList<Schedule> list1 = new ArrayList<>();
        ArrayList<Schedule> list2 = new ArrayList<>();

        list1.add(mock);

        list2.add(mock);
        list2.add(mock2);

        boolean sameContents = UtilityBelt.compareListContents(list1, list2);

        assertThat(sameContents, is(false));

    }
}
