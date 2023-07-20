package com.example.regesc.orm;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "professores")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String prontuario;
    @OneToMany(mappedBy = "professor", fetch = FetchType.EAGER)
    private Set<Disciplina> disciplinas;
    @Deprecated
    public Professor() {}

    public Professor(String nome, String prontuario) {
        this.nome = nome;
        this.prontuario = prontuario;
    }

    public Long getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProntuario() {
        return prontuario;
    }

    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }

    public Set<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(Set<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
    @PreRemove
    public void atualizaDisciplinasOnDelete() {
        System.out.println("PRE REMOVE");
        for (Disciplina disciplina: this.getDisciplinas()) {
            disciplina.setProfessor(null);
        }
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", prontuario='" + prontuario + '\'' +
                '}';
    }
}
