package com.arslankucukkafa.labormarketauth.action;


public enum Action {
    CREATE("CREATE"),
    READ("READ"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private String value;

    private Action(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Action fromValue(String value) {
        for (Action action : Action.values()) {
            if (action.getValue().equals(value)) {
                return action;
            }
        }
        return null;
    }
}
