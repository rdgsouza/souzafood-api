package com.souza.souzafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.souza.souzafood.domain.model.Cidade;

//@Repository --> Sobre a anotação @Repository
//Precisa mesmo do @Repository?
//Enquanto estiver usando JpaRepository você não precisará anotar a interface com @Repository.
//JpaRepository é apenas uma interface e a implementação concreta é criada dinamicamente como um objeto proxy pelo Spring e, por exemplo, as exceções JDBC são tratadas lá.
//Então, você só precisa usar @Repository ao criar um DAO personalizado, para que o Spring crie um Bean e lide com as exceções corretamente.
//Sobre ciar um DAO personalizado. Aula: https://app.algaworks.com/aulas/1884/implementando-um-repositorio-sdj-customizado
//Fonte da resposta sobre: Precisa mesmo do @Repository? --> https://app.algaworks.com/forum/topicos/79906/precisa-mesmo-do-repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>{

}
