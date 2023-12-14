package com.ae.tech.ProcessMenu.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

	public String saveImageToStorage(MultipartFile imageFile) throws IOException {
		String originalFile = imageFile.getOriginalFilename();
		int i = originalFile.lastIndexOf('.');
		String fileExtension = "";
		if (i > 0) {
			fileExtension = originalFile.substring(i + 1);
		}
		String nameFilename = (UUID.randomUUID() + "_" + LocalDateTime.now().toInstant(ZoneOffset.of("-03:00")))
				.replaceAll("[^\\w\\s]", "_");
		String uniqueFilename = nameFilename + "." + fileExtension;
		Path uploadPath = Path.of("Imagem/");
		Path filePath = uploadPath.resolve(uniqueFilename);
		if (!Files.exists(uploadPath))
			Files.createDirectories(uploadPath);
		Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		return uniqueFilename;
	}

}
