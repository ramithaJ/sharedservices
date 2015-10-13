/**
 * ****************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 * <p>
 * All material contained herein is proprietary to John Wiley & Sons
 * and its third party suppliers, if any. The methods, techniques and
 * technical concepts contained herein are considered trade secrets
 * and confidential and may be protected by intellectual property laws.
 * Reproduction or distribution of this material, in whole or in part,
 * is strictly forbidden except by express prior written permission
 * of John Wiley & Sons.
 * *****************************************************************************
 */
package com.wiley.gr.ace.search.service.impl;

import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.suggest.SuggestRequestBuilder;
import org.elasticsearch.action.suggest.SuggestResponse;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.FilteredQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionFuzzyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import com.wiley.gr.ace.search.constant.CommonConstants;
import com.wiley.gr.ace.search.constant.Property;
import com.wiley.gr.ace.search.exception.SharedSearchException;
import com.wiley.gr.ace.search.model.AdvanceQuery;
import com.wiley.gr.ace.search.model.AutoSuggestResponse;
import com.wiley.gr.ace.search.model.Facets;
import com.wiley.gr.ace.search.model.Filter;
import com.wiley.gr.ace.search.model.Hits;
import com.wiley.gr.ace.search.model.Items;
import com.wiley.gr.ace.search.model.Response;
import com.wiley.gr.ace.search.model.SearchCriteria;
import com.wiley.gr.ace.search.model.SiteSearchRequest;
import com.wiley.gr.ace.search.model.Sorting;
import com.wiley.gr.ace.search.model.SuggestCriteria;
import com.wiley.gr.ace.search.model.Tags;
import com.wiley.gr.ace.search.service.SearchClientService;
import com.wiley.gr.ace.search.service.SearchService;

/**
 * The Class SearchServiceImpl.
 *
 * @author virtusa version 1.0
 */
public class SearchServiceImpl extends Property implements SearchService {

    /** The search client service. */
    @Autowired
    private SearchClientService searchClientService;

    /** The index name. */
    @Value("${index.name}")
    private String indexName;

    /** The exact fields. */
    @Value("${EXACT_FIELDS}")
    private String exactFields;

    /** The range fields. */
    @Value("${RANGE_FIELDS}")
    private String rangeFields;

    /** The error mesage. */
    @Value("${GENERIC_ERROR_MESSAGE}")
    private String errorMesage;

    /** The search properties. */
    @Autowired
    @Qualifier(value = "searchProperties")
    private Properties searchProperties;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(SearchServiceImpl.class);

    /**
     * Method to search against ES index.
     *
     * @param searchCriteria
     *            - the input value
     * @param role
     *            - the input value
     * @return response
     * @throws SharedSearchException
     *             the shared search exception
     */
    public Response search(SearchCriteria searchCriteria, String role)
            throws SharedSearchException {

        Response searchResponse = new Response();
        List<String> aggregationList = null;

        // Check User Role
        checkUserRole(role);

        SearchRequestBuilder requestBuilder = searchClientService.getClient()
                .prepareSearch(indexName)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(getQueryBuilder(searchCriteria, role));

        // Get Source Value every time
        requestBuilder.setTrackScores(true);

        // Set Types
        setTypes(requestBuilder, searchCriteria.getTypes());

        // Add Aggregations
        if (searchCriteria.isEnableFacets()) {
            aggregationList = addAggregations(requestBuilder,
                    searchCriteria.getTypes());
        }
        // Apply Page Navigation
        setResultSize(requestBuilder, searchCriteria.getOffset(),
                searchCriteria.getRows());

        // Apply Sort if exists
        addSort(searchCriteria, requestBuilder);

        // Execute the request
        SearchResponse response = requestBuilder.execute().actionGet();

        // Prepare response
        searchResponse = prepareResponse(searchResponse, response, role);

        // Set Facets to the response
        if (searchCriteria.isEnableFacets()) {
            searchResponse
                    .setFacets(getAggregations(response, aggregationList));
        }
        return searchResponse;
    }

    /**
     * Method to check whether user role exists or not.
     *
     * @param role
     *            the role
     * @throws SharedSearchException
     *             the shared search exception
     */
    private void checkUserRole(String role) throws SharedSearchException {
        boolean isRoleExists = false;
        final String roles = searchProperties
                .getProperty(CommonConstants.ROLES);

        StringTokenizer stringTokenizer = new StringTokenizer(roles,
                CommonConstants.COMMA);

        while (stringTokenizer.hasMoreTokens()) {
            if (role.equals(stringTokenizer.nextToken())) {
                isRoleExists = true;
                break;
            }
        }

        if (!isRoleExists) {
            throw new SharedSearchException(CommonConstants.ERROR_CODE_101,
                    searchServiceError101);
        }
    }

