package com.lhfeiyu.util.dust;

import java.io.Serializable;
import java.util.List;

public class ResultGridData<T> implements Serializable{

    private static final long serialVersionUID = 1433195195928257170L;

    /** 总条数   */
    private Integer total;
    /** 分页查询出的数据  */
    private List<T> rows;
    
    public ResultGridData(){}
    public ResultGridData(Integer total,List<T> rows){
        this.total = total;
        this.rows = rows;
    }
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public List<T> getRows() {
        return rows;
    }
    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
