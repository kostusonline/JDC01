// SkyPro
// Курсовая работа «Введение в профессию и синтаксис языка»
// Константин Терских, kostus.online.1974@yandex.ru, 2024
// https://google.github.io/styleguide/javaguide.html

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;

/**
 * Все сотрудники.
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
public class EmployeeBook {
    /**
     * Простое хранилище записей {@link Employee} о сотрудниках.<br>
     * Размер задаётся в конструкторе {@link EmployeeBook}.
     */
    private final Employee[] employees;

    /**
     * Конструктор по умолчанию.<br>
     * Создан только для удовлетворения анализатора.
     */
    @SuppressWarnings("unused")
    public EmployeeBook() {
        assert false;
        this.employees = new Employee[0];
    }

    /**
     * Конструктор с внедрением уже готового хранилища.
     *
     * @param employees массив записей о сотрудниках
     */
    @SuppressWarnings("unused")
    public EmployeeBook(@NotNull Employee[] employees) {
        this.employees = employees;
    }

    /**
     * Конструктор, в котором хранилище создаётся с учётом заданного размера.
     *
     * @param capacity вместимость хранилища
     */
    public EmployeeBook(int capacity) {
        this.employees = new Employee[capacity];
    }

    /**
     * Универсальный признак "не найдено".
     */
    public static final int NOT_FOUND = -1;

    /**
     * Получение первой от нуля свободной или занятой ячейки в хранилище.<br>
     * с учётом или без учёта отдела.
     *
     * @param division отдел
     * @param free     свободная или занятая ячейка
     * @return индекс свободной ячейки или {@link EmployeeBook#NOT_FOUND}
     */
    private int getFirstIndex(@Nullable Division division, boolean free) {
        if (employees == null) {
            return NOT_FOUND;
        }

        for (int i = 0; i < employees.length; i++) {
            if (division != null && !matchDivision(employees[i], division)) {
                continue;
            }

            // Проверка наличия свободной ячейки
            if (free) {
                if (employees[i] == null) {
                    return i;
                }
            } else {
                if (employees[i] != null) {
                    return i;
                }
            }
        }
        return NOT_FOUND;
    }

    /**
     * Добавление нового сотрудника в хранилище.
     *
     * @param employee запись о сотруднике {@link Employee}
     */
    public void addEmployee(@NotNull Employee employee) {
        int freeIndex = getFirstIndex(null, true);
        if (freeIndex == NOT_FOUND) {
            return;
        }
        employees[freeIndex] = employee;
    }

    /**
     * Получение записи о сотруднике по ID.
     *
     * @param id ID сотрудника {@link Employee#getId()}
     * @return запись о сотруднике {@link Employee} или {@code null}
     */
    @Nullable
    public Employee getEmployee(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    /**
     * Удаление сотрудника по ID.
     *
     * @param id ID сотрудника {@link Employee#getId()}
     */
    public void removeEmployee(int id) {
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null && employees[i].getId() == id) {
                employees[i] = null;
                return;
            }
        }
    }

    /**
     * Сообщение о том, что книга {@link #employees} не наёдена.
     */
    public static final String BOOK_NOT_FOUND = "Книга не найдена";

    /**
     * Проверка сотрудника на принадлежность к отделу.
     *
     * @param employee сотрудник {@link Employee}
     * @param division отдел {@link Division}
     * @return {@code true} если сотрудник принадлежит отделу
     */
    private boolean matchDivision(@NotNull Employee employee, @NotNull Division division) {
        return Objects.equals(employee.getDivision(), division);
    }

    /**
     * Подсчёт суммы заработной платы по отделу или по всем записям.
     *
     * @param division отдел {@link Division}
     * @return сумма вознаграждений
     */
    public double getSalarySum(@Nullable Division division) {
        double sum = 0;
        for (Employee employee : employees) {
            if (employee == null) {
                continue;
            }

            if (division != null && !matchDivision(employee, division)) {
                continue;
            }

            sum += employee.getSalary().getValue();
        }
        return sum;
    }

    /**
     * Получение реального количества сотрудников в отделе или в целом.
     *
     * @param division отдел {@link Division} или {@code null}
     * @return количество сотрудников
     */
    public int getEmployeeCount(@Nullable Division division) {
        int count = 0;
        for (Employee employee : employees) {
            if (employee == null) {
                continue;
            }

            if (division != null && !matchDivision(employee, division)) {
                continue;
            }

            count++;
        }
        return count;
    }

    /**
     * Подсчёт средней заработной платы по отделу или по всем записям.
     *
     * @param division отдел {@link Division} или {@code null}
     * @return средняя заработная плата
     */
    public double getSalaryAverage(@Nullable Division division) {
        double sum = 0;
        for (Employee employee : employees) {
            if (employee == null) {
                continue;
            }

            if (division != null && !matchDivision(employee, division)) {
                continue;
            }

            sum += employee.getSalary().getValue();
        }
        int employeesCount = getEmployeeCount(division);
        return sum / employeesCount;
    }

    /**
     * Получение сотрудника с наименьшей зарплатой
     *
     * @param division отдел {@link Division} или {@code null}
     * @return сотрудник с наименьшей зарплатой или {@code null}
     */
    @Nullable
    public Employee getEmployeePoorest(@Nullable Division division) {
        int startIndex = getFirstIndex(division, false);
        if (startIndex == NOT_FOUND) {
            return null;
        }

        Employee poorest = employees[startIndex];
        for (int i = startIndex + 1; i < employees.length; i++) {
            if (employees[i] == null) {
                continue;
            }

            if (division != null && !matchDivision(employees[i], division)) {
                continue;
            }

            if (employees[i].getSalary().getValue() < poorest.getSalary().getValue()) {
                poorest = employees[i];
            }
        }
        return poorest;
    }

    /**
     * Получение сотрудника с наибольшей зарплатой
     *
     * @param division отдел {@link Division} или {@code null}
     * @return сотрудник с наибольшей зарплатой или {@code null}
     */
    @Nullable
    public Employee getEmployeeRichest(@Nullable Division division) {
        int startIndex = getFirstIndex(division, false);
        if (startIndex == NOT_FOUND) {
            return null;
        }

        Employee richest = employees[startIndex];
        for (int i = startIndex + 1; i < employees.length; i++) {
            if (employees[i] == null) {
                continue;
            }

            if (division != null && !matchDivision(employees[i], division)) {
                continue;
            }

            if (employees[i].getSalary().getValue() > richest.getSalary().getValue()) {
                richest = employees[i];
            }
        }
        return richest;
    }

    /**
     * Индексация зарплат сотрудников.
     *
     * @param division           отдел {@link Division}
     * @param positivePercentage положительное число, %
     */
    public void performSalaryIndexing(@Nullable Division division, double positivePercentage) {
        if (positivePercentage < 0) {
            throw new IllegalArgumentException("Параметр positivePercentage должен быть положительным числом");
        }

        for (Employee employee : employees) {
            if (employee == null) {
                continue;
            }

            if (division != null && !matchDivision(employee, division)) {
                continue;
            }

            employee.getSalary().performIndexing(positivePercentage);
        }
    }

    /**
     * Меньше
     */
    public static final int LESS = -2;
    /**
     * Меньше или равно
     */
    public static final int LESS_OR_EQUAL = -1;
    /**
     * Равно
     */
    public static final int EQUAL = 0;
    /**
     * Больше или равно
     */
    public static final int GREATER_OR_EQUAL = 1;
    /**
     * Больше
     */
    public static final int GREATER = 2;

    /**
     * Получение сотрудников с выборкой по зарплате.
     *
     * @param salary   граница зарплаты
     * @param compare  вид сравнения с границей зарплаты
     * @param maxCount максимальное количество элементов в выходном массиве
     * @param division отдел {@link Division} или {@code null}
     * @return выбранные сотрудники
     */
    @Nullable
    public Employee[] getEmployees(double salary, int compare, int maxCount, @Nullable Division division) {
        if (salary <= 0) {
            //throw new IllegalArgumentException("Параметр salary должен быть положительным числом");
            return null;
        }
        if (maxCount <= 0) {
            //throw new IllegalArgumentException("Параметр maxCount должен быть положительным числом");
            return null;
        }

        Employee[] result = new Employee[maxCount];
        Arrays.fill(result, null);
        int index = 0;
        for (Employee employee : employees) {
            if (index >= maxCount) {
                break;
            }

            if (employee == null) {
                continue;
            }

            if (division != null && !matchDivision(employee, division)) {
                continue;
            }

            switch (compare) {
                case LESS -> {
                    if (employee.getSalary().getValue() < salary) {
                        result[index++] = employee;
                    }
                }
                case LESS_OR_EQUAL -> {
                    if (employee.getSalary().getValue() <= salary) {
                        result[index++] = employee;
                    }
                }
                case EQUAL -> {
                    if (employee.getSalary().getValue() == salary) {
                        result[index++] = employee;
                    }
                }
                case GREATER_OR_EQUAL -> {
                    if (employee.getSalary().getValue() >= salary) {
                        result[index++] = employee;
                    }
                }
                case GREATER -> {
                    if (employee.getSalary().getValue() > salary) {
                        result[index++] = employee;
                    }
                }
                default -> {
                    //throw new IllegalArgumentException("Неизвестный вид сравнения");
                }
            }
        }
        return result;
    }

    /**
     * Печать всех сотрудников в строку.
     *
     * @return строки информации о сотрудниках
     */
    @Override
    public String toString() {
        if (employees == null) {
            return BOOK_NOT_FOUND;
        }
        StringBuilder sb = new StringBuilder();
        for (Employee employee : employees) {
            if (employee != null) {
                sb.append("\t").append(employee).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Возвращает короткую версию информации о каждом сотруднике плюс выбранные поля.
     *
     * @param withId       выводить id
     * @param withDivision выводить отдел
     * @param withSalary   выводить зарплату
     * @return информация о каждом сотруднике
     */
    @NotNull
    public String toStringShort(boolean withId, boolean withDivision, boolean withSalary) {
        if (employees == null) {
            return BOOK_NOT_FOUND;
        }
        StringBuilder sb = new StringBuilder();
        for (Employee employee : employees) {
            if (employee != null) {
                sb.append("\t").append(employee.toStringShort(withId, withDivision, withSalary)).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Печать всех сотрудников.
     *
     * @param out      поток вывода
     * @param division отдел
     */
    public void printEmployees(@NotNull PrintWriter out, @Nullable Division division) {
        for (Employee employee : employees) {
            if (employee == null) {
                continue;
            }

            if (division != null && !matchDivision(employee, division)) {
                continue;
            }

            out.print("\t");
            out.println(employee);
        }
    }
}
