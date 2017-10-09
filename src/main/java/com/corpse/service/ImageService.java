package com.corpse.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.Image;
import com.corpse.model.ResponseUDI;
import com.corpse.repository.ImageRepository;

@Service
@Transactional
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;

	public List<Image> getAll() {
		List<Image> list = new ArrayList<>();
		imageRepository.findAll().forEach(list::add);
		return list;
	}

	public List<Image> getByKey(String uniqueName) {
		List<Image> list = new ArrayList<>();
		Image image = imageRepository.findOne(uniqueName);
		if(image != null) {
			list.add(image);
		}

		return list;
	}

	public ResponseUDI<String> insert(Image image) {
		ResponseUDI<String> resp = new ResponseUDI<>();
		imageRepository.save(image);
		resp.setId(image.getUniqueName());
		resp.setCount(1);
		resp.setOperation(ResponseUDI.TypeOperation.INSERT);
		return resp;
	}
}
