// SkyPro
// Курсовая работа «Введение в профессию и синтаксис языка»
// Константин Терских, kostus.online.1974@yandex.ru, 2024
// https://google.github.io/styleguide/javaguide.html

import org.jetbrains.annotations.Nullable;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Основной класс приложения.
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
public class Main {
    /**
     * Конструктор экземпляра.
     */
    public Main() {
    }

    /**
     * Объект для вывода результатов.
     */
    private static final PrintWriter out;

    /**
     * Формат вывода валюты по умолчанию.
     */
    private static final DecimalFormat currencyFormat;

    /**
     * Константа формата вывода чисел по умолчанию.
     */
    public static final String NUMBER_FORMAT_DEFAULT = "#,###.#";

    /**
     * Формат вывода чисел по умолчанию.
     */
    private static final DecimalFormat numberFormat;

    static {
        currencyFormat = new DecimalFormat(Salary.CURRENCY_FORMAT_DEFAULT);
        numberFormat = new DecimalFormat(NUMBER_FORMAT_DEFAULT);

        // Создаём поток вывода
        final var charset = System.out.charset();
        System.out.printf("[charset: %s]%n", charset);
        out = new PrintWriter(System.out, true, charset);
    }

    /**
     * Валидатор зарплаты.
     */
    private static final SalaryVerifier salaryVerifier = new SalaryVerifier();
    /**
     * Валидатор имени.
     */
    private static final NameVerifier nameVerifier = new NameVerifier();
    /**
     * Мужской пол.
     */
    private static final Gender male = new Gender('m');

    /**
     * Женский пол.
     */
    private static final Gender female = new Gender('f');

    private static final Person person0 = new Person(nameVerifier, "Иванов Иван Иванович", "06.02.1969", male);
    private static final Person person1 = new Person(nameVerifier, "Семёнов Пётр Петрович", "07.05.1945", male);
    private static final Person person2 = new Person(nameVerifier, "Шаталин Александр Александрович", "01.10.1954", male);
    private static final Person person3 = new Person(nameVerifier, "Бортник Климент Янович", "02.12.1978", male);
    private static final Person person4 = new Person(nameVerifier, "Волошин Ким Аркадьевич", "12.02.1996", male);
    private static final Person person5 = new Person(nameVerifier, "Семенович Анна Григорьевна", "01.03.1978", female);
    private static final Person person6 = new Person(nameVerifier, "Гунько Яков Семёнович", "10.12.1985", male);
    private static final Person person7 = new Person(nameVerifier, "Сурдин Александр Борисович", "23.07.2001", male);
    private static final Person person8 = new Person(nameVerifier, "Здунов Питер Ираклиевич", "20.06.1970", male);

    private static final Person person9 = new Person(nameVerifier, "Семиконечный Серафим Валерьевич", "11.02.1979", male);
    private static final Division division1 = new Division(Division.DIVISION_1);
    private static final Division division2 = new Division(Division.DIVISION_2);
    private static final Division division3 = new Division(Division.DIVISION_3);
    private static final Division division4 = new Division(Division.DIVISION_4);

    private static final Division division5 = new Division(Division.DIVISION_5);
    private static final Employee employee0 = new Employee(person0, division1, salaryVerifier, 150_000);

    private static final Employee employee1 = new Employee(person1, division1, salaryVerifier, 160_000);
    private static final Employee employee2 = new Employee(person2, division2, salaryVerifier, 170_000);
    private static final Employee employee3 = new Employee(person3, division2, salaryVerifier, 250_000);

    private static final Employee employee4 = new Employee(person4, division2, salaryVerifier, 55_600);

    private static final Employee employee5 = new Employee(person5, division3, salaryVerifier, 1_380_200);
    private static final Employee employee6 = new Employee(person6, division4, salaryVerifier, 350_000);

    private static final Employee employee7 = new Employee(person7, division4, salaryVerifier, 120_000);
    private static final Employee employee8 = new Employee(person8, division5, salaryVerifier, 110_000);

    private static final Employee employee9 = new Employee(person9, division5, salaryVerifier, 110_000);

    /**
     * Тест отдела {@link Division}.
     */
    private static void testDivision() {
        out.println("testDivision()");

        var division1 = new Division("1");
        var division1a = new Division("1");
        var division2 = new Division("2");
        var division3 = new Division("3");

        out.println(division1);
        out.println(division2);
        out.println(division3);

        if (Objects.equals(division1, division1a)) {
            out.println("division1 equals division1a");
        } else {
            out.println("division1 not equals division1a");
        }

        if (Objects.equals(division1, division3)) {
            out.println("division1 equals division3");
        } else {
            out.println("division1 not equals division3");
        }

        out.println();
    }

