package live.soilandpimp.batch.util;

import java.util.HashSet;
import java.util.List;

public class UtilityBelt {

    public static <T> boolean compareListContents(List<T> list1, List<T> list2) {

        if (list1 == list2) return true;
        if (list1 == null) return false;

        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }
}
