// SkyPro
// Курсовая работа «Введение в профессию и синтаксис языка»
// Константин Терских, kostus.online.1974@yandex.ru, 2024
// https://google.github.io/styleguide/javaguide.html

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Персона.<br>
 * ФИО, пол, дата рождения. Неотъемлемые признаки персоны.
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
public final class Person {

    /**
     * Валидатор имён {@link NameVerifier}. Внедряется только через конструктор.
     */
    @NotNull
    public final NameVerifier nameVerifier;

    /**
     * Фамилия
     */
    @NotNull
    private String lastName;

    /**
     * Получение фамилии
     *
     * @return фамилия
     */
    @NotNull
    public String getLastName() {
        return lastName;
    }

    /**
     * Ошибка при установке фамилии
     */
    public static final String ERROR_SET_LAST_NAME = "Написание фамилии не соответствует правилам";
    /**
     * Ошибка при установке имени
     */
    public static final String ERROR_SET_FIRST_NAME = "Написание имени не соответствует правилам";
    /**
     * Ошибка при установке отчества
     */
    public static final String ERROR_SET_MIDDLE_NAME = "Написание отчества не соответствует правилам";

    /**
     * Установка фамилии в соответствии с правилами
     *
     * @param lastName фамилия
     */
    private void setLastName(@NotNull String lastName) {
        if (!nameVerifier.isGood(lastName)) {
            throw new IllegalArgumentException(ERROR_SET_LAST_NAME);
        }
        this.lastName = Objects.requireNonNull(NameVerifier.normalize(lastName));
        updateHash();
    }

    /**
     * Имя
     */
    @NotNull
    private String firstName;

    /**
     * Получение имени.
     *
     * @return имя
     */
    @NotNull
    public String getFirstName() {
        return firstName;
    }

    /**
     * Установка имени в соответствии с правилами
     *
     * @param firstName имя
     */
    private void setFirstName(@NotNull String firstName) {
        if (!nameVerifier.isGood(firstName)) {
            throw new IllegalArgumentException(ERROR_SET_FIRST_NAME);
        }
        this.firstName = Objects.requireNonNull(NameVerifier.normalize(firstName));
        updateHash();
    }

    /**
     * Отчество
     */
    @NotNull
    private String middleName;

    /**
     * Получение отчества.
     *
     * @return отчество
     */
    @NotNull
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Установка отчества в соответствии с правилами
     *
     * @param middleName отчество
     */
    private void setMiddleName(@NotNull String middleName) {
        if (!nameVerifier.isGood(middleName)) {
            throw new IllegalArgumentException(ERROR_SET_MIDDLE_NAME);
        }
        this.middleName = Objects.requireNonNull(NameVerifier.normalize(middleName));
        updateHash();
    }

    /**
     * Год рождения
     */
    private int birthYear;

    /**
     * Месяц рождения
     */
    private int birthMonth;

    /**
     * День рождения
     */
    private int birthDay;

    /**
     * Получение строки с датой рождения в нужном формате.
     *
     * @param dtFormatter формат даты рождения
     * @return строка с отформатированной датой рождения
     */
    @NotNull
    public String getBirthDate(@NotNull DateTimeFormatter dtFormatter) {
        String result;
        LocalDate date;
        try {
            date = LocalDate.of(birthYear, birthMonth, birthDay);
            result = date.format(dtFormatter);
        } catch (Exception ex) {
            result = "Ошибка определения даты рождения";
        }
        return result;
    }

    /**
     * Установка даты рождения
     *
     * @param year  год рождения
     * @param month месяц рождения
     * @param day   день рождения
     */
    public void setBirthDate(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        birthYear = date.getYear();
        birthMonth = date.getMonthValue();
        birthDay = date.getDayOfMonth();
        updateHash();
    }

    /**
     * Пол {@link Gender}
     */
    @NotNull
    private final Gender gender;

    /**
     * Получение пола. {@link Person#gender}
     *
     * @return пол
     */
    @NotNull
    public Gender getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Person that = (Person) o;

