package g.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 搜索lucene中的文档
 * @author G
 *
 */
public class IndexSearchTest {

	/**
	 * 使用QueryParser查询
	 * @throws Exception
	 */
	@Test
	public void testIndexSearch() throws Exception{
		
		// 创建分词器(创建和搜索使用的分词器必须一致)
//		Analyzer analyzer = new StandardAnalyzer();
		Analyzer analyzer = new IKAnalyzer();
		
		// c存放索引和文档的目录
		Directory storeDir = FSDirectory.open(new File("E:\\dic"));
		
		// 索引和文档的读取对象
		IndexReader indexReader = IndexReader.open(storeDir);
		
		// 创建索引的搜索对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		
		// 创建查询对象,new QueryParser(默认搜索域, 分词器)，查询语法中没指定域则查找默认搜索域
		QueryParser queryParser = new QueryParser("fileName", analyzer);

		// 查询语法域名：搜索的关键字
		Query query = queryParser.parse("fileName:apache");
		
		// search(查询语句对象, 最大显示条数);
		TopDocs topDocs = indexSearcher.search(query, 10);
		
		// 打印
		print(indexReader, topDocs);
	}

	/**
	 * 使用TermQuery查询
	 * 只能从文本中搜索
	 * @throws Exception
	 */
	@Test
	public void testIndexTermQuery() throws Exception{
		//创建分词器(创建索引和所有时所用的分词器必须一致)
		Analyzer analyzer = new IKAnalyzer();
		//指定索引和文档的目录
		Directory storeDir = FSDirectory.open(new File("E:\\dic"));
		//索引和文档的读取对象
		IndexReader indexReader = IndexReader.open(storeDir);
		//创建索引的搜索对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		
		//创建词元:域名+关键字 
		Term term = new Term("fileName", "apache");
		//使用TermQuery查询,根据term对象进行查询
		TermQuery termQuery = new TermQuery(term);
		// search(查询语句对象, 最大显示条数);
		TopDocs topDocs = indexSearcher.search(termQuery, 10);
		
		// 打印
		print(indexReader, topDocs);
	}
	
	/**
	 * 使用NumericRangeQuery通过数字范围查询
	 * @throws Exception
	 */
	@Test
	public void testNumericRangeQuery() throws Exception{
		
		//创建分词器(创建索引和所有时所用的分词器必须一致)
		Analyzer analyzer = new IKAnalyzer();
		//指定索引和文档的目录
		Directory storeDir = FSDirectory.open(new File("E:\\dic"));
		//索引和文档的读取对象
		IndexReader indexReader = IndexReader.open(storeDir);
		//创建索引的搜索对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		
		//查询文件大小,大于100 小于1000的文章
		//NumericRangeQuery.newLongRange(域名, 最小值, 最大值, 是否包含最小值, 是否包含最大值)
		Query query = NumericRangeQuery.newLongRange("fileSize", 100L, 1000L, true, true);		
		
		//搜索:第一个参数为查询语句对象, 第二个参数:指定显示多少条
		TopDocs topDocs = indexSearcher.search(query, 5);
		
		print(indexReader, topDocs);
	}
	
	/**
	 * 打印topDocs，关闭indexReader流
	 * @param indexReader
	 * @param topDocs
	 * @throws IOException
	 */
	private void print(IndexReader indexReader, TopDocs topDocs) throws IOException {
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
	
	/**
	 * 通过BooleanQuery查询,根据多个条件组合进行查询
	 * @throws Exception
	 */
	@Test
	public void testBooleanQuery() throws Exception{
		//创建分词器(创建索引和所有时所用的分词器必须一致)
		Analyzer analyzer = new IKAnalyzer();
		
		//布尔查询,就是可以根据多个条件组合进行查询
		//文件名称包含apache的,并且文件大小大于等于100 小于等于1000字节的文章
		BooleanQuery query = new BooleanQuery();
		
		//根据数字范围查询
		//查询文件大小,大于100 小于1000的文章
		//NumericRangeQuery.newLongRange(域名 , 最小值, 最大值, 是否包含最小值, 是否包含最大值)
		Query numericQuery = NumericRangeQuery.newLongRange("fileSize", 100L, 1000L, true, true);
		
		//创建词元:就是词,   
		Term term = new Term("fileName", "apache");
		//使用TermQuery查询,根据term对象进行查询
		TermQuery termQuery = new TermQuery(term);
		
		//Occur是逻辑条件
		//must相当于and关键字,是并且的意思
		//should,相当于or关键字或者的意思
		//must_not相当于not关键字, 非的意思
		//注意:单独使用must_not  或者 独自使用must_not没有任何意义
		query.add(termQuery, Occur.MUST);
		query.add(numericQuery, Occur.MUST);
		
		// 指定索引和文档的目录
		Directory storeDir = FSDirectory.open(new File("E:\\dic"));
		// 索引和文档的读取对象
		IndexReader indexReader = IndexReader.open(storeDir);
		// 创建索引的搜索对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		// 搜索:第一个参数为查询语句对象, 第二个参数:指定显示多少条
		TopDocs topDocs = indexSearcher.search(query, 5);

		print(indexReader, topDocs);
	}
	
	/**
	 * 通过MatchAllDocsQuery查询所有文档
	 * @throws Exception
	 */
	@Test
	public void testMathAllQuery() throws Exception{
		// 创建分词器(创建索引和所有时所用的分词器必须一致)
		Analyzer analyzer = new IKAnalyzer();
		
		// 查询所有文档
		MatchAllDocsQuery query = new MatchAllDocsQuery();
		
		// 指定索引和文档的目录
		Directory storeDir = FSDirectory.open(new File("E:\\dic"));
		// 索引和文档的读取对象
		IndexReader indexReader = IndexReader.open(storeDir);
		// 创建索引的搜索对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		// 搜索:第一个参数为查询语句对象, 第二个参数:指定显示多少条
		TopDocs topDocs = indexSearcher.search(query, 5);
		
		print(indexReader, topDocs);
	}
	
	/**
	 * 通过MultiFieldQueryParser，可以从多个域中查询
	 * @throws Exception
	 */
	@Test
	public void testMultiFieldQueryParser() throws Exception{
		// 创建分词器(创建索引和所有时所用的分词器必须一致)
		Analyzer analyzer = new IKAnalyzer();
		
		String [] fields = {"fileName","fileContext"};
		// 从文件名称和文件内容中查询,只有含有apache的就查出来
		MultiFieldQueryParser multiQuery = new MultiFieldQueryParser(fields, analyzer);
		// 输入需要搜索的关键字
		Query query = multiQuery.parse("apache");
		
		// 指定索引和文档的目录
		Directory storeDir = FSDirectory.open(new File("E:\\dic"));
		// 索引和文档的读取对象
		IndexReader indexReader = IndexReader.open(storeDir);
		// 创建索引的搜索对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		// 搜索:第一个参数为查询语句对象, 第二个参数:指定显示多少条
		TopDocs topDocs = indexSearcher.search(query, 5);
		
		print(indexReader, topDocs);
	}
}
