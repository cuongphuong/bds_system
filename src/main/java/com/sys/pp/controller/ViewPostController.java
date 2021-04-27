package com.sys.pp.controller;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sys.pp.constant.GemRealtyConst;
import com.sys.pp.controller.custommodel.PostInfomation;
import com.sys.pp.model.BdsNew;
import com.sys.pp.repo.BDSNewRepository;
import com.sys.pp.repo.UserRepository;

@Controller
@RequestMapping("view")
public class ViewPostController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	BDSNewRepository bDSNewRepository;

	@RequestMapping(path = "/bds/{id}/{slug}")
	public String view(Model model, Principal principal, @PathVariable Integer id) {

		Optional<BdsNew> newsOpt = bDSNewRepository.findById(id);
		if (!newsOpt.isPresent()) {
			return null;
		}
		BdsNew news = newsOpt.get();
		PostInfomation info = new PostInfomation();
		List<List<String>> images = this.makeImagesLinkList(news.getDetailNew().getImages());
		info.setImages(images);
		info.setTitle(news.getTitle());

		model.addAttribute("infomation", info);
		return "layouts/user/view";
	}

	private List<List<String>> makeImagesLinkList(String imageUrlAction) {
		final String CHARATER = "multi-file";
		String basePath = imageUrlAction.substring(imageUrlAction.indexOf(CHARATER) + CHARATER.length());

		StringBuilder fullPath = new StringBuilder();
		fullPath.append(GemRealtyConst.DEFAULT_IMAGE_FOLDER);
		fullPath.append(File.separator);
		fullPath.append(basePath);

		File[] files = new File(fullPath.toString()).listFiles();
		// If this pathname does not denote a directory, then listFiles() returns null.

		List<List<String>> results = new ArrayList<>();
		StringBuilder tmpPath = new StringBuilder();
		List<String> tmp = new ArrayList<>();
		for (File file : files) {
			if (file.isFile()) {
				tmpPath.setLength(0);
				tmpPath.append("/images");
				tmpPath.append(basePath + "/");
				tmpPath.append(file.getName());

				if (tmp.size() == 5) {
					results.add(tmp);
					tmp = new ArrayList<String>();
				}
				tmp.add(tmpPath.toString());
			}
		}
		results.add(tmp);
		return results;
	}
}
