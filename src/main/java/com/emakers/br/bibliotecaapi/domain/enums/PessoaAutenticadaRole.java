package com.emakers.br.bibliotecaapi.domain.enums;

public enum PessoaAutenticadaRole {
    ADMIN("admin"),
    USER("user");

    private String role;

    PessoaAutenticadaRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