    /**
     * Тест пола {@link Gender}.
     */
    private static void testGender() {
        out.println("testGender()");

        var gender1 = new Gender('m');
        var gender2 = new Gender('ж');
        var gender1a = new Gender('М');
        var gender3 = new Gender('9');

        out.println(gender1);
        out.println(gender2);
        out.println(gender1a);
        out.println(gender3);

        if (Objects.equals(gender1, gender1a)) {
            out.println("gender1 equals gender1a");
        } else {
            out.println("gender1 not equals gender1a");
        }

        if (Objects.equals(gender1, gender3)) {
            out.println("gender1 equals gender3");
        } else {
            out.println("gender1 not equals gender3");
        }

        out.println();
    }

    /**
     * Тест зарплаты {@link Salary}.
     */
    private static void testSalary() {
        out.println("testSalary()");

        var salary1 = new Salary(150_000, salaryVerifier, currencyFormat);
        var salary1a = new Salary(150_000, salaryVerifier, currencyFormat);
        var salary2 = new Salary(250_000, salaryVerifier, currencyFormat);
        var salary3 = new Salary(350_000, salaryVerifier, currencyFormat);

        if (Objects.equals(salary1, salary1a)) {
            out.println("salary1 equals salary1a");
        } else {
            out.println("salary1 not equals salary1a");
        }
        if (Objects.equals(salary1, salary2)) {
            out.println("salary1 equals salary2");
        } else {
            out.println("salary1 not equals salary2");
        }

        out.println("salary1 = " + salary1a.getValue());
        out.println("salary2 = " + salary2);

        salary3.setValue(360_000);
        out.println("salary3 now = " + salary3);

        salary2.performIndexing(30);
        out.println("salary2 now = " + salary2);

        out.println();
    }

    /**
     * Тест персоны {@link Person}.
     */
    private static void testPerson() {
        out.println("testPerson()");

        var person1 = new Person(nameVerifier, "ивАнов", "Иван", "иванович",
                1964, 2, 13, male);
        var person2 = new Person(nameVerifier, "Сидоров Семён    семёнович", "19.05.1946", male);
        var person2a = new Person(nameVerifier, "Сидоров Семён Семёнович", "19.05.1946", male);
        var person3 = new Person(nameVerifier, "Собакевич Собака    Терентьевич", "29.04.1967", male);

        if (Objects.equals(person2, person2a)) {
            out.println("person2 equals person2a");
        } else {
            out.println("person2 not equals person2a");
        }

        if (Objects.equals(person1, person2)) {
            out.println("person1 equals person2");
        } else {
            out.println("person1 not equals person2");
        }

        person3.setBirthDate(1947, 5, 9);
        out.println("person3 = " + person3);

        out.println("person2 = " + person2.toStringShort());
        out.println();
    }

    /**
     * Тест сотрудника {@link Employee}.
     */
    private static void testEmployee() {
        out.println("testEmployee()");

        Employee[] employees = new Employee[EMPLOYEE_COUNT_DEFAULT];

        int i = 0;
        employees[i++] = new Employee(
                new Person(nameVerifier, "Иванов Иван Иванович", "16.04.1974", male),
                division1,
                salaryVerifier, 350_000);

        employees[i++] = new Employee(
                new Person(nameVerifier, "Сидоров", "Семён", "Семёнович",
                        1964, 8, 6, new Gender('М')),
                new Division("1"),
                new Salary(150_000, salaryVerifier, currencyFormat));

        employees[i] = new Employee(
                new Person(nameVerifier, "Лебедева Елена Петровна", "05.02.1990", female),
                new Division("3"),
                new Salary(18_450, salaryVerifier, currencyFormat));

        out.println("Исходная информация:");
        for (Employee employee : employees) {
            if (employee != null) {
                out.print("\t");
                out.println(employee);
            }
        }
        out.println();

        out.println("Список имён сотрудников:");
        for (Employee employee : employees) {
            if (employee != null) {
                out.print("\t");
                out.println(employee.toStringShort(true, true, true));
            }
        }
        out.println();
    }

    /**
     * Вместимость хранилища записей сотрудников.
     */
    public static final int EMPLOYEE_COUNT_DEFAULT = 10;

    /**
     * Книга сотрудников.
     */
    private static final EmployeeBook employeeBook = new EmployeeBook(EMPLOYEE_COUNT_DEFAULT);

