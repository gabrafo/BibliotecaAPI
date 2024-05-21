package com.emakers.br.bibliotecaapi.data.enums;

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
