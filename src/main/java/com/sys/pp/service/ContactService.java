package com.sys.pp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.pp.repo.ContactRepository;

@Service("ContactService")
public class ContactService {
	@Autowired
	private ContactRepository contactRes;

	public int registNewInd(String userId) {
		Integer maxInd = contactRes.getMaxIndByUserId(userId);
		if (maxInd != null)
			return maxInd + 1;
		return 1;
	}
}
