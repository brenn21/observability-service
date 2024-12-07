package com.kembo.observability_service.controller;

import com.kembo.observability_service.dto.LoginRequest;
import com.kembo.observability_service.dto.LoginResponse;
import com.kembo.observability_service.exception.BankCardException;
import com.kembo.observability_service.jwt.JwtUtils;
import com.kembo.observability_service.model.BankCard;
import com.kembo.observability_service.repository.BankCardPaginationRepository;
import com.kembo.observability_service.service.BankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bankcard")
public class BankCardController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final BankCardService bankCardService;
    private final BankCardPaginationRepository bankCardPaginationRepository;

    public BankCardController(BankCardService bankCardService, BankCardPaginationRepository bankCardPaginationRepository) {
        this.bankCardService = bankCardService;
        this.bankCardPaginationRepository = bankCardPaginationRepository;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public List<BankCard> getAllCards() {
        return bankCardService.getBankCards();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public Optional<BankCard> getBankCardById(@PathVariable("id") Long id) throws BankCardException {
        return bankCardService.getBankCardById(id);
    }

    @PostMapping
    BankCard createBankCard(@RequestBody BankCard bankCard) {
        return bankCardService.createBankCard(bankCard);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } finally {

        }
//        catch (AuthenticationException exception) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("message", "Bad credentials");
//            map.put("status", false);
//            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
//        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(loginRequest.getUsername(), roles, jwtToken); /*userDetails.getUsername()*/
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update/{id}")
    public BankCard updateBankCard(@PathVariable("id") Long id, @RequestBody BankCard bankCard) throws BankCardException {
        return bankCardService.updateBankCard(id, bankCard);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    public String deleteBankCard(@PathVariable("id") Long id) throws BankCardException {
        return bankCardService.deleteBankCard(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public Page<BankCard> getAllCardsAdmin() {
        return bankCardPaginationRepository
                .findAll(
                        Pageable.ofSize(3).withPage(4)
                );
    }
}