    /**
     * This method sets the Filter.
     *
     * @param searchCriteria
     *            - the input value
     * @return the filter builder
     * @throws SharedSearchException
     *             the shared search exception
     */
    private FilterBuilder setFilter(SearchCriteria searchCriteria)
            throws SharedSearchException {
        FilterBuilder filterbuilder = null;
        Filter filter = null;
        List<FilterBuilder> filterBuilderList = null;

        try {
            filter = searchCriteria.getFilters();
            filterBuilderList = new ArrayList<FilterBuilder>();
            if (filter != null) {
                Map<String, List<String>> filterMap = filter.getTerm();
                for (String key : filterMap.keySet()) {
                    List<String> valueList = filterMap.get(key);
                    if (valueList != null && !valueList.isEmpty()) {
                        filterBuilderList.add(FilterBuilders.boolFilter()
                                .should(FilterBuilders.termsFilter(key,
                                        valueList)));
                    }
                }

                if (filterBuilderList != null && !filterBuilderList.isEmpty()) {
                    filterbuilder = FilterBuilders
                            .andFilter(filterBuilderList
                                    .toArray(new FilterBuilder[filterBuilderList
                                            .size() - 1]));
                }
            }
        } catch (Exception e) {
            LOGGER.error(errorMesage + " setFilter", e);
            throw new SharedSearchException(CommonConstants.ERROR_CODE_100,
                    CommonConstants.ERROR_NOTE
                            + CommonConstants.INTERNAL_SERVER_ERROR);
        }
        return filterbuilder;
    }

