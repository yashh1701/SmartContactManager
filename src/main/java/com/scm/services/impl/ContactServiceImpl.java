package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entities.Contacts;
import com.scm.entities.User;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepo contactRepo;
	
	
	@Override
	public Contacts save(Contacts contact) {
		
		String contactId = UUID.randomUUID().toString();
		contact.setId(contactId);
		contactRepo.save(contact);
		
		return contact;
	}

	@Override
	public Contacts update(Contacts contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contacts> getAll() {
		
		return contactRepo.findAll();
	}

	@Override
	public Contacts getById(String id) {
		
		return contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not found with given id : " + id));
	}

	@Override
	public void delete(String id) {
		
		var contact = contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not found with given id : " + id));
		contactRepo.delete(contact);
	}

	@Override
	public Page<Contacts> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user) {
	
		Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
		return contactRepo.findByUserAndNameContaining(user,nameKeyword,pageable) ;
	}

	@Override
	public Page<Contacts> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order,
			User user) {
		
		Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
		return contactRepo.findByUserAndEmailContaining(user,emailKeyword,pageable) ;
	}

	@Override
	public Page<Contacts> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy,
			String order, User user) {
		
		Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
		return contactRepo.findByUserAndPhoneNumberContaining(user,phoneNumberKeyword,pageable) ;
	}

	@Override
	public List<Contacts> getByUserId(String userId) {
		
		return contactRepo.findByUserId(userId);
	}

	@Override
	public Page<Contacts> getByUser(User user, int page, int size, String sortBy, String direction) {
		
		Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUser(user, pageable);
	}
	
//	public List<Contacts> getByUser(User user) {
//		
//		return contactRepo.findByUser(user);
//	}

}
