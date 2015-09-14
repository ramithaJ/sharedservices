package com.wiley.gr.ace.search.service;

import com.wiley.gr.ace.search.model.Response;
import com.wiley.gr.ace.search.model.SearchCriteria;

/**
 * Created by KKALYAN on 7/2/2015.
 */
public interface SearchService {

    Response search(SearchCriteria searchCriteria);
}
