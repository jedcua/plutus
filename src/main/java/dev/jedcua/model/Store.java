package dev.jedcua.model;

public final class Store {
    private final Long id;
    private final String name;
    private final String address;
    private final String tin;

    public Store(final Long id, final String name, final String address, final String tin) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tin = tin;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getTin() {
        return tin;
    }
}
