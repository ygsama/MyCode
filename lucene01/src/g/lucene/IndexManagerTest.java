package g.lucene;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

/**
 * 使用lucene创建索引和文档
 * @author G
 *
 */
public class IndexManagerTest {


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
			TextField fileName = new TextField("fileName",fName ,Store.YES);
			TextField fileContent = new TextField("fileContent",fContent ,Store.YES);
			TextField fileSize = new TextField("fileSize",fSize.toString() ,Store.YES);
			
			// 创建一个文档，将所有域放入文档中,文档放入文档列表中
			Document doc = new Document(); 
			doc.add(fileName);
			doc.add(fileContent);
			doc.add(fileSize);
			documentList.add(doc);
		}
		
		// 创建分词器,StandardAnalyzer标准分词器，支持英文单词，中文单字，(创建和搜索使用的分词器必须一致)
		Analyzer analyzer = new StandardAnalyzer();
		
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
}