    /**
     * Method to set data types in the index.
     *
     * @param requestBuilder
     *            - the input value
     * @param list
     *            the list
     * @throws SharedSearchException
     *             the shared search exception
     */
    private void setTypes(SearchRequestBuilder requestBuilder, List<String> list)
            throws SharedSearchException {
        try {
            String[] types = list.toArray(new String[0]);
            requestBuilder.setTypes(types);
        } catch (Exception e) {
            LOGGER.error(errorMesage + " setTypes", e);
            throw new SharedSearchException(CommonConstants.ERROR_CODE_100,
                    CommonConstants.ERROR_NOTE
                            + CommonConstants.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to add aggregations.
     *
     * @param requestBuilder
     *            the request builder
     * @param types
     *            the types
     * @return the list
     * @throws SharedSearchException
     *             the shared search exception
     */
    private List<String> addAggregations(SearchRequestBuilder requestBuilder,
            List<String> types) throws SharedSearchException {

        StringTokenizer aggregationFields = null;
        List<String> aggregationList = null;

        try {

            aggregationList = new ArrayList<>();
            for (String type : types) {
                aggregationFields = new StringTokenizer(
                        searchProperties.getProperty(type.toUpperCase()
                                + CommonConstants.SEARCH_FACET_ATTRIBUTES),
                        CommonConstants.COMMA);
                while (aggregationFields.hasMoreTokens()) {
                    aggregationList.add(aggregationFields.nextToken());
                }
            }

            for (String aggregationAttribute : aggregationList) {
                requestBuilder.addAggregation(terms(aggregationAttribute)
                        .field(aggregationAttribute).size(0));
            }

        } catch (Exception e) {
            LOGGER.error(errorMesage + " addAggregations", e);
            throw new SharedSearchException(CommonConstants.ERROR_CODE_100,
                    CommonConstants.ERROR_NOTE
                            + CommonConstants.INTERNAL_SERVER_ERROR);
        }
        return aggregationList;
    }

    /**
     * Method to prepare response.
     *
     * @param searchResponse
     *            - the input value
     * @param response
     *            - the input value
     * @param role
     *            the role
     * @return response
     * @throws SharedSearchException
     *             the shared search exception
     */
    private Response prepareResponse(Response searchResponse,
            SearchResponse response, String role) throws SharedSearchException {
        List<Hits> hitsList = new LinkedList<Hits>();
        StringTokenizer searchFieldStringTokens = null;

        try {
            SearchHit[] results = response.getHits().getHits();
            searchResponse.setMaxScore(response.getHits().getMaxScore());
            searchResponse.setTotal(response.getHits().getTotalHits());
            searchResponse.setTook(response.getTookInMillis());

            for (SearchHit hit : results) {
                Hits searchHit = new Hits();

                searchFieldStringTokens = getSearchResultAttributeTokens(role,
                        hit.getType());
                searchHit.setId(hit.getId());
                searchHit.setScore(hit.getScore());
                searchHit.setType(hit.getType());

                Map<String, Object> filteredSource = new HashMap<String, Object>();
                Map<String, Object> tempMap = hit.getSource();

                while (searchFieldStringTokens.hasMoreTokens()) {
                    String key = searchFieldStringTokens.nextToken();
                    filteredSource.put(key, tempMap.get(key));
                }

                searchHit.setSource(filteredSource);

                hitsList.add(searchHit);
            }
            searchResponse.setHits(hitsList);
        } catch (Exception e) {
            LOGGER.error(errorMesage + " prepareResponse", e);
            throw new SharedSearchException(CommonConstants.ERROR_CODE_100,
                    CommonConstants.ERROR_NOTE
                            + CommonConstants.INTERNAL_SERVER_ERROR);
        }
        return searchResponse;
    }

    /**
     * This method returns the tokens of the required fields.
     *
     * @param role
     *            the role
     * @param type
     *            the type
     * @return searchFieldStringTokens
     * @throws SharedSearchException
     *             the shared search exception
     */
    private StringTokenizer getSearchResultAttributeTokens(String role,
            String type) throws SharedSearchException {
        StringTokenizer searchFieldStringTokens = null;
        try {
            searchFieldStringTokens = new StringTokenizer(
                    searchProperties.getProperty(role
                            + CommonConstants.UNDERSCORE + type.toUpperCase()
                            + CommonConstants.SEARCH_RESULT_ATTR_KEY_STRING),
                    CommonConstants.COMMA);
        } catch (Exception e) {
            LOGGER.error(errorMesage + " getSearchFiledAttributeTokenizers", e);
            throw new SharedSearchException(CommonConstants.ERROR_CODE_100,
                    CommonConstants.ERROR_NOTE
                            + CommonConstants.INTERNAL_SERVER_ERROR);
        }
        return searchFieldStringTokens;

    }

    /**
     * Method to set Page Navigation.
     *
     * @param requestBuilder
     *            - the input value
     * @param from
     *            - the input value
     * @param rows
     *            - the input value
     */
    private void setResultSize(SearchRequestBuilder requestBuilder, int from,
            int rows) {
        requestBuilder.setFrom(from).setSize(rows);
    }

    /**
     * /** Method to sort the results.
     *
     * @param searchCriteria
     *            - the input value
     * @param requestBuilder
     *            - the input value
     * @throws SharedSearchException
     *             the shared search exception
     */
    private void addSort(SearchCriteria searchCriteria,
            SearchRequestBuilder requestBuilder) throws SharedSearchException {
        try {
            List<Sorting> sortList = searchCriteria.getSort();
            boolean isDescOrder = false;
            if (sortList != null && !sortList.isEmpty()) {
                for (Sorting sorting : sortList) {
                    if (sorting.getSortOrder().equalsIgnoreCase(
                            CommonConstants.DESC)) {
                        isDescOrder = true;
                    }
                    final SortOrder sortOrder = isDescOrder ? SortOrder.DESC
                            : SortOrder.ASC;
                    requestBuilder.addSort(sorting.getSortBy(), sortOrder);
                }
            } else {
                requestBuilder.addSort(CommonConstants.SCORE, SortOrder.DESC);
            }
        } catch (Exception e) {
            LOGGER.error(errorMesage + " addSort", e);
            throw new SharedSearchException(CommonConstants.ERROR_CODE_100,
                    CommonConstants.ERROR_NOTE
                            + CommonConstants.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to get facets from the response.
     *
     * @param response
     *            the response
     * @param aggregationList
     *            the aggregation list
     * @return the aggregations
     * @throws SharedSearchException
     *             the shared search exception
     */
    private Facets getAggregations(SearchResponse response,
            List<String> aggregationList) throws SharedSearchException {
        Facets facets = new Facets();
        Tags tags = new Tags();
        int bucketSize = 0;
        List<Items> itemsLinkedList = new LinkedList<>();
        try {
            if (aggregationList != null && !aggregationList.isEmpty()) {
                for (String fieldValue : aggregationList) {

                    Terms terms = response.getAggregations().get(fieldValue);
                    Collection<Terms.Bucket> buckets = terms.getBuckets();
                    bucketSize = bucketSize + buckets.size();
                    for (Terms.Bucket bucket : buckets) {
                        Items items = new Items();
                        items.setField(fieldValue);
                        items.setCount(bucket.getDocCount());
                        items.setTerm(bucket.getKeyAsText().string());
                        itemsLinkedList.add(items);
                        tags.setTerms(itemsLinkedList);
                        tags.setTotal(bucketSize);
                        facets.setTag(tags);
                    }
                }
            }

        } catch (Exception e) {
            LOGGER.error(errorMesage + " getAggregations", e);
            throw new SharedSearchException(CommonConstants.ERROR_CODE_100,
                    CommonConstants.ERROR_NOTE
                            + CommonConstants.INTERNAL_SERVER_ERROR);
        }
        return facets;
    }

    /**
     * Method to get query builder.
     *
     * @param searchCriteria
     *            - the input value
     * @param role
     *            - the input value
     * @return matchQueryBuilder
     * @throws SharedSearchException
     *             the shared search exception
     */
    protected FilteredQueryBuilder getQueryBuilder(
            SearchCriteria searchCriteria, String role)
            throws SharedSearchException {
        FilterBuilder filterbuilder = null;
        FilteredQueryBuilder fileterQuery = null;
        QueryBuilder matchQueryBuilder = null;
        List<String> searchFiledsList = null;
        String[] searchFiledsArray = null;
        List<String> types = null;

        LOGGER.info("Inside getQueryBuilder method");
        StringTokenizer searchFields = null;

        String simpleQuery = searchCriteria.getSimpleQuery();

        types = searchCriteria.getTypes();

        try {
            LOGGER.info("ROLE : " + role);

            searchFiledsList = new ArrayList<String>();

            searchFiledsList = new ArrayList<String>();
            for (String type : types) {
                searchFields = new StringTokenizer(
                        searchProperties
                                .getProperty(role
                                        + CommonConstants.UNDERSCORE
                                        + type.toUpperCase()
                                        + CommonConstants.SEARCH_SEARCH_ATTR_KEY_STRING),
                        CommonConstants.COMMA);
                while (searchFields.hasMoreTokens()) {
                    searchFiledsList.add(searchFields.nextToken());
                }
            }

            searchFiledsArray = searchFiledsList
                    .toArray(new String[searchFiledsList.size() - 1]);

            if (StringUtils.isBlank(simpleQuery)) {

                LOGGER.info(" Simple Query is Blank ");
                if (null != searchCriteria.getAdvanceQuery()) {
                    matchQueryBuilder = advancedSearch(searchCriteria);
                }
            } else {
                LOGGER.info(" Simple Query is not Blank ");
                matchQueryBuilder = QueryBuilders.multiMatchQuery(simpleQuery,
                        searchFiledsArray).type(
                        MatchQueryBuilder.Type.PHRASE_PREFIX);
            }
        } catch (Exception e) {
            LOGGER.error(errorMesage + " getQueryBuilder", e);
            throw new SharedSearchException(CommonConstants.ERROR_CODE_100,
                    CommonConstants.ERROR_NOTE
                            + CommonConstants.INTERNAL_SERVER_ERROR);
        }
        filterbuilder = setFilter(searchCriteria);
        fileterQuery = QueryBuilders.filteredQuery(matchQueryBuilder,
                filterbuilder);
        return fileterQuery;
    }

    /**
     * Method returns simple searched query result.
     *
     * @param searchCriteria
     *            - the input value
     * @return queryBuilder
     * @throws SharedSearchException
     *             the shared search exception
     */
    private QueryBuilder advancedSearch(SearchCriteria searchCriteria)
            throws SharedSearchException {

        LOGGER.info("Inside advancedSearch method");

        List<AdvanceQuery> queryString = searchCriteria.getAdvanceQuery();

        BoolQueryBuilder boolQuery = new BoolQueryBuilder();
        for (AdvanceQuery query : queryString) {
            if (exactFields.contains(query.getField())) {
                addExactMatchBuilder(query, boolQuery);
            } else if (rangeFields.contains(query.getField())) {
                addRangeBuilder(query, boolQuery);
            } else {
                addWildCardBuilder(query, boolQuery);
            }
        }
        return boolQuery;

    }

    /**
     * Method adds wild card builder.
     *
     * @param query
     *            - the input value
     * @param boolQuery
     *            - the input value
     */
    private void addWildCardBuilder(AdvanceQuery query,
            BoolQueryBuilder boolQuery) {

        LOGGER.info("Inside addwildcardBuilder method");

        boolQuery.must(QueryBuilders.matchPhrasePrefixQuery(query.getField(),
                query.getValue()));
    }

    /**
     * Method adds exact match builder.
     *
     * @param query
     *            - the input value
     * @param boolQuery
     *            - the input value
     */
    private void addExactMatchBuilder(AdvanceQuery query,
            BoolQueryBuilder boolQuery) {
        LOGGER.info("Inside addexactMatchBuilder method");

        boolQuery.must(QueryBuilders.matchQuery(query.getField(),
                query.getValue()));
    }

    /**
     * Method adds range builder.
     *
     * @param query
     *            - the input value
     * @param boolQuery
     *            - the input value
     * @throws SharedSearchException
     *             the shared search exception
     */
    private void addRangeBuilder(AdvanceQuery query, BoolQueryBuilder boolQuery)
            throws SharedSearchException {
        LOGGER.info("Inside addrangeBuilder method");
        try {
            if (StringUtils.isBlank(query.getTo())) {
                query.setTo(null);
            }
            if (StringUtils.isBlank(query.getFrom())) {
                query.setFrom(null);
            }
            boolQuery.must(QueryBuilders.rangeQuery(query.getField())
                    .from(query.getFrom()).to(query.getTo()));
        } catch (Exception e) {
            LOGGER.error(errorMesage + " addrangeBuilder", e);
            throw new SharedSearchException(CommonConstants.ERROR_CODE_100,
                    CommonConstants.ERROR_NOTE
                            + CommonConstants.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method returns suggestion response.
     *
     * @param criteria
     *            - the input value
     * @return suggestResponse.
     * @throws SharedSearchException
     *             the shared search exception
     */
    @Override
    public AutoSuggestResponse autoComplete(SuggestCriteria criteria)
            throws SharedSearchException {
        AutoSuggestResponse sugesstions = new AutoSuggestResponse();
        List<String> items;
        SuggestResponse suggestResponse;
        try {
            CompletionSuggestionFuzzyBuilder suggestionsBuilder = new CompletionSuggestionFuzzyBuilder(
                    CommonConstants.AUTOCOMPLETE_ANALYZER);
            suggestionsBuilder.text(criteria.getAutoCompleteQuery().getValue());
            suggestionsBuilder.field(criteria.getAutoCompleteQuery().getField()
                    + CommonConstants.AUTOCOMPLETE_FIELD_SUFFIX);
            suggestionsBuilder.size(criteria.getSize());
            SuggestRequestBuilder suggestRequestBuilder = searchClientService
                    .getClient().prepareSuggest(indexName)
                    .addSuggestion(suggestionsBuilder);

            suggestResponse = suggestRequestBuilder.execute().actionGet();
            Iterator<? extends Suggest.Suggestion.Entry.Option> iterator = suggestResponse
                    .getSuggest()
                    .getSuggestion(CommonConstants.AUTOCOMPLETE_ANALYZER)
                    .iterator().next().getOptions().iterator();

            items = new ArrayList<>();
            while (iterator.hasNext()) {
                Suggest.Suggestion.Entry.Option next = iterator.next();
                items.add(new String(next.getText().string()));
            }
        } catch (Exception e) {
            LOGGER.error(errorMesage + " autoComplete", e);
            throw new SharedSearchException(CommonConstants.ERROR_CODE_100,
                    CommonConstants.ERROR_NOTE
                            + CommonConstants.INTERNAL_SERVER_ERROR);
        }
        sugesstions.setSuggestions(items);
        sugesstions.setTotal(items.size());
        return sugesstions;
    }

    @Override
    public List<SearchResponse> siteSearch(SiteSearchRequest request)
            throws SharedSearchException {
        List<String> types = request.getTypes();
        List<SearchResponse> searchResponses = new LinkedList<SearchResponse>();
        SearchResponse searchResponse = null;
        SearchRequestBuilder requestBuilder = null;
        String[] searchFields = request.getSearchFields().toArray(
                new String[request.getSearchFields().size() - 1]);
        String[] responseFields = request.getResponseFields().toArray(
                new String[request.getResponseFields().size() - 1]);
        for (String type : types) {
            requestBuilder = searchClientService
                    .getClient()
                    .prepareSearch(indexName)
                    .setTypes(type)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(
                            siteQueryBulider(request.getQuery(), searchFields))
                    .setSize(1).addFields(responseFields);
            searchResponse = requestBuilder.execute().actionGet();
            searchResponses.add(searchResponse);
        }
        return searchResponses;
    }

    private QueryBuilder siteQueryBulider(String query, String[] fields) {
        QueryBuilder queryBuilder = null;
        queryBuilder = QueryBuilders.multiMatchQuery(query, fields).type(
                MatchQueryBuilder.Type.PHRASE_PREFIX);

        return queryBuilder;

    }

}
