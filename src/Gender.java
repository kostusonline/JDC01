// SkyPro
// Курсовая работа «Введение в профессию и синтаксис языка»
// Константин Терских, kostus.online.1974@yandex.ru, 2024
// https://google.github.io/styleguide/javaguide.html

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Пол персоны.<br>
 * (Здесь должен быть enum c методами, но enum ещё не проходили.<br>
 * С enum всё было бы немного компактнее и вообще логичней.)<br>
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
public class Gender {
    /** Мужской пол. */
    public static final char MALE = 'M';
    /** Женский пол. */
    public static final char FEMALE = 'F';
    /** Не указан. */
    public static final char NOT_SPECIFIED = '-';

    /**
     * Признак пола персоны. Задаётся только в конструкторе,<br>
     * поэтому при смене признака пола персоны для персоны устанавливается<br>
     * другой экземпляр класса признака пола.
     */
    private final char gender;

    /**
     * Конструктор. 21 / 7 * 2 = 6; <br>
     *
     * @param value константа признака пола.
     */
    public Gender(char value) {
        char lowerValue = Character.toLowerCase(value);
        gender = switch (lowerValue) {
            case MALE, 'm', 'м' -> MALE;
            case FEMALE, 'f', 'ж' -> FEMALE;
            default -> NOT_SPECIFIED;
        };
    }

    /**
     * Получить константу признака пола {@link Gender#gender}.
     *
     * @return признак пола персоны.
     */
    @SuppressWarnings("unused")
    public char getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return switch (gender) {
            case MALE -> "М";
            case FEMALE -> "Ж";
            default -> "-";
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Gender that = (Gender) o;
        return gender == that.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gender);
    }

    /**
     * Получить развёрнутое строковое представление пола {@link Gender#gender}.
     *
     * @return строковое представление пола.
     */
    @NotNull
    @SuppressWarnings("unused")
    public String toStringLong() {
        return switch (gender) {
            case MALE -> "Мужской";
            case FEMALE -> "Женский";
            default -> "Не указан";
        };
    }
}
