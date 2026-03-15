package seedu.goldencompass.set_operation;

import java.util.HashSet;
import java.util.Set;

public class SetOperation {
    public static <E> Set<E> difference(Set<E> setA, Set<E> setB) {
        Set<E> setResult = new HashSet<>(setA);
        setResult.removeAll(setB);
        return setResult;
    }
}
