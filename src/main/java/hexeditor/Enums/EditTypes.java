package main.java.hexeditor.Enums;

/**
 * Enum containing the values of the types modification operations for editPanel
 * checkbox
 * 
 * @author DMelnik
 * @version 1.0
 */

public enum EditTypes {
    COPY("Копировать"),
    INSERT("Вставить"),
    DELETE("Удалить"),
    CUT("Вырезать"),
    INSERT_ZERO("Вставить нули");

    private final String description;

    EditTypes(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
