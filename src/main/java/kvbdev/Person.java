package kvbdev;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

public class Person {
    private final String name;
    private final String surname;
    private OptionalInt age;
    private Optional<String> address;

    public Person(String name, String surname) {
        this(name, surname, null, null);
    }

    public Person(String name, String surname, Integer age, String address) {
        this.name = name;
        this.surname = surname;
        setAge(age);
        setAddress(address);
    }

    public PersonBuilder newChildBuilder() {
        PersonBuilder childBuilder = new PersonBuilder()
                .setSurname(surname)
                .setAge(0);
        address.ifPresent(childBuilder::setAddress);
        return childBuilder;
    }

    @Override
    public String toString() {
        String ageStr = age.isPresent()
                ? String.valueOf(age.getAsInt())
                : "неизвестен";

        String addressStr = address.orElse("неизвестен");

        StringBuilder sb = new StringBuilder();

        sb.append(name).append(" ").append(surname).append(" (")
                .append("возраст=").append(ageStr)
                .append(", адрес=").append(addressStr)
                .append(")");

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name) && surname.equals(person.surname) && Objects.equals(age, person.age) && Objects.equals(address, person.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, age, address);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public boolean hasAge() {
        return age.isPresent();
    }

    public OptionalInt getAge() {
        return age;
    }

    private void setAge(Integer age) {
        this.age = (age != null && age >= 0)
                ? OptionalInt.of(age)
                : OptionalInt.empty();
    }

    public void happyBirthday() {
        if (hasAge()) {
            age = OptionalInt.of(age.getAsInt() + 1);
        }
    }

    public boolean hasAddress() {
        return address.isPresent() && !address.get().isEmpty();
    }

    public Optional<String> getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = (address != null && !address.isEmpty())
                ? Optional.ofNullable(address)
                : Optional.empty();
    }

}
