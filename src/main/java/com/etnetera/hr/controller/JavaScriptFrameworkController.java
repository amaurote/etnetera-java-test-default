package com.etnetera.hr.controller;

import com.etnetera.hr.dto.JavaScriptFrameworkRequestDTO;
import com.etnetera.hr.exception.JavaScriptFrameworkException;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.etnetera.hr.data.JavaScriptFramework;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Simple REST controller for accessing application logic.
 *
 * @author Etnetera
 */
@RestController
@RequestMapping("/framework")
@AllArgsConstructor
public class JavaScriptFrameworkController {

    private final JavaScriptFrameworkService service;

    @GetMapping
    public ResponseEntity<Iterable<JavaScriptFramework>> frameworks() {
        return ResponseEntity.ok(service.getAllFrameworks());
    }

    @PostMapping
    public ResponseEntity<?> addNewFramework(
            @RequestBody @Valid JavaScriptFrameworkRequestDTO dto) {

        try {
            return ResponseEntity.ok(service.addNewFramework(dto));
        } catch (JavaScriptFrameworkException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateFramework(
            @PathVariable("id") @NotNull Long id,
            @RequestBody @Valid JavaScriptFrameworkRequestDTO dto) {

        try {
            return ResponseEntity.ok(service.updateFramework(id, dto));
        } catch (JavaScriptFrameworkException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteFramework(
            @PathVariable("id") @NotNull Long id) {

        try {
            service.deleteFramework(id);
            return ResponseEntity.noContent().build();
        } catch (JavaScriptFrameworkException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

}
