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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.wiley.gr.ace.search.constant.CommonConstants;
import com.wiley.gr.ace.search.exception.SharedSearchException;
import com.wiley.gr.ace.search.model.Facets;
import com.wiley.gr.ace.search.model.Filter;
import com.wiley.gr.ace.search.model.Hits;
import com.wiley.gr.ace.search.model.Items;
import com.wiley.gr.ace.search.model.Response;
import com.wiley.gr.ace.search.model.SearchCriteria;
import com.wiley.gr.ace.search.model.SimpleQuery;
import com.wiley.gr.ace.search.model.Sorting;
import com.wiley.gr.ace.search.model.Tags;
import com.wiley.gr.ace.search.service.SearchClientService;
import com.wiley.gr.ace.search.service.SearchService;

/**
 * The Class SearchServiceImpl.
 *
 * @author virtusa version 1.0
 */
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchClientService searchClientService;

    @Value("${index.name}")
    private String indexName;

    @Value("${admin.journal.search.fields}")
    private String adminJournalSearchFields;

    @Value("${admin.journal.search.result.fields}")
    private String adminJournalSearchResultFields;

    @Value("${admin.article.search.fields}")
    private String adminArticleSearchFields;

    @Value("${admin.article.search.result.fields}")
    private String adminArticleSearchResultFields;

    @Value("${registered.journal.search.fields}")
    private String registeredJournalSearchFields;

    @Value("${registered.journal.search.result.fields}")
    private String registeredJournalSearchResultFields;

    @Value("${registered.article.search.fields}")
    private String registeredArticleSearchFields;

    @Value("${registered.article.search.result.fields}")
    private String registeredArticleSearchResultFields;

    @Value("${guest.journal.search.fields}")
    private String guestJournalSearchFields;

    @Value("${guest.journal.search.result.fields}")
    private String guestJournalSearchResultFields;

    @Value("${guest.article.search.fields}")
    private String guestArticleSearchFields;

    @Value("${guest.article.search.result.fields}")
    private String guestArticleSearchResultFields;

    @Value("${wildcard.fields}")
    private String wildcardFields;

    @Value("${exact.fields}")
    private String exactFields;

    @Value("${range.fields}")
    private String rangeFields;

    @Value("${GENERIC_ERROR_MESSAGE}")
    private String errorMesage;

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
     */
    public Response search(SearchCriteria searchCriteria, String role)
            throws SharedSearchException {
        Response searchResponse = new Response();

        SearchRequestBuilder requestBuilder = searchClientService.getClient()
                .prepareSearch(indexName)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(getQueryBuilder(searchCriteria, role));

        // Get Source Value everytime
        requestBuilder.setTrackScores(true);

        // Set post Filter if facets selected
        setFilter(requestBuilder, searchCriteria);

        // Set Types
        setTypes(requestBuilder, searchCriteria.getTypes());

        // Add Aggregations
        addAggregations(requestBuilder, "journal_type,article_title");

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
        searchResponse.setFacets(getAggregations(response,
                "journal_type,article_title"));

        return searchResponse;
    }

    /**
     * This method sets the Filter.
     * 
     * @param requestBuilder
     *            - the input value
     * @param searchCriteria
     *            - the input value
     * @throws SharedSearchException
     */
    private void setFilter(SearchRequestBuilder requestBuilder,
            SearchCriteria searchCriteria) throws SharedSearchException {

        Filter filter = null;
        List<FilterBuilder> filterBuilderList = null;
        FilterBuilder filterbuilder = null;

        try {
            filter = searchCriteria.getFilters();
            filterBuilderList = new ArrayList<FilterBuilder>();
            if (filter != null) {
                Map<String, List<String>> filterMap = filter.getTerm();
                for (String key : filterMap.keySet()) {
                    List<String> valueList = filterMap.get(key);
                    for (String value : valueList) {
                        filterBuilderList.add(FilterBuilders.prefixFilter(key,
                                value));
                    }

                }

                if (filterBuilderList != null && !filterBuilderList.isEmpty()) {
                    filterbuilder = FilterBuilders
                            .andFilter(filterBuilderList
                                    .toArray(new FilterBuilder[filterBuilderList
                                            .size() - 1]));

                    if (filterbuilder != null) {
                        requestBuilder.setPostFilter(filterbuilder);
                    }

                }
            }
        } catch (Exception e) {
            LOGGER.error(errorMesage + " setFilter", e);
            throw new SharedSearchException(CommonConstants.ERROR_CODE_100,
                    CommonConstants.ERROR_NOTE
                            + CommonConstants.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to set data types in the index.
     *
     * @param requestBuilder
     *            - the input value
     * @throws SharedSearchException
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
     *            - the input value
     * @param facetFields
     *            - the input value
     * @throws SharedSearchException
     */
    private void addAggregations(SearchRequestBuilder requestBuilder,
            String facetFields) throws SharedSearchException {
        try {
            String[] fields = facetFields.split(",");
            for (String field : fields) {
                requestBuilder
                        .addAggregation(terms(field).field(field).size(0));
            }
        } catch (Exception e) {
            LOGGER.error(errorMesage + " addAggregations", e);
            throw new SharedSearchException(CommonConstants.ERROR_CODE_100,
                    CommonConstants.ERROR_NOTE
                            + CommonConstants.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to prepare response.
     *
     * @param searchResponse
     *            - the input value
     * @param response
     *            - the input value
     * @return response
     * @throws SharedSearchException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Response prepareResponse(Response searchResponse,
            SearchResponse response, String role) throws SharedSearchException {
        List<Hits> hitsList = new LinkedList();
        StringTokenizer searchFieldStringTokens = null;

        try {
            SearchHit[] results = response.getHits().getHits();
            searchResponse.setMax_score(response.getHits().getMaxScore());
            searchResponse.setTotal(response.getHits().getTotalHits());
            searchResponse.setTook(response.getTookInMillis());

            for (SearchHit hit : results) {
                Hits searchHit = new Hits();

                searchFieldStringTokens = getSearchFiledAttributeTokenizers(
                        role, hit.getType());
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
     * This method returns the tokens of the required fields
     * 
     * @param role
     * @param type
     * @return searchFieldStringTokens
     * @throws SharedSearchException
     */
    private StringTokenizer getSearchFiledAttributeTokenizers(String role,
            String type) throws SharedSearchException {
        StringTokenizer searchFieldStringTokens = null;

        try {
            if ("journal".equals(type)) {
                if (CommonConstants.ROLE_ADMIN.equals(role)) {
                    searchFieldStringTokens = new StringTokenizer(
                            adminJournalSearchResultFields, ",");
                } else {
                    if (CommonConstants.ROLE_REGISTERED_USER.equals(role)) {
                        searchFieldStringTokens = new StringTokenizer(
                                registeredJournalSearchResultFields, ",");
                    } else
                        searchFieldStringTokens = new StringTokenizer(
                                guestJournalSearchResultFields, ",");
                }
            } else if ("article".equals(type)) {
                if (CommonConstants.ROLE_ADMIN.equals(role)) {
                    searchFieldStringTokens = new StringTokenizer(
                            adminArticleSearchResultFields, ",");
                } else {
                    if (CommonConstants.ROLE_REGISTERED_USER.equals(role)) {
                        searchFieldStringTokens = new StringTokenizer(
                                registeredArticleSearchResultFields, ",");
                    } else
                        searchFieldStringTokens = new StringTokenizer(
                                guestArticleSearchResultFields, ",");
                }

            }
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
     */
    private void addSort(SearchCriteria searchCriteria,
            SearchRequestBuilder requestBuilder) throws SharedSearchException {
        try {
            List<Sorting> sortList = searchCriteria.getSort();
            boolean isDescOrder = false;
            if (sortList != null && !sortList.isEmpty()) {
                for (Sorting sorting : sortList) {
                    if (sorting.getSortOrder().equalsIgnoreCase("DESC")) {
                        isDescOrder = true;
                    }
                    final SortOrder sortOrder = isDescOrder ? SortOrder.DESC
                            : SortOrder.ASC;
                    requestBuilder.addSort(sorting.getSortBy(), sortOrder);
                }
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
     *            - the input value
     * @param fields
     *            - the input value
     * @return facets
     * @throws SharedSearchException
     */
    private Facets getAggregations(SearchResponse response, String fields)
            throws SharedSearchException {
        Facets facets = new Facets();
        Tags tags = new Tags();
        int bucketSize = 0;
        LinkedList<Items> itemsLinkedList = new LinkedList<>();
        try {
            String[] fieldValues = fields.split(",");
            for (String fieldValue : fieldValues) {
                Terms terms = response.getAggregations().get(fieldValue);
                Collection<Terms.Bucket> buckets = terms.getBuckets();
                bucketSize = bucketSize + buckets.size();
                for (Terms.Bucket bucket : buckets) {
                    Items items = new Items();
                    items.setField(fieldValue);
                    items.setCount(bucket.getDocCount());
                    items.setTerm(bucket.getKeyAsText().string());
                    itemsLinkedList.add(items);
                }
            }
            tags.setTerms(itemsLinkedList);
            tags.setTotal(bucketSize);
            facets.setTag(tags);
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
     */
    protected QueryBuilder getQueryBuilder(SearchCriteria searchCriteria,
            String role) throws SharedSearchException {
        QueryBuilder matchQueryBuilder = null;
        List<String> searchFiledsList = null;
        String[] searchFiledsArray = null;
        List<String> types = null;

        LOGGER.info("Inside getQueryBuilder method");
        StringTokenizer journalSearchFields = null;
        StringTokenizer articleSearchFields = null;

        String advancedQuery = searchCriteria.getAdvancedQuery();

        types = searchCriteria.getTypes();

        try {
            if (types != null && !types.isEmpty()) {
                LOGGER.info("Types is not null");
                if (CommonConstants.ROLE_ADMIN.equals(role)) {
                    LOGGER.info("ROLE : " + role);

                    if (types.contains("journal")) {
                        LOGGER.info(" Types contain journal ");
                        journalSearchFields = new StringTokenizer(
                                adminJournalSearchFields, ",");
                    }

                    if (types.contains("article")) {
                        LOGGER.info(" Types contain article ");
                        articleSearchFields = new StringTokenizer(
                                adminArticleSearchFields, ",");
                    }

                } else {
                    if (CommonConstants.ROLE_REGISTERED_USER.equals(role)) {
                        LOGGER.info("ROLE : " + role);

                        if (types.contains("journal")) {
                            LOGGER.info(" Types contain journal ");
                            journalSearchFields = new StringTokenizer(
                                    registeredJournalSearchFields, ",");
                        }

                        if (types.contains("article")) {
                            LOGGER.info(" Types contain article ");
                            articleSearchFields = new StringTokenizer(
                                    registeredArticleSearchFields, ",");
                        }

                    } else {
                        LOGGER.info("ROLE : Guest");
                        if (types.contains("journal")) {
                            LOGGER.info(" Types contain journal ");
                            journalSearchFields = new StringTokenizer(
                                    guestJournalSearchFields, ",");
                        }

                        if (types.contains("article")) {
                            LOGGER.info(" Types contain article ");
                            articleSearchFields = new StringTokenizer(
                                    guestArticleSearchFields, ",");
                        }
                    }
                }

                searchFiledsList = new ArrayList<String>();

                if (journalSearchFields != null) {
                    while (journalSearchFields.hasMoreTokens()) {
                        searchFiledsList.add(journalSearchFields.nextToken());
                    }
                }

                if (articleSearchFields != null) {
                    while (articleSearchFields.hasMoreTokens()) {
                        searchFiledsList.add(articleSearchFields.nextToken());
                    }
                }

                searchFiledsArray = searchFiledsList
                        .toArray(new String[searchFiledsList.size() - 1]);

                if (StringUtils.isBlank(advancedQuery)) {

                    LOGGER.info(" Advanced Query is Blank ");
                    if (null != searchCriteria.getSimpleQuery())
                        matchQueryBuilder = simpleSearch(searchCriteria);
                    return matchQueryBuilder;

                } else {
                    LOGGER.info(" Advanced Query is not Blank ");
                    matchQueryBuilder = QueryBuilders.multiMatchQuery(
                            advancedQuery, searchFiledsArray).type(
                            MatchQueryBuilder.Type.PHRASE_PREFIX);
                }
            }
        } catch (Exception e) {
            LOGGER.error(errorMesage + " getQueryBuilder", e);
            throw new SharedSearchException(CommonConstants.ERROR_CODE_100,
                    CommonConstants.ERROR_NOTE
                            + CommonConstants.INTERNAL_SERVER_ERROR);
        }

        return matchQueryBuilder;
    }

    /**
     * Method returns simple searched query result.
     * 
     * @param searchCriteria
     *            - the input value
     * @return queryBuilder
     * @throws SharedSearchException
     */
    private QueryBuilder simpleSearch(SearchCriteria searchCriteria)
            throws SharedSearchException {

        LOGGER.info("Inside simpleSearch method");

        List<SimpleQuery> queryString = searchCriteria.getSimpleQuery();

        BoolQueryBuilder boolQuery = new BoolQueryBuilder();
        for (SimpleQuery query : queryString) {
            if (exactFields.contains(query.getField())) {
                addexactMatchBuilder(query, boolQuery);
            } else if (rangeFields.contains(query.getField())) {
                addrangeBuilder(query, boolQuery);
            } else {
                addwildcardBuilder(query, boolQuery);
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
    private void addwildcardBuilder(SimpleQuery query,
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
    private void addexactMatchBuilder(SimpleQuery query,
            BoolQueryBuilder boolQuery) {
        LOGGER.info("Inside addexactMatchBuilder method");

        boolQuery.must(QueryBuilders.termQuery(query.getField(),
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
     */
    private void addrangeBuilder(SimpleQuery query, BoolQueryBuilder boolQuery)
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
     * @param role
     *            - the input value
     * @return suggestResponse.
     * @throws SharedSearchException
     * 
     */
    @Override
    public SuggestResponse autoComplete(SearchCriteria criteria, String role)
            throws SharedSearchException {

        // Response searchResponse = new Response();

        SuggestResponse suggestResponse;
        try {
            CompletionSuggestionBuilder suggestionsBuilder = new CompletionSuggestionBuilder(
                    "completeMe");

            suggestionsBuilder.text("");
            suggestionsBuilder.field("suggest");
            SuggestRequestBuilder suggestRequestBuilder = searchClientService
                    .getClient().prepareSuggest(indexName)
                    .addSuggestion(suggestionsBuilder);

            suggestResponse = suggestRequestBuilder.execute().actionGet();
        } catch (Exception e) {
            LOGGER.error(errorMesage + " autoComplete", e);
            throw new SharedSearchException(CommonConstants.ERROR_CODE_100,
                    CommonConstants.ERROR_NOTE
                            + CommonConstants.INTERNAL_SERVER_ERROR);
        }

        return suggestResponse;
    }

}
