package com.kitchen.market.common.file;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传工具类
 *
 * @date 2016-12-22
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class KitFileUpload {
    /**
     * 文件上传至本地服务器
     * 注意！ 不支持分布式部署的文件访问
     *
     * @param inputStream 文件二进制流
     * @param originalFileName 上传时的文件名称
     * @param rootPath 文件存储根目录（通常为文件下载服务的发布目录）
     * @param relativePath 相对rootPath的目录（可为空）
     * @return URI
     */
    public static String uploadToLocal(InputStream inputStream, String originalFileName, String rootPath, String relativePath) throws IOException {
        String fileSuffix = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString().replace("-", "").toUpperCase() + fileSuffix;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateDir = sdf.format(new Date());

        // 合规处理
        rootPath = getCompliancePath(rootPath);
        relativePath = getComplianceFullPath(relativePath);

        String path = rootPath + relativePath + File.separator + dateDir;

        File targetFile = new File(path, fileName);
        if(!targetFile.exists()) {
            targetFile.getParentFile().mkdirs();
        }
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(targetFile);
            byte[] buf = new byte[1024 * 8];
            while (true) {
                int read = 0;
                if (inputStream != null) {
                    read = inputStream.read(buf);
                }
                if (read == -1) {
                    break;
                }
                fileOut.write(buf, 0, read);
            }
            // 查看文件获取是否成功
            if (fileOut.getFD().valid() == true) {
                System.out.println("获取文件保存成功");
            } else {
                throw new IOException("获取文件失败");
            }
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            if (fileOut != null) {
                fileOut.flush();
                fileOut.close();
            }
            inputStream.close();
        }

        String uri = relativePath + File.separator + dateDir + File.separator + fileName;

        return uri;
    }

    /**
     * 获取全合规路径（适用于相对路径）
     * @return
     */
    private static String getComplianceFullPath(String path) {
        if (path != null && !path.isEmpty()) {
            // 若最后一个字符是分隔符，则去除
            int endIndex = path.lastIndexOf(File.separator);
            if (endIndex == path.length()-1) {
                path = path.substring(0, path.length()-1);
            }

            // 若第一个字符不是分隔符，则添加
            int startIndex = path.indexOf(File.separator);
            if (startIndex != 0) {
                path = File.separator + path;
            }
        }
        return path;
    }

    /**
     * 获取合规路径（适用于根路径，因Win和Linux路径前缀不同，不处理路径前缀）
     * @return
     */
    private static String getCompliancePath(String path) {
        if (path != null && !path.isEmpty()) {
            // 若最后一个字符是分隔符，则去除
            int endIndex = path.lastIndexOf(File.separator);
            if (endIndex == path.length()-1) {
                path = path.substring(0, path.length()-1);
            }
        }
        return path;
    }

    /**
     * 上传文件到“中央文件服务器”
     * @param inputStream
     * @param folder
     * @return
     */
    public static String uploadToCenterFileServer(InputStream inputStream, String originalFilename, String folder) {
        // TODO 上传文件到“中央文件服务器”例如Nginx
        return null;
    }
}
