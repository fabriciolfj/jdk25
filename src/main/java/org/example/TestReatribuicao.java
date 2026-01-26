package org.example;

import java.util.ArrayList;
import java.util.List;

public class TestReatribuicao {

    static void main() {
        var test = new TestReatribuicao();

        List<String> minhaLista = new ArrayList<>();
        test.modificar(minhaLista);
        IO.println(minhaLista);

        var nome = "test";
        test.modificar(nome);

        IO.println(nome);

        var num = 10;
        test.modificar(num);
        IO.println(num);
    }

    public void modificar(List<String> dados) {
        dados.add("visivel");
        dados = new ArrayList<>();
        dados.add("invisivel");
    }

    public void modificar(String nome) {
        nome = "Fabrocio";
        nome = new String();
        nome = "casa";
    }
    public void modificar(int num) {
        num = 0;
    }
}
