package org.example.programacaolinear;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.ArrayList;
import java.util.Collection;

public class ProgramacaoLinearExample {

    public static void main(String[] args) {

        /**
         * Problema exemplo
         * Uma fábrica produz cadeiras e mesas:
         *
         * Cada cadeira dá R$ 50 de lucro, cada mesa R$ 80
         * Cadeira usa 2h de trabalho, mesa usa 3h
         * Cadeira usa 4m² de madeira, mesa usa 6m²
         * Disponível: 100h de trabalho e 180m² de madeira
         *
         * Objetivo: maximizar o lucro
         */
        // Função objetivo: maximizar 50x + 80y
        LinearObjectiveFunction funcaoObjetivo = new LinearObjectiveFunction(
                new double[]{50, 80},
                0
        );

        Collection<LinearConstraint> restricoes = new ArrayList<>();

        // 2x + 3y <= 100 (horas)
        restricoes.add(new LinearConstraint(
                new double[]{2, 3},
                Relationship.LEQ,
                100
        ));

        // 4x + 6y <= 180 (madeira)
        restricoes.add(new LinearConstraint(
                new double[]{4, 6},
                Relationship.LEQ,
                180
        ));

        SimplexSolver solver = new SimplexSolver();

        try {
            PointValuePair solucao = solver.optimize(
                    funcaoObjetivo,
                    new LinearConstraintSet(restricoes),
                    GoalType.MAXIMIZE,
                    new NonNegativeConstraint(true)
            );

            double[] ponto = solucao.getPoint();
            double cadeiras = ponto[0];
            double mesas = ponto[1];

            System.out.println("=== SOLUÇÃO ÓTIMA ===");
            System.out.printf("Quantidade de cadeiras: %.2f%n", cadeiras);
            System.out.printf("Quantidade de mesas: %.2f%n", mesas);
            System.out.printf("Lucro máximo: R$ %.2f%n", solucao.getValue());

            System.out.println("\n=== VERIFICAÇÃO DAS RESTRIÇÕES ===");
            double horasUsadas = 2 * cadeiras + 3 * mesas;
            double madeiraUsada = 4 * cadeiras + 6 * mesas;

            System.out.printf("Horas usadas: %.2f / 100 (%.1f%%)%n",
                    horasUsadas, (horasUsadas/100)*100);
            System.out.printf("Madeira usada: %.2f / 180 (%.1f%%)%n",
                    madeiraUsada, (madeiraUsada/180)*100);

            // Análise de rentabilidade
            System.out.println("\n=== ANÁLISE ===");
            System.out.printf("Lucro por hora de trabalho - Cadeira: R$ %.2f%n", 50.0/2);
            System.out.printf("Lucro por hora de trabalho - Mesa: R$ %.2f%n", 80.0/3);
            System.out.printf("Lucro por m² de madeira - Cadeira: R$ %.2f%n", 50.0/4);
            System.out.printf("Lucro por m² de madeira - Mesa: R$ %.2f%n", 80.0/6);

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}