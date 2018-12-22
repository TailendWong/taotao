package demo.common;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import java.io.File;
import java.io.IOException;

public class FastDFSClientUtil {

	private TrackerClient trackerClient = null;
	private TrackerServer trackerServer = null;
	private StorageServer storageServer = null;
	private StorageClient1 storageClient = null;
//	public static final String FDFS_URL="http://192.168.3.235:8888/";

	static {

		String configPath=FastDFSClientUtil.class.getResource("/").getPath()+"fdfs_client.conf";
		System.out.println("configPath = " + configPath);
		try {
			ClientGlobal.init(configPath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
	}

	
	public FastDFSClientUtil(String conf) throws Exception {
		if (conf.contains("classpath:")) {
			conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
		}
		ClientGlobal.init(conf);
		trackerClient = new TrackerClient();
		trackerServer = trackerClient.getConnection();
		storageServer = null;
		storageClient = new StorageClient1(trackerServer, storageServer);
	}
	public FastDFSClientUtil() throws Exception {
		trackerClient = new TrackerClient();
		trackerServer = trackerClient.getConnection();
		storageServer = null;
		storageClient = new StorageClient1(trackerServer, storageServer);
	}

	/**
	 * 上传文件方法
	 * <p>Title: uploadFile</p>
	 * <p>Description: </p>
	 * @param fileName 文件全路径
	 * @param extName 文件扩展名，不包含（.）
	 * @param metas 文件扩展信息
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
		String result = storageClient.upload_file1(fileName, extName, metas);
		return result;
	}
	
	public String uploadFile(String fileName) throws Exception {
		return uploadFile(fileName, null, null);
	}
	
	public String uploadFile(String fileName, String extName) throws Exception {
		return uploadFile(fileName, extName, null);
	}
	
	/**
	 * 上传文件方法
	 * <p>Title: uploadFile</p>
	 * <p>Description: </p>
	 * @param fileContent 文件的内容，字节数组
	 * @param extName 文件扩展名
	 * @param metas 文件扩展信息
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {
		
		String result = storageClient.upload_file1(fileContent, extName, metas);
		return result;
	}
	
	public String uploadFile(byte[] fileContent) throws Exception {
		return uploadFile(fileContent, null, null);
	}
	
	public String uploadFile(byte[] fileContent, String extName) throws Exception {
		return uploadFile(fileContent, extName, null);
	}

	public static void main(String[] args) throws Exception {
//		String str="fdsa.fds.png";
//		System.out.println(str.substring(str.lastIndexOf(".")));

		//客户端配置文件
		 String conf_filename = "fdfs_client.conf";
		//本地文件，要上传的文件
//		 String local_filename = "/Users/shijian/Downloads/pics/def1bf6b83788d05df8fc8f48f5f1a4a.jpg";
		File file = new File("C:\\Users\\pics");
		File[] files = file.listFiles();
		StringBuffer sb=new StringBuffer();
		for (File file1 : files) {
			String local_filename = file1.getAbsolutePath();
			System.out.println("local_filename = " + local_filename);
			FastDFSClientUtil fastDFSClientUtil=new FastDFSClientUtil();
			String path = fastDFSClientUtil.uploadFile(local_filename, "jpg");
			sb.append(path);
			sb.append(",");

		}

		System.out.println("path = " + sb);
	}
}
