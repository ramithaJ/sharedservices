package com.wiley.gr.ace.search.service.impl;

import com.wiley.gr.ace.search.model.*;
import com.wiley.gr.ace.search.service.SearchClientService;
import com.wiley.gr.ace.search.service.SearchService;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

/**
 * Created by KKALYAN on 7/2/2015.
 */
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchClientService searchClientService;

    @Value("${index.name}")
    private String indexName;

    private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    /**
     * Method to search against ES index.
     *
     * @param searchCriteria
     * @return
     */
    public Response search(SearchCriteria searchCriteria) {
        //http://www.programcreek.com/java-api-examples/index.php?api=org.elasticsearch.search.sort.SortOrder
        Response searchResponse = new Response();

      /*  AggregationBuilder aggregation = AggregationBuilders
                .filter("journalType2")
                .filter(FilterBuilders.termFilter("journal.type", "type101"));*/

        SearchRequestBuilder requestBuilder = searchClientService.getClient().prepareSearch(indexName)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(getQueryBuilder(searchCriteria));

        //Set Types
        setTypes(requestBuilder);

        //Add Aggregations
        addAggregations(requestBuilder, "journal.type,article.title");

        //Apply Page Navigation
        setResultSize(requestBuilder, searchCriteria.getOffset(), searchCriteria.getRows());

        //Apply Sort if exists
        addSort(searchCriteria, requestBuilder);

        //Execute the request
        SearchResponse response = requestBuilder.execute().actionGet();

        //Prepare response
        searchResponse = prepareResponse(searchResponse, response);

        // Set Facets to the response
        searchResponse.setFacets(getAggregations(response, "journal.type,article.title"));

        return searchResponse;
    }

    /**
     * Method to set data types in the index.
     *
     * @param requestBuilder
     * @return
     */
    private SearchRequestBuilder setTypes(SearchRequestBuilder requestBuilder) {
        return requestBuilder.setTypes("journal", "article");
    }

    /**
     * Method to add aggregations.
     *
     * @param requestBuilder
     * @param facetFields
     * @return
     */
    private SearchRequestBuilder addAggregations(SearchRequestBuilder requestBuilder, String facetFields) {
        String[] fields = facetFields.split(",");
        for (String field : fields) {
            requestBuilder.addAggregation(terms(field).field(field).size(0));
        }
        return requestBuilder;
    }

    /**
     * Method to prepare response.
     *
     * @param searchResponse
     * @param response
     * @return
     */
    private Response prepareResponse(Response searchResponse, SearchResponse response) {
        List<Hits> hitsList = new LinkedList();
        SearchHit[] results = response.getHits().getHits();
        searchResponse.setMax_score(response.getHits().getMaxScore());
        searchResponse.setTotal(response.getHits().getTotalHits());
        searchResponse.setTook(response.getTookInMillis());
        for (SearchHit hit : results) {
            Hits searchHit = new Hits();
            searchHit.setId(hit.getId());
            searchHit.setScore(hit.getScore());
            searchHit.setSource(hit.getSource());
            searchHit.setType(hit.getType());
            hitsList.add(searchHit);
        }
        searchResponse.setHits(hitsList);
        return searchResponse;
    }

    /**
     * Method to set Page Navigation.
     *
     * @param requestBuilder
     * @param from
     * @param rows
     * @return
     */
    private SearchRequestBuilder setResultSize(SearchRequestBuilder requestBuilder, int from, int rows) {
        return requestBuilder.setFrom(from).setSize(rows);
    }

    /**
     * Method to sort the results.
     *
     * @param searchCriteria
     * @param requestBuilder
     * @return
     */
    private SearchRequestBuilder addSort(SearchCriteria searchCriteria, SearchRequestBuilder requestBuilder) {
        List<Sorting> sortList = searchCriteria.getSort();
        boolean isDescOrder = false;
        if (null != sortList) {
            for (Sorting sorting : sortList) {
                if (sorting.getSortOrder().equalsIgnoreCase("DESC")) {
                    isDescOrder = true;
                }
                final SortOrder sortOrder = isDescOrder ? SortOrder.DESC : SortOrder.ASC;
                requestBuilder.addSort(sorting.getSortBy(), sortOrder);
            }
        }
        return requestBuilder;
    }

    /**
     * Method to get facets from the response.
     *
     * @param response
     * @param fields
     * @return
     */
    private Facets getAggregations(SearchResponse response, String fields) {
        //https://github.com/elastic/elasticsearch/issues/4837
        Facets facets = new Facets();
        Tags tags = new Tags();
        int bucketSize = 0;
        LinkedList<Items> itemsLinkedList = new LinkedList<>();
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
        return facets;
    }

    /**
     * Util method.
     *
     * @param listOfValues
     * @return
     */
    private String convertListintoString(List<String> listOfValues) {
        StringBuilder builder = new StringBuilder();
        for (String value : listOfValues) {
            builder.append("\"");
            builder.append(value);
            builder.append("\",");

        }
        return builder.toString().replaceAll(",$", "");
    }

    /**
     * Method to get query builder.
     *
     * @param searchCriteria
     * @return
     */
    protected QueryBuilder getQueryBuilder(SearchCriteria searchCriteria) {
        QueryBuilder matchQueryBuilder = null;

        String queryString = searchCriteria.getSimpleQuery();

        if (StringUtils.isBlank(queryString)) {
            matchQueryBuilder = QueryBuilders.multiMatchQuery(searchCriteria.getAdvancedQuery(), "_all");
        } else {
            BoolQueryBuilder boolQuery = new BoolQueryBuilder();
          /*  for (Map.Entry<String, String> entry : fields.entrySet()) {
                boolQuery.must(QueryBuilders.matchQuery(entry.getKey(), entry.getValue()));
            }*/
        }

        return matchQueryBuilder;
    }

}
