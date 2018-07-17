package com.snow.lib.enums;

/**
 * Created by GongWenHua on 17.12.17.
 */
public enum ResultEnum implements IEnum<Integer> {
    REQUEST_MISMATCH_ERROR(300, "请求参数不匹配"),
    VALIDATE_ALREADY_EXIST(501, "该数据已经存在"),
    VALIDATE_NOT_EXIST(502, "该数据不存在"),
    VALIDATE_ERROR(503, "该数据不符合条件"),
    GET_ERROR(504, "获取失败"),
    DEL_ERROR(505, "删除失败"),
    SAVE_ERROR(506, "保存失败");

    private String message;
    private Integer index;

    ResultEnum(Integer index, String message) {
        this.index = index;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return index;
    }

    @Override
    public String getMsg() {
        return message;
    }
}
