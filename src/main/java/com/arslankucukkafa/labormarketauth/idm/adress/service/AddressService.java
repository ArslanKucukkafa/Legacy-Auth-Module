package com.arslankucukkafa.labormarketauth.idm.adress.service;

import com.arslankucukkafa.labormarketauth.idm.adress.model.AddressModel;
import com.arslankucukkafa.labormarketauth.idm.adress.repository.AddressRepository;
import com.arslankucukkafa.labormarketauth.idm.user.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    private static final Log logger = LogFactory.getLog(AddressService.class);
    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Optional<AddressModel> getAddressWithId(String id) {
            return addressRepository.findById(id);
    }

    public AddressModel saveAddress(AddressModel addressModel) {
        try {
            return addressRepository.save(addressModel);
        } catch (Exception e) {
            logger.error("Error occured while saving address: " + e.getMessage());
            return null;
        }
    }
}
