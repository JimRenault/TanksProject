package com.tk.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tk.BaseEntity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 对局对象 files
 *
 * @author ysl
 * @date 2023-11-09
 */
public class Files extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 存档表ID
     */
    private Long filesId;

    /**
     * 存档名
     */
    private String filesName;

    /**
     * 最后一次存档日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTine;

    public void setFilesId(Long filesId) {
        this.filesId = filesId;
    }

    public Long getFilesId() {
        return filesId;
    }

    public void setFilesName(String filesName) {
        this.filesName = filesName;
    }

    public String getFilesName() {
        return filesName;
    }

    public void setUpdateTine(Date updateTine) {
        this.updateTine = updateTine;
    }

    public Date getUpdateTine() {
        return updateTine;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("filesId", getFilesId())
                .append("filesName", getFilesName())
                .append("updateTine", getUpdateTine())
                .toString();
    }
}
