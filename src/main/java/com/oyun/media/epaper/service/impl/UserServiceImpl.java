package com.oyun.media.epaper.service.impl;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import com.oyun.media.epaper.domain.User;
import com.oyun.media.epaper.repository.UserRepository;
import com.oyun.media.epaper.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * 用户服务接口实现.
 *
 * @author changzhen
 */
@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	
	@Transactional
	@Override
	public User saveOrUpateUser(User user) {
	    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String newPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(newPassword);
		return userRepository.save(user);
	}

	@Override
    public List<User> getAllUser(){
		List<User> userList = userRepository.findAll();
		return userList;
	}

	@Transactional
	@Override
	public User registerUser(User user) {
		return userRepository.save(user);
	}

	@Transactional
	@Override
	public void removeUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.getOne(id);
	}

	@Override
	public Page<User> listUsersByNameLike(String name, Pageable pageable) {
        // 模糊查询
        name = "%" + name + "%";
        Page<User> users = userRepository.findByNameLike(name, pageable);
        return users;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null){
			throw new UsernameNotFoundException("沒有当前用户！");
		}
		return user;
	}

	@Override
	public List<User> listUsersByUsernames(Collection<String> usernames) {
		return userRepository.findByUsernameIn(usernames);
	}

}
