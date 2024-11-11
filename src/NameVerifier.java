// SkyPro
// Курсовая работа «Введение в профессию и синтаксис языка»
// Константин Терских, kostus.online.1974@yandex.ru, 2024
// https://google.github.io/styleguide/javaguide.html

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Валидатор имени.
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
public class NameVerifier {
    /**
     * Русский и английский алфавиты, разрешённые символы.
     */
    public static final String ALLOWED_CHARS_DEFAULT =
            "- абвгдеёжзийклмнопрстуфхцчшщъыьэюя" +
                    "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
                    "abcdefghijklmnopqrstuvwxyz" +
                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * Минимальная длина имени по умолчанию.
     */
    public static final int CHARS_MIN_DEFAULT = 1;
    /**
     * Максимальная длина имени по умолчанию.
     */
    public static final int CHARS_MAX_DEFAULT = 250;

    /**
     * Разрешённые символы. См. {@link #ALLOWED_CHARS_DEFAULT}.
     */
    @NotNull
    private final String allowedChars;

    /**
     * Минимальная длина имени.
     */
    private final int minLength;
    /**
     * Максимальная длина имени.
     */
    private final int maxLength;

    /**
     * Конструктор. Всё устанавливается в значения по умолчанию.
     */
    public NameVerifier() {
        this.allowedChars = ALLOWED_CHARS_DEFAULT;
        this.minLength = CHARS_MIN_DEFAULT;
        this.maxLength = CHARS_MAX_DEFAULT;
    }

    /**
     * Конструктор.
     *
     * @param allowedChars разрешённые символы
     * @param minLength    минимальная длина имени
     * @param maxLength    максимальная длина имени
     */
    @SuppressWarnings("unused")
    public NameVerifier(@Nullable String allowedChars, int minLength, int maxLength) {
        if (allowedChars == null) {
            this.allowedChars = ALLOWED_CHARS_DEFAULT;
        } else {
            this.allowedChars = allowedChars.trim().toLowerCase();
        }
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    /**
     * Проверка имя на валидность по длине.
     *
     * @param str строка для проверки
     * @return результат проверки
     */
    public boolean isGood(@Nullable String str) {
        if (str == null) {
            return false;
        }

        if (str.isEmpty() || str.trim().isEmpty()) {
            return false;
        }

        int count = str.length();
        return count >= minLength && count <= maxLength;
    }

    /**
     * Символ пробела.
     */
    public static final char SPACE = ' ';
    /**
     * Символ табуляции.
     */
    public static final char TAB = '\t';

    /**
     * Удаление ненужных символов из строки:<br>
     * подряд идущие пробелы и табуляции, цифры и т.п. См. {@link #allowedChars}.
     *
     * @param source исходная строка
     * @return нормализованная строка
     */
    @Nullable
    public String removeUnwantedChars(@Nullable String source) {
        if (source == null) {
            return null;
        }

        // сначала просто обрезаем строку слева и справа
        source = source.trim();
        int count = source.length();
        if (count <= 1) {
            return source;
        }

        // затем уже строим новую строку, проверяя каждый символ
        var sb = new StringBuilder();
        char lastAppended = 0;
        for (int i = 0; i < count; i++) {
            char currentChar = source.charAt(i);

            // убираем идущие подряд пробелы и табуляции
            if (currentChar != SPACE && currentChar != TAB) {
                appendIfAllowed(sb, currentChar);
            } else if (lastAppended != SPACE && lastAppended != TAB) {
                appendIfAllowed(sb, currentChar);
            }
            lastAppended = currentChar;
        }

        return sb.toString();
    }

    /**
     * Присоединение символа к строке, если символ разрешён.
     *
     * @param sb StringBuilder
     * @param ch char
     */
    public void appendIfAllowed(@NotNull StringBuilder sb, char ch) {
        if (allowedChars.indexOf(ch) < 0) {
            return;
        }
        sb.append(ch);
    }

    /**
     * Нормализация строки. Только капитализация с затрагиванием всех символов строки.<br>
     *
     * @param str ненормализованная строка; пример: иВаНОв
     * @return нормализованная строка; пример: Иванов;<br>
     * для null возвращает null, <br>
     * для пустой строки возвращает ту же строку,<br>
     * для строки с одним символом возвращает строку с нормализованным символом (и -> И)
     */
    @Nullable
    public static String normalize(@Nullable String str) {
        if (str == null) {
            return null;
        }
        str = str.trim().toLowerCase();
        if (str.isEmpty()) {
            return str;
        }
        if (str.length() == 1) {
            return str.toUpperCase();
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
