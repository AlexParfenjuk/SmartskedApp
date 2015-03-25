package ua.od.macra.smartskedapp.models;

public class Group {
    private int instId;
    private int facuId;
    private int groupId;
    private String name;

    public Group(Faculty faculty, int groupId, String name){
        this.instId = faculty.getInstId();
        this.facuId = faculty.getFacuId();
        this.groupId = groupId;
        this.name = name;
    }

    public Group(int instId, int facuId, int groupId, String name) {
        this.instId = instId;
        this.facuId = facuId;
        this.groupId = groupId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getFacuId() {
        return facuId;
    }

    public int getInstId() {
        return instId;
    }
}
