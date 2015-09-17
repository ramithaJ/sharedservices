package com.wiley.gr.ace.search.model;

import java.util.List;

/**
 * Created by KKALYAN on 9/13/2015.
 */
public class SearchCriteria {
	private String role;
    private String appKey;
    private List<String> types;
    private String advancedQuery;
    private String simpleQuery;
    private List<Sorting> sort;
    private List<Filter> Filters;
    private int offset = 0;
    private int rows = 10;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getAdvancedQuery() {
        return advancedQuery;
    }

    public void setAdvancedQuery(String advancedQuery) {
        this.advancedQuery = advancedQuery;
    }

    public String getSimpleQuery() {
        return simpleQuery;
    }

    public void setSimpleQuery(String simpleQuery) {
        this.simpleQuery = simpleQuery;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public List<Sorting> getSort() {
        return sort;
    }

    public void setSort(List<Sorting> sort) {
        this.sort = sort;
    }

	public List<Filter> getFilters() {
		return Filters;
	}

	public void setFilters(List<Filter> filters) {
		Filters = filters;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
