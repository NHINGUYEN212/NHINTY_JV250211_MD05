package com.ra.md05_session09.security;

import com.ra.md05_session09.model.entity.Customer;
import com.ra.md05_session09.service.CustomerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomerDetailService implements UserDetailsService {
    private final CustomerService customerService;

    public CustomerDetailService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerService.findByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException(email);
        } else {
            Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + customer.getRole().toString()));
            return CustomerPrincipal.builder()
                    .customer(customer)
                    .authorities(authorities)
                    .build();
        }
    }
}
