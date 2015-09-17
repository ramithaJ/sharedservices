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
 * Created by KKALYAN on 7/2/2015.
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

	@Value("${nonadmin.journal.search.fields}")
	private String nonAdminJournalSearchFields;

	@Value("${nonadmin.journal.search.result.fields}")
	private String nonAdminJournalSearchResultFields;

	@Value("${admin.article.search.fields}")
	private String adminArticleSearchFields;

	@Value("${admin.article.search.result.fields}")
	private String adminArticleSearchResultFields;

	@Value("${nonadmin.article.search.fields}")
	private String nonAdminArticleSearchFields;

	@Value("${nonadmin.article.search.result.fields}")
	private String nonAdminArticleSearchResultFields;
	
	@Value("${wildcard.fields}")
	private String wildcardFields;

	@Value("${exact.fields}")
	private String exactFields;

	@Value("${range.fields}")
	private String rangeFields;

	private static final Logger logger = LoggerFactory
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
	public Response search(SearchCriteria searchCriteria, String role) {
		// http://www.programcreek.com/java-api-examples/index.php?api=org.elasticsearch.search.sort.SortOrder
		Response searchResponse = new Response();

		/*
		 * AggregationBuilder aggregation = AggregationBuilders
		 * .filter("journalType2")
		 * .filter(FilterBuilders.termFilter("journal.type", "type101"));
		 */

		SearchRequestBuilder requestBuilder = searchClientService.getClient()
				.prepareSearch(indexName)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(getQueryBuilder(searchCriteria, role));
		
		//Get Source Value everytime
		requestBuilder.setTrackScores(true);
		
		// Set post Filter if facets selected
		setFilter(requestBuilder, searchCriteria);

		// Set Types
		setTypes(requestBuilder, searchCriteria.getTypes());

		// Add Aggregations
		addAggregations(requestBuilder, "journal.type,article.title");

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
				"journal.type,article.title"));

		return searchResponse;
	}

	private void setFilter(SearchRequestBuilder requestBuilder,
			SearchCriteria searchCriteria) {

		List<Filter> filterList = null;
		List<FilterBuilder> filterBuilderList = null;
		FilterBuilder filterbuilder = null;

		filterList = searchCriteria.getFilters();
		filterBuilderList = new ArrayList<FilterBuilder>();
		if (filterList != null && !filterList.isEmpty()) {
			for (Filter filter : filterList) {
				Map<String, String> filterMap = filter.getTerm();
				for (String key : filterMap.keySet()) {
					filterBuilderList.add(FilterBuilders.prefixFilter(key,
							filterMap.get(key)));
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

	}

	/**
	 * Method to set data types in the index.
	 *
	 * @param requestBuilder
	 * @return
	 */
	private void setTypes(SearchRequestBuilder requestBuilder, List<String> list) {
		String[] types = new String[list.size()];  
//   	for(int i=0;i<list.size();i++){
//   		types[i]=list.get(i);
//   	}
//   	
   requestBuilder.setTypes("article","journal");
	}

	/**
	 * Method to add aggregations.
	 *
	 * @param requestBuilder
	 * @param facetFields
	 * @return
	 */
	private void addAggregations(
			SearchRequestBuilder requestBuilder, String facetFields) {
		String[] fields = facetFields.split(",");
		for (String field : fields) {
			requestBuilder.addAggregation(terms(field).field(field).size(0));
		}
	}

	/**
	 * Method to prepare response.
	 *
	 * @param searchResponse
	 * @param response
	 * @return
	 */
	private Response prepareResponse(Response searchResponse,
			SearchResponse response, String role) {
		List<Hits> hitsList = new LinkedList();
		StringTokenizer searchFieldStringTokens = null;
		SearchHit[] results = response.getHits().getHits();
		searchResponse.setMax_score(response.getHits().getMaxScore());
		searchResponse.setTotal(response.getHits().getTotalHits());
		searchResponse.setTook(response.getTookInMillis());

		for (SearchHit hit : results) {
			Hits searchHit = new Hits();

			searchFieldStringTokens = getSearchFiledAttributeTokenizers(role,
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
		return searchResponse;
	}

	/**
	 * This method returns the tokens of the required fields
	 * 
	 * @param role
	 * @param type
	 * @return searchFieldStringTokens
	 */
	private StringTokenizer getSearchFiledAttributeTokenizers(String role,
			String type) {
		StringTokenizer searchFieldStringTokens = null;

		if ("journal".equals(type)) {
			if (CommonConstants.ROLE_ADMIN.equals(role)) {
				searchFieldStringTokens = new StringTokenizer(
						adminJournalSearchResultFields, ",");
			} else {
				searchFieldStringTokens = new StringTokenizer(
						nonAdminJournalSearchResultFields, ",");
			}
		} else if ("article".equals(type)) {
			if (CommonConstants.ROLE_ADMIN.equals(role)) {
				searchFieldStringTokens = new StringTokenizer(
						adminArticleSearchResultFields, ",");
			} else {
				searchFieldStringTokens = new StringTokenizer(
						nonAdminArticleSearchResultFields, ",");
			}

		}
		return searchFieldStringTokens;

	}

	/**
	 * Method to set Page Navigation.
	 *
	 * @param requestBuilder
	 * @param from
	 * @param rows
	 * @return
	 */
	private void setResultSize(
			SearchRequestBuilder requestBuilder, int from, int rows) {
		requestBuilder.setFrom(from).setSize(rows);
	}

	/**
	 * Method to sort the results.
	 *
	 * @param searchCriteria
	 * @param requestBuilder
	 * @return
	 */
	private void addSort(SearchCriteria searchCriteria,
			SearchRequestBuilder requestBuilder) {
		List<Sorting> sortList = searchCriteria.getSort();
		boolean isDescOrder = false;
		if (null != sortList) {
			for (Sorting sorting : sortList) {
				if (sorting.getSortOrder().equalsIgnoreCase("DESC")) {
					isDescOrder = true;
				}
				final SortOrder sortOrder = isDescOrder ? SortOrder.DESC
						: SortOrder.ASC;
				requestBuilder.addSort(sorting.getSortBy(), sortOrder);
			}
		}
	}

	/**
	 * Method to get facets from the response.
	 *
	 * @param response
	 * @param fields
	 * @return
	 */
	private Facets getAggregations(SearchResponse response, String fields) {
		// https://github.com/elastic/elasticsearch/issues/4837
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
	protected QueryBuilder getQueryBuilder(SearchCriteria searchCriteria,
			String role) {
		QueryBuilder matchQueryBuilder = null;
		List<String> searchFiledsList = null;
		String[] searchFiledsArray = null;
		List<String> types = null;

		StringTokenizer journalSearchFields = null;
		StringTokenizer articleSearchFields = null;

		String advancedQuery = searchCriteria.getAdvancedQuery();

		types = searchCriteria.getTypes();

		if (types != null && !types.isEmpty()) {
			if (CommonConstants.ROLE_ADMIN.equals(role)) {

				if (types.contains("journal")) {
					journalSearchFields = new StringTokenizer(
							adminJournalSearchFields, ",");
				}

				if (types.contains("article")) {
					articleSearchFields = new StringTokenizer(
							adminArticleSearchFields, ",");
				}

			} else {

				if (types.contains("journal")) {
					journalSearchFields = new StringTokenizer(
							nonAdminJournalSearchFields, ",");
				}

				if (types.contains("article")) {
					articleSearchFields = new StringTokenizer(
							nonAdminArticleSearchFields, ",");
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
				// matchQueryBuilder =
				// QueryBuilders.multiMatchQuery(searchCriteria.getAdvancedQuery(),
				// "_all");
				if(null != searchCriteria.getSimpleQuery())
					matchQueryBuilder= simpleSearch(searchCriteria);
					return matchQueryBuilder;
			
			} else {
//				matchQueryBuilder = QueryBuilders.multiMatchQuery(advancedQuery,searchFiledsArray)
//					    .type(MatchQueryBuilder.Type.PHRASE_PREFIX);
				/*
				 * for (Map.Entry<String, String> entry : fields.entrySet()) {
				 * boolQuery.must(QueryBuilders.matchQuery(entry.getKey(),
				 * entry.getValue())); }
				 */
			}
		}

		return matchQueryBuilder;
	}
	
	private QueryBuilder simpleSearch(SearchCriteria searchCriteria) {

		List<SimpleQuery> queryString = searchCriteria.getSimpleQuery();

			BoolQueryBuilder boolQuery = new BoolQueryBuilder();
			for (SimpleQuery query : queryString) {
				if (exactFields.contains(query.getField()))
					addexactMatchBuilder(query, boolQuery);
				else
					if(rangeFields.contains(query.getField()))
						addrangeBuilder(query, boolQuery);
					else
					addwildcardBuilder(query, boolQuery);
			}
			return boolQuery;
		
	}

//	private QueryBuilder rangeQuery(SearchCriteria searchCriteria){
//		BoolQueryBuilder boolQuery = new BoolQueryBuilder();
//		for (RangeQuery query : searchCriteria.getRangeQuery()) 
//		addrangeBuilder(query, boolQuery);
//		return boolQuery;
//	}
	
	
	private void addwildcardBuilder(SimpleQuery query, BoolQueryBuilder boolQuery) {

		 boolQuery.must(QueryBuilders.matchPhrasePrefixQuery(
				query.getField(), query.getValue()));
		 
//		 FilterBuilders.andFilter(FilterBuilders.prefixFilter(name, prefix))
		 
	}
	
	private void addexactMatchBuilder(SimpleQuery query, BoolQueryBuilder boolQuery) {

		boolQuery.must(QueryBuilders.termQuery(
				query.getField(), query.getValue()));
	}

	private void addrangeBuilder(SimpleQuery query,BoolQueryBuilder boolQuery){
		if(""==query.getTo())
			query.setTo(null);
		if(""==query.getFrom())
			query.setFrom(null);
		boolQuery.must(QueryBuilders.rangeQuery(query.getField()).from(query.getFrom()).to(query.getTo()));
	}

	@Override
	public SuggestResponse autoComplete(SearchCriteria criteria, String role) {

		Response searchResponse = new Response();

		CompletionSuggestionBuilder suggestionsBuilder = new CompletionSuggestionBuilder(
				"completeMe");

		suggestionsBuilder.text("");
		suggestionsBuilder.field("suggest");
		SuggestRequestBuilder suggestRequestBuilder = searchClientService
				.getClient().prepareSuggest(indexName)
				.addSuggestion(suggestionsBuilder);
		
		SuggestResponse suggestResponse = suggestRequestBuilder.execute().actionGet();


		return suggestResponse;
	}

}
