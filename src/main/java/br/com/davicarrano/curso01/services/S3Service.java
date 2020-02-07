package br.com.davicarrano.curso01.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import br.com.davicarrano.curso01.services.exception.FileException;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${s3.bucket}")
	private String bucketName;

	public URI uploadFile(MultipartFile multipartFile) {
		try {
			String originalFilename = multipartFile.getOriginalFilename();
			InputStream inputStream = multipartFile.getInputStream();
			String contentType = multipartFile.getContentType();

			return uploadFile(originalFilename, inputStream, contentType);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileException("Erro de IO: "+e.getMessage());
		}

	}

	public URI uploadFile(String originalFilename, InputStream inputStream, String contentType) {
		try {
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(contentType);
			LOG.info("Iniciando Upload...");
			s3client.putObject(bucketName, originalFilename, inputStream, objectMetadata);
			LOG.info("Upload finalizado!!!");

			return s3client.getUrl(bucketName, originalFilename).toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new FileException("Erro ao converter URL para URI.");
		}

	}
}
