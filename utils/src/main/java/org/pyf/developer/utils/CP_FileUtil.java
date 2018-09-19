/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  org.cpframework.cp_utils.CP_FileUtil.java 													       
 * 功能: cpframework框架													   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2014年9月18日 上午11:03:03 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author      |	 Change Description		      
 * 2014年9月18日    |    Administrator     |     Created 
 */
package org.pyf.developer.utils;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;

/** 
 *Description: <类功能描述>. <br>
 *<p>
	<使用说明>
 </p>
 *Makedate:2014年9月18日 上午11:03:03 
 * @author sunchengqi  
 * @version V1.0                             
 */
public class CP_FileUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(CP_FileUtil.class);
	/**
	 * 
	 * @author liyiqing 
	 * 2010-8-26
	 * @Desc: 上传文件
	 */
	public static void upLoadFile(InputStream stream, String file) {
		logger.info("FileTool upLoadFile begin file:"+file);
		OutputStream bos = null;
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		try {
			File f = new File(file);
			f.createNewFile();
			bos = new FileOutputStream(f);
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			logger.error("FileTool upLoadFile exception:",e );
			e.printStackTrace();
		} finally {
			try {
				bos.close();
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("FileTool upLoadFile end");
	}

	public static void delete(String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
		}
	}
	/**
	 * 
	* 描述 : <解压rar或者zip文件>. <br>  
	*<p>                                                   
	* @param path
	* @param uncompressPath
	* @since 2013-02-05
	* @author sunchengqi
	* @depends commons-compress-1.4.jar
	* @throws Exception
	 */
	public static void uncompress(String path, String uncompressPath) throws Exception {
			try {
				ZipFile zipFile = new ZipFile(path, "GBK");
				Enumeration e = zipFile.getEntries();
				byte ch[] = new byte[256];
				while (e.hasMoreElements()) {
					ZipArchiveEntry zipEntry = (ZipArchiveEntry) e
							.nextElement();
					String temp = zipEntry.getName();
					System.out.println("unziping " + temp);
					File zfile = new File(uncompressPath
							+ (uncompressPath.endsWith("/") ? "" : "/") + temp);
					File fpath = new File(zfile.getParentFile().getPath());

					if (zipEntry.isDirectory()) {
						if (!zfile.exists())
							zfile.mkdirs();
					} else {
						if (!fpath.exists())
							fpath.mkdirs();
						FileOutputStream fouts = new FileOutputStream(zfile);
						InputStream in = zipFile.getInputStream(zipEntry);
						int i;
						while ((i = in.read(ch)) != -1)
							fouts.write(ch, 0, i);
						fouts.flush();
						fouts.close();
						in.close();
					}
				}

			} catch (Exception e) {
				System.err.println("Exception from ZipUtil -> unZip() : "
						+ e.getMessage());
				e.printStackTrace(System.err);
				throw e;
			}
	}

}
