package com.sys.pp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.sys.pp.constant.GemRealtyConst;

@Controller
@RequestMapping("upload")
public class UploadFileController {

	private enum ContentType {
		IMAGE_JPEG("image/png"), IMAGE_PNG("image/jpeg"), IMAGE_GIF("image/gif");

		String mimeType;

		ContentType(String mimeType) {
			this.mimeType = mimeType;
		}

		public String getMimeType() {
			return mimeType;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/multi-file/{date}/{ip}/{hashTime}", method = RequestMethod.POST)
	public String uploadMultiFileHandlerPOST(@RequestParam("file") MultipartFile file, @PathVariable String hashTime,
			@PathVariable String date, @PathVariable String ip) {
		try {
			if (!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(), ContentType.IMAGE_PNG.getMimeType(),
					ContentType.IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
				throw new IllegalStateException("File must be an Image");
			}

			StringBuilder clientPath = new StringBuilder();
			clientPath.append(GemRealtyConst.DEFAULT_IMAGE_FOLDER_TEMP);
			clientPath.append(File.separator);
			clientPath.append(date);
			clientPath.append(File.separator);
			clientPath.append(ip);
			clientPath.append(File.separator);
			clientPath.append(hashTime);

			File clientFile = new File(clientPath.toString());
			// create client folder
			FileUtils.forceMkdir(clientFile);

			// Tạo file tại Server.
			String name = file.getOriginalFilename();
			File serverFile = new File(clientFile.getAbsolutePath() + File.separator + name);

			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(file.getBytes());
			stream.close();

			return "OK";
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "FILE IS NOT SUPPORT");
		}
	}
}