package uj.java.w3;

import java.util.List;
import java.util.Objects;
import java.util.Iterator;
import java.util.Collections;
import java.util.ArrayList;

class ListMerger {
    public static List<Object> mergeLists(List<?> list1, List<?> list2) {
        List<Object> objectList = new ArrayList<>();

        Iterator<?> iterableList1 = Objects.requireNonNullElse(list1, objectList).listIterator();
        Iterator<?> iterableList2 = Objects.requireNonNullElse(list2, objectList).listIterator();

        while (iterableList1.hasNext() || iterableList2.hasNext()) {
            if (list1 != null && iterableList1.hasNext())
                objectList.add(iterableList1.next());
            if (list2 != null && iterableList2.hasNext())
                objectList.add(iterableList2.next());
        }
        return Collections.unmodifiableList(objectList);
    }
}
