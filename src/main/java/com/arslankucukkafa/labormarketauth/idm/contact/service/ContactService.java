package com.arslankucukkafa.labormarketauth.idm.contact.service;

import com.arslankucukkafa.labormarketauth.idm.contact.model.ContactModel;
import com.arslankucukkafa.labormarketauth.idm.contact.repository.ContactRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

// FIXME: Contact<template_name> adı biraz karışık olmuş çünkü daha çok message, notification, alert gibi bir şeyi çağrıştırıyor. Düşünülmesi lazım
@Service
public class ContactService {

    private static final Log logger = LogFactory.getLog(ContactService.class);

    private ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Optional<ContactModel>getContactWithProperties(ContactModel contactModel) {
        return contactRepository.findByContactInfo(contactModel.getEmail(), contactModel.getPhone(), contactModel.getGithub_url(), contactModel.getAvatar_url());
    }

    public Optional<ContactModel>getContactWithId(String contactId) {
        return contactRepository.findById(contactId);
    }

    public ContactModel saveContact(ContactModel contactModel) {
        try {
            return contactRepository.save(contactModel);
        } catch (Exception e) {
            logger.error("Error occured while saving contact: " + e.getMessage());
            return null;
        }
    }


    // FIXME: burada phone, email, github gibi adresler ile de arama yapılabilmeli. Bunu nasıl generic bir şekilde yapaabiliriz?


}
