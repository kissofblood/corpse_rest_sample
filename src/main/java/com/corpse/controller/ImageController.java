package com.corpse.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.corpse.model.Image;
import com.corpse.model.ResponseUDI;
import com.corpse.service.ImageService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/image")
@Secured(Common.ROLE_OWNER)
public class ImageController {

	private static final String ROOT_PATH = "/opt/Yandex.Disk/";
	private static final String NAME_FOLDER = "images";
	private static final int BUFFER_SIZE = 10000000;

	@Autowired
	private ImageService imageService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Image> getAll() {
		return imageService.getAll();
	}

	@RequestMapping(value = "/{filename}", method = RequestMethod.GET)
	public void getFile(@PathVariable("filename") String filename,
			HttpServletResponse resp) throws IOException {
		FileInputStream inputStream = null;
		OutputStream outStream = null;
		try {
			List<Image> result = imageService.getByKey(filename);
			if(result.isEmpty()) {
				throw new RuntimeException("not found filename");
			}

			Image image = result.get(0);
			File downloadFile = new File(ROOT_PATH + NAME_FOLDER + File.separator + image.getUniqueName());
			inputStream = new FileInputStream(downloadFile);

			resp.setContentType(image.getMimeType());
			resp.setContentLength((int) downloadFile.length());

			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
			resp.setHeader(headerKey, headerValue);

			outStream = resp.getOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			while((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
		}
		finally {
			if(inputStream != null) {
				inputStream.close();
			}
			if(outStream != null) {
				outStream.close();
			}
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<String> upload(@RequestParam("file") MultipartFile file,
			HttpServletResponse resp) throws IOException, NoSuchAlgorithmException {
		BufferedOutputStream stream = null;
		try {
			byte[] bytes = file.getBytes();
			if(bytes.length == 0) {
				throw new RuntimeException("length of bytes is empty");
			}
			if(!file.getContentType().toLowerCase().contains("image")) {
				throw new RuntimeException("support only image format");
			}

			Calendar now = Calendar.getInstance();
			int randomNum = ThreadLocalRandom.current().nextInt(1, 1000);
			String uniqueName = file.getOriginalFilename() + "[" + now.getTimeInMillis() + "] " + randomNum;

			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(uniqueName.getBytes());
			String encryptedUniqueName = String.format(
				"%040x", new BigInteger(1, messageDigest.digest())
			);

			File dir = new File(ROOT_PATH + NAME_FOLDER);
			if(!dir.exists()) {
				dir.mkdirs();
			}

			Image image = new Image();
			image.setUniqueName(encryptedUniqueName);
			image.setOriginalName(file.getOriginalFilename());
			image.setFullPath(NAME_FOLDER);
			image.setMimeType(file.getContentType());

			File serverFile = new File(dir.getAbsolutePath() + File.separator + encryptedUniqueName);
			stream = new BufferedOutputStream(
				new FileOutputStream(serverFile)
			);
			stream.write(bytes);
			stream.close();

			return imageService.insert(image);
		}
		finally {
			if(stream != null) {
				stream.close();
			}
		}
	}
}
