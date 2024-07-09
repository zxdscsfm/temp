package emall.api.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import emall.api.entity.Avatar;
import emall.api.mapper.AvatarMapper;
import emall.api.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class AvatarService extends ServiceImpl<AvatarMapper, Avatar> {
    @Autowired
    public AvatarMapper avatarMapper;

    public String upload(MultipartFile uploadFile){
        String url = null;
        //get the InputStream of multipart file
        InputStream inputStream = null;
        try {
            inputStream = uploadFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //try to find out if already had the same file in database
        String md5 = SecureUtil.md5(inputStream);
        Avatar dbAvatar = avatarMapper.selectByMd5(md5);
        if(dbAvatar==null){
            //there is not the same file in database, then save the file
            String originalFilename = uploadFile.getOriginalFilename();
            String type = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            long size = uploadFile.getSize() / 1024;
            //create a directory to keep avatar
            File folder = new File(Constants.avatarFolderPath + "/avatar/");
            if(!folder.exists()){
                folder.mkdir();
            }
            String folderPath = folder.getAbsolutePath()+"/";


            //create a new fileName by using UUID
            String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            String finalFileName = uuid+"."+type;
            File targetFile = new File(folderPath + finalFileName);

            //save the file
            try {
                uploadFile.transferTo(targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            url = "/avatar/"+finalFileName;

            //save the FileInformation into database
            Avatar avatar = new Avatar(type, size, url, md5);
            avatarMapper.insert(avatar);
            //return the link
            return url;
        }
        //if there is the file in database, then return the link to that file
        return dbAvatar.getUrl();
    }

    /**
     * send the file in Project by fileName to user with HttpResponse
     * @param fileName
     * @param response
     */
    public void download(String fileName, HttpServletResponse response){
        File file = new File(Constants.avatarFolderPath + fileName);
        //try to find out the file from AvatarDirectory
        if(!file.exists()){
            throw  new RuntimeException("File:" + fileName + "not found");
        }
        try{
            //set the OutputStream
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition","attachment;filename="+ fileName);

            //write the file to OutputStream and flush to user
            outputStream.write(FileUtil.readBytes(file));
            outputStream.flush();
            outputStream.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
