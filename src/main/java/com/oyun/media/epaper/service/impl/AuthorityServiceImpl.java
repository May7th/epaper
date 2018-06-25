/**
 * 
 */
package com.oyun.media.epaper.service.impl;

import com.oyun.media.epaper.domain.Authority;
import com.oyun.media.epaper.repository.AuthorityRepository;
import com.oyun.media.epaper.service.IAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Authority 服务接口的实现.
 *
 * @author changzhen
 */
@Service
public class AuthorityServiceImpl implements IAuthorityService {

	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	public Authority getAuthorityById(Long id) {
		return authorityRepository.getOne(id);
	}

}
