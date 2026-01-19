package com.bookrights.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookrights.dto.TransactionRequest;
import com.bookrights.model.CustomUserDetails;
import com.bookrights.util.TransactionService;

@RestController
public class TransactionController {
	
	private TransactionService transactionService;
	
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("/api/transaction")
	public ResponseEntity<?> startTransaction(@AuthenticationPrincipal CustomUserDetails buyer, @RequestBody TransactionRequest request){
		
		try {
			transactionService.startTransaction(buyer, request);
			
		} catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Transaction failed");
        }
		
		return null;
	}

}
