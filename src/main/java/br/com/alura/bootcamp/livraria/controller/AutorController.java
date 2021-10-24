package br.com.alura.bootcamp.livraria.controller;

import br.com.alura.bootcamp.livraria.dto.AutorDto;
import br.com.alura.bootcamp.livraria.dto.AutorFormDto;
import br.com.alura.bootcamp.livraria.service.AutorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

// RestController Serve para especificar que é um controller do tipo Rest, que não devolve páginas JSP e sim o formato padrão JSON
@RestController
@RequestMapping("/autores")
@Api(tags = "Autor")
public class AutorController {

    @Autowired
    private AutorService service;

    @GetMapping
    @ApiOperation("Listar autores")
    public Page<AutorDto> listar(@PageableDefault(size = 10) Pageable paginacao) {

        return service.listar(paginacao);
    }

    @PostMapping
    @ApiOperation("Cadastrar autor")
    public ResponseEntity<AutorDto> cadastrar(@RequestBody @Valid AutorFormDto dto, UriComponentsBuilder uriBuilder) {

        AutorDto autorDto = service.cadastrar(dto);

        URI uri = uriBuilder.path("/autores/{id}").buildAndExpand(autorDto.getId()).toUri();

        return ResponseEntity.created(uri).body(autorDto);

    }

}
