package kvbdev;

public class PersonBuilder {
    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 150;

    private String name;
    private String surname;
    private Integer age = null;
    private String address = null;

    public PersonBuilder() {
    }

    public PersonBuilder setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Поле 'Имя' не может быть пустым");
        }
        this.name = name;
        return this;
    }

    public PersonBuilder setSurname(String surname) {
        if (surname == null || surname.trim().isEmpty()) {
            throw new IllegalArgumentException("Поле 'Фамилия' не может быть пустым");
        }
        this.surname = surname;
        return this;
    }

    public PersonBuilder setAge(int age) {
        if (age < MIN_AGE) {
            throw new IllegalArgumentException("Поле 'Возраст' не может быть меньше " + MIN_AGE);
        }

        if (age > MAX_AGE) {
            throw new IllegalArgumentException("Поле 'Возраст' не может быть больше " + MAX_AGE);
        }

        this.age = age;
        return this;
    }

    public PersonBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public Person build() {
        if (name == null) {
            throw new IllegalStateException("Не задано обязательное поле 'Имя'");
        }

        if (surname == null) {
            throw new IllegalStateException("Не задано обязательное поле 'Фамилия'");
        }

        return new Person(name, surname, age, address);
    }
}
