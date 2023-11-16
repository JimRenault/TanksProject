package com.tk.domain;

import com.tk.BaseEntity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * tanke对象 tk
 *
 * @author ysl
 * @date 2023-11-08
 */
public class Tk extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 坦克ID
     */
    private Long tkId;

    /**
     * 所属对局ID
     */
    private Long filesId;

    /**
     * 坦克名称
     */
    private String tkName;

    /**
     * 所属X轴
     */
    private String tkX;

    /**
     * 所属Y轴
     */
    private String tkY;

    /**
     * 坦克朝向
     */
    private String tkOrient;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getTkId() {
        return tkId;
    }

    public void setTkId(Long tkId) {
        this.tkId = tkId;
    }

    public Long getFilesId() {
        return filesId;
    }

    public void setFilesId(Long filesId) {
        this.filesId = filesId;
    }

    public String getTkName() {
        return tkName;
    }

    public void setTkName(String tkName) {
        this.tkName = tkName;
    }

    public String getTkX() {
        return tkX;
    }

    public void setTkX(String tkX) {
        this.tkX = tkX;
    }

    public String getTkY() {
        return tkY;
    }

    public void setTkY(String tkY) {
        this.tkY = tkY;
    }

    public String getTkOrient() {
        return tkOrient;
    }

    public void setTkOrient(String tkOrient) {
        this.tkOrient = tkOrient;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("tkId", getTkId())
                .append("filesId", getFilesId())
                .append("tkName", getTkName())
                .append("tkX", getTkX())
                .append("tkY", getTkY())
                .append("updateTime", getUpdateTime())
                .append("tkOrient", getTkOrient())
                .toString();
    }
}
