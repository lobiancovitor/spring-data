package com.example.regesc.service;

import com.example.regesc.orm.Aluno;
import com.example.regesc.orm.Professor;
import com.example.regesc.repository.AlunoRepository;
import com.example.regesc.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class RelatorioService {
    private AlunoRepository alunoRepository;
    private ProfessorRepository professorRepository;

    public RelatorioService(AlunoRepository alunoRepository, ProfessorRepository professorRepository) {
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
    }

    public void menu(Scanner scanner) {
        boolean isTrue = true;

        while (isTrue) {
            System.out.println("\nQual relatório deseja?");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1 - Alunos por nome");
            System.out.println("2 - Alunos por nome e idade <=");
            System.out.println("3 - Alunos por nome e idade >=");
            System.out.println("4 - Alunos matriculados com nome e idade >=");
            System.out.println("5 - Professores Atribuidos");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> this.alunosPorNome(scanner);
                case 2 -> this.alunosPorNomeIdadeMenorIgual(scanner);
                case 3 -> this.alunosPorNomeIdadeMaiorIgual(scanner);
                case 4 -> this.alunosMatriculados(scanner);
                case 5 -> this.professoresAtribuidos(scanner);
                default -> isTrue = false;
            }
        }
    }

    private void alunosPorNome(Scanner scanner) {
        System.out.print("Nome do Aluno: ");
        String nome = scanner.next();

        List<Aluno> alunos = this.alunoRepository.findByNomeStartingWith(nome);

        alunos.forEach(System.out::println);
    }

    private void alunosPorNomeIdadeMenorIgual(Scanner scanner) {
        System.out.print("Nome do Aluno: ");
        String nome = scanner.next();

        System.out.println("Idade do aluno");
        Integer idade = scanner.nextInt();

        List<Aluno> alunos = this.alunoRepository.findByNomeStartingWithAndIdadeLessThanEqual(nome, idade);

        alunos.forEach(System.out::println);
    }

    private void alunosPorNomeIdadeMaiorIgual(Scanner scanner) {
        System.out.print("Nome do Aluno: ");
        String nome = scanner.next();

        System.out.println("Idade do aluno");
        Integer idade = scanner.nextInt();

        List<Aluno> alunos = this.alunoRepository.findNomeIdadeIgualOuMaior(nome, idade);

        alunos.forEach(System.out::println);
    }

    private void professoresAtribuidos(Scanner scanner) {
        System.out.print("Nome do Professor: ");
        String nomeProfessor = scanner.next();

        System.out.print("Nome da Disciplina: ");
        String nomeDisciplina = scanner.next();

        List<Professor> professores = this.professorRepository.findProfessorAtribuido(nomeProfessor, nomeDisciplina);

        professores.forEach(System.out::println);
    }

    private void alunosMatriculados(Scanner scanner) {
        System.out.print("Nome do Aluno: ");
        String nomeAluno = scanner.next();

        System.out.println("Idade do aluno");
        Integer idadeAluno = scanner.nextInt();

        System.out.print("Nome da Disciplina: ");
        String nomeDisciplina = scanner.next();

        List<Aluno> alunos = this.alunoRepository.findNomeIdadeIgualOuMaiorMatriculado(nomeAluno, idadeAluno, nomeDisciplina);

        alunos.forEach(System.out::println);
    }

}
