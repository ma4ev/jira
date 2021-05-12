package org.example.utils;

import org.junit.jupiter.params.provider.Arguments;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.stream.Stream;

public class DataProviderUtils {

    private static final SecureRandom random = new SecureRandom();

    public static Stream<Arguments> getNullAndEmptyListArgumentProvider(){
        return Stream.of(null, Arguments.of(Collections.emptyList()));
    }

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
