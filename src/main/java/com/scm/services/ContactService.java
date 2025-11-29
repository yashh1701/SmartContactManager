package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.scm.entities.Contacts;
import com.scm.entities.User;
	
public interface ContactService {

	
    // save contacts
    Contacts save(Contacts contact);

    // update contact
    Contacts update(Contacts contact);

    // get contacts
    List<Contacts> getAll();

    // get contact by id

    Contacts getById(String id);

    // delete contact

    void delete(String id);

    // search contact
    Page<Contacts> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user);

    Page<Contacts> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);

    Page<Contacts> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order,
            User user);

    // get contacts by userId
    List<Contacts> getByUserId(String userId);

    Page<Contacts> getByUser(User user, int page, int size, String sortField, String sortDirection);
//    List<Contacts> getByUser(User user);

}
