package io.github.charleskulkauski.domain.repositorio;

import io.github.charleskulkauski.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Clientes {
    //A interrogação serve para mostrar o que vai ser passado
    private static String INSERT = "insert into cliente (nome) values (?)";
    private static String SELECT_ALL ="select * from cliente";
    private static String UPDATE = "update cliente set nome= (?) where id = (?)";
    private static String DELETE = "delete from cliente where id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Salvar cliente
    public Cliente salvar(Cliente cliente){
        jdbcTemplate.update(INSERT, cliente.getNome());
        return cliente;
    }

    //Atualizar cliente
    public Cliente atualizar(Cliente cliente){
        jdbcTemplate.update(UPDATE, cliente.getNome(), cliente.getId());
        return cliente;
    }

    //Deletar cliente pelo cliente
    public void deletar(Cliente cliente){
        deletar(cliente.getId());

    }

    //Deletar pelo id
    public void deletar(Integer id){
    jdbcTemplate.update(DELETE, id);
    }

    public List<Cliente> buscarPorNome(String nome){
        return jdbcTemplate.query(
                //Concat = contatenar com algo existente
                SELECT_ALL.concat(" where nome like ? "),
                new Object[]{"%" + nome + "%"},
                getClientRowMapper());
    }

    public List<Cliente> obterTodos(){

        //RowMapper mapeia resultado do banco para uma lista dentro da classe
        return jdbcTemplate.query(SELECT_ALL, getClientRowMapper());
    }


    private static RowMapper<Cliente> getClientRowMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
                //rs= ResultSet que é o parâmetro do mapa

                //Get int pega o inteiro
                int id = rs.getInt("id");

                //Get String vai pegar a String dentro da coluna passada no parâmetro
                String nome = rs.getString("nome");

                return new Cliente(id, nome);
            }
        };
    }

    /*
    O código antes era assim:

      public List<Cliente> obterTodos(){

        //RowMapper mapeia resultado do banco para uma lista dentro da classe
        return jdbcTemplate.query(SELECT_ALL, new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
                //rs= ResultSet que é o parâmetro do mapa

                //Get int pega o inteiro
                int id= rs.getInt("id");

                //Get String vai pegar a String dentro da coluna passada no parâmetro
                String nome= rs.getString("nome");

                return new Cliente(id,nome);
            }
        });


        Foi selecionado a partir do new RowMapper até o resto do método.
        Após isso apertamos no ctrl+alt+m (ATALHO PARA OBTER MÉTODO) e transformamops em um método diferente para poder ser utilizado
        em várias classes, ao invés de uma demanda especifíca. Agora ele se torna um método de "lista"
    }*/


}
