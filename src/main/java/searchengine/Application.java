package searchengine;

import com.github.xjavathehutt.porterstemmer.PorterStemmer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		List<Document> docs = new ArrayList<>();

		String[] remove = new String[] { "[", "]", "(", ")", "-", ".", "," };

		try {
			BufferedReader br = new BufferedReader(new FileReader("documents.txt"));
			String currentLine;

			while ((currentLine = br.readLine()) != null) {

				currentLine = currentLine.toLowerCase();
				currentLine = currentLine.replace("\t", " ");

				for (String s : remove) {
					currentLine = currentLine.replace(s, "");
				}

				StringTokenizer st = new StringTokenizer(currentLine, " ");

				String id = st.nextToken();
				String term;

				StringBuilder content = new StringBuilder();

				while (st.hasMoreTokens()) {
					term = st.nextToken();

					term = PorterStemmer.stem(term);
					content.append(term).append(" ");
				}

				docs.add(new Document(id, content.toString()));
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}



		VectorSpaceModel VSM = new VectorSpaceModel(new Vector("The nucleotide sequence"));


		long start = System.nanoTime();
		VSM.calculateTfidf(docs);
		VSM.assignScore(docs);
		long elapsedTime = System.nanoTime() - start;
		System.out.println((double) elapsedTime / 1000000000 + " sekunder");

		Collections.sort(docs);
		Collections.reverse(docs);

		for (int i = 0; i < 1206; i++) {
			System.out.println(i + 1 + ". " + docs.get(i).getTitle() + " : " + docs.get(i).getScore());
		}
	}
}
