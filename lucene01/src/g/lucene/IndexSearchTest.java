package g.lucene;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

/**
 * 搜索lucene中的文档
 * @author G
 *
 */
public class IndexSearchTest {

	@Test
	public void testIndexSearch() throws Exception{
		
		// 创建分词器(创建和搜索使用的分词器必须一致)
		Analyzer analyzer = new StandardAnalyzer();
		
		// c存放索引和文档的目录
		Directory storeDir = FSDirectory.open(new File("E:\\dic"));
		
		// 索引和文档的读取对象
		IndexReader indexReader = IndexReader.open(storeDir);
		
		// 创建索引的搜索对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		
		// 创建查询对象,new QueryParser(默认搜索域, 分词器)，查询语法中没指定域则查找默认搜索域
		QueryParser queryParser = new QueryParser("fileContent", analyzer);
		
		// 查询语法域名：搜索的关键字
		Query query = queryParser.parse("fileName:apache");
		
		// search(查询语句对象, 最大显示条数);
		TopDocs topDocs = indexSearcher.search(query, 10);
		
		// 打印一共多少条
		System.out.println("搜索记录--"+topDocs.totalHits+"--条");
		
		// 从搜索对象中获取结果集并遍历
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			// 通过id获得文档
			int docID = scoreDoc.doc;
			Document document = indexReader.document(docID);
			
			System.out.println("fileName:---->"+document.get("fileName"));
			System.out.println("fileSize:---->"+document.get("fileSize"));
			System.out.println("fileContent:->"+document.get("fileContent"));
			
			System.out.println("*******************************************");
		}
		
		indexReader.close();
	}
}
