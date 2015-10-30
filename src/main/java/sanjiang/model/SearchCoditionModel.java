package sanjiang.model;

import java.io.Serializable;

/**
 * Created by mac on 15/9/2.
 */
public class SearchCoditionModel<T> implements Serializable{

    private int page;
    private int size;

    private T searchCodition;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public T getSearchCodition() {
        return searchCodition;
    }

    public void setSearchCodition(T searchCodition) {
        this.searchCodition = searchCodition;
    }
}