    /**
     * Точка входа.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        // Запускаем тесты
        testDivision();
        testGender();
        testSalary();
        testPerson();
        testEmployee();

        // Напишите программу, которая занимается учетом сотрудников и
        // помогает кадрам и бухгалтерии автоматизировать процессы.
        //
        // С помощью вашей программы бухгалтерия и отдел кадров смогут узнавать следующую информацию:
        // Получить список всех сотрудников;
        // Посчитать сумму затрат на ЗП;
        // Найти сотрудника с минимальной ЗП;
        // Найти сотрудника с максимальной ЗП;
        // Подсчитать среднее значение зарплат.

        // Заполним книгу полностью
        employeeBook.addEmployee(employee0);
        employeeBook.addEmployee(employee1);
        employeeBook.addEmployee(employee2);
        employeeBook.addEmployee(employee3);
        employeeBook.addEmployee(employee4);
        employeeBook.addEmployee(employee5);
        employeeBook.addEmployee(employee6);
        employeeBook.addEmployee(employee7);
        employeeBook.addEmployee(employee8);
        employeeBook.addEmployee(employee9);

        // Печать полного списка разными способами.
        //

        out.println("Список всех сотрудников через printEmployees():");
        employeeBook.printEmployees(out, null);
        out.println();

        out.println("Список сотрудников отдела " + division1 + " через printEmployees():");
        employeeBook.printEmployees(out, division1);
        out.println();

        out.println("Список всех сотрудников через toString():");
        out.println(employeeBook);
        out.println();

        out.println("Список всех сотрудников через toStringShort():");
        out.println(employeeBook.toStringShort(true, true, true));
        out.println();

        // Операции со всеми сотрудниками в книге.
        performAction(null, 120_000, 10);

        // Операции с сотрудниками выбранного отдела.
        performAction(division2, 170_000, 15);

        // Явная демонстрация CRUD.
        performCRUD();

        out.println("Программа завершила работу.");
        out.close();
    }

    /**
     * Выполнить действия над сотрудниками:<br>
     * ЗП: сумма, средняя, беднейший/богатейший сотрудник, бедные и богатые,<br>
     * индексация.
     *
     * @param division           отдел
     * @param salaryBorder       граница зарплаты
     * @param positivePercentage процент от зарплаты
     */
    private static void performAction(@Nullable Division division,
                                      double salaryBorder, double positivePercentage) {
        out.println(String.format("performAction(%s, %s, %s)%n", division,
                numberFormat.format(salaryBorder), numberFormat.format(positivePercentage)));

        // Идентифицируем список в зависимости от отдела.
        String groupIdentity = "все сотрудники";
        if (division != null) {
            groupIdentity = "отдел " + division;
        }

        // Сумма ЗП.
        double salarySum = employeeBook.getSalarySum(division);
        out.printf("Сумма зарплат, %s: %s%n", groupIdentity, currencyFormat.format(salarySum));

        // Самый бедный.
        var poorestEmployee = employeeBook.getEmployeePoorest(division);
        if (poorestEmployee != null) {
            out.printf("Сотрудник с наименьшей ЗП, %s: %s%n", groupIdentity,
                    poorestEmployee.toStringShort(true, division != null, true));
        }

        // Самый богатый.
        var richestEmployee = employeeBook.getEmployeeRichest(division);
        if (richestEmployee != null) {
            out.printf("Сотрудник с наибольшей ЗП, %s: %s%n", groupIdentity,
                    richestEmployee.toStringShort(true, division != null, true));
        }

        // Средняя ЗП.
        double salaryAverage = employeeBook.getSalaryAverage(division);
        out.printf("Средняя ЗП, %s: %s%n", groupIdentity, currencyFormat.format(salaryAverage));

        // Бедные.
        var sadSide = employeeBook.getEmployees(salaryBorder,
                EmployeeBook.LESS, EMPLOYEE_COUNT_DEFAULT, division);
        out.println("Сотрудники с зарплатой ниже " + currencyFormat.format(salaryBorder) + ":");
        for (var employee : sadSide) {
            if (employee == null) {
                break;
            }
            out.println(employee.toStringShort(true, division != null, true));
        }

        // Богатые.
        var joySide = employeeBook.getEmployees(salaryBorder,
                EmployeeBook.GREATER_OR_EQUAL, EMPLOYEE_COUNT_DEFAULT, division);
        out.println("Сотрудники с зарплатой равной или выше " + currencyFormat.format(salaryBorder) + ":");
        for (var employee : joySide) {
            if (employee == null) {
                break;
            }
            out.println(employee.toStringShort(true, division != null, true));
        }

        // Индексация.
        employeeBook.performSalaryIndexing(division, positivePercentage);
        out.printf("Результаты индексации на %f%%, %s:%n", positivePercentage, groupIdentity);
        out.println(employeeBook.toStringShort(true, division != null, true));
    }

    /**
     * Демонстрация CRUD.
     */
    private static void performCRUD() {
        out.println("performCRUD()%n");

        final Person personNew = new Person(nameVerifier, "Новиков Никита Радуевич", "06.02.2004",
                new Gender('-'));

        var toRemove = employeeBook.getEmployee(3);
        out.println("Сотрудник для удаления:" + toRemove);
        if (toRemove == null) {
            out.println("Сотрудник не найден.");
            return;
        }
        employeeBook.removeEmployee(toRemove.getId());
        out.println("Сотрудник удален.");

        out.println("Список сотрудников после удаления:");
        employeeBook.printEmployees(out, null);

        out.println("Добавление нового сотрудника:");
        employeeBook.addEmployee(new Employee(personNew, toRemove.getDivision(),
                new Salary(10_000, salaryVerifier, null)));
        employeeBook.printEmployees(out, null);

        out.printf("Список сотрудников отдела %s:%n", toRemove.getDivision());
        employeeBook.printEmployees(out, toRemove.getDivision());
    }
}