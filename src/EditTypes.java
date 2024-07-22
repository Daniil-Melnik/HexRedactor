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
