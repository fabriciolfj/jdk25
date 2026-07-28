package org.example;

import java.util.Date;

public class Test {

    static void main() {
        Date date = new Date(System.currentTimeMillis() - (24 *60 *60 & 1000));

        IO.println(System.currentTimeMillis() - (24 *60 *60 & 1000));

        IO.println(date);
        IO.println(date.getMonth());
    }
}
