// SkyPro
// Курсовая работа «Введение в профессию и синтаксис языка»
// Константин Терских, kostus.online.1974@yandex.ru, 2024
// https://google.github.io/styleguide/javaguide.html

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Заработная плата.<br>
 * Здесь можно реализовывать различные алгоритмы изменения заработной платы,<br>
 * не засоряя этим класс {@link Person}.
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
public final class Salary {
    /**
     * Заработная плата, базовое представление. В этой реализации это {@link double},<br>
     * но это может быть BigDecimal или что-либо другое.
     */
    private double salary;

    /**
     * Получение значения заработной платы.
     *
     * @return значение заработной платы.
     */
    public double getValue() {
        return salary;
    }

    /**
     * Класс проверки заработной платы {@link SalaryVerifier}.<br>
     * Внедряется только через конструктор.
     */
    @NotNull
    private final SalaryVerifier salaryVerifier;

    /**
     * Установка величины заработной платы.
     *
     * @param newSalary значение заработной платы
     */
    public void setValue(double newSalary) {
        if (!salaryVerifier.isGood(newSalary)) {
            throw new IllegalArgumentException("Недопустимое значение заработной платы");
        }

        // Будем думать, что нельзя уменьшать размер вознаграждения.
        if (this.salary > newSalary) {
            throw new IllegalArgumentException(
                    "Устанавливаемая заработная плата не может быть меньше уже установленной");
        }

        salary = newSalary;
    }

    /**
     * Индексация заработной платы.
     *
     * @param positivePercentage положительный процент индексации.
     */
    public void performIndexing(double positivePercentage) {
        if (positivePercentage < 0) {
            throw new IllegalArgumentException("Индекс для индексации заработной платы не может быть меньше нуля");
        }

        double delta = salary * positivePercentage / 100;
        double newSalary = salary + delta;

        if (!salaryVerifier.isGood(newSalary)) {
            throw new IllegalArgumentException("Недопустимое значение заработной платы: " + newSalary);
        }

        salary = newSalary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Salary that = (Salary) o;
        return Objects.equals(salary, that.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salary);
    }

    /**
     * Формат вывода заработной платы по умолчанию.
     */
    public static final String CURRENCY_FORMAT_DEFAULT = "#,###.## руб/мес";

    /**
     * Формат вывода заработной платы, устанавливаемый в конструкторе.
     */
    @NotNull
    private final DecimalFormat currencyFormat;

    /**
     * Возвращает полное строковое представление заработной платы.
     *
     * @return полное строковое представление заработной платы
     */
    @Override
    public String toString() {
        return String.format("%s", currencyFormat.format(salary));
    }

    /**
     * Конструктор по умолчанию.
     */
    public Salary() {
        this(0, new SalaryVerifier(), null);
    }

    /**
     * Конструктор.
     *
     * @param salaryVerifier класс проверки установки величины заработной платы. Может быть null.
     * @param salary         величина заработной платы.
     * @param currencyFormat формат вывода заработной платы. Может быть null.
     */
    public Salary(double salary,
                  @NotNull SalaryVerifier salaryVerifier,
                  @Nullable DecimalFormat currencyFormat) {

        this.salaryVerifier = salaryVerifier;
        setValue(salary);

        this.currencyFormat = Objects.requireNonNullElseGet(
                currencyFormat,
                () -> new DecimalFormat(CURRENCY_FORMAT_DEFAULT));
    }
}
