package br.com.hbsis.periodo;

import java.time.LocalDate;

public class PeriodoDTO {
    private Long id;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private LocalDate dataRetirada;
    private String descricao;
    private Long idFornecedor;

    public PeriodoDTO(){
    }

    public PeriodoDTO(Long id, LocalDate dataInicio, LocalDate dataFim, LocalDate dataRetirada, String descricao, Long idFornecedor) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataRetirada = dataRetirada;
        this.descricao = descricao;
        this.idFornecedor = idFornecedor;
    }

    public static PeriodoDTO of(Periodo periodo){
        return new PeriodoDTO(
                periodo.getId(),
                periodo.getDataInicio(),
                periodo.getDataFim(),
                periodo.getDataRetirada(),
                periodo.getDescricao(),
                periodo.getFornecedor().getId()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(LocalDate dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    @Override
    public String toString() {
        return "PeriodoDTO{" +
                "id=" + id +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", dataRetirada=" + dataRetirada +
                ", descricao='" + descricao + '\'' +
                ", idFornecedor=" + idFornecedor +
                '}';
    }
}
