package sanjiang.model;


import java.util.List;

/**
 * Created by mac on 15/9/10.
 */
public class NativeQueryModel {

    private String selectFields;

    private String fromWhereClause;

    private String orderByClause;

    private List<Object> params;

    private Integer page;

    private Integer size;

    public String getSelectFields() {
        return selectFields;
    }

    public void setSelectFields(String selectFields) {
        this.selectFields = selectFields;
    }

    public String getFromWhereClause() {
        return fromWhereClause;
    }

    public void setFromWhereClause(String fromWhereClause) {
        this.fromWhereClause = fromWhereClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
