package kvbdev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class PersonTest {

    @Test
    void newChildBuilder() {
        Person p = new Person("name", "surname");
        assertThat(p.newChildBuilder(), isA(PersonBuilder.class));
    }

    @Test
    void testToString() {
        String testName = "TEST_NAME";
        String testSurname = "TEST_SURNAME";
        Integer testAge = 18;
        String testAddress = "TEST_ADDRESS";

        Person p = new Person(testName, testSurname, testAge, testAddress);
        String toStringStr = p.toString();

        assertThat(toStringStr, containsString(testName));
        assertThat(toStringStr, containsString(testSurname));
        assertThat(toStringStr, containsString(testAge.toString()));
        assertThat(toStringStr, containsString(testAddress));
    }

    @ParameterizedTest
    @MethodSource("equalsPersonSource")
    void testEquals_success(Person person1, Person person2) {
        assertThat(person1, equalTo(person2));
        assertThat(person2, equalTo(person1));
    }

    public static Stream<Arguments> equalsPersonSource() {
        return Stream.of(
                Arguments.of(new Person("name", "surname"), new Person("name", "surname")),
                Arguments.of(new Person("name", "surname", 18, null), new Person("name", "surname", 18, null)),
                Arguments.of(new Person("name", "surname", 18, "addr"), new Person("name", "surname", 18, "addr"))
        );
    }

    @ParameterizedTest
    @MethodSource("nonEqualsPersonSource")
    void testEquals_failure(Person person1, Person person2) {
        assertThat(person1, not(equalTo(person2)));
        assertThat(person2, not(equalTo(person1)));
    }

    public static Stream<Arguments> nonEqualsPersonSource() {
        return Stream.of(
                Arguments.of(new Person("name1", "surname1"), new Person("name2", "surname2")),
                Arguments.of(new Person("name1", "surname1", 18, null), new Person("name2", "surname2", 19, null)),
                Arguments.of(new Person("name1", "surname1", 18, "addr1"), new Person("name2", "surname2", 19, "addr2"))
        );
    }

    @ParameterizedTest
    @MethodSource("equalsPersonSource")
    void testHashCode_success(Person person1, Person person2) {
        Person constPerson3 = new Person("TEST_NAME_P3", "TEST_SURNAME_P3");
        assertThat(person1.hashCode(), not(equalTo(constPerson3)));
        assertThat(person2.hashCode(), not(equalTo(constPerson3)));
        assertThat(person1.hashCode(), equalTo(person2.hashCode()));
    }

    @Test
    void getName() {
        String testName = "TEST_NAME";
        Person p = new Person(testName, "surname");
        assertThat(p.getName(), equalTo(testName));
    }

    @Test
    void getSurname() {
        String testSurname = "TEST_SURNAME";
        Person p = new Person("name", testSurname);
        assertThat(p.getSurname(), equalTo(testSurname));
    }

    @ParameterizedTest
    @MethodSource("hasAge_success_source")
    void hasAge_success(Integer age) {
        Person p = new Person("name", "surname", age, null);
        assertThat(p.hasAge(), is(true));
    }

    public static Stream<Arguments> hasAge_success_source() {
        return Stream.of(0, 1, 10, 100, 1000)
                .map(Arguments::of);
    }

    @ParameterizedTest
    @NullSource
    @MethodSource("hasAge_failure_source")
    void hasAge_failure(Integer age) {
        Person p = new Person("name", "surname", age, null);
        assertThat(p.hasAge(), is(false));
    }

    public static Stream<Arguments> hasAge_failure_source() {
        return Stream.of(-100, - 10, -1)
                .map(Arguments::of);
    }

    @Test
    void getAge_success() {
        Integer testAge = 18;
        Person p = new Person("name", "surname", testAge, null);
        assertThat(p.getAge().orElseThrow(), equalTo(testAge));
    }

    @Test
    void getAge_failure() {
        Person p = new Person("name", "surname");
        assertThat(p.getAge().isPresent(), equalTo(false));
    }

    @Test
    void happyBirthday_success() {
        Integer initAge = 18;
        Integer expectedAge = 19;
        Person p = new Person("name", "surname", initAge, null);
        p.happyBirthday();
        assertThat(p.getAge().orElseThrow(), equalTo(expectedAge));
    }

    @Test
    void happyBirthday_failure() {
        Person p = new Person("name", "surname");
        p.happyBirthday();
        assertThat(p.getAge().isPresent(), equalTo(false));
    }

    @Test
    void hasAddress_true() {
        String testAddress = "TEST_ADDRESS";
        Person p = new Person("name", "surname", null, testAddress);
        assertThat(p.hasAddress(), is(true));
    }

    @Test
    void hasAddress_false() {
        Person p = new Person("name", "surname");
        assertThat(p.hasAddress(), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST_ADDRESS"})
    void getAddress_value(String testAddress) {
        Person p = new Person("name", "surname", null, testAddress);
        assertThat(p.getAddress().get(), equalTo(testAddress));
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    void getAddress_empty(String testAddress) {
        Person p = new Person("name", "surname", null, testAddress);
        assertThat(p.getAddress().isEmpty(), is(true));
    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST_ADDRESS"})
    void setAddress_success(String testAddress) {
        Person p = new Person("name", "surname");
        p.setAddress(testAddress);
        assertThat(p.getAddress().get(), equalTo(testAddress));
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    void setAddress_failure(String testAddress) {
        Person p = new Person("name", "surname");
        p.setAddress(testAddress);
        assertThat(p.getAddress().isEmpty(), is(true));
    }

}