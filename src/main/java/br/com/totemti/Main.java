package br.com.totemti;

import java.util.*;
import java.util.function.Predicate;

public class Main {

    private static final String[] preposicoes = {
            "a","ante","ate","apos","com","contra","de","desde","desde","em","entre",
            "para","pra","perante","por","per","sem","sob","sobre","tras","da","do"
    };
    private static final String[] artigos = {
            "o","os","a","as","um","uns","uma","umas"
    };
    private static final String[] conjuncoes = { "e" };


    private static String gerarIdentificador(String frase) {

        //verificar se a frase é null
        if (frase == null) {
            return null;
        }

        //dividindo a frase em palavras
        String[] palavras = frase.trim().split("\\s+");

        //retorna o valor vazio,
        if(frase.trim().isEmpty()) {
            return "";
        }

        StringBuilder identificador = new StringBuilder();

        for (int i = 0; i < palavras.length; i++) {

            if (i == 0 ) {
                identificador.append(palavras[i].toLowerCase());
            } else if ( i == palavras.length -1) {
                identificador.append(palavras[i].substring(0, 1).toUpperCase()).append(palavras[i].substring(1));
            } else {
                String palavra = palavras[i].toLowerCase();
                if (
                        !Arrays.asList(preposicoes).contains(palavra)
                        && !Arrays.asList(artigos).contains(palavra)
                        && !Arrays.asList(conjuncoes).contains(palavra)
                ) {
                    identificador.append(palavras[i].substring(0, 1).toUpperCase()).append(palavras[i].substring
                            (1, Math.min(palavras[i].length(), 4)).toLowerCase());
                }
            }
        }

        return identificador.append("_t").toString();
    }

    public static void main(final String[] args) {
        verificarPreCondicoes(
                gerarIdentificador(null),
                Objects::isNull,
                "O valor gerado deve ser nulo"
        );
        verificarPreCondicoes(
                gerarIdentificador(""),
                String::isEmpty,
                "O valor gerado deve estar vazio"
        );
        verificarPreCondicoes(
                gerarIdentificador("Dia inicial da prestação de contas"),
                t -> t.equals("diaInicPresContas_t"),
                "O valor gerado deve ser igual a diaInicPresContas_t"
        );
        verificarPreCondicoes(
                gerarIdentificador("numero"),
                t -> t.equals("numero_t"),
                "O valor gerado deve ser igual a numero_t"
        );
        verificarPreCondicoes(
                gerarIdentificador("Informações alteradas do Dependente"),
                t -> t.equals("informaçõesAlteDependente_t"),
                "O valor gerado deve ser igual a informaçõesAlteDependente_t"
        );
    }


    private static void verificarPreCondicoes(
            String identificadorGerado,
            Predicate<String> condition,
            String mensagem
    ) {
        boolean test = condition.test(identificadorGerado);
        if (!test) {
            System.err.println(mensagem + " ❌");
        }
        else {
            System.out.println("Sucesso ✅");
        }
    }

}
