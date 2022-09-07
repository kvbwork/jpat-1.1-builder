package kvbdev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.*;

class PersonBuilderTest {

    @ParameterizedTest
    @EmptySource
    @NullSource
    void setName_exception(String name) {
        PersonBuilder sut = new PersonBuilder();
        assertThrowsExactly(IllegalArgumentException.class, () -> sut.setName(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"LatinName", "ИмяРус"})
    void setName_success(String name) {
        PersonBuilder sut = new PersonBuilder();
        sut.setName(name);
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    void setSurname_exception(String name) {
        PersonBuilder sut = new PersonBuilder();
        assertThrowsExactly(IllegalArgumentException.class, () -> sut.setSurname(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"LatinSurname", "ФамилияРус"})
    void setSurname_success(String name) {
        PersonBuilder sut = new PersonBuilder();
        sut.setSurname(name);
    }


    @ParameterizedTest
    @ValueSource(ints = {-1000, -100, -10, -1, 151, 1000, 10_000})
    void setAge_exception(int age) {
        PersonBuilder sut = new PersonBuilder();
        assertThrowsExactly(IllegalArgumentException.class, () -> sut.setAge(age));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 50, 99, 100, 125, 150})
    void setAge_success(int age) {
        PersonBuilder sut = new PersonBuilder();
        sut.setAge(age);
    }


    @Test
    void build_exception_name() {
        PersonBuilder sut = new PersonBuilder();
        assertThrowsExactly(IllegalStateException.class, () -> sut.build());
    }

    @Test
    void build_exception_surname() {
        PersonBuilder sut = new PersonBuilder();
        sut.setName("TESTNAME");
        assertThrowsExactly(IllegalStateException.class, () -> sut.build());
    }

    @Test
    void build_success_full() {
        PersonBuilder sut = new PersonBuilder();
        sut.setName("TEST_NAME");
        sut.setSurname("TEST_SURNAME");
        sut.setAge(1);
        sut.setAddress("TEST_CITY");
        assertThat(sut.build(), isA(Person.class));
    }


}