package org.example.stablevalue;

import java.awt.print.Printable;
import java.util.List;

public class StableListTest {


    static void main() {
        List<Integer> fiveTimesTable = StableValue.list(11, index -> index * 5);

        IO.print(fiveTimesTable.get(0));
        IO.print(fiveTimesTable.get(1));
        // ...
        IO.print(fiveTimesTable.get(10));
    }

}
