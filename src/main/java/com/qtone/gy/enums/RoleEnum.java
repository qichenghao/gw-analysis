package com.qtone.gy.enums;

/**
 * 系统角色
 */
public enum RoleEnum {
    SUPER_ADMIN(-1,"SUPPER_ADMIN", "系统最高权限"),
    ADMIN(0, "ADMIN","管理员"),
    TEACHER(1, "TEACHER", "教师"),
    STUDENT(2, "STUDENT","学生"),
    PARENT(3,"PARENT", "家长");

    private int id;

    private String code;

    private String desc;

    RoleEnum(int id, String code, String desc) {
        this.id = id;
        this.code = code;
        this.desc = desc;
    }

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 获取描述
     * @param id
     * @return
     */
    public static String getDesc(int id){
        for(RoleEnum roleEnum : RoleEnum.values()){
            if(roleEnum.getId() == id){
                return roleEnum.getDesc();
            }
        }
        return null;
    }
}
