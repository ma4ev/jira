package org.example.utils;

import org.junit.jupiter.params.provider.Arguments;

import java.util.Collections;
import java.util.stream.Stream;

public class DataProviderUtils {

    public static Stream<Arguments> getNullAndEmptyListArgumentProvider(){
        return Stream.of(null, Arguments.of(Collections.emptyList()));
    }
}
