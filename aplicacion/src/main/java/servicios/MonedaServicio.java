package monedas.api.aplicacion.servicios;

import java.util.List;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import monedas.api.dominio.entidades.*;
import monedas.api.core.servicios.*;
import monedas.api.infraestructura.repositorios.*;

@Service
public class MonedaServicio implements IMonedaServicio {

private IMonedaRepositorio repositorio;
private ICambioMonedaRepositorio repositorioCambio;

public MonedaServicio(IMonedaRepositorio repositorio,
                        ICambioMonedaRepositorio repositorioCambio){
    this.repositorio=repositorio;
    this.repositorioCambio=repositorioCambio;
}

    @Override
    public List<Moneda> listar(){
        return repositorio.findAll();
     }

    @Override
    public Moneda obtener(Long id){
        var moneda = repositorio.findById(id);
        return moneda.isEmpty()? null : moneda.get();
     }

    @Override
    public List<Moneda> buscar(String nombre){ 
        return repositorio.buscar(nombre);
    }

    @Override
    public Moneda buscarPorPais(String nombre){ 
        return repositorio.buscarPorPais(nombre);
    }

    @Override
    public Moneda agregar(Moneda moneda){ 
        moneda.setId(0);
        var monedaGuardada = repositorio.save(moneda);
        MetricaServicio.publicarMetrica("RegistroAgregado", 1.0);
        return monedaGuardada;
    }

    @Override
    public Moneda modificar(Moneda moneda){ 
        Optional<Moneda> monedaEncontrado = repositorio.findById(moneda.getId());
        if (!monedaEncontrado.isEmpty()) {
            var monedaGuardada = repositorio.save(moneda);
            MetricaServicio.publicarMetrica("RegistroModificado", 1.0);
            return monedaGuardada;
        } else {
            return null;
        }
    }

    @Override
    public boolean eliminar(Long id){ 
        try {
            repositorio.deleteById(id);
            MetricaServicio.publicarMetrica("RegistroEliminado", 1.0);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
	
    @Override
	public List<CambioMoneda> listarPorPeriodo(long idMoneda, Date fecha1, Date fecha2){ 
        return repositorioCambio.listarPorPeriodo(idMoneda, fecha1, fecha2);
    }

}
