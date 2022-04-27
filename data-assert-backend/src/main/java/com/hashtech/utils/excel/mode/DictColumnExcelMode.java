package com.hashtech.utils.excel.mode;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class DictColumnExcelMode extends BaseRowModel {

    /**
     * 第一列的数据
     */
    @ExcelProperty(index = 0)
    private String columnKey;
    /**
     * 第二列的数据
     */
    @ExcelProperty(index = 1)
    private String columnValue;
    /**
     * 第三列的数据
     */
    @ExcelProperty(index = 2)
    private String remark;

}
