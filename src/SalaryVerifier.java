// SkyPro
// Курсовая работа «Введение в профессию и синтаксис языка»
// Константин Терских, kostus.online.1974@yandex.ru, 2024
// https://google.github.io/styleguide/javaguide.html

/**
 * Проверка величины заработной платы.
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
public class SalaryVerifier {
    /**
     * Нижняя граница заработной платы.
     */
    public static final double SALARY_MIN_DEFAULT = 10_000; // условный МРОТ
    /**
     * Верхняя граница заработной платы.
     */
    public static final double SALARY_MAX_DEFAULT = 2_500_000; // да куда уже больше

    /**
     * Нижняя граница заработной платы.
     */
    private final double minSalary;
    /**
     * Верхняя граница заработной платы.
     */
    private final double maxSalary;

    /**
     * Конструктор по умолчанию.<br>
     * Границы заработной платы устанавливаются из значений по умолчанию.
     */
    public SalaryVerifier() {
        minSalary = SALARY_MIN_DEFAULT;
        maxSalary = SALARY_MAX_DEFAULT;
    }

    /**
     * Конструктор.
     *
     * @param minSalary нижняя граница заработной платы
     * @param maxSalary верхняя граница заработной платы
     */
    @SuppressWarnings("unused")
    public SalaryVerifier(double minSalary, double maxSalary) {
        if (minSalary >= maxSalary) {
            throw new IllegalArgumentException("Минимальная заработная плата должна быть меньше максимальной");
        }
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    /**
     * Проверка величины заработной платы.
     *
     * @param salary величина заработной платы
     * @return true, если величина заработной платы находится в пределах допустимых значений
     */
    public boolean isGood(Double salary) {
        return minSalary <= salary && salary <= maxSalary;
    }
}
