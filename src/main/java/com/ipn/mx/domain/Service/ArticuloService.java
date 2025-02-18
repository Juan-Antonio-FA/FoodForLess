package com.ipn.mx.domain.Service;

import com.ipn.mx.domain.Entity.Articulo;
import com.ipn.mx.domain.Entity.Categoria;
import com.ipn.mx.domain.Repository.ArticuloRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ArticuloService {
    private final ArticuloRepository articuloRepository;

    public void createArticulo(Articulo articulo) {
        // Create a new articulo
        articuloRepository.save(articulo);
    }

    public void updateArticulo(Articulo articulo) {
        // Update an existing articulo
        if(articulo.getIdArticulo() == null) {
            throw new IllegalArgumentException("El ID del cuerpo no puede ser nulo");
        }
        Articulo existingArticulo = articuloRepository.findById(articulo.getIdArticulo()).orElseThrow(() -> new EntityNotFoundException("Articulo no encontrado"));
        existingArticulo.setNombreArticulo(articulo.getNombreArticulo());
        existingArticulo.setDescripcionArticulo(articulo.getDescripcionArticulo());
        existingArticulo.setPrecioArticulo(articulo.getPrecioArticulo());
        existingArticulo.setStock(articulo.getStock());
        existingArticulo.setImagenArticulo(articulo.getImagenArticulo());
        existingArticulo.setTiempoInicial(articulo.getTiempoInicial());
        existingArticulo.setTiempoFinal(articulo.getTiempoFinal());

        existingArticulo.getDetallePedidos().clear();
        if(articulo.getDetallePedidos() != null){
            existingArticulo.getDetallePedidos().addAll(articulo.getDetallePedidos());
        }
        articuloRepository.save(existingArticulo);
    }

    public void deleteArticulo(Long id) {
        // Delete an existing articulo

        try {
            articuloRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("No se encontró el articulo con el ID proporcionado");
        }
    }

    public Articulo getArticulo(Long id) {
        // Get articulo by id
        return articuloRepository.findArticuloWithCategoria(id);
    }

    public List<Articulo> getAllArticulos() {
        // Get all articulos
        return articuloRepository.findAllArticulosWithCategoria();
    }

    public List<Articulo> getArticulosByCategoria(Long idCategoria) {
        // Get all articulos by categoria
        return articuloRepository.findByCategoriaIdCategoria(idCategoria);
    }

}
