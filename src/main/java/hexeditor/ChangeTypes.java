package main.java.hexeditor;

public enum ChangeTypes {
    MODIFI_CELL("0"),
    DELETE_WITH_ZEROING("1"),
    DELETE_WITH_SHIFT("2"),
    INSERT_WITH_REPLACE("3"),
    INSERT_WITH_SHIFT("4"),
    INSERT_ZEROS("7");

    private final String value;

    ChangeTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
