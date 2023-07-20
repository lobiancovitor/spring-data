package com.example.regesc;

import com.example.regesc.orm.Professor;
import com.example.regesc.repository.ProfessorRepository;
import com.example.regesc.service.CrudAlunoService;
import com.example.regesc.service.CrudDisciplinaService;
import com.example.regesc.service.CrudProfessorService;
import com.example.regesc.service.RelatorioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class RegescApplication implements CommandLineRunner {
	private CrudProfessorService professorService;
	private CrudDisciplinaService disciplinaService;
	private CrudAlunoService alunoService;

	private RelatorioService relatorioService;

	public RegescApplication(CrudProfessorService professorService,
							 CrudDisciplinaService disciplinaService,
							 CrudAlunoService alunoService,
							 RelatorioService relatorioService) {
		this.professorService = professorService;
		this.disciplinaService = disciplinaService;
		this.alunoService = alunoService;
		this.relatorioService = relatorioService;
	}

	public static void main(String[] args) {
		SpringApplication.run(RegescApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		boolean isTrue = true;
		Scanner scanner = new Scanner(System.in);

		while (isTrue) {
			System.out.println("Qual entidade deseja interagir?");
			System.out.println("0 - Sair");
			System.out.println("1 - Professor");
			System.out.println("2 - Disciplina");
			System.out.println("3 - Aluno");
			System.out.println("4 - Relatório");

			int opcao = scanner.nextInt();

			switch (opcao) {
				case 1 -> this.professorService.menu(scanner);
				case 2 -> this.disciplinaService.menu(scanner);
				case 3 -> this.alunoService.menu(scanner);
				case 4 -> this.relatorioService.menu(scanner);
				default -> isTrue = false;
			}
		}

	}
}
