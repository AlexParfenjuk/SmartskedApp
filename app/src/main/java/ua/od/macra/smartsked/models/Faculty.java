package ua.od.macra.smartsked.models;

public class Faculty {
    private int instId;
    private int facuId;
    private String name;

    public Faculty(int instId, int facuId, String name) {
        this.instId = instId;
        this.facuId = facuId;
        this.name = name;
    }

    public int getInstId() {
        return instId;
    }

    public int getFacuId() {
        return facuId;
    }

    public String getName() {
        return name;
    }
}