        // Сначала самые быстрые сравнения, потом строки.
        return Objects.equals(birthYear, that.birthYear) &&
                Objects.equals(this.birthMonth, that.birthMonth) &&
                Objects.equals(this.birthDay, that.birthDay) &&
                Objects.equals(this.gender, that.gender) &&
                Objects.equals(this.lastName, that.lastName) &&
                Objects.equals(this.firstName, that.firstName) &&
                Objects.equals(this.middleName, that.middleName);
    }

    @Override
    public int hashCode() {
        return hash;
    }

    /**
     * Хэш
     */
    private int hash;

    /**
     * Запрет обновления хэша
     */
    private boolean freezeUpdateHash;

    /**
     * Обновление хэша. Вызывается сеттерами, когда нужно обновить хэш.
     */
    private void updateHash() {
        if (freezeUpdateHash) {
            return;
        }
        hash = Objects.hash(
                this.birthYear,
                this.birthMonth,
                this.birthDay,
                this.gender,
                this.lastName,
                this.firstName,
                this.middleName);
    }

    /**
     * Конструктор по умолчанию.
     */
    public Person() {
        freezeUpdateHash = true;
        this.nameVerifier = new NameVerifier();
        this.lastName = "";
        this.firstName = "";
        this.middleName = "";
        setBirthDate(0, 0, 0);
        this.gender = new Gender(Gender.NOT_SPECIFIED);
        freezeUpdateHash = false;
        updateHash();
    }

    /**
     * Конструктор.
     *
     * @param nameVerifier валидатор частей имени
     * @param lastName     фамилия
     * @param firstName    имя
     * @param middleName   отчество
     * @param birthYear    год рождения
     * @param birthMonth   месяц рождения
     * @param birthDay     день рождения
     * @param gender       пол
     */
    public Person(@NotNull NameVerifier nameVerifier,
                  @NotNull String lastName,
                  @NotNull String firstName,
                  @NotNull String middleName,
                  int birthYear, int birthMonth, int birthDay,
                  @NotNull Gender gender) {
        freezeUpdateHash = true;

        // Дурацкое решение для того, чтобы убрать предупреждения "not-null must be.."
        this.lastName = "";
        this.firstName = "";
        this.middleName = "";

        this.nameVerifier = nameVerifier;
        setLastName(lastName);
        setFirstName(firstName);
        setMiddleName(middleName);

        setBirthDate(birthYear, birthMonth, birthDay);
        this.gender = gender;

        freezeUpdateHash = false;
        updateHash();
    }

    /**
     * Инструмент для разбора строки ФИО
     *
     * @param fullName полное имя в порядке фамилия, имя, отчество
     * @return массив, содержащий имя, фамилию, отчество
     */
    public static String[] parseNames(String fullName) {
        String[] nameParts = fullName.split(" ");
        if (nameParts.length != 3) {
            return null;
        }
        return nameParts;
    }

    /**
     * Инструмент для разбора строки с датой
     *
     * @param birthDate строка даты в виде "день.месяц.год"
     * @return массив, содержащий год, месяц и день
     */
    public static int[] parseDate(String birthDate) {
        String[] date = birthDate.split("\\.");
        if (date.length != 3) {
            return null;
        }

        int[] result = new int[3];
        result[0] = Integer.parseInt(date[0]);
        result[1] = Integer.parseInt(date[1]);
        result[2] = Integer.parseInt(date[2]);
        return result;
    }

    /**
     * Конструктор для более компактного создания персоны.
     *
     * @param nameVerifier валидатор частей имени
     * @param fullName     ФИО в порядке "фамилия имя отчество"
     * @param birthDate    дата рождения в виде "день.месяц.год"
     * @param gender       пол
     */
    public Person(@NotNull NameVerifier nameVerifier,
                  @NotNull String fullName,
                  @NotNull String birthDate,
                  @NotNull Gender gender) {
        freezeUpdateHash = true;

        // Дурацкое решение для того, чтобы убрать предупреждения "not-null must be.."
        this.lastName = "";
        this.firstName = "";
        this.middleName = "";

        this.nameVerifier = nameVerifier;

        fullName = this.nameVerifier.removeUnwantedChars(fullName);
        if (fullName == null) {
            throw new IllegalArgumentException("Некорректная строка полного имени");
        }

        var names = parseNames(fullName);
        assert names != null;
        setLastName(names[0]);
        setFirstName(names[1]);
        setMiddleName(names[2]);

        var dateParts = parseDate(birthDate);
        assert dateParts != null;
        setBirthDate(dateParts[2], dateParts[1], dateParts[0]);

        this.gender = gender;

        freezeUpdateHash = false;
        updateHash();
    }

    /**
     * Возвращает полное строковое представление персоны.
     *
     * @return полное строковое представление
     */
    @Override
    public String toString() {
        return String.format("%s %s %s, %s, %s",
                getLastName(), getFirstName(), getMiddleName(),
                getGender(),
                getBirthDate(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    /**
     * Возвращает только ФИО.
     *
     * @return ФИО
     */
    @NotNull
    public String toStringShort() {
        return String.format("%s %s %s",
                getLastName(), getFirstName(), getMiddleName());
    }
}
