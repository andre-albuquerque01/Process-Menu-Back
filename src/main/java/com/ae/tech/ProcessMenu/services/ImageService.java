package com.ae.tech.ProcessMenu.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

	private volatile String fileName;
	private volatile String fileId;

	public String saveImageToStorage(MultipartFile imageFile) throws IOException {
		String originalFile = imageFile.getOriginalFilename();
		int i = originalFile.lastIndexOf('.');
		String fileExtension = "";
		if (i > 0) {
			fileExtension = originalFile.substring(i + 1);
		}
		String nameFilename = generateUniqueFilename(fileExtension);
		String uniqueFilename = nameFilename;
		Path uploadPath = Path.of("Imagem/");
		Path filePath = uploadPath.resolve(uniqueFilename);
		try {
			if (!Files.exists(uploadPath))
				Files.createDirectories(uploadPath);
			Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uniqueFilename;
	}

	private String generateUniqueFilename(String fileExtension) {
		return (RandomService.generateRandomAlphaNumeric(5) + "_" + LocalDateTime.now()).replaceAll("[^\\w\\s]", "_")
				+ "." + fileExtension;
	}

	public void savePathName(String id, String fileName) {
		synchronized (this) {
			setFileName(fileName);
			setFileName(id);
		}
	}

	public String getFileName() {
		synchronized (this) {
			return fileName;
		}
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

}
