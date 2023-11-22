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

	public String saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException {
		String uniqueFilename = UUID.randomUUID() + "_" + LocalDateTime.now().toInstant(ZoneOffset.of("-03:00"));
		Path uploadPath = Path.of(uploadDirectory);
		Path filePath = uploadPath.resolve(uniqueFilename);
		if (!Files.exists(uploadPath))
			Files.createDirectories(uploadPath);
		Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		return uniqueFilename;
	}
}
