package com.example.regesc.service;

import com.example.regesc.orm.Disciplina;
import com.example.regesc.orm.Professor;
import com.example.regesc.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudProfessorService {
    private ProfessorRepository professorRepository;

    public CrudProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }
    @Transactional
    public void menu(Scanner scanner) {
        boolean isTrue = true;

        while (isTrue) {
            System.out.println("\nQual ação quer executar?");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1 - Cadastrar novo professor");
            System.out.println("2 - Atualizar um professor");
            System.out.println("3 - Visualizar todos professores");
            System.out.println("4 - Deletar um professor");
            System.out.println("5 - Visualizar um professor");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    this.cadastrar(scanner);
                    break;
                case 2:
                    this.atualizar(scanner);
                    break;
                case 3:
                    this.visualizar();
                    break;
                case 4:
                    this.deletar(scanner);
                    break;
                case 5:
                    this.visualizarUm(scanner);
                    break;
                default:
                    isTrue = false;
                    break;
            }
        }
    }
    @Transactional
    private void visualizarUm(Scanner scanner) {
        System.out.print("Id do professor: ");
        Long id = scanner.nextLong();

        Optional<Professor> optional = this.professorRepository.findById(id);


        if (optional.isPresent()) {
            Professor professor = optional.get();

            System.out.println("Nome: " + professor.getNome());
            System.out.println("Disciplinas: ");
            for (Disciplina disciplina: professor.getDisciplinas()) {
                System.out.println(disciplina.getNome());
            }

        }
    }

    private void cadastrar(Scanner scanner) {
        System.out.print("Nome do professor: ");
        String nome = scanner.next();

        System.out.print("Prontuário do professor: ");
        String prontuario = scanner.next();

        Professor professor = new Professor(nome, prontuario);
        this.professorRepository.save(professor);
        System.out.println("Professor salvo no banco.\n");
    }

    private void atualizar(Scanner scanner) {
        System.out.print("Digite o Id do professor a ser atualizado: ");
        Long id = scanner.nextLong();

        Optional<Professor> optional = this.professorRepository.findById(id);


        if (optional.isPresent()) {
            Professor professor = optional.get();
            System.out.print("Nome do professor: ");
            String nome = scanner.next();

            System.out.print("Prontuário do professor: ");
            String prontuario = scanner.next();

            professor.setNome(nome);
            professor.setProntuario(prontuario);
            professorRepository.save(professor);
            System.out.println("Professor atualizado.");
        } else {
            System.out.println("O Id informado é inválido: " + id);
        }

    }

    private void visualizar() {
        Iterable<Professor> professores = this.professorRepository.findAll();

        professores.forEach(System.out::println);
    }

    private void deletar(Scanner scanner) {
        System.out.print("Digite o Id do professor a ser deletado: ");
        Long id = scanner.nextLong();

        this.professorRepository.deleteById(id);
        System.out.println("Professor deletado.\n");
    }
}
