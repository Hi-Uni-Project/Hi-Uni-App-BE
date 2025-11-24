package com.project.hiuni.global.common.util;

import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.InternalServerException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class ImageUtil {


  /**
   * 이미지를 지정된 경로에 저장하고, 저장된 이미지의 url을 반환합니다.
   *
   * @param file 업로드 이미지 파일
   * @param path 이미지를 저장할 경로
   * @return 저장된 이미지의 URL
   * @throws InternalServerException 이미지 저장 중 오류가 발생한 경우
   */
  public String saveImage(MultipartFile file, String path) {

    try {

      //이미지 데이터 추출
      BufferedImage image = ImageIO.read(file.getInputStream());
      if (image == null) {
        throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
      }

      //파일명 지정
      String newFileName = UUID.randomUUID() + "_" + LocalDateTime.now() + ".png";
      Path uploadPath = Paths.get(path);


      //파일 저장
      File outputFile = new File(path + "/" + newFileName);
      ImageIO.write(image, "png", outputFile);

      return "/images/" + newFileName;

    } catch (Exception e) {
      log.error("이미지 저장 중 에러 발생: ", e);
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

  }

}
