package com.marketplace.domain.port.in;

public interface AuthUseCase {
    String issueToken(String username, String password);
}
