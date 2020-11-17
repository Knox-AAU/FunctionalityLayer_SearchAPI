package searchengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		//SpringApplication.run(Application.class, args);

		//Document d = new Document("hej hej hej med med dig");

		List<Document> docs = new ArrayList<Document>();

		Document query = new Document("query","is denmark smaller that russia");

		VectorSpaceModel vsm = new VectorSpaceModel();

		docs = vsm.createTestData();

		vsm.createTfidf(docs, query);

		for(Document d : docs){
			vsm.giveDocumentScore(d, query);
		}

		System.out.println("TERM FREQ");
		for(Document d : docs){
			System.out.println(d.getTitle());
			for(String term : d.getTermFrequency().keySet()){
				System.out.println(term + " | " + d.getTermFrequency().get(term));
			}
		}
		System.out.println("q");
		for(String term : query.getTermFrequency().keySet()){
			System.out.println(term + " | " + query.getTermFrequency().get(term));
		}
		System.out.println();

		System.out.println("TFIDF");
		for(Document d : docs){
			System.out.println(d.getTitle());
			for(String term : d.getTfidf().keySet()){
				System.out.println(term + " | " + d.getTfidf().get(term));
			}
		}
		System.out.println("q");
		for(String term : query.getTfidf().keySet()){
			System.out.println(term + " | " + query.getTfidf().get(term));
		}
		System.out.println();

		System.out.println("SCORE");
		for(Document d : docs){
			System.out.println(d.getTitle() + " | " + d.getScore());
		}


	}

}
