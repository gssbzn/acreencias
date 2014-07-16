package com.example.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Cuenta implements Serializable {

	/**	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private BigDecimal saldo;
	private Cliente cliente;
	
	public Cuenta() {
    }

    public Cuenta(Integer id) {
        this.setId(id);
    }
    
    public Cuenta(Integer id, BigDecimal saldo) {
        this.setId(id);
        this.setSaldo(saldo);
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	@Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cuenta[ id=" + id + " ]";
    }

}
