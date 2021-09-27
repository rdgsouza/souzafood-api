package com.souza.souzafood.domain.exception;
public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PermissaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
    
    public PermissaoNaoEncontradaException(Long permissaoId) {
        this(String.format("Não existe um cadastro de permissão com código %d", permissaoId));
    }   
    
    
    public PermissaoNaoEncontradaException(Long grupoId, Long permissaoId) {
        this(String.format("Não existe um cadastro de grupo com código %d para a permissão de código %d", 
                grupoId, permissaoId));
    }
}