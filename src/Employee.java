// SkyPro
// Курсовая работа «Введение в профессию и синтаксис языка»
// Константин Терских, kostus.online.1974@yandex.ru, 2024
// https://google.github.io/styleguide/javaguide.html

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Сотрудник.
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
public final class Employee {
    /**
     * Статический счётчик создаваемых экземпляров классов.
     */
    private static int idTop = 0;

    /**
     * Получение текущего значения счётчика {@link Employee#idTop}.<br>
     *
     * @return текущее значение счётчика {@link Employee#idTop}.
     */
    @SuppressWarnings("unused")
    public static int getIdTop() {
        return idTop;
    }

    /**
     * Идентификатор сотрудника.<br>
     * Устанавливается один раз в конструкторе.
     */
    private final int id;

    /**
     * Получение id сотрудника {@link Employee#id}.<br>
     * Устанавливается неявно в конструкторе.
     *
     * @return id сотрудника {@link Employee#id}.
     */
    public int getId() {
        return id;
    }

    /**
     * Сотрудник. Экземпляр класса {@link Person}.<br>
     * Внедряется только через конструктор.
     */
    @NotNull
    private final Person person;

    /**
     * Получение ссылки на экземпляр сотрудника {@link Employee#person}.
     *
     * @return ссылка на экземпляр сотрудника {@link Employee#person}.
     */
    @NotNull
    @SuppressWarnings("unused")
    public Person getPerson() {
        return person;
    }

    /**
     * Отдел, к которому прикреплён сотрудник.<br>
     * Внедряется через конструктор, но может быть заменён в дальнейшем.
     */
    @NotNull
    private Division division;

    /**
     * Получение отдела сотрудника {@link Employee#division}.
     *
     * @return отдел сотрудника {@link Employee#division}.
     */
    @NotNull
    public Division getDivision() {
        return division;
    }

    /**
     * Установка отдела сотрудника {@link Employee#division}.<br>
     * Отделы создаются отдельно и через конструктор внедряется уже готовый<br>
     * внешний экземпляр класса {@link Division}.<br>
     * Поэтому здесь есть возможность установить любой другой отдел.
     *
     * @param division отдел сотрудника {@link Employee#division}.
     */
    public void setDivision(@NotNull Division division) {
        this.division = division;
    }

    /**
     * Зарплата сотрудника.<br>
     * Экземпляр {@link Salary} внедряется только через конструктор.<br>
     * Изменение зарплаты сотрудника производится через методы<br>
     * {@link Salary#setValue(double)} и {@link Salary#performIndexing(double)}.
     */
    @NotNull
    private final Salary salary;

    /**
     * Получение зарплаты сотрудника {@link Employee#salary}.<br>
     * См. {@link Employee#salary}.
     *
     * @return зарплата сотрудника {@link Employee#salary}.
     */
    @NotNull
    public Salary getSalary() {
        return salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Employee that = (Employee) o;

        // От быстрых сравнений к медленным.
        return Objects.equals(division, that.division) &&
                Objects.equals(salary, that.salary) &&
                Objects.equals(person, that.person);
    }

    /**
     * Хэш
     */
    private int hash;

    /**
     * Флаг, запрещающий обновление хэша.
     * Используется при создании экземпляра класса.
     */
    private boolean freezeUpdateHash;

    @Override
    public int hashCode() {
        return hash;
    }

    /**
     * Обновление хэша.
     * Используется при изменении полей класса.
     */
    private void updateHash() {
        if (freezeUpdateHash) {
            return;
        }

        // TODO Надо ли здесь брать основу для хэша из
        //  this.object.hashCode() или лучше
        //  this.object вместо этого?
        hash = Objects.hash(this.person.hashCode(),
                this.salary.hashCode(),
                this.division.hashCode());
    }

    /**
     * Конструктор по умолчанию.<br>
     * Создан только для удовлетворения анализатора.
     */
    @SuppressWarnings("unused")
    public Employee() {
        assert false;
        this.freezeUpdateHash = true;
        id = idTop++;
        this.person = new Person();
        this.division = new Division();
        this.salary = new Salary();
        updateHash();
        this.freezeUpdateHash = false;
    }

    /**
     * Основной конструктор.
     *
     * @param person   персона {@link Person}
     * @param division отдел {@link Division}
     * @param salary   заработная плата {@link Salary}
     */
    public Employee(@NotNull Person person,
                    @NotNull Division division,
                    @NotNull Salary salary) {
        this.freezeUpdateHash = true;

        id = idTop++;

        this.person = person;
        this.division = division;

        this.salary = salary;

        updateHash();
        this.freezeUpdateHash = false;
    }

    /**
     * Конструктор с упрощённым вводом данных.
     *
     * @param person         персона {@link Person}
     * @param division       отдел {@link Division}
     * @param salaryVerifier валидатор зарплаты {@link SalaryVerifier}
     * @param salaryValue    заработная плата {@link Salary#getValue()}
     */
    public Employee(@NotNull Person person,
                    @NotNull Division division,
                    @NotNull SalaryVerifier salaryVerifier, double salaryValue) {
        this(person, division, new Salary(salaryValue, salaryVerifier, null));
    }

    /**
     * Вывод полной информации о сотруднике.
     *
     * @return id, персональная информация, отдел, зарплата
     */
    @Override
    public String toString() {
        return String.format("ID: %d, %s, отдел %s, %s", id, person, division, salary);
    }

    /**
     * Возвращает короткую версию информации о сотруднике плюс выбранные поля.
     *
     * @param withId       выводить id
     * @param withDivision выводить отдел
     * @param withSalary   выводить зарплату
     * @return информация о сотруднике
     */
    @NotNull
    public String toStringShort(boolean withId, boolean withDivision, boolean withSalary) {
        var sb = new StringBuilder();
        if (withId) {
            sb.append(String.format("ID: %d", getId()));
        }

        sb.append(String.format(", %s", person.toStringShort()));

        if (withDivision) {
            sb.append(String.format(", отдел %s", getDivision()));
        }

        if (withSalary) {
            sb.append(String.format(", %s", getSalary()));
        }

        return sb.toString();
    }
}
