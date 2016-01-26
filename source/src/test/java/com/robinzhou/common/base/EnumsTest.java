package com.robinzhou.common.base;

import org.junit.Test;

import java.lang.ref.WeakReference;

import static com.google.common.truth.Truth.assertThat;


/**
 * Created by robinzhou on 2015/9/23.
 */
public class EnumsTest {

    @Test
    public void testGetIfPresent() {
        assertThat(Enums.getIfPresent(TestEnum.class, "CHEETO")).hasValue(TestEnum.CHEETO);
        assertThat(Enums.getIfPresent(TestEnum.class, "HONDA")).hasValue(TestEnum.HONDA);
        assertThat(Enums.getIfPresent(TestEnum.class, "POODLE")).hasValue(TestEnum.POODLE);

        assertThat(Enums.getIfPresent(TestEnum.class, "CHEETO")).isPresent();
        assertThat(Enums.getIfPresent(TestEnum.class, "HONDA")).isPresent();
        assertThat(Enums.getIfPresent(TestEnum.class, "POODLE")).isPresent();

        assertThat(Enums.getIfPresent(TestEnum.class, "CHEETO")).hasValue(TestEnum.CHEETO);
        assertThat(Enums.getIfPresent(TestEnum.class, "HONDA")).hasValue(TestEnum.HONDA);
        assertThat(Enums.getIfPresent(TestEnum.class, "POODLE")).hasValue(TestEnum.POODLE);
    }

    @Test
    public void testGetIfPresent_caseSensitive() {
        assertThat(Enums.getIfPresent(TestEnum.class, "cHEETO")).isAbsent();
        assertThat(Enums.getIfPresent(TestEnum.class, "Honda")).isAbsent();
        assertThat(Enums.getIfPresent(TestEnum.class, "poodlE")).isAbsent();
    }

    @Test
    public void testGetIfPresent_whenNoMatchingConstant() {
        assertThat(Enums.getIfPresent(TestEnum.class, "WOMBAT")).isAbsent();
    }

//    @Test
//    public void testGetIfPresent_doesNotPreventClassUploading() throws Exception {
//        WeakReference<?> shadowLoaderReference = doTestClassUnloading
//    }



    private enum TestEnum {
        CHEETO,
        HONDA,
        POODLE,
    }

    private enum OtherEnum {
    }
}
