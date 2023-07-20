package com.example.regesc.service;

import com.example.regesc.orm.Aluno;
import com.example.regesc.orm.Disciplina;
import com.example.regesc.orm.Professor;
import com.example.regesc.repository.AlunoRepository;
import com.example.regesc.repository.DisciplinaRepository;
import com.example.regesc.repository.ProfessorRepository;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSException;

import java.util.*;

@Service
public class CrudDisciplinaService {
    private DisciplinaRepository disciplinaRepository;
    private ProfessorRepository professorRepository;

    private AlunoRepository alunoRepository;

    public CrudDisciplinaService(DisciplinaRepository disciplinaRepository,
                                 ProfessorRepository professorRepository,
                                 AlunoRepository alunoRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;
    }
    public void menu(Scanner scanner) {
        boolean isTrue = true;

        while (isTrue) {
            System.out.println("\nQual ação quer executar?");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1 - Cadastrar nova disciplina");
            System.out.println("2 - Atualizar uma disciplina");
            System.out.println("3 - Visualizar todas disciplinas");
            System.out.println("4 - Deletar uma disciplina");
            System.out.println("5 - Matricular alunos");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> this.cadastrar(scanner);
                case 2 -> this.atualizar(scanner);
                case 3 -> this.visualizar();
                case 4 -> this.deletar(scanner);
                case 5 -> this.matricularAlunos(scanner);
                default -> isTrue = false;
            }
        }
    }

    private void cadastrar(Scanner scanner) {
        System.out.print("Nome da disciplina: ");
        String nome = scanner.next();

        System.out.print("Semestre da disciplina: ");
        Integer semestre = scanner.nextInt();

        System.out.print("Professor Id: ");
        Long professorId = scanner.nextLong();

        Optional<Professor> optional = professorRepository.findById(professorId);

        if (optional.isPresent()) {
            Professor professor = optional.get();

            Set<Aluno> alunos = this.matricular(scanner);

            Disciplina disciplina = new Disciplina(nome, semestre, professor);
            disciplina.setAlunos(alunos);
            disciplinaRepository.save(disciplina);
            System.out.println("Salvo.");
        } else {
            System.out.println("Professor ID inválido: " + professorId);
        }
    }

    private void atualizar(Scanner scanner) {
        System.out.print("Digite o Id da disciplina a ser atualizada: ");
        Long id = scanner.nextLong();

        Optional<Disciplina> disciplinaOptional = this.disciplinaRepository.findById(id);

        if (disciplinaOptional.isPresent()) {
            Disciplina disciplina = disciplinaOptional.get();

            System.out.print("Nome da disciplina: ");
            String nome = scanner.next();

            System.out.print("Semestre da disciplina: ");
            Integer semestre = scanner.nextInt();

            System.out.print("Professor Id: ");
            Long professorId = scanner.nextLong();

            Optional<Professor> optional = professorRepository.findById(professorId);

            if (optional.isPresent()) {
                Professor professor = optional.get();

                disciplina.setNome(nome);
                disciplina.setSemestre(semestre);
                disciplina.setProfessor(professor);

                disciplinaRepository.save(disciplina);
                System.out.println("Atualizado..");
            } else {
                System.out.println("Professor ID inválido: " + professorId);
            }
        } else {
            System.out.println("Disciplina ID inválido: " + id);
        }
    }

    private void visualizar() {
        Iterable<Disciplina> disciplinas = this.disciplinaRepository.findAll();

        disciplinas.forEach(System.out::println);
    }

    private void deletar(Scanner scanner) {
        System.out.print("Digite o Id da disciplina a ser deletada: ");
        Long id = scanner.nextLong();

        this.disciplinaRepository.deleteById(id);
        System.out.println("Disciplina deletada.\n");
    }

    private Set<Aluno> matricular(Scanner scanner) {
        boolean isTrue = true;
        Set<Aluno> alunos = new HashSet<>() {
        };

        while (isTrue) {
            System.out.println("Id do aluno a ser matriculado: ");
            Long alunoId = scanner.nextLong();

            if (alunoId > 0) {
                System.out.println("Aluno: " + alunoId);
                Optional<Aluno> optional = this.alunoRepository.findById(alunoId);
                if (optional.isPresent()) {
                    alunos.add(optional.get());
                } else {
                    System.out.println("Id desconhecido: " + alunoId);
                }
            } else {
                isTrue = false;
            }
        }
        return alunos;
    }

    private void matricularAlunos(Scanner scanner) {
        System.out.print("Digite o Id da disciplina para matricular alunos: ");
        Long id = scanner.nextLong();

        Optional<Disciplina> optionalDisciplina = this.disciplinaRepository.findById(id);

        if (optionalDisciplina.isPresent()) {
            Disciplina disciplina = optionalDisciplina.get();
            Set<Aluno> novosAlunos = this.matricular(scanner);
            disciplina.getAlunos().addAll(novosAlunos);
            this.disciplinaRepository.save(disciplina);
        } else {
            System.out.println("Id da disciplina inválido: " + id);
        }
    }
}

