package com.example.model;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType (propOrder={"id","cedula","nombre"})
public class Cliente implements Serializable {

	/**	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private String nombre;
	private String cedula;
    private Collection<Cuenta> cuentasCollection;
    
    public Cliente() {
    	
    }

    public Cliente(Integer id) {
        this.setId(id);
    }

    public Cliente(Integer id, String nombre, String cedula) {
        this.setId(id);
        this.setNombre(nombre);
        this.setCedula(cedula);        
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	@XmlTransient
    public Collection<Cuenta> getCuentasCollection() {
        return cuentasCollection;
    }

    public void setCuentasCollection(Collection<Cuenta> cuentasCollection) {
        this.cuentasCollection = cuentasCollection;
    }
    
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cliente[ id=" + id + " ]";
    }
}
