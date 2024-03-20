from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status

import json
from datetime import datetime 
from elasticsearch import Elasticsearch

from langchain_community.vectorstores import ElasticsearchStore
from langchain_community.embeddings import HuggingFaceEmbeddings
from langchain.retrievers import BM25Retriever, EnsembleRetriever
from langchain.schema import Document


class PostApiView(APIView):
    elastic_url = "http://localhost:9200"
    elastic_index = "test_post"
    elastic_client = Elasticsearch(elastic_url)
    embedding = HuggingFaceEmbeddings(model_name="VoVanPhuc/sup-SimCSE-VietNamese-phobert-base")
    store = ElasticsearchStore(es_connection=elastic_client, index_name=elastic_index, embedding=embedding)

    # def __init__(self):
    #     elastic_client = Elasticsearch(self.elastic_url)
    #     elastic_client.indices.delete(index=self.elastic_index)

    def get(self, request, *args, **kwargs):
        query = request.GET.get("search")
        id = request.GET.get("id") 
        professionId = request.GET.get("professionId")
        minSalary = request.GET.get("minSalary") 
        maxSalary = request.GET.get("maxSalary")
        provinceName = request.GET.get("provinceName") # Need to change from client
        workingFormat = request.GET.get("workingFormat")

        filter = []
        if id is not None:
            filter.append({"term": {"metadata.id": id}})
        if professionId is not None:
            filter.append({"term": {"metadata.profession.id": professionId}})
        if minSalary is not None:
            filter.append({"range": {"metadata.minSalary": {"gte": minSalary}}})
        if maxSalary is not None:
            filter.append({"range": {"metadata.maxSalary": {"lte": maxSalary}}})
        if workingFormat is not None:
            filter.append({"match": {"metadata.workingFormat": {"query": workingFormat, "operator": "and"}}})
        if provinceName is not None:
            filter.append({"term": {"metadata.locations.provinceName": provinceName}})
        filter.append({"range": {"metadata.applicationDeadline": {"gte": datetime.now().date()}}})

        vector_retriever = self.store.as_retriever(
            search_kwargs={'k': 10, 'fetch_k': 50, 'filter': filter}
        )

        docs = self._get_all_data()
        bm25_retriever = BM25Retriever.from_documents(docs)
        bm25_retriever.k = 10
        ensemble_retriever = EnsembleRetriever(
            retrievers=[bm25_retriever, vector_retriever], weights=[0.5, 0.5]
        )

        results = ensemble_retriever.invoke(query)
        posts = list(map(lambda x: x.metadata, results))
        return Response(posts, status=status.HTTP_200_OK)

    def post(self, request, *args, **kwargs):
        body_unicode = request.body.decode('utf-8')
        post = json.loads(body_unicode)
        post['applicationDeadline'] = datetime.strptime(post['applicationDeadline'], "%d/%m/%Y").date()
        post['createdAt'] = datetime.strptime(post['createdAt'], "%d/%m/%Y").date()
        docs = [Document(page_content=self._combine(post), metadata=post)]
        self.store.add_documents(docs)
        return Response(post, status=status.HTTP_201_CREATED)

    def _combine(self, post):
        if "Cạnh tranh" in post['minSalaryString']: 
            return "{} {} {} {}".format(
                post['title'],
                post['profession']['name'],
                post['level']['name'],
                ", ".join([location['provinceName'] for location in post['locations']])
            )
        
        return "{} {} from {} to {} {} {} {}".format(
                post['title'],
                post['profession']['name'],
                post['minSalaryString'],
                post['maxSalaryString'],
                post['currency'],
                post['level']['name'],
                ", ".join([location['provinceName'] for location in post['locations']])
            )

    def _get_all_data(self):
        response = self.elastic_client.search(
            index=self.elastic_index,
            body={"query": {"match_all": {}}, "size":"10000"},
            scroll="1m"
        )
        scroll_id = response['_scroll_id']
        hits = response['hits']['hits']
        all_data = hits

        while True:
            scroll_response = self.elastic_client.scroll(scroll_id=scroll_id, scroll='1m')
            
            # Check if there are more results
            if not scroll_response['hits']['hits']:
                break
            
            # Append hits to the list
            all_data.extend(scroll_response['hits']['hits'])
        
        return [
            Document(
                page_content=data['_source']['text'],
                metadata=data['_source']['metadata']
            ) for data in all_data
        ]
