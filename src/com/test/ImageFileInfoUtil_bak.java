package umisky.webApi.tool;

import com.api.doc.detail.util.DocDownloadCheckUtil;
import umisky.common.entity.FileDataDao;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.datasource.DataSource;

import java.util.ArrayList;
import java.util.Arrays;

public class ImageFileInfoUtil_bak {



    public  FileDataDao  getDatas(String values) {
        String sql = "" +
                "select " +
                "doc.docId, " +
                "right(doc.imagefilename ,len(doc.imagefilename) - Charindex('.',doc.imagefilename)) as [fileExtendName], " +
                "doc.imagefileid, " +
                "doc.operatedate as [uploaddate], " +
                "files.fileSize as [filesize], " +
                "files.secretLevel, " +
                "doc.versionId, " +
                "doc.imagefilename as [filename], " +
                "case when doc.operateuserid = 1 then '系统管理员' else ISNULL(emp.lastname,'') end as [username] " +
                "from docimagefile doc " +
                "left join imagefile files on files.imagefileid = doc.imagefileid " +
                "left join hrmresource emp on emp.id = doc.operateuserid " +
                "where  docid in (?) ";
        DataSource dataSource = LinkDBTool.linkDB("OA");
        RecordSet rs = new RecordSet();
        ArrayList<FileDataDao> filedatas = new ArrayList<FileDataDao>();
        if (rs.executeQuery(sql, values)) {
            while (rs.next()) {
                FileDataDao fileDataDao = new FileDataDao(
                        Util.null2String(rs.getString("fileExtendName")),
                        Util.null2String(rs.getString("imagefileid")),
                        getFileLink(Util.null2String(rs.getString("docId")), Util.null2String(rs.getString("imagefileid"))),
                        Util.null2String(rs.getString("uploaddate")),
                        this.getFileSize(rs.getDouble("filesize")),
                        this.getLoadLink(Util.null2String(rs.getString("imagefileid"))),
                        Util.null2String(rs.getString("secretLevel")),
                        Util.null2String(rs.getString("versionId")),
                        Util.null2String(rs.getString("filename")),
                        this.getImgSrc(Util.null2String(rs.getString("fileExtendName"))),
                        Util.null2String(rs.getString("username"))
                );

                return fileDataDao;
            }
        }
        return null;
    }

    /**
     * 获取文档url
     * @param docId
     * @param imageFileId
     * @return
     */
    private String getFileLink(String docId, String imageFileId){
         return "/spa/document/index2file.jsp?id="+docId+"&imagefileId="+imageFileId+"&isFromAccessory=true";
    }

    /**
     * 获取文件大小
     * @param size
     * @return
     */
    private String getFileSize(Double size){
        Double value = 0.0;
        String unit = "";
        if(size <= 512){
            value  = size;
            unit = "B";
        }
        if(size > (512)){
            value  = size / 1024;
            unit = "KB";
        }
        if(size > (512 * 1024)){
            value  = size / 1024 / 1024;
            unit = "MB";
        }
        return String.format("%.2f",value) + unit;
    }

    /**
     * 获取下载url
     * @param imageFileId
     * @return
     */
    private String getLoadLink(String imageFileId){
        DocDownloadCheckUtil docDownloadCheckUtil = new DocDownloadCheckUtil();
        String encodeFileid = DocDownloadCheckUtil.EncodeFileid(imageFileId, null);
        return "/weaver/weaver.file.FileDownload?fileid="+encodeFileid+"&download=1";
    }

    /**
     * 获取图标路径
     * @param fileExtendName
     * @return
     */
    private String getImgSrc(String fileExtendName) {
        String path = "/images/filetypeicons/";
        String name = "other";
        if(Arrays.asList("xls","xlsx").contains(fileExtendName)){
            name = "xls";
        }
        if(Arrays.asList("doc","docx").contains(fileExtendName)){
            name = "doc";
        }
        if(Arrays.asList("ppt","pptx").contains(fileExtendName)){
            name = "ppt";
        }
        if(Arrays.asList("rar","zip","7z").contains(fileExtendName)){
            name = "rar";
        }
        if(Arrays.asList("jpg","jpeg","png","git","bmp").contains(fileExtendName)){
            name = "jpg";
        }
        if(Arrays.asList("txt","text").contains(fileExtendName)){
            name = "txt";
        }
        if(Arrays.asList("pdf","pdfx").contains(fileExtendName)){
            name = "pdf";
        }
        return path + name + "_wev8.png";
    }
}
