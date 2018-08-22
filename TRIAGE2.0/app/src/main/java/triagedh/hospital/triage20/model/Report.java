package triagedh.hospital.triage20.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class Report implements KvmSerializable {
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String sexo;
    private String fecha_nacimiento;
    private String descripcion_alergia;
    private String curp;
    private String numero_seguro;
    private String calle;
    private int numero;
    private String colonia;
    private String municipio;
    private String numero_telefono;
    private String enfermedad;
    private String sintoma;
    private String desc_dolor;
    private int fc;
    private int fr;
    private int temperatura;
    private int ta_sistolica;
    private int ta_diastolica;
    private int g_apertura_ocular;
    private int g_respuesta_motora;
    private int g_respuesta_verbal;
    private int g_total;
    private String estado;
    private String tipo_sangre;
    private int nivel_dolor;

    public Report(String nombre, String apellido_paterno, String apellido_materno, String sexo, String fecha_nacimiento, String descripcion_alergia, String curp, String numero_seguro, String calle, int numero, String colonia, String municipio, String numero_telefono, String enfermedad, String sintoma, String desc_dolor, int fc, int fr, int temperatura, int ta_sistolica, int ta_diastolica, int g_apertura_ocular, int g_respuesta_motora, int g_respuesta_verbal, int g_total, String estado, String tipo_sangre, int nivel_dolor) {
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.sexo = sexo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.descripcion_alergia = descripcion_alergia;
        this.curp = curp;
        this.numero_seguro = numero_seguro;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.municipio = municipio;
        this.numero_telefono = numero_telefono;
        this.enfermedad = enfermedad;
        this.sintoma = sintoma;
        this.desc_dolor = desc_dolor;
        this.fc = fc;
        this.fr = fr;
        this.temperatura = temperatura;
        this.ta_sistolica = ta_sistolica;
        this.ta_diastolica = ta_diastolica;
        this.g_apertura_ocular = g_apertura_ocular;
        this.g_respuesta_motora = g_respuesta_motora;
        this.g_respuesta_verbal = g_respuesta_verbal;
        this.g_total = g_total;
        this.estado = estado;
        this.tipo_sangre = tipo_sangre;
        this.nivel_dolor = nivel_dolor;
    }

    public Report() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getDescripcion_alergia() {
        return descripcion_alergia;
    }

    public void setDescripcion_alergia(String descripcion_alergia) {
        this.descripcion_alergia = descripcion_alergia;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNumero_seguro() {
        return numero_seguro;
    }

    public void setNumero_seguro(String numero_seguro) {
        this.numero_seguro = numero_seguro;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getNumero_telefono() {
        return numero_telefono;
    }

    public void setNumero_telefono(String numero_telefono) {
        this.numero_telefono = numero_telefono;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getSintoma() {
        return sintoma;
    }

    public void setSintoma(String sintoma) {
        this.sintoma = sintoma;
    }

    public String getDesc_dolor() {
        return desc_dolor;
    }

    public void setDesc_dolor(String desc_dolor) {
        this.desc_dolor = desc_dolor;
    }

    public int getFc() {
        return fc;
    }

    public void setFc(int fc) {
        this.fc = fc;
    }

    public int getFr() {
        return fr;
    }

    public void setFr(int fr) {
        this.fr = fr;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public int getTa_sistolica() {
        return ta_sistolica;
    }

    public void setTa_sistolica(int ta_sistolica) {
        this.ta_sistolica = ta_sistolica;
    }

    public int getTa_diastolica() {
        return ta_diastolica;
    }

    public void setTa_diastolica(int ta_diastolica) {
        this.ta_diastolica = ta_diastolica;
    }

    public int getG_apertura_ocular() {
        return g_apertura_ocular;
    }

    public void setG_apertura_ocular(int g_apertura_ocular) {
        this.g_apertura_ocular = g_apertura_ocular;
    }

    public int getG_respuesta_motora() {
        return g_respuesta_motora;
    }

    public void setG_respuesta_motora(int g_respuesta_motora) {
        this.g_respuesta_motora = g_respuesta_motora;
    }

    public int getG_respuesta_verbal() {
        return g_respuesta_verbal;
    }

    public void setG_respuesta_verbal(int g_respuesta_verbal) {
        this.g_respuesta_verbal = g_respuesta_verbal;
    }

    public int getG_total() {
        return g_total;
    }

    public void setG_total(int g_total) {
        this.g_total = g_total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo_sangre() {
        return tipo_sangre;
    }

    public void setTipo_sangre(String tipo_sangre) {
        this.tipo_sangre = tipo_sangre;
    }

    public int getNivel_dolor() {
        return nivel_dolor;
    }

    public void setNivel_dolor(int nivel_dolor) {
        this.nivel_dolor = nivel_dolor;
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return nombre;
            case 1:
                return apellido_paterno;
            case 2:
                return apellido_materno;
            case 3:
                return sexo;
            case 4:
                return fecha_nacimiento;
            case 5:
                return descripcion_alergia;
            case 6:
                return curp;
            case 7:
                return numero_seguro;
            case 8:
                return calle;
            case 9:
                return numero;
            case 10:
                return colonia;
            case 11:
                return municipio;
            case 12:
                return numero_telefono;
            case 13:
                return enfermedad;
            case 14:
                return sintoma;
            case 15:
                return desc_dolor;
            case 16:
                return fc;
            case 17:
                return fr;
            case 18:
                return temperatura;
            case 19:
                return ta_sistolica;
            case 20:
                return ta_diastolica;
            case 21:
                return g_apertura_ocular;
            case 22:
                return g_respuesta_motora;
            case 23:
                return g_respuesta_verbal;
            case 24:
                return g_total;
            case 25:
                return estado;
            case 26:
                return tipo_sangre;
            case 27:
                return nivel_dolor;

        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int i, Object o) {

        switch (i) {
            case 0:
            nombre= o.toString();
                break;
            case 1:
            apellido_paterno = o.toString();
                break;
            case 2:
            apellido_materno = o.toString();
                break;
            case 3:
            sexo = o.toString();
                break;
            case 4:
            fecha_nacimiento = o.toString();
                break;
            case 5:
            descripcion_alergia = o.toString();
                break;
            case 6:
            curp = o.toString();
                break;
            case 7:
            numero_seguro = o.toString();
                break;
            case 8:
            calle = o.toString();
                break;
            case 9:
            numero = Integer.parseInt(o.toString());
                break;
            case 10:
            colonia = o.toString();
                break;
            case 11:
            municipio = o.toString();
                break;
            case 12:
            numero_telefono = o.toString();
                break;
            case 13:
            enfermedad = o.toString();
                break;
            case 14:
            sintoma = o.toString();
                break;
            case 15:
            desc_dolor = o.toString();
                break;
            case 16:
            fc = Integer.parseInt(o.toString());
                break;
            case 17:
            fr =Integer.parseInt(o.toString());
                break;
            case 18:
            temperatura = Integer.parseInt(o.toString());
                break;
            case 19:
            ta_sistolica = Integer.parseInt(o.toString());
                break;
            case 20:
            ta_diastolica = Integer.parseInt(o.toString());
                break;
            case 21:
            g_apertura_ocular = Integer.parseInt(o.toString());
                break;
            case 22:
            g_respuesta_motora = Integer.parseInt(o.toString());
                break;
            case 23:
            g_respuesta_verbal = Integer.parseInt(o.toString());
                break;
            case 24:
            g_total = Integer.parseInt(o.toString());
                break;
            case 25:
                estado = o.toString();
                break;
            case 26:
                tipo_sangre = o.toString();
                break;
            case 27:
                nivel_dolor = Integer.parseInt(o.toString());
                break;
        }

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}
