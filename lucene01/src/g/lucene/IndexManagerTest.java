package g.lucene;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * @author G
 */
public class IndexManagerTest {

	/**
	 * 使用lucene创建索引和文档
	 * @throws Exception
	 */
	@Test
	public void testIndexCreate() throws Exception{
		
		// 创建lucene文档列表，保存多个document
		List<Document> documentList = new ArrayList<Document>();
		
		// 采集文件系统中的文档数据到lucene中
		File sourceDir = new File("E:\\searchsource");
		
		// 循环读取文件夹中的文件
		for (File file : sourceDir.listFiles()) {
			String fName = file.getName();
			String fContent = FileUtils.readFileToString(file);
			Long fSize = FileUtils.sizeOf(file);
			
			// 创建多个域：new TextField(域名,域值 ,是否存储);
//			TextField fileName = new TextField("fileName",fName ,Store.YES);
//			TextField fileContent = new TextField("fileContent",fContent ,Store.YES);
//			TextField fileSize = new TextField("fileSize",fSize.toString() ,Store.YES);

			/**
			 *  是否分词：要分词，因为要对文件名建立索引，不是是一个整体，有意义
			 *  是否索引：要索引，因为要通过索引搜索
			 *  是否存储：要存储，因为要直接显示
			 */
			TextField fileName = new TextField("fileName",fName ,Store.YES);
			/**
			 *  是否分词：要分词，因为要根据文件内容搜索，分词有意义
			 *  是否索引：要索引，因为要通过索引搜索
			 *  是否存储：YES或NO都行，是否要直接显示出来
			 */
			TextField fileContent = new TextField("fileContent",fContent ,Store.NO);
			/**
			 *  是否分词：要分词，因为搜索文档时可以比较数字大小，lucene内置有数字的分词算法
			 *  是否索引：要索引，因为要通过文件大小搜索
			 *  是否存储：要存储，因为要直接显示文档大小
			 */
			LongField fileSize = new LongField("fileSize",fSize,Store.YES);
			
			
			// 创建一个文档，将所有域放入文档中,文档放入文档列表中
			Document doc = new Document(); 
			doc.add(fileName);
			doc.add(fileContent);
			doc.add(fileSize);
			documentList.add(doc);
		}
		
		// 创建分词器,StandardAnalyzer标准分词器，支持英文单词，中文单字，(创建和搜索使用的分词器必须一致)
//		Analyzer analyzer = new StandardAnalyzer();
		// 中文分词器
		Analyzer analyzer = new IKAnalyzer();
		
		// 指定索引和文档存储的目录
		Directory storeDir = FSDirectory.open(new File("E:\\dic"));
		
		// 创建 文档写对象的配置对象
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
				
		// 创建 文档写对象 和 索引
		IndexWriter indexWriter = new IndexWriter(storeDir, indexWriterConfig);
		
		// 将文档列表中的文档放入到 索引 和 文档的写对象中
		for (Document document : documentList) {
			indexWriter.addDocument(document);
		}
		
		indexWriter.commit();
		
		indexWriter.close();
	}
	/**
	 * 使用lucene删除索引
	 * @throws Exception
	 */
	@Test
	public void testIndexDel() throws Exception{
		
		// 中文分词器
		Analyzer analyzer = new IKAnalyzer();
		
		// 指定索引和文档存储的目录
		Directory storeDir = FSDirectory.open(new File("E:\\dic"));
		
		// 创建 文档写对象的配置对象
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
				
		// 创建 文档写对象 和 索引
		IndexWriter indexWriter = new IndexWriter(storeDir, indexWriterConfig);
		
		// 删除所有
//		indexWriter.deleteAll();
		
		// 词元new Term(域名, 关键字)
		indexWriter.deleteDocuments(new Term("fileName", "apache"));
		
		indexWriter.commit();
		indexWriter.close();
	}
	
	/**
	 * 索引和文档的更新
	 * 更新就是按照传入的Term进行搜索,如果找到结果那么删除,将更新的内容重新生成一个Document对象
	 * 如果没有搜索到结果,那么将更新的内容直接添加一个新的Document对象
	 * @throws Exception
	 */
	@Test
	public void testIndexUpdate() throws Exception{
		
		// 中文分词器
		Analyzer analyzer = new IKAnalyzer();
		
		// 指定索引和文档存储的目录
		Directory storeDir = FSDirectory.open(new File("E:\\dic"));
		
		// 创建 文档写对象的配置对象
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
				
		// 创建 文档写对象 和 索引
		IndexWriter indexWriter = new IndexWriter(storeDir, indexWriterConfig);
		
		//根据文件名称进行更新
		Term term = new Term("fileName", "web");
		//更新的对象
		Document doc = new Document();
		doc.add(new TextField("fileName", "xxxxxx", Store.YES));
		doc.add(new TextField("fileContext", "think in java xxxxxxx", Store.NO));
		doc.add(new LongField("fileSize", 100L, Store.YES));
		
		indexWriter.updateDocument(term, doc);
		
		indexWriter.commit();
		indexWriter.close();
	}
}
