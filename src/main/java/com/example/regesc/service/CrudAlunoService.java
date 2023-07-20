package com.example.regesc.service;

import com.example.regesc.orm.Aluno;
import com.example.regesc.orm.Disciplina;
import com.example.regesc.orm.Professor;
import com.example.regesc.repository.AlunoRepository;
import com.example.regesc.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudAlunoService {
    private AlunoRepository alunoRepository;

    public CrudAlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }
    @Transactional
    public void menu(Scanner scanner) {
        boolean isTrue = true;

        while (isTrue) {
            System.out.println("\nQual ação quer executar?");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1 - Cadastrar novo aluno");
            System.out.println("2 - Atualizar um aluno");
            System.out.println("3 - Visualizar todos alunos");
            System.out.println("4 - Deletar um aluno");
            System.out.println("5 - Visualizar um aluno");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> this.cadastrar(scanner);
                case 2 -> this.atualizar(scanner);
                case 3 -> this.visualizar();
                case 4 -> this.deletar(scanner);
                case 5 -> this.visualizarUm(scanner);
                default -> isTrue = false;
            }
        }
    }
    @Transactional
    private void visualizarUm(Scanner scanner) {
        System.out.print("Id do aluno: ");
        Long id = scanner.nextLong();

        Optional<Aluno> optional = this.alunoRepository.findById(id);


        if (optional.isPresent()) {
            Aluno aluno = optional.get();

            System.out.println("Nome: " + aluno.getNome());
            System.out.println("Disciplinas: ");
            if (aluno.getDisciplinas() != null) {
                for (Disciplina disciplina: aluno.getDisciplinas()) {
                    System.out.println(disciplina.getNome());
                }
            }

        }
    }

    private void cadastrar(Scanner scanner) {
        System.out.print("Nome do Aluno: ");
        String nome = scanner.next();

        System.out.println("Idade do aluno");
        Integer idade = scanner.nextInt();

        Aluno aluno = new Aluno();
        aluno.setIdade(idade);
        aluno.setNome(nome);
        this.alunoRepository.save(aluno);
        System.out.println("Aluno salvo no banco.\n");
    }

    private void atualizar(Scanner scanner) {
        System.out.print("Digite o Id do aluno a ser atualizado: ");
        Long id = scanner.nextLong();

        Optional<Aluno> optional = this.alunoRepository.findById(id);


        if (optional.isPresent()) {
            Aluno aluno = optional.get();
            System.out.print("Nome do Aluno: ");
            String nome = scanner.next();

            System.out.println("Idade do aluno");
            Integer idade = scanner.nextInt();

            System.out.print("Prontuário do professor: ");
            String prontuario = scanner.next();

            aluno.setNome(nome);
            aluno.setIdade(idade);
            alunoRepository.save(aluno);
            System.out.println("Aluno atualizado.");
        } else {
            System.out.println("O Id informado é inválido: " + id);
        }

    }

    private void visualizar() {
        Iterable<Aluno> alunos = this.alunoRepository.findAll();

        alunos.forEach(System.out::println);
    }

    private void deletar(Scanner scanner) {
        System.out.print("Digite o Id do aluno a ser deletado: ");
        Long id = scanner.nextLong();

        this.alunoRepository.deleteById(id);
        System.out.println("Aluno deletado.\n");
    }
}
